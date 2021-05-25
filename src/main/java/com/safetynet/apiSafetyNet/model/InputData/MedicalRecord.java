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
}
