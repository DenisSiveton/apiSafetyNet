package com.safetynet.apiSafetyNet.model.Data;

import java.util.ArrayList;
import java.util.Vector;
import lombok.Data;

@Data
public class PeopleDetailed {

    public PeopleDetailed(String lastName, String phone, int age, ArrayList<String> medications, ArrayList<String> allergies) {
        this.lastName = lastName;
        this.phone = phone;
        this.age = age;
        this.medications = medications;
        this.allergies = allergies;
    }

    private String lastName;

    private String phone;

    private int age;

    private ArrayList<String> medications;

    private ArrayList<String> allergies;

    @Override
    public String toString() {
        return "PeopleDetailed{" +
                "lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", age=" + age +
                ", medications=" + medications +
                ", allergies=" + allergies +
                '}';
    }
}
