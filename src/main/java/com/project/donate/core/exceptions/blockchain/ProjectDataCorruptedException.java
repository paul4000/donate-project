package com.project.donate.core.exceptions.blockchain;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ProjectDataCorruptedException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Project data was corrupted";
    }
}
