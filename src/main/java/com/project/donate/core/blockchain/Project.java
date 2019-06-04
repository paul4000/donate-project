package com.project.donate.core.blockchain;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
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
    private static final String BINARY = "608060405234801561001057600080fd5b506040516108873803806108878339810180604052604081101561003357600080fd5b81019080805164010000000081111561004b57600080fd5b8201602081018481111561005e57600080fd5b815164010000000081118282018710171561007857600080fd5b505060209182015160008054600160a060020a03191633179055815191945092506100a991600191908501906100be565b50600455506005805461ffff19169055610159565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106100ff57805160ff191683800117855561012c565b8280016001018555821561012c579182015b8281111561012c578251825591602001919060010190610111565b5061013892915061013c565b5090565b61015691905b808211156101385760008155600101610142565b90565b61071f806101686000396000f3fe6080604052600436106100c4576000357c01000000000000000000000000000000000000000000000000000000009004806391246d781161008157806391246d78146101935780639ac2a011146101cc578063bac28740146101ff578063cc6cb19a14610214578063dcabddb414610247578063e15b49e61461028d576100c4565b806312065fe0146100c957806323265269146100f05780632636b9451461011957806340ea0a941461012e57806377ed63c2146101365780638dd77fd614610169575b600080fd5b3480156100d557600080fd5b506100de6102a2565b60408051918252519081900360200190f35b3480156100fc57600080fd5b506101056102a8565b604080519115158252519081900360200190f35b34801561012557600080fd5b506100de6103ce565b6101056103d4565b34801561014257600080fd5b506100de6004803603602081101561015957600080fd5b5035600160a060020a0316610509565b34801561017557600080fd5b506101056004803603602081101561018c57600080fd5b503561051b565b34801561019f57600080fd5b50610105600480360360408110156101b657600080fd5b50600160a060020a038135169060200135610584565b3480156101d857600080fd5b506100de600480360360208110156101ef57600080fd5b5035600160a060020a031661064e565b34801561020b57600080fd5b50610105610660565b34801561022057600080fd5b506100de6004803603602081101561023757600080fd5b5035600160a060020a031661068d565b34801561025357600080fd5b506102716004803603602081101561026a57600080fd5b503561069f565b60408051600160a060020a039092168252519081900360200190f35b34801561029957600080fd5b506101056106c7565b30315b90565b60008054600160a060020a031633146102c057600080fd5b600554610100900460ff1615156102d657600080fd5b600a546000136103585760005b60075481101561034e5760006007828154811015156102fe57fe5b6000918252602080832090910154600160a060020a0316808352600690915260408083205490519193508392839282156108fc029291818181858888f15050600190950194506102e39350505050565b50600190506102a5565b60005b6003548110156103c657600060038281548110151561037657fe5b6000918252602080832090910154600160a060020a0316808352600290915260408083205490519193508392839282156108fc029291818181858888f150506001909501945061035b9350505050565b506000905090565b60045481565b60055460009060ff16156103e757600080fd5b600454303111156103f757600080fd5b3360008181526002602081815260408084208054340190556003805460018181018355919095527fc2575a0e9e593c00f959f8c92f12db2869c3395a3b0502d05e2516446f71f85b909401805473ffffffffffffffffffffffffffffffffffffffff1916861790558051828152845480861615610100026000190116939093049183018290527fe1146780ffc9622ac5594fb51689a6921a7040641a9ba63f7bce5e98925542cb9392918291820190849080156104f55780601f106104ca576101008083540402835291602001916104f5565b820191906000526020600020905b8154815290600101906020018083116104d857829003601f168201915b50509250505060405180910390a250600190565b60096020526000908152604090205481565b60055460009060ff16151561052f57600080fd5b336000908152600960205260409020541561054957600080fd5b336000908152600260205260408120541161056357600080fd5b50336000908152600960205260409020819055600a80549091019055600190565b60008054600160a060020a0316331461059c57600080fd5b60055460ff16156105ac57600080fd5b600554610100900460ff16156105c157600080fd5b600854303190830111156105d457600080fd5b506007805460018082019092557fa66cc928b5edb82af9bd49922954155ab7b0942694bea4ce44661d9a8736c68801805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a0394909416938417905560009283526006602052604090922080548201905560088054909101905590565b60066020526000908152604090205481565b60008054600160a060020a0316331461067857600080fd5b506005805461ffff1916610100179055600190565b60026020526000908152604090205481565b60078054829081106106ad57fe5b600091825260209091200154600160a060020a0316905081565b60008054600160a060020a031633146106df57600080fd5b506005805460ff191660019081179091559056fea165627a7a7230582084f9847f4bd3a69f28834c7fb66e6f4464079d5a879d930a8fd64a6eabe69f0a0029";

    public static final String FUNC_GETBALANCE = "getBalance";

    public static final String FUNC_EXECUTEPROJECT = "executeProject";

    public static final String FUNC_GOALAMOUNT = "goalAmount";

    public static final String FUNC_MAKEDONATION = "makeDonation";

    public static final String FUNC_VALIDATION = "validation";

    public static final String FUNC_VOTEFORPROJECTEXECUTION = "voteForProjectExecution";

    public static final String FUNC_ADDEXECUTOR = "addExecutor";

    public static final String FUNC_EXECUTORS = "executors";

    public static final String FUNC_CLOSEVALIDATINGPHASE = "closeValidatingPhase";

    public static final String FUNC_DONATIONS = "donations";

    public static final String FUNC_EXECUTORSADDRESSES = "executorsAddresses";

    public static final String FUNC_OPENVALIDATIONPHASE = "openValidationPhase";

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

    public RemoteCall<TransactionReceipt> makeDonation(BigInteger weiValue) {
        final Function function = new Function(
                FUNC_MAKEDONATION, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteCall<BigInteger> validation(String param0) {
        final Function function = new Function(FUNC_VALIDATION, 
                Arrays.<Type>asList(new Address(param0)),
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