package com.project.donate.core.helpers;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.project.donate.core.exceptions.WalletCreationException;
import com.project.donate.core.exceptions.WalletOpeningException;
import org.apache.log4j.Logger;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;

import java.io.IOException;
import java.util.Optional;

import static com.project.donate.core.helpers.PropertiesUtils.getPropertyFromConfig;

public class WalletHelper {

    private final static Logger logger = Logger.getLogger(WalletHelper.class);
    private final static String WALLET_PATH = "path.blockchain.keystore";

    public static Optional<String> extractAccountAddress(String walletName){

        Iterable<String> splitWalletName = Splitter.on("--").split(walletName);

        String address = Iterables.getLast(splitWalletName);

        Iterable<String> splitAddress = Splitter.on('.').split(address);
        Optional<String> addressAccount = Optional.ofNullable(Iterables.getFirst(splitAddress, null));

        if(addressAccount.isEmpty()){
            logger.warn("Something goes wrong with retrieving address");
            return Optional.empty();
        }

        return Optional.of("0x" + addressAccount.get());

    }

    public static Credentials getCredentialsFromWallet(String password, String walletAddress) {

        Optional<String> walletPath = getPropertyFromConfig(WALLET_PATH);

        if(walletPath.isEmpty()) throw new WalletOpeningException();

        String walletSource = walletPath.get() + "\\" + walletAddress;

        try {

            return WalletUtils.loadCredentials(password, walletSource);

        } catch (IOException | CipherException e) {

            e.printStackTrace();
            throw new WalletOpeningException();
        }
    }

}
