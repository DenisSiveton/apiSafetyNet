package com.safetynet.apiSafetyNet.controller;

import com.safetynet.apiSafetyNet.exceptions.ChildrenNotFoundException;
import com.safetynet.apiSafetyNet.exceptions.NoInhabitantForThisCityException;
import com.safetynet.apiSafetyNet.exceptions.NoInhabitantForThisFireStationNumberException;
import com.safetynet.apiSafetyNet.exceptions.PersonNotFoundException;
import com.safetynet.apiSafetyNet.model.Data.Inhabitant;
import com.safetynet.apiSafetyNet.model.Data.People;
import com.safetynet.apiSafetyNet.model.InputData.FireStation;
import com.safetynet.apiSafetyNet.model.InputData.MedicalRecord;
import com.safetynet.apiSafetyNet.model.InputData.Person;
import com.safetynet.apiSafetyNet.model.OutputData.ChildrenInfo;
import com.safetynet.apiSafetyNet.model.OutputData.InhabitantInfo;
import com.safetynet.apiSafetyNet.model.OutputData.PersonInfo;
import com.safetynet.apiSafetyNet.service.PersonService;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Data
@RestController
public class PersonController {

    @Autowired
    private final PersonService personService;

    private static final Logger logger = LogManager.getLogger(PersonController.class);

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    /**
     * This method receives a POST Request with a Person as a body to add in the Database.
     * It calls the Service layer to do so.
     * It returns the added Person with the HTTP Code 201.
     *
     * @see Person
     * @param person the Person to be added into the Database.
     * @return the new added Person.
     *
     * @author Denis Siveton
     * @version 1.0
     */
    @PostMapping(value = "/person")
    public ResponseEntity<Void> addPerson(@Valid @RequestBody Person person) throws FileNotFoundException {
        Person personAdded = personService.addPerson(person);
        if (personAdded == null) {
            return ResponseEntity.noContent().build();
        }
        else{
            return new ResponseEntity(personAdded, HttpStatus.CREATED);
        }
    }

    /**
     * This method receives a PATCH Request with a Person as a body to update in the Database.
     * It calls the Service layer to do so.
     * It returns the updated Person with the HTTP Code 204 or 404 if the Person wasn't found in the Database.
     *
     * @see Person
     * @param person the Person to be updated into the Database.
     * @return the updated Person or NULL if no Person was found in the Database.
     *
     * @author Denis Siveton
     * @version 1.0
     */
    @PatchMapping(value = "/person")
    public ResponseEntity<Void> modifyInfoPerson(@Valid @RequestBody Person person) throws PersonNotFoundException {
        Person personModified = personService.modifyInfoPerson(person);

        if(personModified==null) {
            PersonNotFoundException exception = new PersonNotFoundException("The Person with FirstName : \"" +person.getFirstName()+ "\"and LastName : \"" +person.getLastName()+ "\", was NOT FOUND. Please search with another firstname and lastname.");
            logger.error("Request ended up in ERROR :'" +exception.getMessage() + "'.");
            throw exception;
        }

        else{
            return new ResponseEntity(personModified, HttpStatus.NO_CONTENT);
        }

    }

    /**
     * This method receives a DELETE Request with a Person as a body to delete from the Database.
     * It calls the Service layer to do so.
     * It returns the deleted Person with the HTTP Code 204 or 404 if the Person wasn't found in the Database.
     *
     * @see Person
     * @param person the Person to be deleted from the Database.
     * @return the deleted Person or NULL if no Person was found in the Database.
     *
     * @author Denis Siveton
     * @version 1.0
     */
    @DeleteMapping(value = "/person")
    public ResponseEntity<Void> deletePerson(@Valid @RequestBody Person person) {
        Person personDeleted = personService.deletePerson(person);

        if (personDeleted == null) {
            PersonNotFoundException exception = new PersonNotFoundException("The Person with FirstName : \"" +person.getFirstName()+ "\"and LastName : \"" +person.getLastName()+ "\", was NOT FOUND. Please search with another firstname and lastname.");
            logger.error("Request ended up in ERROR :'" +exception.getMessage() + "'.");
            throw exception;
        }
        else{
            return new ResponseEntity(personDeleted, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * This method receives a GET Request with an address as a parameter.
     * It calls the Service layer to proceed.
     * It returns a ChildrenInfo data (the information about children and adults living at the specified address)
     * with the HTTP Code 200 or 404 if there is no children at the specified address.
     *
     * @see ChildrenInfo
     * @see People
     *
     * @param address the address we want the information from.
     * @return the new ChildrenInfo data or NULL if there is no children at the specified address.
     *
     * @author Denis Siveton
     * @version 1.0
     */
    @GetMapping("/childAlert")
    public ResponseEntity<Void> getChildListFromAddress(@RequestParam(name = "address") String address) {
        ChildrenInfo childrenInfo =  personService.getChildListFromAddress(address);

        if (childrenInfo == null) {
            ChildrenNotFoundException exception = new ChildrenNotFoundException("There are no children living at the following address :'" + address + "'. Please try another address");
            logger.error("Request ended up in ERROR :'" +exception.getMessage() + "'.");
            throw exception;
        }
        else{
            return new ResponseEntity(childrenInfo, HttpStatus.OK);
        }
    }

    /**
     * This method receives a GET Request with a FireStation's number as a parameter.
     * It calls the Service layer to proceed.
     * It returns a list(String) of the people's phone (that are managed by the fire station that have the specified number)
     * with the HTTP Code 200 or 404 if no address is managed by that fire station's number.
     *
     * @see FireStation
     * @see Person
     *
     * @param fireStationNumber the FireStation's number.
     * @return the list of phone or NULL if no addresses in the Database are managed by the firestation's number.
     *
     * @author Denis Siveton
     * @version 1.0
     */
    @GetMapping("/phoneAlert")
    public ResponseEntity<Void> getPhoneNumberListFromFireStationNumber(@RequestParam(name = "firestation") String fireStationNumber) {
        List<String> listPhone = personService.getPhoneNumberListFromFireStationNumber(fireStationNumber);

        if (listPhone == null) {
            NoInhabitantForThisFireStationNumberException exception = new NoInhabitantForThisFireStationNumberException("There are no addresses under the responsability of the given firestation with the following station :'" + fireStationNumber + "'. Please try another station");
            logger.error("Request ended up in ERROR :'" +exception.getMessage() + "'.");
            throw exception;
        }
        else{
            return new ResponseEntity(listPhone, HttpStatus.OK);
        }
    }

    /**
     * This method receives a GET Request with a city name as a parameter.
     * It calls the Service layer to proceed.
     * It returns a list(String) of the people's email (that live in the specified city)
     * with the HTTP Code 200 or 404 if no body live in the specified city.
     *
     * @see FireStation
     * @see Person
     *
     * @param city the name of the city.
     * @return the list of phone or NULL if no body live in the specified city.
     *
     * @author Denis Siveton
     * @version 1.0
     */
    @GetMapping(value = "communityEmail")
    public ResponseEntity<Void> getMailFromPersonFromCity(@RequestParam(name = "city") String city) {
        List<String> listMail = personService.getMailFromAllPersonsFromCity(city);

        if (listMail == null) {
            NoInhabitantForThisCityException exception = new NoInhabitantForThisCityException("There are no inhabitants registered in the database that live in the following city :'" + city + "'. Please try another city name");
            logger.error("Request ended up in ERROR :'" +exception.getMessage() + "'.");
            throw exception;
        }
        else{
            return new ResponseEntity(listMail, HttpStatus.OK);
        }

    }

    /**
     * This method receives a GET Request with a first name and a last name as a parameter.
     * It calls the Service layer to proceed.
     * It returns a list of PersonInfo data (detailed information about the people identified person and its relatives)
     * with the HTTP Code 200 or 404 if no person is found having both the specified first and last name.
     *
     * @see PersonInfo
     *
     * @param firstName the first name of the person we are looking for.
     * @param lastName the last name of the person we are looking for.
     * @return the list of PersonInfo data or NULL if no person is found having both the specified first and last name.
     *
     * @author Denis Siveton
     * @version 1.0
     */
    @GetMapping("/personInfo")
    public ResponseEntity<Void> getInfoFromPersonWithName(@RequestParam(name = "firstName") String firstName, @RequestParam(name = "lastName") String lastName) {
        ArrayList<PersonInfo> listPersonInfo = personService.getInfoFromPersonWithName(firstName, lastName);

        if (listPersonInfo == null) {
            PersonNotFoundException exception = new PersonNotFoundException("The Person with FirstName : \"" +firstName+ "\"and LastName : \"" + lastName+ "\", was NOT FOUND. Please search with another firstname and lastname.");
            logger.error("Request ended up in ERROR :'" +exception.getMessage() + "'.");
            throw exception;
        }
        else{
            return new ResponseEntity(listPersonInfo, HttpStatus.OK);
        }
    }
}
