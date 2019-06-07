pragma solidity ^0.5.3;

contract Project {

    address public initiator;
    string public projectId;
    mapping (address => uint) public donations;
    address[] donators;

    uint public goalAmount;

    bool validatingPhase;
    bool executionPhase;

    mapping (address => uint) public executors;
    address[] public executorsAddresses;

    uint distributedFunds;

    mapping(address => int) public validation;
    int forAgainstSum;

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

        donations[msg.sender] += msg.value;
        donators.push(msg.sender);

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
        return true;
    }

    function openValidationPhase() public initiatorAllowed returns (bool openedPhase) {
        validatingPhase = true;
        return true;
    }

    function closeValidatingPhase() public initiatorAllowed returns (bool closedPhase) {
        validatingPhase = false;
        executionPhase = true;
        return true;
    }

    function executeProject() public initiatorAllowed returns (bool ifDonted) {

        require(executionPhase);

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

}