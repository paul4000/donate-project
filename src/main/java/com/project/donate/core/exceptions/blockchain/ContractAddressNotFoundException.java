package com.project.donate.core.exceptions.blockchain;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ContractAddressNotFoundException extends RuntimeException {

    @Override
    public String getMessage() {
        return "Contract address not found in configuration. This may mean that contract is not deployed.";
    }

}
