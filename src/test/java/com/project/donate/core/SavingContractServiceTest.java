package com.project.donate.core;

import org.junit.Test;

import java.util.concurrent.ExecutionException;

public class SavingContractServiceTest {

    @Test
    public void test() throws Exception {
       //given
       SavingContractService savingContractService = new SavingContractService();
       String sampleHash = "0xaksfh384v3wbcasjdahkca83cks";

       //when
        savingContractService.saveProject(sampleHash);
//        savingContractService.deployContract();

       //then
    }

    @Test
    public void test1() throws ExecutionException, InterruptedException {
//        SavingContractService savingContractService = new SavingContractService();
//
//        EthBlockNumber blockNumbers = savingContractService.getBlockNumbers();
//
//        System.out.println(blockNumbers.getBlockNumber().toString());
    }
}