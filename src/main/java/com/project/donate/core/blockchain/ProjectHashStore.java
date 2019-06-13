package com.project.donate.core.blockchain;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.2.0.
 */
public class ProjectHashStore extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b506102f7806100206000396000f3fe608060405234801561001057600080fd5b506004361061005d577c010000000000000000000000000000000000000000000000000000000060003504630c1e24638114610062578063824a58df14610090578063ceb63092146100de575b600080fd5b61008e6004803603604081101561007857600080fd5b5080359060200135600160a060020a031661011e565b005b6100b6600480360360208110156100a657600080fd5b5035600160a060020a0316610207565b60408051938452600160a060020a039092166020840152151582820152519081900360600190f35b61010a600480360360408110156100f457600080fd5b50600160a060020a038135169060200135610246565b604080519115158252519081900360200190f35b600160a060020a038116600090815260208190526040902060010154819074010000000000000000000000000000000000000000900460ff161561016157600080fd5b6101696102ab565b5050604080516060810182529283523360208085019182526001858401818152600160a060020a03958616600090815292839052939091209451855590519301805491511515740100000000000000000000000000000000000000000274ff0000000000000000000000000000000000000000199490931673ffffffffffffffffffffffffffffffffffffffff199092169190911792909216179055565b60006020819052908152604090208054600190910154600160a060020a0381169074010000000000000000000000000000000000000000900460ff1683565b600160a060020a038216600090815260208190526040812060010154839074010000000000000000000000000000000000000000900460ff16151561028a57600080fd5b5050600160a060020a03919091166000908152602081905260409020541490565b60408051606081018252600080825260208201819052918101919091529056fea165627a7a723058206b99fd203c1c4ba88c6868884beda2d419e7c360159f1a873858990d01ac58ed0029";

    public static final String FUNC_REGISTERPROJECT = "registerProject";

    public static final String FUNC_PROJECTSTORE = "projectStore";

    public static final String FUNC_ISPROJECTVERSIONTHESAME = "isProjectVersionTheSame";

    @Deprecated
    protected ProjectHashStore(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected ProjectHashStore(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected ProjectHashStore(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected ProjectHashStore(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<TransactionReceipt> registerProject(byte[] _projectHash, String _projectAddress) {
        final Function function = new Function(
                FUNC_REGISTERPROJECT, 
                Arrays.<Type>asList(new Bytes32(_projectHash),
                new Address(_projectAddress)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Tuple3<byte[], String, Boolean>> projectStore(String param0) {
        final Function function = new Function(FUNC_PROJECTSTORE, 
                Arrays.<Type>asList(new Address(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}, new TypeReference<Address>() {}, new TypeReference<Bool>() {}));
        return new RemoteCall<Tuple3<byte[], String, Boolean>>(
                new Callable<Tuple3<byte[], String, Boolean>>() {
                    @Override
                    public Tuple3<byte[], String, Boolean> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<byte[], String, Boolean>(
                                (byte[]) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (Boolean) results.get(2).getValue());
                    }
                });
    }

    public RemoteCall<Boolean> isProjectVersionTheSame(String _projectAddress, byte[] _hashToCheck) {
        final Function function = new Function(FUNC_ISPROJECTVERSIONTHESAME, 
                Arrays.<Type>asList(new Address(_projectAddress),
                new Bytes32(_hashToCheck)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    @Deprecated
    public static ProjectHashStore load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new ProjectHashStore(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static ProjectHashStore load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new ProjectHashStore(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static ProjectHashStore load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new ProjectHashStore(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static ProjectHashStore load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new ProjectHashStore(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<ProjectHashStore> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(ProjectHashStore.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<ProjectHashStore> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(ProjectHashStore.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<ProjectHashStore> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(ProjectHashStore.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<ProjectHashStore> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(ProjectHashStore.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }
}
