package com.project.donate.core.exceptions.blockchain;

public class OpeningProjectException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Error occurred while deploying Project contract.";
    }
}
