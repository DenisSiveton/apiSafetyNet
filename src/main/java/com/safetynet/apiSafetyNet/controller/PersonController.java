package com.safetynet.apiSafetyNet.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.safetynet.apiSafetyNet.model.Person;
import com.safetynet.apiSafetyNet.model.viewModel.OutputData.ChildrenInfo;
import com.safetynet.apiSafetyNet.model.viewModel.OutputData.PersonInfo;
import com.safetynet.apiSafetyNet.service.PersonService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonController {

    private PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping("/person")
    public Person addPerson(@RequestBody Person person) {
        return personService.addPerson(person);
    }

    @PatchMapping("/person")
    public Person modifyInfoPerson(@RequestBody Person person) {
        return personService.modifyInfoPerson(person);
    }

    @DeleteMapping("/person")
    public void deletePerson(@RequestBody Person person) {
        personService.deletePerson(person);
    }

    @GetMapping("/childAlert?address=<address>")
    public ChildrenInfo getChildListFromAddress(@PathVariable("address") final String address) {
        return personService.getChildListFromAddress(address);
    }
    @GetMapping("/phoneAlert?firestation=<firestation_number>")
    public List<String> getPhoneNumberListFromFireStationNumber(@PathVariable("firestation") final int fireStation) {
        return personService.getPhoneNumberListFromFireStationNumber(fireStation);
    }

    @GetMapping("/communityEmail?city=<city>")
    public List<String> getMailFromPersonFromCity(@PathVariable("city") final String city) {
        return personService.getMailFromAllPersonsFromCity(city);
    }

    @GetMapping("/personInfo?firstName=<firstName>&lastName=<lastName>")
    public PersonInfo getIntoFromPersonWithName(@PathVariable("firstName") final String firstName, @PathVariable("lastName") final String lastName) {
        return personService.getIntoFromPersonWithName(firstName, lastName);
    }
}
