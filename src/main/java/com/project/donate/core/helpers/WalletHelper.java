package com.project.donate.core.helpers;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import org.apache.log4j.Logger;

import java.util.Optional;

public class WalletHelper {

    private final static Logger logger = Logger.getLogger(WalletHelper.class);

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

}
