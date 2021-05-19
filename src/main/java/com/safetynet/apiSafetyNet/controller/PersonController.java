package com.safetynet.apiSafetyNet.controller;

import com.safetynet.apiSafetyNet.model.Person;
import com.safetynet.apiSafetyNet.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class PersonController {

    @Autowired
    private PersonService personService;

    @PostMapping("/person")
    public Person addPerson() {
        return personService.addPerson();
    }

    @PatchMapping("/person")
    public Person modifyInfoPerson() {
        return personService.modifyInfoPerson();
    }

    @DeleteMapping("/person")
    public void deletePerson() {
        personService.deletePerson();
    }
}
