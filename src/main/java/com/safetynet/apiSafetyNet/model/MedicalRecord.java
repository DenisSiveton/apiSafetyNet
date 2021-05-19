package com.safetynet.apiSafetyNet.model;

import java.util.Vector;
import lombok.Data;

@Data
public class MedicalRecord {

    private String firstName;

    private String lastName;

    private String birthdate;

    private Vector<String> medications;

    private Vector<String> allergies;
}
