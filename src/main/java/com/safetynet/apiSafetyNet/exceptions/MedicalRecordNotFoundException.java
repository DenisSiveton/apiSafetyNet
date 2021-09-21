package com.safetynet.apiSafetyNet.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
@ResponseStatus(HttpStatus.NOT_FOUND)
public class MedicalRecordNotFoundException extends RuntimeException {
    public MedicalRecordNotFoundException(@NotNull @Pattern(regexp = "^[A-Za-z]+$") String s) {
        super(s);
    }
}
