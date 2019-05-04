package com.project.donate.core.blockchain;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple4;
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
    private static final String BINARY = "608060405234801561001057600080fd5b506105f8806100206000396000f3fe608060405234801561001057600080fd5b506004361061007e577c010000000000000000000000000000000000000000000000000000000060003504630564dfb38114610083578063093566a9146100b4578063107046bd146100d75780635592db6e1461011a578063cb33f4de1461013d578063d994947714610188575b600080fd5b6100a06004803603602081101561009957600080fd5b50356101ad565b604080519115158252519081900360200190f35b6100a0600480360360408110156100ca57600080fd5b5080359060200135610205565b6100f4600480360360208110156100ed57600080fd5b50356102a5565b604080519485526020850193909352838301919091526060830152519081900360800190f35b6100a06004803603604081101561013057600080fd5b50803590602001356102dd565b6101766004803603604081101561015357600080fd5b5073ffffffffffffffffffffffffffffffffffffffff8135169060200135610339565b60408051918252519081900360200190f35b6101ab6004803603604081101561019e57600080fd5b5080359060200135610369565b005b6000806101ba338461045e565b905060008112156101cf576000915050610200565b60006001828154811015156101e057fe5b906000526020600020906004020190504281600301819055506001925050505b919050565b60008061021184610551565b9050600081121561022657600091505061029f565b61022e6105a5565b600180548390811061023c57fe5b90600052602060002090600402016080604051908101604052908160008201548152602001600182015481526020016002820154815260200160038201548152505090508060600151816040015110801561029a5750838160200151145b925050505b92915050565b60018054829081106102b357fe5b60009182526020909120600490910201805460018201546002830154600390930154919350919084565b6000806102ea338561045e565b905060008112156102ff57600091505061029f565b600060018281548110151561031057fe5b600091825260209091206001600490920201818101869055426002909101559250505092915050565b60006020528160005260406000208181548110151561035457fe5b90600052602060002001600091509150505481565b6103716105a5565b50604080516080810182529283526020808401928352428483019081526000606086018181526001805480820182558184529751600489027fb10e2d527612073b26eecdfd717e6a320cf44b4afac2b0732d9fcbe2b7fa0cf681019190915596517fb10e2d527612073b26eecdfd717e6a320cf44b4afac2b0732d9fcbe2b7fa0cf788015592517fb10e2d527612073b26eecdfd717e6a320cf44b4afac2b0732d9fcbe2b7fa0cf8870155517fb10e2d527612073b26eecdfd717e6a320cf44b4afac2b0732d9fcbe2b7fa0cf9909501949094553384528382529183208054928301815583529091200155565b73ffffffffffffffffffffffffffffffffffffffff821660009081526020818152604080832080548251818502810185019093528083526060938301828280156104c757602002820191906000526020600020905b8154815260200190600101908083116104b3575b505083519394506000925050505b818110156105445784600184838151811015156104ee57fe5b602090810290910101518154811061050257fe5b906000526020600020906004020160000154141561053c57828181518110151561052857fe5b90602001906020020151935050505061029f565b6001016104d5565b5060001995945050505050565b600154600090815b8181101561059a578360018281548110151561057157fe5b90600052602060002090600402016000015414156105925791506102009050565b600101610559565b506000199392505050565b6040805160808101825260008082526020820181905291810182905260608101919091529056fea165627a7a72305820bdc89e30b96a858e19d007c4fc8be94081533e12e3c43fa5443e0aeb6db5eac60029";

    public static final String FUNC_OPENPROJECT = "openProject";

    public static final String FUNC_ISNOTCHANGE = "isNotChange";

    public static final String FUNC_PROJECTS = "project";

    public static final String FUNC_UPDATEPROJECT = "updateProject";

    public static final String FUNC_PROJECTINDICESSTORAGE = "projectIndicesStorage";

    public static final String FUNC_REGISTERPROJECT = "registerProjectInDatabase";

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

    public RemoteCall<TransactionReceipt> openProject(byte[] _projectId) {
        final Function function = new Function(
                FUNC_OPENPROJECT, 
                Arrays.<Type>asList(new Bytes32(_projectId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Boolean> isNotChange(byte[] _projectId, byte[] _projectHash) {
        final Function function = new Function(FUNC_ISNOTCHANGE, 
                Arrays.<Type>asList(new Bytes32(_projectId),
                new Bytes32(_projectHash)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<Tuple4<byte[], byte[], BigInteger, BigInteger>> projects(BigInteger param0) {
        final Function function = new Function(FUNC_PROJECTS, 
                Arrays.<Type>asList(new Uint256(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteCall<Tuple4<byte[], byte[], BigInteger, BigInteger>>(
                new Callable<Tuple4<byte[], byte[], BigInteger, BigInteger>>() {
                    @Override
                    public Tuple4<byte[], byte[], BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple4<byte[], byte[], BigInteger, BigInteger>(
                                (byte[]) results.get(0).getValue(), 
                                (byte[]) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue());
                    }
                });
    }

    public RemoteCall<TransactionReceipt> updateProject(byte[] _projectId, byte[] _newHash) {
        final Function function = new Function(
                FUNC_UPDATEPROJECT, 
                Arrays.<Type>asList(new Bytes32(_projectId),
                new Bytes32(_newHash)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> projectIndicesStorage(String param0, BigInteger param1) {
        final Function function = new Function(FUNC_PROJECTINDICESSTORAGE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(param0), 
                new Uint256(param1)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> registerProject(byte[] _id, byte[] _projectHash) {
        final Function function = new Function(
                FUNC_REGISTERPROJECT, 
                Arrays.<Type>asList(new Bytes32(_id),
                new Bytes32(_projectHash)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
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
