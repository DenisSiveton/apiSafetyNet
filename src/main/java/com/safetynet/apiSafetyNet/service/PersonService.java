package com.safetynet.apiSafetyNet.service;

import com.safetynet.apiSafetyNet.model.Data.People;
import com.safetynet.apiSafetyNet.model.InputData.FireStation;
import com.safetynet.apiSafetyNet.model.InputData.MedicalRecord;
import com.safetynet.apiSafetyNet.model.InputData.Person;
import com.safetynet.apiSafetyNet.model.OutputData.ChildrenInfo;
import com.safetynet.apiSafetyNet.model.OutputData.PersonInfo;
import com.safetynet.apiSafetyNet.repository.FireStationCRUD;
import com.safetynet.apiSafetyNet.repository.MedicalRecordCRUD;
import com.safetynet.apiSafetyNet.repository.PersonCRUD;
import com.safetynet.apiSafetyNet.service.management.ChildrenInfoManagement;
import com.safetynet.apiSafetyNet.service.management.FireStationManagement;
import com.safetynet.apiSafetyNet.service.management.MedicalRecordManagement;
import com.safetynet.apiSafetyNet.service.management.PersonManagement;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService {

    @Value("${safetynet.path-data}")
    private String filePath;

    @Autowired
    private PersonCRUD personCRUD;
    @Autowired
    private FireStationCRUD fireStationCRUD;
    @Autowired
    private MedicalRecordCRUD medicalRecordCRUD;
    @Autowired
    private PersonManagement personManagement;
    @Autowired
    private FireStationManagement fireStationManagement;
    @Autowired
    private MedicalRecordManagement medicalRecordManagement;
    @Autowired
    private ChildrenInfoManagement childrenInfoManagement;

    public PersonService(){
    }

    public PersonService(String filePath,PersonCRUD personCRUD, FireStationCRUD fireStationCRUD, MedicalRecordCRUD medicalRecordCRUD,
                         PersonManagement personManagement, FireStationManagement fireStationManagement,
                         MedicalRecordManagement medicalRecordManagement, ChildrenInfoManagement childrenInfoManagement) {
        this.filePath = filePath;
        this.personCRUD = personCRUD;
        this.fireStationCRUD = fireStationCRUD;
        this.medicalRecordCRUD = medicalRecordCRUD;
        this.personManagement = personManagement;
        this.fireStationManagement = fireStationManagement;
        this.medicalRecordManagement = medicalRecordManagement;
        this.childrenInfoManagement = childrenInfoManagement;
    }

    /**
     *
     * This method adds a Person into the Database.
     *
     * @see Person
     *
     * @param person the Person to be added into the Database.
     * @return the new added Person.
     *
     * @author denisSiveton
     * @version 1.0
     */
    public Person addPerson(Person person) throws FileNotFoundException {
        return personCRUD.addPerson(person);
    }

    /**
     *
     * This method updates a Person in the Database.
     *
     * @see Person
     *
     * @param person the Person with the up to date data to be updated in the Database.
     * @return the updated Person or NULL if the Person wasn't found in the Database.
     *
     * @author denisSiveton
     * @version 1.0
     */
    public Person modifyInfoPerson(Person person) {
        return personCRUD.modifyInfoPerson(person);
    }

    /**
     *
     * This method deletes a Person from the Database.
     *
     * @see Person
     *
     * @param person the Person to be deleted from the Database.
     * @return the deleted Person or NULL if the Person wasn't found in the Database.
     *
     * @author denisSiveton
     * @version 1.0
     */
    public Person deletePerson(Person person) {
        return personCRUD.deletePerson(person);
    }

    /**
     *
     * This method retrieves all the emails from the Persons in the specified City.
     *
     * @see Person
     *
     * @param city the city where the persons we want the emails from live in.
     * @return a list of email (string) or NULL if no one live in the city specified.
     *
     * @author denisSiveton
     * @version 1.0
     */
    public List<String> getMailFromAllPersonsFromCity(String city) {
        ArrayList<String> emails = new ArrayList<>();
        JSONParser jsonP = new JSONParser();
        try {
            JSONObject jsonO = (JSONObject) jsonP.parse(new FileReader(filePath));

            /* Create a list of Person from JSON file*/
            ArrayList<Person> personListJSON = personCRUD.getPersonsFromJSONFile(jsonO);

            //For each Person, compare their city with the Requested City.
            //If the same, then add person's email to email list
            emails = personManagement.createEmailListFromAddress(city, personListJSON);
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return emails;
    }

    /**
     *
     * This method creates a list of "PersonInfo". First the Person that has the first and last name specified.
     * Secondly, it adds the persons that share the same last name.
     *
     * @see PersonInfo
     *
     * @param firstName the first name of the Person we are looking for.
     * @param lastName the last name of the Person we are looking for.
     * @return the list of PersonInfo.
     *
     * @author denisSiveton
     * @version 1.0
     */
    public ArrayList<PersonInfo> getInfoFromPersonWithName(String firstName, String lastName) {
        ArrayList<PersonInfo> personInfoList = new ArrayList<>();
        JSONParser jsonP = new JSONParser();
        try {
            JSONObject jsonO = (JSONObject) jsonP.parse(new FileReader(filePath));

            /* Create a list of Person and Medical Record from JSON file*/
            ArrayList<Person> personListJSON = personCRUD.getPersonsFromJSONFile(jsonO);
            ArrayList<MedicalRecord> medicalRecordListJSON = medicalRecordCRUD.getAllMedicalRecords(jsonO);

            /* Get persons with same LastName */
            ArrayList<Person> filteredPersonList= personManagement.filterListPersonWithFirstNameAndLastName(personListJSON, lastName, firstName);

            if(filteredPersonList.size()!=0) {
                /* Get medicalRecords with same LastName */
                ArrayList<MedicalRecord> filteredMedicalRecordList = medicalRecordManagement.filterListMedicalRecordWithFirstNameAndLastName(medicalRecordListJSON, lastName, firstName);

                /* Add personInfo to the list */
                personManagement.createPersonInfoListFromFilteredLists(personInfoList, filteredMedicalRecordList, filteredPersonList);
            }
            else{
                personInfoList = null;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return personInfoList;
    }

    /**
     *
     * This method creates a "ChildrenInfo" (the number of children living at the said address
     * and a list of both adults and children) object from an address.
     *
     * @see ChildrenInfo
     *
     * @param address the address we want the information about the people living at.
     * @return ChildrenInfo.
     *
     * @author denisSiveton
     * @version 1.0
     */
    public ChildrenInfo getChildListFromAddress(String address) {
        int numberOfChildren = 0;
        ArrayList<People> children = new ArrayList<>();
        ArrayList<People> adults = new ArrayList<>();
        ChildrenInfo childrenInfo = new ChildrenInfo(children, adults, numberOfChildren);
        JSONParser jsonP = new JSONParser();
        try {
            JSONObject jsonO = (JSONObject) jsonP.parse(new FileReader(filePath));

            /* Create a list of Person from JSON file*/
            ArrayList<Person> personListJSON = personCRUD.getPersonsFromJSONFile(jsonO);

            /* Create a list of MedicalRecord from JSON file*/
            ArrayList<MedicalRecord> medicalRecordListJSON = medicalRecordCRUD.getAllMedicalRecords(jsonO);

            /* Get all Person From an Address */
            ArrayList<Person> personListFromAddress = personManagement.getPersonListFromAddress(address, personListJSON);

            /* Get the medicalRecords from these persons */
            ArrayList<MedicalRecord> medicalRecordListFromPersonList = medicalRecordManagement.getMedicalRecordFromPersonList(personListFromAddress, medicalRecordListJSON);

            /* Calculate the age for each person, if minor (age <19) then add as a child */
            childrenInfoManagement.calculateAgeForPersonListAndAddPersonToAdultOrChildList(personListFromAddress, medicalRecordListFromPersonList, childrenInfo);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(childrenInfo.getNumberOfChildren() == 0){
            return null;
        }
        else{
            return childrenInfo;
        }
    }

    /**
     *
     * This method retrieves all the phone numbers from the Persons
     * that are managed by the fire stations which have the specified fire station number.
     *
     * @see Person
     * @see FireStation
     *
     * @param fireStationNumber the number of the fire station(s).
     * @return a list of phone (string) or NULL if there's no person managed by the specified fire station's number.
     *
     * @author denisSiveton
     * @version 1.0
     */
    public ArrayList<String> getPhoneNumberListFromFireStationNumber(String fireStationNumber) {
        ArrayList<String> phones = null;
        JSONParser jsonP = new JSONParser();
        try {
            JSONObject jsonO = (JSONObject) jsonP.parse(new FileReader(filePath));

            /* Create a list of Person from JSON file*/
            ArrayList<Person> personListJSON = personCRUD.getPersonsFromJSONFile(jsonO);

            /* Create a list of FireStation from JSON file*/
            ArrayList<FireStation> fireStationListJSON = fireStationCRUD.getFireStationsFromJSONFile(jsonO);

            /* Gather all addresses under the fireStation number's jurisdiction */
            ArrayList<String> addresses = fireStationManagement.getAddressesFromFireStationNumber(fireStationNumber, fireStationListJSON);

            /* Add phone if inhabitant's address is in the Address list */
            phones = personManagement.getPhonesFromAddressList(personListJSON, addresses);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return phones;
    }
}
