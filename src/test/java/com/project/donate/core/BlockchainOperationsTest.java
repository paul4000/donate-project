package com.project.donate.core;

import com.project.donate.core.blockchain.Project;
import org.junit.Test;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;

public class BlockchainOperationsTest {

    @Test
    public void shouldRegisterProject() throws Exception {

        //given
        Web3j web3j = new Web3jServiceSupplier().getWeb3j();
        Credentials credentialsInitiator = WalletUtils.loadCredentials("account2",
                "E:\\Studia\\Informatyka IX\\mgr\\chaindata\\keystore\\UTC--2019-05-11T14-58-16.382748100Z--41306825d5f39c6f21977115ea0d6be7a45679d2.json");

//        Project projectDeployed = Project.deploy(web3j, credentialsInitiator, new DefaultGasProvider(),
//                "test", new BigInteger("100")).send();
//
//        System.out.println("Contract deployed under address: " + projectDeployed.getContractAddress());
//
//        BigInteger balance = projectDeployed.getBalance().send();
//        BigInteger goalAmount = projectDeployed.getGoalAmount().send();
//
//        System.out.println("Balance: " + balance.toString());
//        System.out.println("Goal amount: " + goalAmount.toString());

        Credentials credentialsDonator = WalletUtils.loadCredentials("donator1",
                "E:\\Studia\\Informatyka IX\\mgr\\chaindata\\keystore\\UTC--2019-06-06T20-22-14.901017000Z--401797f625488acd45ed49572563e333b0a978c6.json");


        Project donatedProjects = Project.load("0x10B7a6f2ca45cBe14124f11A696AfF7728ccf32A",
                web3j, credentialsDonator, new DefaultGasProvider());

        TransactionReceipt transactionReceipt = donatedProjects.makeDonation(new BigInteger("50")).send();

        System.out.println(transactionReceipt.toString());

    }
}