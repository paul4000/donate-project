package com.project.donate.core.service.project;

import com.project.donate.core.Web3jServiceSupplier;
import com.project.donate.core.auth.SecurityService;
import com.project.donate.core.auth.UserService;
import com.project.donate.core.blockchain.ProjectHashStore;
import com.project.donate.core.exceptions.WalletCreationException;
import com.project.donate.core.exceptions.blockchain.HandlingProjectException;
import com.project.donate.core.exceptions.blockchain.ProjectDataCorruptedException;
import com.project.donate.core.exceptions.blockchain.ProjectInfoException;
import com.project.donate.core.helpers.ProjectContractTypesConverter;
import com.project.donate.core.helpers.ProjectHasher;
import com.project.donate.core.model.Project;
import com.project.donate.core.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.Keys;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.project.donate.core.helpers.PropertiesUtils.getPropertyFromConfig;

@Service
public class ProjectInfoService extends AbstractProjectService {

    private static String PROJECT_ADDRESS_PROPERTY = "address.contract.projectHashStore";

    @Autowired
    public ProjectInfoService(UserService userService, SecurityService securityService, ProjectsService projectsService, Web3jServiceSupplier web3jServiceSupplier) {
        super(userService, securityService, projectsService, web3jServiceSupplier);
    }

    public double getProjectContractGoalAmount(long projectId) {

        try {

            com.project.donate.core.blockchain.Project projectFromBlockchain = getProjectFromBlockchain(projectId);

            BigInteger goalAmount = projectFromBlockchain.getGoalAmount().send();
            BigDecimal bigDecimal = Convert.fromWei(goalAmount.toString(), Convert.Unit.ETHER);

            return bigDecimal.doubleValue();

        } catch (InvalidAlgorithmParameterException | NoSuchProviderException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new WalletCreationException();
        } catch (Exception e) {
            e.printStackTrace();
            throw new HandlingProjectException("Error during info retrieving");
        }

    }

    public double getProjectCurrentBalance(long projectId) {
        try {
            com.project.donate.core.blockchain.Project projectFromBlockchain = getProjectFromBlockchain(projectId);

            BigInteger balanceProject = projectFromBlockchain.finalGainedAmount().send();
            BigDecimal bigDecimal = Convert.fromWei(balanceProject.toString(), Convert.Unit.ETHER);

            return bigDecimal.doubleValue();

        } catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
            throw new WalletCreationException();
        } catch (Exception e) {
            e.printStackTrace();
            throw new HandlingProjectException("Error while getting project info");
        }
    }

    public boolean canUserVote(long projectId) {
        try {
            com.project.donate.core.blockchain.Project projectFromBlockchain = getProjectFromBlockchain(projectId);

            String loggedInUsername = getSecurityService().findLoggedInUsername();
            User userFromDatabase = getUserService().getUserFromDatabase(loggedInUsername);

            BigInteger donation = projectFromBlockchain.donations(userFromDatabase.getAccount()).send();
            BigInteger validation = projectFromBlockchain.validation(userFromDatabase.getAccount()).send();

            return (donation.compareTo(BigInteger.ZERO) > 0) && validation.equals(BigInteger.ZERO);

        } catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
            throw new WalletCreationException();
        } catch (Exception e) {
            e.printStackTrace();
            throw new HandlingProjectException("Error while getting project info");
        }
    }

    private com.project.donate.core.blockchain.Project getProjectFromBlockchain(long projectId) throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException {
        Project project = getProjectsService().getProject(projectId);
        Credentials universalCredentials = Credentials.create(Keys.createEcKeyPair());
        return com.project.donate.core.blockchain.Project.load(project.getAddress(), getWeb3jServiceSupplier().getWeb3j(),
                universalCredentials, new DefaultGasProvider());
    }

    private ProjectHashStore getProjectHashStore() throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException {
        Credentials universalCredentials = Credentials.create(Keys.createEcKeyPair());

        Optional<String> addressOfHashStore = getPropertyFromConfig(PROJECT_ADDRESS_PROPERTY);

        if(addressOfHashStore.isEmpty()) throw new ProjectInfoException();

        return com.project.donate.core.blockchain.ProjectHashStore.load(addressOfHashStore.get(), getWeb3jServiceSupplier().getWeb3j(),
                universalCredentials, new DefaultGasProvider());
    }

    public int getDonatorsNumber(long projectId) {
        try{
            com.project.donate.core.blockchain.Project projectFromBlockchain = getProjectFromBlockchain(projectId);
            BigInteger number = projectFromBlockchain.getDonatorsCount().send();

            return number.intValue();

        } catch (Exception e) {
            e.printStackTrace();

            throw new ProjectInfoException();
        }
    }

    public int getNumberOfVotes(long projectId) {
        try{
            com.project.donate.core.blockchain.Project projectFromBlockchain = getProjectFromBlockchain(projectId);
            BigInteger votes = projectFromBlockchain.votesCount().send();

            return votes.intValue();

        } catch (Exception e) {
            e.printStackTrace();

            throw new ProjectInfoException();
        }
    }

    public List<String> getProjectExecutors(long projectId) {
        try{
            com.project.donate.core.blockchain.Project projectFromBlockchain = getProjectFromBlockchain(projectId);

            List<String> executors = projectFromBlockchain.getExecutors().send();

            return executors;

        } catch (Exception e) {
            e.printStackTrace();

            throw new ProjectInfoException();
        }
    }

    public boolean isProjectOpened(long projectId) {

        try {

            com.project.donate.core.blockchain.Project projectFromBlockchain = getProjectFromBlockchain(projectId);

            Boolean validationPhase = projectFromBlockchain.validatingPhase().send();
            Boolean executionPhase = projectFromBlockchain.executionPhase().send();

            return !validationPhase && !executionPhase;

        } catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
            throw new ProjectInfoException();
        } catch (Exception e) {
            throw new ProjectInfoException();
        }
    }

    public boolean isValidationPhase(long id) {
        try {

            com.project.donate.core.blockchain.Project projectFromBlockchain = getProjectFromBlockchain(id);

            return projectFromBlockchain.validatingPhase().send();

        } catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
            throw new ProjectInfoException();
        } catch (Exception e) {
            throw new ProjectInfoException();
        }
    }

    public Boolean isExecutedWithSuccess(long id) {
        try {

            com.project.donate.core.blockchain.Project projectFromBlockchain = getProjectFromBlockchain(id);

            Boolean isExecuted = projectFromBlockchain.executionPhase().send();

            if(!isExecuted) return null;

            return projectFromBlockchain.getIfProjectExecutionSuccess().send();


        } catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
            throw new ProjectInfoException();
        } catch (Exception e) {
            throw new ProjectInfoException();
        }
    }


    public boolean getIfProjectVerified(long projectId) {
        Project project = getProjectsService().getProject(projectId);

        try{
            ProjectHashStore projectHashStore = getProjectHashStore();
            String hashedProject = ProjectHasher.hashProject(project.getData(), project.getSummary());
            byte[] projectBytes = ProjectContractTypesConverter.convertProjectHash(hashedProject);

            return projectHashStore.isProjectVersionTheSame(project.getAddress(), projectBytes).send();

        } catch (Exception e) {
            e.printStackTrace();

            throw new ProjectInfoException();
        }
    }

    public void validateIfProjectAddressMatchesContractProjectId(Project project) {

        try {
            com.project.donate.core.blockchain.Project projectFromBlockchain = getProjectFromBlockchain(project.getId());
            String projectBlockchainVersion = projectFromBlockchain.projectId().send();

            if(!projectBlockchainVersion.equals(String.valueOf(project.getId())))
                throw new ProjectDataCorruptedException();

        } catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
            throw new ProjectInfoException();
        } catch (ProjectDataCorruptedException e) {
            throw e;
        } catch (Exception e) {
            throw new ProjectInfoException();
        }

    }

    public int getValidationTimeLeft(long id) {
        try {

            com.project.donate.core.blockchain.Project projectFromBlockchain = getProjectFromBlockchain(id);

            BigInteger validationTimeLeft = projectFromBlockchain.getValidationTimeLeft().send();

            return validationTimeLeft.intValue();

        } catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
            throw new ProjectInfoException();
        } catch (Exception e) {
            throw new ProjectInfoException();
        }
    }

    public Set<String> getUserProjects(String userAddress) {
        try{
            ProjectHashStore projectHashStore = getProjectHashStore();
            List<String> allProjects = projectHashStore.getProjectsExisting().send();

            return allProjects.stream()
                    .filter(projectAddress -> {
                        try {

                            Tuple3<byte[], String, Boolean> projectTuple = projectHashStore.projectStore(projectAddress).send();

                            String owner = projectTuple.getValue2();

                            return owner.equals(userAddress);

                        } catch (Exception e) {
                            throw new ProjectInfoException();
                        }
                    })
                    .collect(Collectors.toSet());


        } catch (Exception e) {
            e.printStackTrace();

            throw new ProjectInfoException();
        }
    }
}
