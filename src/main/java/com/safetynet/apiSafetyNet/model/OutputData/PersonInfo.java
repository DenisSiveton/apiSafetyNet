package com.safetynet.apiSafetyNet.model.OutputData;

import java.util.ArrayList;
import java.util.Vector;
import lombok.Data;

@Data
public class PersonInfo {

    private String lastName;

    private String address;

    private int age;

    private String email;

    private ArrayList<String> medications;

    private ArrayList<String> allergies;

    @Override
    public String toString() {
        return "PersonInfo{" +
                "lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", medications=" + medications +
                ", allergies=" + allergies +
                '}';
    }
}