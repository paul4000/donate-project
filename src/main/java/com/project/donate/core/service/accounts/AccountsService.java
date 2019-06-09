package com.project.donate.core.service.accounts;

import com.project.donate.core.exceptions.blockchain.HandlingProjectException;
import com.project.donate.core.helpers.PropertiesUtils;
import com.project.donate.core.model.Role;
import com.project.donate.core.model.User;
import com.project.donate.core.repositories.UsersRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.WalletUtils;

import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Optional;

@Service
public class AccountsService {

    private final Logger logger = Logger.getLogger(AccountsService.class);

    private UsersRepository usersRepository;

    @Autowired
    public AccountsService(UsersRepository usersRepository) {

        Assert.notNull(usersRepository, "UsersRepository should not be null");

        this.usersRepository = usersRepository;
    }

    public Optional<String> createWalletFile(String password) throws CipherException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException, IOException {

        Optional<String> keystorePath = PropertiesUtils.getPropertyFromConfig("path.blockchain.keystore");

        Optional<String> fullNewWalletFile = Optional.empty();

        if(keystorePath.isPresent()){

            fullNewWalletFile = Optional.of(WalletUtils.generateFullNewWalletFile(password, new File(keystorePath.get())));

        }

        return fullNewWalletFile;
    }

    public void validateIfChosenAddressIsExecutor(String executor) {

        User byAccount = usersRepository.findByAccount(executor);

        if(!byAccount.getRole().equals(Role.EXECUTOR)) throw new HandlingProjectException("Chosen account is not executor");
    }


}
