package com.safetynet.apiSafetyNet.model.Data;

import java.util.ArrayList;
import java.util.Vector;
import lombok.Data;

@Data
public class PeopleDetailed {

    private String lastName;

    private String phone;

    private int age;

    private ArrayList<String> medications;

    private ArrayList<String> allergies;
}
