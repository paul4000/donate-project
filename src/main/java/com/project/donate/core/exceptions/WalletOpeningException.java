package com.project.donate.core.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class WalletOpeningException extends RuntimeException {

    @Override
    public String getMessage() {
        return "Can't get credentials from wallet. Wrong path or incorrect password.";
    }

}
