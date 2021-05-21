package com.safetynet.apiSafetyNet.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.safetynet.apiSafetyNet.model.Person;
import com.safetynet.apiSafetyNet.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class PersonController {

    @Autowired
    private PersonService personService;

    @PostMapping("/person")
    public JSONPObject addPerson(@RequestBody Person person) {
        return personService.addPerson(person);
    }

    @PatchMapping("/person")
    public JSONPObject modifyInfoPerson(@RequestBody Person person) {
        return personService.modifyInfoPerson(person);
    }

    @DeleteMapping("/person")
    public void deletePerson(@RequestBody Person person) {
        personService.deletePerson(person);
    }

    @GetMapping("/childAlert?address=<address>")
    public JSONPObject getChildListFromAddress(@PathVariable("address") final String address) {
        return personService.getChildListFromAddress(address);
    }
    @GetMapping("/phoneAlert?firestation=<firestation_number>")
    public JSONPObject getPhoneNumberListFromFireStationNumber(@PathVariable("firestation") final int fireStation) {
        return personService.getPhoneNumberListFromFireStationNumber(fireStation);
    }

    @GetMapping("/communityEmail?city=<city>")
    public JSONPObject getMailFromPersonFromCity(@PathVariable("city") final String city) {
        return personService.getMailFromAllPersonsFromCity(city);
    }

    @GetMapping("/personInfo?firstName=<firstName>&lastName=<lastName>")
    public JSONPObject getIntoFromPersonWithName(@PathVariable("firstName") final String firstName, @PathVariable("lastName") final String lastName) {
        return personService.getIntoFromPersonWithName(firstName, lastName);
    }
}
