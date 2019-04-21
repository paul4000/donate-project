package com.project.donate.core;

import org.springframework.stereotype.Service;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jService;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;


import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

@Service
public class SavingContractService {

    private static Web3jService service = new HttpService("http://localhost:8545");
    private static Web3j web3j = Web3j.build(service);
    private static String CONTRACT_ADDRESS = "0x5ea436b355b1bc20347bea67cf284ee2103510a8";
    private static String sampleHash = "0xaksfh384v3wbcasjdahkca83cks";

    Logger LOGGER = Logger.getLogger(SavingContractService.class.getName());

    public void saveProject(String projectHash) throws Exception {

        SampleContract saveProject = getSaveProject(CONTRACT_ADDRESS);

//        saveProject.setSample(new BigInteger("1")).send();
//        saveProject.set(projectHash).send();

        LOGGER.info("Saving hash");

        BigInteger send = saveProject.getSample().send();

        LOGGER.info("Saved hash: " + projectHash + " retrieved hash: "+ send);
    }

    public void deployContract() throws Exception {
        Credentials credentials1 = WalletUtils.loadCredentials("account1",
                "E:\\Studia\\Informatyka IX\\mgr\\chaindata\\keystore\\UTC--2019-02-24T21-03-15.812871500Z--61665f9c0d08cfeaad3213da647bcd1a56603c7e");

//        Credentials credentials1 = Credentials.create("4f47d66ced14dd6a6268252e3326cf44b87c043ef53f107f40f8209370ef7cad");

        LOGGER.info("Credentials loaded succesfully");

        SampleContract savedProject = SampleContract.deploy(web3j, credentials1, new DefaultGasProvider()).send();

        LOGGER.info("Contract deployed under address: " + savedProject.getContractAddress());

        LOGGER.info("Is contract valid : " + savedProject.isValid());

//        savedProject.getTransactionReceipt().ifPresent(transactionReceipt -> System.out.println(transactionReceipt.getContractAddress()));

//        savedProject.setSample(new BigInteger("1")).send();

//        SampleContract myContract = SampleContract.load(savedProject.getContractAddress(), web3j, credentials, new DefaultGasProvider());

        LOGGER.info("Loaded contract address: "+ savedProject.getContractAddress());

        LOGGER.info("Saving hash");

        BigInteger sampleNumber = savedProject.getSample().sendAsync().get();

        LOGGER.info("Saved number: 1 " + " retrieved number: "+ sampleNumber.toString());

    }

    private SampleContract getSaveProject(String address) throws IOException, CipherException {
        Credentials credentials = WalletUtils.loadCredentials("account1",
                "E:\\Studia\\Informatyka IX\\mgr\\chaindata\\keystore\\UTC--2019-02-24T21-03-15.812871500Z--61665f9c0d08cfeaad3213da647bcd1a56603c7e");

//        Credentials credentials = Credentials.create("4f47d66ced14dd6a6268252e3326cf44b87c043ef53f107f40f8209370ef7cad");
        LOGGER.info("Credentials loaded succesfully");

        SampleContract saveProject = SampleContract.load(address,
                web3j, credentials, new DefaultGasProvider());

//        saveProject.getTransactionReceipt().ifPresent(transactionReceipt -> System.out.println(transactionReceipt.getContractAddress()));

        LOGGER.info("Save project contract loaded succesfully");
        return saveProject;
    }

    public EthBlockNumber getBlockNumbers() throws ExecutionException, InterruptedException {
        return web3j.ethBlockNumber().sendAsync().get();
    }

}
