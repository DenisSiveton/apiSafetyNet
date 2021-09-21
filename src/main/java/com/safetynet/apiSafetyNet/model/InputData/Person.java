package com.safetynet.apiSafetyNet.model.InputData;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class Person {
    public Person() {
    }

    public Person(String firstName, String lastName, String address, String city, String zip, String phone, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.zip = zip;
        this.phone = phone;
        this.email = email;
    }

    @NotNull
    @Pattern(regexp = "^[A-Za-z]+$")
    private String firstName;

    @NotNull
    @Pattern(regexp = "^[A-Za-z]+$")
    private String lastName;

    @NotNull
    private String address;

    @NotNull
    @Pattern(regexp = "^[A-Za-z]+$")
    private String city;

    @NotNull
    @Pattern(regexp = "^[0-9]+$")
    private String zip;

    @NotNull
    @Pattern(regexp = "\\d{3}-\\d{3}-\\d{4}", message = "Phone format is not accepted. Corrected format \"XXX-XXX-XXXX\"")
    private String phone;

    @Email
    private String email;

    @Override
    public String toString() {
        return "Person{" +
                "firstName\"=\"" + firstName + "\"" +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", zip=" + zip +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
