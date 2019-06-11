package com.project.donate.core.exceptions.blockchain;

public class ProjectInfoException extends RuntimeException {

    @Override
    public String getMessage() {
        return "Error during retrieving project info from blockchain";
    }
}
