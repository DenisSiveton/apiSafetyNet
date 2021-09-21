package com.safetynet.apiSafetyNet.model.Data;

import lombok.Data;

@Data
public class Inhabitant {

    public Inhabitant(String firstName, String lastName, String address, String phone){
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
    }
    private String firstName;

    private String lastName;

    private String address;

    private String phone;

    @Override
    public String toString() {
        return "Inhabitant{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}