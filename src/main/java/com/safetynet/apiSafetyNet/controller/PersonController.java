package com.safetynet.apiSafetyNet.controller;

import com.safetynet.apiSafetyNet.model.InputData.Person;
import com.safetynet.apiSafetyNet.model.OutputData.ChildrenInfo;
import com.safetynet.apiSafetyNet.model.OutputData.PersonInfo;
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
    public ChildrenInfo getChildListFromAddress(@RequestParam(name = "address") String address) {
        return personService.getChildListFromAddress(address);
    }
    @GetMapping("/phoneAlert?firestation=<firestation_number>")
    public List<String> getPhoneNumberListFromFireStationNumber(@RequestParam(name = "firestation") int fireStationNumber) {
        return personService.getPhoneNumberListFromFireStationNumber(fireStationNumber);
    }

    @GetMapping("/communityEmail?city=<city>")
    public List<String> getMailFromPersonFromCity(@RequestParam(name = "city") String city) {
        return personService.getMailFromAllPersonsFromCity(city);
    }

    @GetMapping("/personInfo?firstName=<firstName>&lastName=<lastName>")
    public PersonInfo getIntoFromPersonWithName(@RequestParam(name = "firstName") String firstName, @RequestParam(name = "lastName") String lastName) {
        return personService.getIntoFromPersonWithName(firstName, lastName);
    }
}
