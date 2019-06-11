package com.project.donate.core.blockchain;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Int256;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
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
public class Project extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b50604051610bc1380380610bc18339810180604052604081101561003357600080fd5b81019080805164010000000081111561004b57600080fd5b8201602081018481111561005e57600080fd5b815164010000000081118282018710171561007857600080fd5b505060209182015160008054600160a060020a03191633179055815191945092506100a991600191908501906100be565b50600455506005805461ffff19169055610159565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106100ff57805160ff191683800117855561012c565b8280016001018555821561012c579182015b8281111561012c578251825591602001919060010190610111565b5061013892915061013c565b5090565b61015691905b808211156101385760008155600101610142565b90565b610a59806101686000396000f3fe60806040526004361061013c576000357c0100000000000000000000000000000000000000000000000000000000900480638778b27d116100bd578063cc6cb19a11610081578063cc6cb19a1461039b578063cc6e07b6146103ce578063dcabddb4146103e3578063e15b49e61461040d578063ef09e78f146104225761013c565b80638778b27d146102db5780638dd77fd6146102f057806391246d781461031a5780639ac2a01114610353578063bac28740146103865761013c565b806340ea0a941161010457806340ea0a9414610245578063425fa9d11461024d5780635c39fcc1146102625780635f410b221461029357806377ed63c2146102a85761013c565b806312065fe0146101415780631a8be6b11461016857806323265269146101915780632636b945146101a65780633fafa127146101bb575b600080fd5b34801561014d57600080fd5b50610156610487565b60408051918252519081900360200190f35b34801561017457600080fd5b5061017d61048d565b604080519115158252519081900360200190f35b34801561019d57600080fd5b5061017d6104c2565b3480156101b257600080fd5b506101566105e8565b3480156101c757600080fd5b506101d06105ee565b6040805160208082528351818301528351919283929083019185019080838360005b8381101561020a5781810151838201526020016101f2565b50505050905090810190601f1680156102375780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b61017d61067b565b34801561025957600080fd5b506101566107b0565b34801561026e57600080fd5b506102776107b6565b60408051600160a060020a039092168252519081900360200190f35b34801561029f57600080fd5b506101566107c5565b3480156102b457600080fd5b50610156600480360360208110156102cb57600080fd5b5035600160a060020a03166107cb565b3480156102e757600080fd5b506101566107dd565b3480156102fc57600080fd5b5061017d6004803603602081101561031357600080fd5b50356107e3565b34801561032657600080fd5b5061017d6004803603604081101561033d57600080fd5b50600160a060020a038135169060200135610856565b34801561035f57600080fd5b506101566004803603602081101561037657600080fd5b5035600160a060020a0316610920565b34801561039257600080fd5b5061017d610932565b3480156103a757600080fd5b50610156600480360360208110156103be57600080fd5b5035600160a060020a031661095f565b3480156103da57600080fd5b50610156610971565b3480156103ef57600080fd5b506102776004803603602081101561040657600080fd5b5035610977565b34801561041957600080fd5b5061017d61099f565b34801561042e57600080fd5b506104376109cb565b60408051602080825283518183015283519192839290830191858101910280838360005b8381101561047357818101518382015260200161045b565b505050509050019250505060405180910390f35b30315b90565b600554600090610100900460ff1615156104a657600080fd5b60055460ff16156104b657600080fd5b6000600a541215905090565b60008054600160a060020a031633146104da57600080fd5b600554610100900460ff1615156104f057600080fd5b600a546000136105725760005b60075481101561056857600060078281548110151561051857fe5b6000918252602080832090910154600160a060020a0316808352600690915260408083205490519193508392839282156108fc029291818181858888f15050600190950194506104fd9350505050565b506001905061048a565b60005b6003548110156105e057600060038281548110151561059057fe5b6000918252602080832090910154600160a060020a0316808352600290915260408083205490519193508392839282156108fc029291818181858888f15050600190950194506105759350505050565b506000905090565b60045481565b60018054604080516020600284861615610100026000190190941693909304601f810184900484028201840190925281815292918301828280156106735780601f1061064857610100808354040283529160200191610673565b820191906000526020600020905b81548152906001019060200180831161065657829003601f168201915b505050505081565b60055460009060ff161561068e57600080fd5b6004543031111561069e57600080fd5b3360008181526002602081815260408084208054340190556003805460018181018355919095527fc2575a0e9e593c00f959f8c92f12db2869c3395a3b0502d05e2516446f71f85b909401805473ffffffffffffffffffffffffffffffffffffffff1916861790558051828152845480861615610100026000190116939093049183018290527fe1146780ffc9622ac5594fb51689a6921a7040641a9ba63f7bce5e98925542cb93929182918201908490801561079c5780601f106107715761010080835404028352916020019161079c565b820191906000526020600020905b81548152906001019060200180831161077f57829003601f168201915b50509250505060405180910390a250600190565b600a5481565b600054600160a060020a031681565b60045490565b60096020526000908152604090205481565b600b5481565b60055460009060ff1615156107f757600080fd5b336000908152600960205260409020541561081157600080fd5b336000908152600260205260408120541161082b57600080fd5b50336000908152600960205260409020819055600a80549091019055600b8054600190810190915590565b60008054600160a060020a0316331461086e57600080fd5b60055460ff161561087e57600080fd5b600554610100900460ff161561089357600080fd5b600854303190830111156108a657600080fd5b506007805460018082019092557fa66cc928b5edb82af9bd49922954155ab7b0942694bea4ce44661d9a8736c68801805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a0394909416938417905560009283526006602052604090922080548201905560088054909101905590565b60066020526000908152604090205481565b60008054600160a060020a0316331461094a57600080fd5b506005805461ffff1916610100179055600190565b60026020526000908152604090205481565b60035490565b600780548290811061098557fe5b600091825260209091200154600160a060020a0316905081565b60008054600160a060020a031633146109b757600080fd5b506005805460ff1916600190811790915590565b60606007805480602002602001604051908101604052809291908181526020018280548015610a2357602002820191906000526020600020905b8154600160a060020a03168152600190910190602001808311610a05575b505050505090509056fea165627a7a72305820c44782bba6cf2f36142302ae6ffc562df2fe720fec4032f5c891206aac160c6b0029";

    public static final String FUNC_GETBALANCE = "getBalance";

    public static final String FUNC_GETIFPROJECTEXECUTIONSUCCESS = "getIfProjectExecutionSuccess";

    public static final String FUNC_EXECUTEPROJECT = "executeProject";

    public static final String FUNC_GOALAMOUNT = "goalAmount";

    public static final String FUNC_PROJECTID = "projectId";

    public static final String FUNC_MAKEDONATION = "makeDonation";

    public static final String FUNC_FORAGAINSTSUM = "forAgainstSum";

    public static final String FUNC_INITIATOR = "initiator";

    public static final String FUNC_GETGOALAMOUNT = "getGoalAmount";

    public static final String FUNC_VALIDATION = "validation";

    public static final String FUNC_VOTESCOUNT = "votesCount";

    public static final String FUNC_VOTEFORPROJECTEXECUTION = "voteForProjectExecution";

    public static final String FUNC_ADDEXECUTOR = "addExecutor";

    public static final String FUNC_EXECUTORS = "executors";

    public static final String FUNC_CLOSEVALIDATINGPHASE = "closeValidatingPhase";

    public static final String FUNC_DONATIONS = "donations";

    public static final String FUNC_GETDONATORSCOUNT = "getDonatorsCount";

    public static final String FUNC_EXECUTORSADDRESSES = "executorsAddresses";

    public static final String FUNC_OPENVALIDATIONPHASE = "openValidationPhase";

    public static final String FUNC_GETEXECUTORS = "getExecutors";

    public static final Event DONATEPROJECT_EVENT = new Event("DonateProject", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Utf8String>() {}));
    ;

    @Deprecated
    protected Project(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Project(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Project(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Project(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<BigInteger> getBalance() {
        final Function function = new Function(FUNC_GETBALANCE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<Boolean> getIfProjectExecutionSuccess() {
        final Function function = new Function(FUNC_GETIFPROJECTEXECUTIONSUCCESS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<TransactionReceipt> executeProject() {
        final Function function = new Function(
                FUNC_EXECUTEPROJECT, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> goalAmount() {
        final Function function = new Function(FUNC_GOALAMOUNT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<String> projectId() {
        final Function function = new Function(FUNC_PROJECTID, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> makeDonation(BigInteger weiValue) {
        final Function function = new Function(
                FUNC_MAKEDONATION, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteCall<BigInteger> forAgainstSum() {
        final Function function = new Function(FUNC_FORAGAINSTSUM, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<String> initiator() {
        final Function function = new Function(FUNC_INITIATOR, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<BigInteger> getGoalAmount() {
        final Function function = new Function(FUNC_GETGOALAMOUNT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> validation(String param0) {
        final Function function = new Function(FUNC_VALIDATION, 
                Arrays.<Type>asList(new Address(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> votesCount() {
        final Function function = new Function(FUNC_VOTESCOUNT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> voteForProjectExecution(BigInteger value) {
        final Function function = new Function(
                FUNC_VOTEFORPROJECTEXECUTION, 
                Arrays.<Type>asList(new Int256(value)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> addExecutor(String executor, BigInteger amount) {
        final Function function = new Function(
                FUNC_ADDEXECUTOR, 
                Arrays.<Type>asList(new Address(executor),
                new Uint256(amount)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> executors(String param0) {
        final Function function = new Function(FUNC_EXECUTORS, 
                Arrays.<Type>asList(new Address(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> closeValidatingPhase() {
        final Function function = new Function(
                FUNC_CLOSEVALIDATINGPHASE, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> donations(String param0) {
        final Function function = new Function(FUNC_DONATIONS, 
                Arrays.<Type>asList(new Address(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> getDonatorsCount() {
        final Function function = new Function(FUNC_GETDONATORSCOUNT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<String> executorsAddresses(BigInteger param0) {
        final Function function = new Function(FUNC_EXECUTORSADDRESSES, 
                Arrays.<Type>asList(new Uint256(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> openValidationPhase() {
        final Function function = new Function(
                FUNC_OPENVALIDATIONPHASE, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<List> getExecutors() {
        final Function function = new Function(FUNC_GETEXECUTORS, 
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

    public List<DonateProjectEventResponse> getDonateProjectEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(DONATEPROJECT_EVENT, transactionReceipt);
        ArrayList<DonateProjectEventResponse> responses = new ArrayList<DonateProjectEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            DonateProjectEventResponse typedResponse = new DonateProjectEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.donator = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.projectId = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<DonateProjectEventResponse> donateProjectEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, DonateProjectEventResponse>() {
            @Override
            public DonateProjectEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(DONATEPROJECT_EVENT, log);
                DonateProjectEventResponse typedResponse = new DonateProjectEventResponse();
                typedResponse.log = log;
                typedResponse.donator = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.projectId = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<DonateProjectEventResponse> donateProjectEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(DONATEPROJECT_EVENT));
        return donateProjectEventFlowable(filter);
    }

    @Deprecated
    public static Project load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Project(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Project load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Project(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Project load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Project(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Project load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Project(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Project> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _projectId, BigInteger _goalAmount) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Utf8String(_projectId),
                new Uint256(_goalAmount)));
        return deployRemoteCall(Project.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<Project> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _projectId, BigInteger _goalAmount) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Utf8String(_projectId),
                new Uint256(_goalAmount)));
        return deployRemoteCall(Project.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<Project> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _projectId, BigInteger _goalAmount) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Utf8String(_projectId),
                new Uint256(_goalAmount)));
        return deployRemoteCall(Project.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<Project> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _projectId, BigInteger _goalAmount) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Utf8String(_projectId),
                new Uint256(_goalAmount)));
        return deployRemoteCall(Project.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static class DonateProjectEventResponse {
        public Log log;

        public String donator;

        public String projectId;
    }
}
