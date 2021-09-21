package com.safetynet.apiSafetyNet.model.OutputData;

import java.util.ArrayList;
import java.util.Vector;
import lombok.Data;

@Data
public class PersonInfo {
    public PersonInfo(String lastName, String address, int age, String email, ArrayList<String> medications, ArrayList<String> allergies) {
        this.lastName = lastName;
        this.address = address;
        this.age = age;
        this.email = email;
        this.medications = medications;
        this.allergies = allergies;
    }

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
