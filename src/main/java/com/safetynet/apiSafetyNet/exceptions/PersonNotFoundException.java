package com.safetynet.apiSafetyNet.exceptions;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class PersonNotFoundException extends Throwable {
    public PersonNotFoundException(@NotNull @Pattern(regexp = "^[A-Za-z]+$") String s) {
    }
}
