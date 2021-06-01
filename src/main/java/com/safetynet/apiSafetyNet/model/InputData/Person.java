package com.safetynet.apiSafetyNet.model.InputData;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jsoniter.annotation.JsonIgnore;
import lombok.Data;

@Data
public class Person {

    @JsonIgnore
    private String firstName;

    @JsonIgnore
    private String lastName;

    private String address;

    private String city;

    private String zip;

    private String phone;

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
