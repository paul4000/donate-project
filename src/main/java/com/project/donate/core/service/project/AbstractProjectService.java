package com.project.donate.core.service.project;

import com.project.donate.core.auth.SecurityService;
import com.project.donate.core.auth.UserService;
import com.project.donate.core.helpers.WalletHelper;
import org.springframework.util.Assert;
import org.web3j.crypto.Credentials;

abstract public class AbstractProjectService {

    private UserService userService;
    private SecurityService securityService;

    public AbstractProjectService(UserService userService, SecurityService securityService) {

        Assert.notNull(securityService, "SecurityService should not be null");
        Assert.notNull(userService, "UserService should not be null");

        this.userService = userService;
        this.securityService = securityService;
    }

    Credentials getCredentials(String passwordToWallet) {
        String loggedInUsername = securityService.findLoggedInUsername();

        String walletName = userService.getWalletName(loggedInUsername);

        return WalletHelper.getCredentialsFromWallet(passwordToWallet, walletName);
    }

}
