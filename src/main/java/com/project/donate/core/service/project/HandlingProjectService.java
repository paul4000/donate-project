package com.project.donate.core.service.project;

import com.project.donate.core.Web3jServiceSupplier;
import com.project.donate.core.auth.SecurityService;
import com.project.donate.core.auth.UserService;
import com.project.donate.core.blockchain.ProjectHashStore;
import com.project.donate.core.exceptions.blockchain.ContractAddressNotFoundException;
import com.project.donate.core.exceptions.blockchain.ConvertProjectDataException;
import com.project.donate.core.exceptions.blockchain.OpeningProjectException;
import com.project.donate.core.helpers.ProjectContractTypesConverter;
import com.project.donate.core.helpers.ProjectHasher;
import com.project.donate.core.helpers.PropertiesUtils;
import com.project.donate.core.model.Project;
import com.project.donate.core.repositories.ProjectRepository;
import org.apache.commons.codec.DecoderException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.web3j.crypto.Credentials;
import org.web3j.tx.exceptions.ContractCallException;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class HandlingProjectService extends AbstractProjectService {

    private ProjectRepository projectRepository;
    private Web3jServiceSupplier web3jServiceSupplier;
    private ProjectsService projectsService;

    private final String PROJECT_ADDRESS_PROPERTY = "address.contract.projectHashStore";

    public HandlingProjectService(ProjectRepository projectRepository, Web3jServiceSupplier web3jServiceSupplier,
                                  UserService userService, SecurityService securityService, ProjectsService projectsService) {

        super(userService, securityService);

        Assert.notNull(projectsService, "ProjectsService should not be null");
        Assert.notNull(web3jServiceSupplier, "Web3jServiceSupplier should not be null");
        Assert.notNull(projectRepository, "ProjectRepository should not be null");


        this.projectRepository = projectRepository;
        this.web3jServiceSupplier = web3jServiceSupplier;
        this.projectsService = projectsService;
    }

    public String openProject(String passwordToWallet, long projectId, String goal) {


        BigDecimal goalWei = Convert.toWei(goal, Convert.Unit.ETHER);

        Credentials userWalletCredentials = getCredentials(passwordToWallet);

        try {
            com.project.donate.core.blockchain.Project project = com.project.donate.core.blockchain.Project.deploy(web3jServiceSupplier.getWeb3j(), userWalletCredentials,
                    new DefaultGasProvider(), String.valueOf(projectId),
                    goalWei.toBigInteger()).send();

            Project byId = projectsService.getProject(projectId);

            byId.setAddress(project.getContractAddress());
            byId.setOpened(true);
            projectRepository.save(byId);

            return project.getContractAddress();

        } catch (Exception e) {
            e.printStackTrace();
            throw new OpeningProjectException();
        }
    }

    public void registerInBlockchain(String passwordToWallet, long projectId) {

        Project project = projectsService.getProject(projectId);

        Credentials userWalletCredentials = getCredentials(passwordToWallet);

        String hashedProject = ProjectHasher.hashProject(project.getData());

        addToContract(userWalletCredentials, hashedProject, projectId);
    }


    private void addToContract(Credentials userWalletCredentials, String hashedProject, long projectId) {

        Optional<String> contractAddress = PropertiesUtils.getPropertyFromConfig(PROJECT_ADDRESS_PROPERTY);

        if (contractAddress.isEmpty()) throw new ContractAddressNotFoundException();

        ProjectHashStore projectHashStore = ProjectHashStore.load(contractAddress.get(), web3jServiceSupplier.getWeb3j(),
                userWalletCredentials, new DefaultGasProvider());

        try {
            byte[] idBytes = ProjectContractTypesConverter.convertProjectId(projectId);
            byte[] projectBytes = ProjectContractTypesConverter.convertProjectHash(hashedProject);

            projectHashStore.registerProject(idBytes, projectBytes).send();

        } catch (DecoderException e) {
            e.printStackTrace();
            throw new ConvertProjectDataException();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ContractCallException("ProjectHashStore contract register project has problems");
        }

    }

}
