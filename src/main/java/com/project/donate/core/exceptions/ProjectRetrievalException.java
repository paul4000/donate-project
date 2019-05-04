package com.project.donate.core.exceptions;

public class ProjectRetrievalException extends RuntimeException {

    @Override
    public String getMessage() {
        return "Can't get project file from resource";
    }
}
