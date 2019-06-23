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
    private static final String BINARY = "608060405234801561001057600080fd5b50604051610d9d380380610d9d8339810180604052604081101561003357600080fd5b81019080805164010000000081111561004b57600080fd5b8201602081018481111561005e57600080fd5b815164010000000081118282018710171561007857600080fd5b5050602091820151600080546201000060b060020a031916336201000002179055815191945092506100b091600191908501906100c5565b50600255506000805461ffff19169055610160565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061010657805160ff1916838001178555610133565b82800160010185558215610133579182015b82811115610133578251825591602001919060010190610118565b5061013f929150610143565b5090565b61015d91905b8082111561013f5760008155600101610149565b90565b610c2e8061016f6000396000f3fe60806040526004361061019e576000357c0100000000000000000000000000000000000000000000000000000000900480638dd77fd6116100ee578063bac28740116100a7578063dcabddb411610081578063dcabddb4146104b4578063e15b49e6146104de578063e26449c4146104f3578063ef09e78f146105085761019e565b8063bac2874014610457578063cc6cb19a1461046c578063cc6e07b61461049f5761019e565b80638dd77fd61461038257806391246d78146103ac57806395e1b666146103e55780639a874695146103fa5780639ac2a0111461040f5780639fc963de146104425761019e565b806340ea0a941161015b5780635f410b22116101355780635f410b221461031057806369823d971461032557806377ed63c21461033a5780638778b27d1461036d5761019e565b806340ea0a94146102c2578063425fa9d1146102ca5780635c39fcc1146102df5761019e565b806312065fe0146101a95780631a8be6b1146101d057806323265269146101f95780632636b9451461020e57806337882277146102235780633fafa12714610238575b6101a661056d565b50005b3480156101b557600080fd5b506101be6106c7565b60408051918252519081900360200190f35b3480156101dc57600080fd5b506101e56106cc565b604080519115158252519081900360200190f35b34801561020557600080fd5b506101e56106ff565b34801561021a57600080fd5b506101be610849565b34801561022f57600080fd5b506101be61084f565b34801561024457600080fd5b5061024d610855565b6040805160208082528351818301528351919283929083019185019080838360005b8381101561028757818101518382015260200161026f565b50505050905090810190601f1680156102b45780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b6101e561056d565b3480156102d657600080fd5b506101be6108e2565b3480156102eb57600080fd5b506102f46108e8565b60408051600160a060020a039092168252519081900360200190f35b34801561031c57600080fd5b506101be6108fd565b34801561033157600080fd5b506101e5610903565b34801561034657600080fd5b506101be6004803603602081101561035d57600080fd5b5035600160a060020a0316610933565b34801561037957600080fd5b506101be610945565b34801561038e57600080fd5b506101e5600480360360208110156103a557600080fd5b503561094b565b3480156103b857600080fd5b506101e5600480360360408110156103cf57600080fd5b50600160a060020a0381351690602001356109bc565b3480156103f157600080fd5b506101be610a8c565b34801561040657600080fd5b506101be610a92565b34801561041b57600080fd5b506101be6004803603602081101561043257600080fd5b5035600160a060020a0316610abb565b34801561044e57600080fd5b506101e5610acd565b34801561046357600080fd5b506101e5610adb565b34801561047857600080fd5b506101be6004803603602081101561048f57600080fd5b5035600160a060020a0316610b21565b3480156104ab57600080fd5b506101be610b33565b3480156104c057600080fd5b506102f4600480360360208110156104d757600080fd5b5035610b39565b3480156104ea57600080fd5b506101e5610b61565b3480156104ff57600080fd5b506101e5610b97565b34801561051457600080fd5b5061051d610ba0565b60408051602080825283518183015283519192839290830191858101910280838360005b83811015610559578181015183820152602001610541565b505050509050019250505060405180910390f35b6000805460ff161561057e57600080fd5b6002543031111561058e57600080fd5b3360009081526004602052604090205415156105f457600680546001810182556000919091527ff652222313e28459528d920b65115c16c04f3efc82aaedc97be59f3f377c0d3f01805473ffffffffffffffffffffffffffffffffffffffff1916331790555b336000818152600460209081526040918290208054349081019091556003805490910190558151818152600180546002600019610100838516150201909116049282018390527fe1146780ffc9622ac5594fb51689a6921a7040641a9ba63f7bce5e98925542cb9390928291820190849080156106b25780601f10610687576101008083540402835291602001916106b2565b820191906000526020600020905b81548152906001019060200180831161069557829003601f168201915b50509250505060405180910390a25060015b90565b303190565b60008054610100900460ff1615156106e357600080fd5b60005460ff16156106f357600080fd5b60006009541215905090565b60008054620100009004600160a060020a0316331461071d57600080fd5b600054610100900460ff16151561073357600080fd5b6000600a54610740610b33565b6009805492909103918201908190559091506000136107d15760005b6007548110156107c757600060078281548110151561077757fe5b6000918252602080832090910154600160a060020a0316808352600590915260408083205490519193508392839282156108fc029291818181858888f150506001909501945061075c9350505050565b5060019150610845565b60005b60065481101561083f5760006006828154811015156107ef57fe5b6000918252602080832090910154600160a060020a0316808352600490915260408083205490519193508392839282156108fc029291818181858888f15050600190950194506107d49350505050565b50600091505b5090565b60025481565b60035481565b60018054604080516020600284861615610100026000190190941693909304601f810184900484028201840190925281815292918301828280156108da5780601f106108af576101008083540402835291602001916108da565b820191906000526020600020905b8154815290600101906020018083116108bd57829003601f168201915b505050505081565b60095481565b600054620100009004600160a060020a031681565b60025490565b600080600a54610911610b33565b14905060008061091f610a92565b11159050818061092c5750805b9250505090565b60086020526000908152604090205481565b600a5481565b6000805460ff16151561095d57600080fd5b336000908152600860205260409020541561097757600080fd5b336000908152600460205260408120541161099157600080fd5b50336000908152600860205260409020819055600980549091019055600a8054600190810190915590565b60008054620100009004600160a060020a031633146109da57600080fd5b60005460ff16156109ea57600080fd5b600054610100900460ff16156109ff57600080fd5b600c5430319083011115610a1257600080fd5b506007805460018082019092557fa66cc928b5edb82af9bd49922954155ab7b0942694bea4ce44661d9a8736c68801805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a03949094169384179055600092835260056020526040909220805482019055600c8054909101905590565b600b5481565b600b5460009042036203f480811015610ab1576203f4800390506106c4565b60009150506106c4565b60056020526000908152604090205481565b600054610100900460ff1681565b60008054620100009004600160a060020a03163314610af957600080fd5b610b01610903565b1515610b0c57600080fd5b506000805461ffff1916610100179055600190565b60046020526000908152604090205481565b60065490565b6007805482908110610b4757fe5b600091825260209091200154600160a060020a0316905081565b60008054620100009004600160a060020a03163314610b7f57600080fd5b506000805460ff1916600190811790915542600b5590565b60005460ff1681565b60606007805480602002602001604051908101604052809291908181526020018280548015610bf857602002820191906000526020600020905b8154600160a060020a03168152600190910190602001808311610bda575b505050505090509056fea165627a7a72305820c073ae6554a88456cebea8da34de551f9d5b30e494fa0ccc9b7727550bc5c85d0029";

    public static final String FUNC_GETBALANCE = "getBalance";

    public static final String FUNC_GETIFPROJECTEXECUTIONSUCCESS = "getIfProjectExecutionSuccess";

    public static final String FUNC_EXECUTEPROJECT = "executeProject";

    public static final String FUNC_GOALAMOUNT = "goalAmount";

    public static final String FUNC_FINALGAINEDAMOUNT = "finalGainedAmount";

    public static final String FUNC_PROJECTID = "projectId";

    public static final String FUNC_MAKEDONATION = "makeDonation";

    public static final String FUNC_FORAGAINSTSUM = "forAgainstSum";

    public static final String FUNC_INITIATOR = "initiator";

    public static final String FUNC_GETGOALAMOUNT = "getGoalAmount";

    public static final String FUNC_CANBEEXECUTED = "canBeExecuted";

    public static final String FUNC_VALIDATION = "validation";

    public static final String FUNC_VOTESCOUNT = "votesCount";

    public static final String FUNC_VOTEFORPROJECTEXECUTION = "voteForProjectExecution";

    public static final String FUNC_ADDEXECUTOR = "addExecutor";

    public static final String FUNC_TIMEOFVALIDATIONPHASESTART = "timeOfValidationPhaseStart";

    public static final String FUNC_GETVALIDATIONTIMELEFT = "getValidationTimeLeft";

    public static final String FUNC_EXECUTORS = "executors";

    public static final String FUNC_EXECUTIONPHASE = "executionPhase";

    public static final String FUNC_CLOSEVALIDATINGPHASE = "closeValidatingPhase";

    public static final String FUNC_DONATIONS = "donations";

    public static final String FUNC_GETDONATORSCOUNT = "getDonatorsCount";

    public static final String FUNC_EXECUTORSADDRESSES = "executorsAddresses";

    public static final String FUNC_OPENVALIDATIONPHASE = "openValidationPhase";

    public static final String FUNC_VALIDATINGPHASE = "validatingPhase";

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

    public RemoteCall<BigInteger> finalGainedAmount() {
        final Function function = new Function(FUNC_FINALGAINEDAMOUNT, 
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

    public RemoteCall<Boolean> canBeExecuted() {
        final Function function = new Function(FUNC_CANBEEXECUTED, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
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
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
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

    public RemoteCall<BigInteger> timeOfValidationPhaseStart() {
        final Function function = new Function(FUNC_TIMEOFVALIDATIONPHASESTART, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> getValidationTimeLeft() {
        final Function function = new Function(FUNC_GETVALIDATIONTIMELEFT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> executors(String param0) {
        final Function function = new Function(FUNC_EXECUTORS, 
                Arrays.<Type>asList(new Address(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<Boolean> executionPhase() {
        final Function function = new Function(FUNC_EXECUTIONPHASE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
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

    public RemoteCall<Boolean> validatingPhase() {
        final Function function = new Function(FUNC_VALIDATINGPHASE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
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
