package com.safetynet.apiSafetyNet.service;

import com.safetynet.apiSafetyNet.model.InputData.Person;
import com.safetynet.apiSafetyNet.model.OutputData.ChildrenInfo;
import com.safetynet.apiSafetyNet.model.OutputData.PersonInfo;
import com.safetynet.apiSafetyNet.repository.PersonRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
public class PersonService {

    private PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person addPerson(Person person) {
        return null;
    }

    public void deletePerson(Person person) {
    }

    public Person modifyInfoPerson(Person person) {
        return null;
    }

    public List<String> getMailFromAllPersonsFromCity(String city) {
        return personRepository.getMailFromAllPersonsFromCity(city);
    }

    public PersonInfo getIntoFromPersonWithName(String firstName, String lastName) {
        return null;

    }

    public ChildrenInfo getChildListFromAddress(String address) {
        return null;
    }

    public List<String> getPhoneNumberListFromFireStationNumber(int fireStation) {
        return personRepository.getPhoneNumberListFromFireStationNumber(fireStation);
    }
}
