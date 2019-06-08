package com.project.donate.core.service.project;

import com.project.donate.core.Web3jServiceSupplier;
import com.project.donate.core.auth.SecurityService;
import com.project.donate.core.auth.UserService;
import com.project.donate.core.helpers.WalletHelper;
import com.project.donate.core.model.Project;
import org.springframework.util.Assert;
import org.web3j.crypto.Credentials;
import org.web3j.tx.gas.DefaultGasProvider;

abstract public class AbstractProjectService {

    private UserService userService;
    private SecurityService securityService;
    private ProjectsService projectsService;
    private Web3jServiceSupplier web3jServiceSupplier;

    public AbstractProjectService(UserService userService, SecurityService securityService, ProjectsService projectsService,
                                  Web3jServiceSupplier web3jServiceSupplier) {

        Assert.notNull(securityService, "SecurityService should not be null");
        Assert.notNull(userService, "UserService should not be null");
        Assert.notNull(projectsService, "ProjectsService should not be null");
        Assert.notNull(web3jServiceSupplier, "Web3jServiceSupplier should not be null");

        this.userService = userService;
        this.securityService = securityService;
        this.projectsService = projectsService;
        this.web3jServiceSupplier = web3jServiceSupplier;
    }

    Credentials getCredentials(String passwordToWallet) {
        String loggedInUsername = securityService.findLoggedInUsername();

        String walletName = userService.getWalletName(loggedInUsername);

        return WalletHelper.getCredentialsFromWallet(passwordToWallet, walletName);
    }

    com.project.donate.core.blockchain.Project loadProjectFromBlockchain(long projectId, Credentials userWalletCredentials) {
        Project project = projectsService.getProject(projectId);

        return com.project.donate.core.blockchain.Project.load(project.getAddress(), web3jServiceSupplier.getWeb3j(),
                userWalletCredentials, new DefaultGasProvider());
    }

    public ProjectsService getProjectsService() {
        return projectsService;
    }

    public Web3jServiceSupplier getWeb3jServiceSupplier() {
        return web3jServiceSupplier;
    }

}
