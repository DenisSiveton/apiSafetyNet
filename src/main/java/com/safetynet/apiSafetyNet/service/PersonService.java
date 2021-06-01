package com.safetynet.apiSafetyNet.service;

import com.safetynet.apiSafetyNet.model.InputData.Person;
import com.safetynet.apiSafetyNet.model.OutputData.ChildrenInfo;
import com.safetynet.apiSafetyNet.model.OutputData.PersonInfo;
import com.safetynet.apiSafetyNet.repository.PersonRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Data
@Service
public class PersonService {

    private PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person addPerson(Person person) throws FileNotFoundException {
        return personRepository.addPerson(person);
    }

    public void deletePerson(Person person) {
        personRepository.deletePerson(person);
    }

    public Person modifyInfoPerson(Person person) {
        return personRepository.modifyInfoPerson(person);
    }

    public List<String> getMailFromAllPersonsFromCity(String city) {
        return personRepository.getMailFromAllPersonsFromCity(city);
    }

    public PersonInfo getInfoFromPersonWithName(String firstName, String lastName) {
        return personRepository.getInfoFromPersonWithName(firstName, lastName);

    }

    public ChildrenInfo getChildListFromAddress(String address) {
        return personRepository.getChildListFromAddress(address);
    }

    public ArrayList<String> getPhoneNumberListFromFireStationNumber(String fireStation) {
        return personRepository.getPhoneNumberListFromFireStationNumber(fireStation);
    }
}
