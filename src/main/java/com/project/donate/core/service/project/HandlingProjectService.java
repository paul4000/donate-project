package com.project.donate.core.service.project;

import com.project.donate.core.Web3jServiceSupplier;
import com.project.donate.core.auth.SecurityService;
import com.project.donate.core.auth.UserService;
import com.project.donate.core.blockchain.ProjectHashStore;
import com.project.donate.core.exceptions.blockchain.ContractAddressNotFoundException;
import com.project.donate.core.exceptions.blockchain.ConvertProjectDataException;
import com.project.donate.core.exceptions.blockchain.HandlingProjectException;
import com.project.donate.core.exceptions.blockchain.OpeningProjectException;
import com.project.donate.core.helpers.ProjectContractTypesConverter;
import com.project.donate.core.helpers.ProjectHasher;
import com.project.donate.core.helpers.PropertiesUtils;
import com.project.donate.core.model.Project;
import com.project.donate.core.repositories.ProjectRepository;
import com.project.donate.core.service.accounts.AccountsService;
import org.apache.commons.codec.DecoderException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.exceptions.ContractCallException;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

@Service
public class HandlingProjectService extends AbstractProjectService {


    private final static Logger logger = Logger.getLogger(HandlingProjectService.class);
    private AccountsService accountsService;

    public HandlingProjectService(ProjectRepository projectRepository, Web3jServiceSupplier web3jServiceSupplier,
                                  UserService userService, SecurityService securityService, ProjectsService projectsService, AccountsService accountsService) {

        super(userService, securityService, projectsService, web3jServiceSupplier);

        Assert.notNull(projectRepository, "ProjectRepository should not be null");
        Assert.notNull(accountsService, "AccountsService should not be null");

        this.accountsService = accountsService;
    }

    public String openProject(String passwordToWallet, long projectId, String goal) {


        BigDecimal goalWei = Convert.toWei(goal, Convert.Unit.ETHER);

        Credentials userWalletCredentials = getCredentials(passwordToWallet);

        try {
            com.project.donate.core.blockchain.Project project = com.project.donate.core.blockchain.Project.deploy(getWeb3jServiceSupplier().getWeb3j(), userWalletCredentials,
                    new DefaultGasProvider(), String.valueOf(projectId),
                    goalWei.toBigInteger()).send();

            Project byId = getProjectsService().getProject(projectId);

            byId.setAddress(project.getContractAddress());
            byId.setOpened(true);
            byId.setOpeningDate(new Date());
            getProjectsService().getProjectRepository().save(byId);

            return project.getContractAddress();

        } catch (Exception e) {
            e.printStackTrace();
            throw new OpeningProjectException();
        }
    }

    public void registerInBlockchain(String passwordToWallet, long projectId) {

        Project project = getProjectsService().getProject(projectId);

        Credentials userWalletCredentials = getCredentials(passwordToWallet);

        String hashedProject = ProjectHasher.hashProject(project.getData(), project.getSummary());

        addToContract(userWalletCredentials, hashedProject, project.getAddress());
    }


    private void addToContract(Credentials userWalletCredentials, String hashedProject, String projectAddress) {

        String PROJECT_ADDRESS_PROPERTY = "address.contract.projectHashStore";
        Optional<String> contractAddress = PropertiesUtils.getPropertyFromConfig(PROJECT_ADDRESS_PROPERTY);

        if (contractAddress.isEmpty()) throw new ContractAddressNotFoundException();

        ProjectHashStore projectHashStore = ProjectHashStore.load(contractAddress.get(), getWeb3jServiceSupplier().getWeb3j(),
                userWalletCredentials, new DefaultGasProvider());

        try {
            byte[] projectBytes = ProjectContractTypesConverter.convertProjectHash(hashedProject);

            projectHashStore.registerProject(projectBytes, projectAddress).send();

        } catch (DecoderException e) {
            e.printStackTrace();
            throw new ConvertProjectDataException();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ContractCallException("ProjectHashStore contract register project has problems");
        }

    }

    public void addExecutors(String executor, long projectId, double amountForDistribution, String passwordToWallet) {

        accountsService.validateIfChosenAddressIsExecutor(executor);

        Credentials userWalletCredentials = getCredentials(passwordToWallet);

        com.project.donate.core.blockchain.Project projectFromBlockchain = loadProjectFromBlockchain(projectId, userWalletCredentials);

        BigDecimal goalWei = Convert.toWei(String.valueOf(amountForDistribution), Convert.Unit.ETHER);

        try {

            TransactionReceipt transactionReceipt = projectFromBlockchain.addExecutor(executor, goalWei.toBigInteger()).send();

            logger.info(transactionReceipt.toString());

        } catch (Exception e) {
            e.printStackTrace();
            throw new HandlingProjectException("Error while submitting executors");
        }
    }

    public void openValidationPhase(String passwordToWallet, long projectId) {

        Credentials userWalletCredentials = getCredentials(passwordToWallet);
        com.project.donate.core.blockchain.Project projectFromBlockchain = loadProjectFromBlockchain(projectId, userWalletCredentials);


        try {
            TransactionReceipt transactionReceipt = projectFromBlockchain.openValidationPhase().send();
            getProjectsService().changeValidationPhase(projectId, true);
            getProjectsService().changeOpenedStatus(projectId, false);

            logger.info(transactionReceipt.toString());

        } catch (Exception e) {
            e.printStackTrace();
            throw new HandlingProjectException("Error while opening validation phase");
        }

    }

    public boolean executeAndCloseProject(String passwordToWallet, long projectId) {

        Credentials userWalletCredentials = getCredentials(passwordToWallet);

        com.project.donate.core.blockchain.Project projectFromBlockchain = loadProjectFromBlockchain(projectId, userWalletCredentials);

        try {

            TransactionReceipt transactionReceipt = projectFromBlockchain.closeValidatingPhase().send();
            logger.info(transactionReceipt.toString());

            getProjectsService().changeValidationPhase(projectId, false);

            TransactionReceipt transactionReceiptExecution = projectFromBlockchain.executeProject().send();
            logger.info(transactionReceiptExecution.toString());

            Boolean projectExecutionResult = projectFromBlockchain.getIfProjectExecutionSuccess().send();
            getProjectsService().changeExecutionStatus(projectId, projectExecutionResult);
            getProjectsService().setCloseDate(projectId);

            return projectExecutionResult;

        } catch (Exception e) {
            e.printStackTrace();
            throw new HandlingProjectException("Error while executing project");
        }
    }
}
