package com.project.donate.core.controllers;

import com.project.donate.core.accounts.AccountsService;
import com.project.donate.core.auth.SecurityService;
import com.project.donate.core.auth.UserService;
import com.project.donate.core.exceptions.WalletCreationException;
import com.project.donate.core.helpers.WalletHelper;
import com.project.donate.core.models.Role;
import com.project.donate.core.models.User;
import com.project.donate.core.models.dtos.UserTO;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.crypto.CipherException;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Optional;

@RestController
@RequestMapping(path = "/users")
public class UsersController {

    private final Logger logger = Logger.getLogger(UsersController.class);

    private final UserService userService;

    private final AccountsService accountsService;

    private final SecurityService securityService;

    @Autowired
    public UsersController(UserService userService, AccountsService accountsService, SecurityService securityService) {
        this.securityService = securityService;

        Assert.notNull(userService, "UserService should not be null");
        Assert.notNull(accountsService, "AccountsService should not be null");
        Assert.notNull(securityService, "SecurityService should not be null");

        this.userService = userService;
        this.accountsService = accountsService;
    }


    @PostMapping(path = "/register")
    public ResponseEntity register(@RequestBody UserTO userTO) {

        logger.log(Level.INFO, "Adding new user");

        String passwordToAccount = userTO.getPasswordToAccount() != null ?
                userTO.getPasswordToAccount() : userTO.getPassword();

        Optional<String> walletFileName;
        try {

            walletFileName = accountsService.createWalletFile(passwordToAccount);

        } catch (CipherException | InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException | IOException e) {
            e.printStackTrace();

            throw new WalletCreationException();
        }

        if(walletFileName.isEmpty()) throw new WalletCreationException();

        Optional<String> accountAddress = WalletHelper.extractAccountAddress(walletFileName.get());

        if(accountAddress.isEmpty()) throw new WalletCreationException();

        User userToAdd = new User();

        userToAdd.setName(userTO.getName());
        userToAdd.setEmail(userTO.getEmail());
        userToAdd.setPassword(userTO.getPassword());
        userToAdd.setUsername(userTO.getUsername());
        userToAdd.setAccount(accountAddress.get());
        userToAdd.setWalletFile(walletFileName.get());
        userToAdd.setRole(Role.valueOf(userTO.getAccountRole()));

        User savedUser = userService.saveUser(userToAdd);

        securityService.login(savedUser.getUsername(), userTO.getPassword());

        return new ResponseEntity<>(savedUser, HttpStatus.OK);
    }

}
