package com.safetynet.apiSafetyNet.model.InputData;

import java.util.ArrayList;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class MedicalRecord {

    @NotNull
    @Pattern(regexp = "^[A-Za-z]+$")
    private String firstName;

    @NotNull
    @Pattern(regexp = "^[A-Za-z]+$")
    private String lastName;

    @NotNull
    @Pattern(regexp = "^(1[0-2]|0[1-9])/(3[01]|[12][0-9]|0[1-9])/[0-9]{4}$", message = "BirthDate format is not accepted. Corrected format \"MM/DD/YYYY\"")
    private String birthDate;

    @Pattern(regexp = "^[A-Za-z0-9]+$")
    private ArrayList<String> medications;

    @Pattern(regexp = "^[A-Za-z0-9]+$")
    private ArrayList<String> allergies;

    public MedicalRecord() {
    }

    public MedicalRecord(String firstName, String lastName, String birthDate, ArrayList<String> medications, ArrayList<String> allergies) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.medications = medications;
        this.allergies = allergies;
    }

    @Override
    public String toString() {
        return "MedicalRecord{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", medications=" + medications +
                ", allergies=" + allergies +
                '}';
    }
}
