package com.safetynet.apiSafetyNet.model.viewModel.OutputData;

import java.util.Vector;
import lombok.Data;

@Data
public class PersonInfo {

    private String lastName;

    private String address;

    private int age;

    private String email;

    private Vector<String> medications;

    private Vector<String> allergies;
}
