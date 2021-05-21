package com.safetynet.apiSafetyNet.service;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.safetynet.apiSafetyNet.model.Person;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
public class PersonService {

    public JSONPObject addPerson(Person person) {
        return null;
    }

    public void deletePerson(Person person) {
    }

    public JSONPObject modifyInfoPerson(Person person) {
        return null;
    }

    public JSONPObject getMailFromAllPersonsFromCity(String city) {
        return null;
    }

    public JSONPObject getIntoFromPersonWithName(String firstName, String lastName) {
        return null;

    }

    public JSONPObject getChildListFromAddress(String address) {
        return null;
    }

    public JSONPObject getPhoneNumberListFromFireStationNumber(int fireStation) {
        return null;
    }
}
