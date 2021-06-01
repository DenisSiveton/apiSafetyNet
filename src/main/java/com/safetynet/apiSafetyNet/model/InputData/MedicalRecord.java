package com.safetynet.apiSafetyNet.model.InputData;

import java.util.ArrayList;
import lombok.Data;

@Data
public class MedicalRecord {

    private String firstName;

    private String lastName;

    private String birthDate;

    private ArrayList<String> medications;

    private ArrayList<String> allergies;

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
