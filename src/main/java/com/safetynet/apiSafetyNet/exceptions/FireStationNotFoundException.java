package com.safetynet.apiSafetyNet.exceptions;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class FireStationNotFoundException extends Throwable {
    public FireStationNotFoundException(@NotNull @Pattern(regexp = "^[A-Za-z0-9]+$") String s) {
    }
}
