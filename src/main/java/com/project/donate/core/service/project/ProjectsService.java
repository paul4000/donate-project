package com.project.donate.core.service.project;

import com.project.donate.core.Web3jServiceSupplier;
import com.project.donate.core.auth.SecurityService;
import com.project.donate.core.auth.UserService;
import com.project.donate.core.blockchain.ProjectHashStore;
import com.project.donate.core.exceptions.ProjectRetrievalException;
import com.project.donate.core.exceptions.blockchain.ContractAddressNotFoundException;
import com.project.donate.core.exceptions.blockchain.ConvertProjectDataException;
import com.project.donate.core.helpers.ProjectContractTypesConverter;
import com.project.donate.core.helpers.ProjectHasher;
import com.project.donate.core.helpers.PropertiesUtils;
import com.project.donate.core.helpers.WalletHelper;
import com.project.donate.core.models.Project;
import com.project.donate.core.repositories.ProjectRepository;
import org.apache.commons.codec.DecoderException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;
import org.web3j.crypto.Credentials;
import org.web3j.tx.exceptions.ContractCallException;
import org.web3j.tx.gas.DefaultGasProvider;

import java.io.IOException;
import java.util.Optional;

@Service
public class ProjectsService {

    private ProjectRepository projectRepository;
    private Web3jServiceSupplier web3jServiceSupplier;
    private UserService userService;
    private SecurityService securityService;

    private final Logger logger = Logger.getLogger(ProjectsService.class);
    private final String PROJECT_ADDRESS_PROPERTY = "address.contract.projectHashStore";

    @Autowired
    public ProjectsService(ProjectRepository projectRepository, Web3jServiceSupplier web3jServiceSupplier,
                           UserService userService, SecurityService securityService) {

        Assert.notNull(projectRepository, "ProjectRepository should not be null");
        Assert.notNull(web3jServiceSupplier, "Web3jServiceSupplier should not be null");
        Assert.notNull(userService, "UserService should not be null");
        Assert.notNull(securityService, "SecurityService should not be null");

        this.securityService = securityService;
        this.userService = userService;
        this.web3jServiceSupplier = web3jServiceSupplier;
        this.projectRepository = projectRepository;
    }

    public Optional<Project> registerProjectInDatabase(MultipartFile projectFile, String summary, String name) {

        Optional<Project> savedProject = Optional.empty();

        try {

            Project project = new Project();
            project.setName(name);
            project.setDataType(projectFile.getContentType());
            project.setSummary(summary);
            project.setData(projectFile.getBytes());

            savedProject = Optional.of(projectRepository.save(project));

        } catch (IOException ex) {
            logger.warn("Can't extract bytes from project file.");
        }

        return savedProject;
    }

    public Project getProject(long id) {

        return projectRepository.findById(id)
                .orElseThrow(ProjectRetrievalException::new);
    }

    public void registerInBlockchain(String passwordToWallet, long projectId){

        Project project = getProject(projectId);

        String loggedInUsername = securityService.findLoggedInUsername();

        String walletName = userService.getWalletName(loggedInUsername);

        Credentials userWalletCredentials = WalletHelper.getCredentialsFromWallet(passwordToWallet, walletName);

        String hashedProject = ProjectHasher.hashProject(project.getData());

        addToContract(userWalletCredentials, hashedProject, projectId);

    }

    private void addToContract(Credentials userWalletCredentials, String hashedProject, long projectId) {

        Optional<String> contractAddress = PropertiesUtils.getPropertyFromConfig(PROJECT_ADDRESS_PROPERTY);

        if(contractAddress.isEmpty()) throw new ContractAddressNotFoundException();

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
