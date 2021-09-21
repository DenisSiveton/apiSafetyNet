package com.safetynet.apiSafetyNet.exceptions;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class MedicalRecordNotFoundException extends Throwable {
    public MedicalRecordNotFoundException(@NotNull @Pattern(regexp = "^[A-Za-z]+$") String s) {
    }
}
