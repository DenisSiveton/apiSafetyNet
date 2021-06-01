package com.safetynet.apiSafetyNet.model.Data;

import lombok.Data;

@Data
public class People {

    private String firstName;

    private String lastName;

    private int age;

    public People(String firstName, String lastName, int agePerson) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = agePerson;
    }

    @Override
    public String toString() {
        return "People{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                '}';
    }
}
