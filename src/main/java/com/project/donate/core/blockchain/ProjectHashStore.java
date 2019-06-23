package com.project.donate.core.blockchain;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicArray;
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
    private static final String BINARY = "608060405234801561001057600080fd5b50610460806100206000396000f3fe608060405234801561001057600080fd5b5060043610610073577c010000000000000000000000000000000000000000000000000000000060003504630c1e24638114610078578063824a58df146100a6578063996b5aaf146100f4578063aefe6e681461014c578063ceb6309214610185575b600080fd5b6100a46004803603604081101561008e57600080fd5b5080359060200135600160a060020a03166101c5565b005b6100cc600480360360208110156100bc57600080fd5b5035600160a060020a03166102e6565b60408051938452600160a060020a039092166020840152151582820152519081900360600190f35b6100fc610325565b60408051602080825283518183015283519192839290830191858101910280838360005b83811015610138578181015183820152602001610120565b505050509050019250505060405180910390f35b6101696004803603602081101561016257600080fd5b5035610387565b60408051600160a060020a039092168252519081900360200190f35b6101b16004803603604081101561019b57600080fd5b50600160a060020a0381351690602001356103af565b604080519115158252519081900360200190f35b600160a060020a038116600090815260208190526040902060010154819074010000000000000000000000000000000000000000900460ff161561020857600080fd5b610210610414565b5050604080516060810182529283523360208085019182526001858401818152600160a060020a03958616600081815293849052948320965187559251958101805493511515740100000000000000000000000000000000000000000274ff0000000000000000000000000000000000000000199790961673ffffffffffffffffffffffffffffffffffffffff1994851617969096169490941790945582548084018455929093527fb10e2d527612073b26eecdfd717e6a320cf44b4afac2b0732d9fcbe2b7fa0cf69091018054909216179055565b60006020819052908152604090208054600190910154600160a060020a0381169074010000000000000000000000000000000000000000900460ff1683565b6060600180548060200260200160405190810160405280929190818152602001828054801561037d57602002820191906000526020600020905b8154600160a060020a0316815260019091019060200180831161035f575b5050505050905090565b600180548290811061039557fe5b600091825260209091200154600160a060020a0316905081565b600160a060020a038216600090815260208190526040812060010154839074010000000000000000000000000000000000000000900460ff1615156103f357600080fd5b5050600160a060020a03919091166000908152602081905260409020541490565b60408051606081018252600080825260208201819052918101919091529056fea165627a7a72305820da82e7ddd3253daf4cd528f61c648359d512f53b78150533558b58d3f4334c500029";

    public static final String FUNC_REGISTERPROJECT = "registerProject";

    public static final String FUNC_PROJECTSTORE = "projectStore";

    public static final String FUNC_GETPROJECTSEXISTING = "getProjectsExisting";

    public static final String FUNC_PROJECTADDRESSLIST = "projectAddressList";

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

    public RemoteCall<List> getProjectsExisting() {
        final Function function = new Function(FUNC_GETPROJECTSEXISTING, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Address>>() {}));
        return new RemoteCall<List>(
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteCall<String> projectAddressList(BigInteger param0) {
        final Function function = new Function(FUNC_PROJECTADDRESSLIST, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
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
