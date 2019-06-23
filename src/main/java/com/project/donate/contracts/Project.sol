pragma solidity ^0.5.3;

contract Project {

    // state variables
    bool public validatingPhase;
    bool public executionPhase;

    // project data
    address public initiator;
    string public projectId;
    uint public goalAmount;
    uint public finalGainedAmount;

    // donations data
    mapping (address => uint) public donations;
    mapping (address => uint) public executors;

    address[] private donators;
    address[] public executorsAddresses;


    // validation data
    mapping(address => int) public validation;
    int public forAgainstSum;
    uint public votesCount;
    uint public timeOfValidationPhaseStart;

    uint distributedFunds;

    modifier initiatorAllowed() {
        require(msg.sender == initiator);
        _;
    }

    modifier notValidatingPhase() {
        require(!validatingPhase);
        _;
    }

    modifier canVoteForProjectExecution() {
        require(validatingPhase);
        require(validation[msg.sender] == 0);
        require(donations[msg.sender] > 0);
        _;
    }

    modifier notExecutionPhase() {
        require(!executionPhase);
        _;
    }

    constructor(string memory _projectId, uint _goalAmount) public {
        initiator = msg.sender;
        projectId = _projectId;
        goalAmount = _goalAmount;
        validatingPhase = false;
        executionPhase = false;
    }

    event DonateProject(address indexed donator, string projectId);

    function makeDonation() payable public notValidatingPhase returns (bool ifDonated) {

        require(address(this).balance <= goalAmount);

        if(donations[msg.sender] == 0) {
            donators.push(msg.sender);
        }

        donations[msg.sender] += msg.value;
        finalGainedAmount += msg.value;

        emit DonateProject(msg.sender, projectId);
        return true;
    }

    function addExecutor(address executor, uint amount) public initiatorAllowed notValidatingPhase notExecutionPhase returns (bool result){

        require((distributedFunds + amount) <= address(this).balance);

        executorsAddresses.push(executor);
        executors[executor] += amount;
        distributedFunds += amount;
        return true;
    }

    function voteForProjectExecution(int value) public canVoteForProjectExecution returns (bool result) {
        validation[msg.sender] = value;
        forAgainstSum += value;
        votesCount += 1;
        return true;
    }

    function openValidationPhase() public initiatorAllowed returns (bool openedPhase) {
        validatingPhase = true;
        timeOfValidationPhaseStart = block.timestamp;
        return true;
    }

    function closeValidatingPhase() public initiatorAllowed returns (bool closedPhase) {
        require(canBeExecuted());

        validatingPhase = false;
        executionPhase = true;
        return true;
    }

    function executeProject() public initiatorAllowed returns (bool ifDonted) {

        require(executionPhase);

        uint howManyNotVoted = getDonatorsCount() - votesCount;

        forAgainstSum += int(howManyNotVoted);

        if(forAgainstSum >= 0) {

            for(uint i=0; i < executorsAddresses.length; i++) {

                address e = executorsAddresses[i];
                address payable eSent = address(uint160(e));
                eSent.send(executors[e]);
            }

            ifDonted = true;

        } else {

            for(uint i=0; i < donators.length; i++) {

                address d = donators[i];
                address payable dSent = address(uint160(d));
                dSent.send(donations[d]);

            }

            ifDonted = false;
        }
    }

    function getBalance() public view returns (uint balance) {
        return address(this).balance;
    }

    function getGoalAmount() public view returns (uint amount) {
        return goalAmount;
    }

    function getIfProjectExecutionSuccess() public view returns (bool result) {

        require(executionPhase);
        require(!validatingPhase);

        return (forAgainstSum >= 0);
    }

    function getExecutors() public view returns (address[] memory  exec) {
        return executorsAddresses;
    }

    function getDonatorsCount() public view returns (uint256 num) {
        return donators.length;
    }

    function getValidationTimeLeft() public view returns (uint result) {
        uint timeDifference = now - timeOfValidationPhaseStart;

        if(3 days > timeDifference) {
            return (3 days - timeDifference);
        } else {
            return 0;
        }
    }

    function canBeExecuted() public view returns (bool result) {
        bool ifEveryoneVoted = getDonatorsCount() == votesCount;
        bool ifValidationTimeIsUp = getValidationTimeLeft() <= 0;

        return ifEveryoneVoted || ifValidationTimeIsUp;
    }

    function() external payable {
        makeDonation();
    }

}