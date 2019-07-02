package com.project.donate.contracts;

import com.project.donate.core.blockchain.Project;
import com.project.donate.core.blockchain.ProjectHashStore;
import com.project.donate.core.helpers.ProjectContractTypesConverter;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jService;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;

import static com.project.donate.contracts.KeyStore.*;


/**
 * You have to have Ganache or other blockchain for testing under address localhost:7545
 */
public class ProjectTest {

    private final static Logger logger = Logger.getLogger(ProjectTest.class);

    private Web3jService service = new HttpService("http://localhost:7545");
    private Web3j web3j = Web3j.build(service);
    private ProjectHashStore projectHashStore;

    @Before
    public void deployProjectHashStore() throws Exception {
        projectHashStore = ProjectHashStore.deploy(web3j, KeyStore.getCredentials(INITIATOR_1), new DefaultGasProvider()).send();
    }

    @Test
    public void wholeScenarioHappyPath() throws Exception {

        //deploying contract
        long projectId = 1;
        BigInteger goalAmount = new BigInteger("20000000000000000000");

        logger.info("--------------DEPLOYING CONTRACT--------------");

        com.project.donate.core.blockchain.Project project = com.project.donate.core.blockchain.Project.deploy(web3j,
                KeyStore.getCredentials(INITIATOR_1),
                new DefaultGasProvider(), String.valueOf(projectId),
                goalAmount).send();

        logger.info("--------------GAS USED--------------");

        logger.info(getGasUsed(project.getTransactionReceipt().get()));
        logger.info("--------------END OF DEPLOYING--------------");

        logger.info("--------------ADDING TO PROJECT STORE --------------");

        String projectHash = "ce3c2b274c17068c1b3eb1eae4b9cfb06bf685f7470001e8e0fe9d7a8d3bf5d5";

        TransactionReceipt transactionReceiptAddedHash = projectHashStore
                .registerProject(ProjectContractTypesConverter.convertProjectHash(projectHash), project.getContractAddress())
                .send();

        logger.info("--------------GAS USED--------------");

        logger.info(getGasUsed(transactionReceiptAddedHash));

        logger.info("--------------DONATIONS--------------");


        TransactionReceipt transactionReceiptMakeDonation = Project.load(project.getContractAddress(), web3j, KeyStore.getCredentials(DONATOR_3), new DefaultGasProvider())
                .makeDonation(new BigInteger("10000000000000000000")).send();

        logger.info("--------------GAS USED--------------");

        logger.info(getGasUsed(transactionReceiptMakeDonation));

        TransactionReceipt transactionReceiptMakeDonation1 = Project.load(project.getContractAddress(), web3j, KeyStore.getCredentials(DONATOR_3), new DefaultGasProvider())
                .makeDonation(new BigInteger("10000000000000000000")).send();

        logger.info("--------------GAS USED--------------");

        logger.info(getGasUsed(transactionReceiptMakeDonation1));


        logger.info("--------------ADD EXECUTORS--------------");

        TransactionReceipt transactionReceiptAddingExe = Project.load(project.getContractAddress(), web3j, getCredentials(INITIATOR_1), new DefaultGasProvider())
                .addExecutor(getExecAddress(EXECUTOR_1), new BigInteger("5000000000000000000")).send();

        logger.info("--------------GAS USED--------------");

        logger.info(getGasUsed(transactionReceiptAddingExe));

        TransactionReceipt transactionReceiptAddingExe1 = Project.load(project.getContractAddress(), web3j, getCredentials(INITIATOR_1), new DefaultGasProvider())
                .addExecutor(getExecAddress(EXECUTOR_2), new BigInteger("10000000000000000000")).send();

        logger.info("--------------GAS USED--------------");

        logger.info(getGasUsed(transactionReceiptAddingExe1));

        TransactionReceipt transactionReceiptAddingExe2 = Project.load(project.getContractAddress(), web3j, getCredentials(INITIATOR_1), new DefaultGasProvider())
                .addExecutor(getExecAddress(EXECUTOR_3), new BigInteger("5000000000000000000")).send();

        logger.info("--------------GAS USED--------------");

        logger.info(getGasUsed(transactionReceiptAddingExe2));

        TransactionReceipt openValidationPhaseTx = Project.load(project.getContractAddress(), web3j, getCredentials(INITIATOR_1), new DefaultGasProvider())
                .openValidationPhase().send();

        logger.info(getGasUsed(openValidationPhaseTx));

        logger.info("--------------VOTING--------------");
        TransactionReceipt votingTransactionReceipt = Project.load(project.getContractAddress(), web3j, getCredentials(DONATOR_3), new DefaultGasProvider())
                .voteForProjectExecution(new BigInteger("-1")).send();
//        Project.load(project.getContractAddress(), web3j, getCredentials(DONATOR_2), new DefaultGasProvider())
//                .voteForProjectExecution(new BigInteger("-1")).send();

        logger.info("--------------GAS USED--------------");
        logger.info(getGasUsed(votingTransactionReceipt));

        logger.info("--------------CLOSE VALIDATION--------------");
        TransactionReceipt closeValidationPhaseTx = Project.load(project.getContractAddress(), web3j, getCredentials(INITIATOR_1), new DefaultGasProvider())
                .closeValidatingPhase().send();
        logger.info("--------------GAS USED--------------");
        logger.info(getGasUsed(closeValidationPhaseTx));

        logger.info("--------------EXECUTE--------------");
        TransactionReceipt executionTx = Project.load(project.getContractAddress(), web3j, getCredentials(INITIATOR_1), new DefaultGasProvider())
                .executeProject().send();
        logger.info("--------------GAS USED--------------");
        logger.info(getGasUsed(executionTx));

    }

    private long getGasUsed(TransactionReceipt receipt) {
        return receipt.getGasUsed().longValue();
    }

}
