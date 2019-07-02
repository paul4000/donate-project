package com.project.donate.contracts;

import org.web3j.crypto.Credentials;

import java.util.AbstractMap;
import java.util.Map;

/**
 * Accounts are copied from Ganache instance. For your testing purposes should paste actual used
 * */
class KeyStore {

    static final String DONATOR_1 = "DONATOR1";
    static final String DONATOR_2 = "DONATOR2";
    static final String DONATOR_3 = "DONATOR3";
    static final String DONATOR_4 = "DONATOR4";
    static final String DONATOR_5 = "DONATOR5";

    static final String INITIATOR_1 = "INITIATOR1";

    static final String EXECUTOR_1 = "EXECUTOR1";
    static final String EXECUTOR_2 = "EXECUTOR2";
    static final String EXECUTOR_3 = "EXECUTOR3";
    static final String EXECUTOR_4 = "EXECUTOR4";
    static final String EXECUTOR_5 = "EXECUTOR5";

    private static Map<String, String> userKeysMap = Map.ofEntries(
            new AbstractMap.SimpleEntry<>(INITIATOR_1, "4f47d66ced14dd6a6268252e3326cf44b87c043ef53f107f40f8209370ef7cad"),
            new AbstractMap.SimpleEntry<>(DONATOR_1, "b9a5ecfcdbf25b926909a83c74034a4336df50807fd67356eb11497b5e2ab051"),
            new AbstractMap.SimpleEntry<>(DONATOR_2, "194ad59d4d0fab16c8974732f6f15908ac6bfb087d6921c28068e52f45dcaa8c"),
            new AbstractMap.SimpleEntry<>(DONATOR_3, "1b56979b5f997ea76db3b3a765b2073de72ce8c883b230726a53a9117e2f8283"),
            new AbstractMap.SimpleEntry<>(DONATOR_4, "a57302208585d7ac5a2d0e7b28c4a33a6bd46988af712d0e7297e0ae2d524888"),
            new AbstractMap.SimpleEntry<>(DONATOR_5, "bcca2cbcbee2a1587c35377366a0b26ac01c8edc57e24a4615368977ad827b05")
    );

    private static Map<String, String> executorKeysMap = Map.ofEntries(
            new AbstractMap.SimpleEntry<>(EXECUTOR_1, "0x50eC447460458235BD0Bb58359081ae6d804D074"),
            new AbstractMap.SimpleEntry<>(EXECUTOR_2, "0x90CC226D2E45709608D359bBD50f3A90819aB8f3"),
            new AbstractMap.SimpleEntry<>(EXECUTOR_3, "0x8054cbC82604A570e8516Ecf6ba682aB6031ebDF"),
            new AbstractMap.SimpleEntry<>(EXECUTOR_4, "0xC2df5F29C225E5eD83D34e46a076a823C8b0b04e"),
            new AbstractMap.SimpleEntry<>(EXECUTOR_5, "0x828C20a6AAcE1a45513B20BBC28F28D779CC8146")
    );

    static Credentials getCredentials(String user) {
        return Credentials.create(userKeysMap.get(user));
    }

    static String getExecAddress(String e) {
        return executorKeysMap.get(e);
    }
}
