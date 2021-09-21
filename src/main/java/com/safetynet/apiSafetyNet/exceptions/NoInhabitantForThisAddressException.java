package com.safetynet.apiSafetyNet.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.constraints.NotNull;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoInhabitantForThisAddressException extends RuntimeException {
    public NoInhabitantForThisAddressException(@NotNull String s) {
        super(s);
    }
}
