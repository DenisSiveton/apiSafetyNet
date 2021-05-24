package com.safetynet.apiSafetyNet.model.viewModel.Data;

import java.util.Vector;
import lombok.Data;

@Data
public class PeopleDetailed {

    private String lastName;

    private String phone;

    private int age;

    private Vector<String> medications;

    private Vector<String> allergies;
}
