package com.project.donate.core.exceptions.blockchain;

public class ContractAddressNotFoundException extends RuntimeException {

    @Override
    public String getMessage() {
        return "Contract address not found in configuration. This may mean that contract is not deployed.";
    }

}
