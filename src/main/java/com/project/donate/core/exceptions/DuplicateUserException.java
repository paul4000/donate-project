package com.project.donate.core.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)

public class DuplicateUserException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Given username or email exists in app already.";
    }
}
