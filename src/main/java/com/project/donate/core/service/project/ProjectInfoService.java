package com.project.donate.core.service.project;

import com.project.donate.core.Web3jServiceSupplier;
import com.project.donate.core.auth.SecurityService;
import com.project.donate.core.auth.UserService;
import com.project.donate.core.exceptions.WalletCreationException;
import com.project.donate.core.exceptions.blockchain.HandlingProjectException;
import com.project.donate.core.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.Keys;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

@Service
public class ProjectInfoService extends AbstractProjectService {

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

            BigInteger balanceProject = projectFromBlockchain.getBalance().send();
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

    private com.project.donate.core.blockchain.Project getProjectFromBlockchain(long projectId) throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException {
        Project project = getProjectsService().getProject(projectId);
        Credentials universalCredentials = Credentials.create(Keys.createEcKeyPair());
        return com.project.donate.core.blockchain.Project.load(project.getAddress(), getWeb3jServiceSupplier().getWeb3j(),
                universalCredentials, new DefaultGasProvider());
    }

}
