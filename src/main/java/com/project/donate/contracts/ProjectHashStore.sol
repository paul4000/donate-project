pragma solidity ^0.5.3; 

contract ProjectHashStore {
    
    struct Project {
        bytes32 projectHash; 
        address owner;
        bool exist;
    }
    
    mapping (address => Project) public projectStore; 
    
    modifier projectNotOpenedYet(address projectAddress) {
        require(!projectStore[projectAddress].exist);
        _; 
    }
    
    modifier projectExist(address projectAddress) {
        require(projectStore[projectAddress].exist);
        _; 
    }
    
    function registerProject(bytes32 _projectHash, address _projectAddress) projectNotOpenedYet(_projectAddress) public {
        
        Project memory newProject = Project(_projectHash, msg.sender, true);
        projectStore[_projectAddress] = newProject; 
    }
    
    function isProjectVersionTheSame(address _projectAddress, bytes32 _hashToCheck) projectExist(_projectAddress) public view returns (bool result) {
        return projectStore[_projectAddress].projectHash == _hashToCheck;
    }
    
}