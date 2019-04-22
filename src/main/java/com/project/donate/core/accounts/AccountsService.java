package com.project.donate.core.accounts;

import com.project.donate.core.helpers.PropertiesUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
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

    public Optional<String> createWalletFile(String password) throws CipherException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException, IOException {

        Optional<String> keystorePath = PropertiesUtils.getPropertyFromConfig("path.blockchain.keystore");

        Optional<String> fullNewWalletFile = Optional.empty();

        if(keystorePath.isPresent()){

            fullNewWalletFile = Optional.of(WalletUtils.generateFullNewWalletFile(password, new File(keystorePath.get())));

        }

        return fullNewWalletFile;
    }

}
