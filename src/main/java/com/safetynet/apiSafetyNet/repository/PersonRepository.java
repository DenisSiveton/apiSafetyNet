package com.safetynet.apiSafetyNet.repository;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jsoniter.JsonIterator;
import com.safetynet.apiSafetyNet.model.Data.People;
import com.safetynet.apiSafetyNet.model.InputData.FireStation;
import com.safetynet.apiSafetyNet.model.InputData.MedicalRecord;
import com.safetynet.apiSafetyNet.model.InputData.Person;
import com.safetynet.apiSafetyNet.model.OutputData.ChildrenInfo;
import com.safetynet.apiSafetyNet.model.OutputData.PersonInfo;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Repository;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static java.time.LocalDate.now;

@Repository
public class PersonRepository {

    public static Person addPerson(Person person) throws FileNotFoundException {

        JSONObject personToAddToJSON = new JSONObject();
        personToAddToJSON.put("firstName", person.getFirstName());
        personToAddToJSON.put("lastName", person.getLastName());
        personToAddToJSON.put("address", person.getAddress());
        personToAddToJSON.put("city", person.getCity());
        personToAddToJSON.put("zip", person.getZip());
        personToAddToJSON.put("phone", person.getPhone());
        personToAddToJSON.put("email", person.getEmail());

        JSONParser jsonP = new JSONParser();
        try {
            String filePath ="src/main/resources/data.json";
            JSONObject jsonO = (JSONObject) jsonP.parse(new FileReader(filePath));

            /* Get all Person from JSON file*/
            JSONArray persons = (JSONArray) jsonO.get("persons");

            persons.add(personToAddToJSON);

            FileWriter file = new FileWriter(filePath);
            file.write(persons.toJSONString());
            file.flush();
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return person;
    }

    public void deletePerson(Person person) {

    }
    // TODO : finalize the method

    public Person modifyInfoPerson(Person person) {
        /*JSONParser jsonP = new JSONParser();
        try {
            String filePath ="src/main/resources/data.json";
            JSONObject jsonO = (JSONObject) jsonP.parse(new FileReader(filePath));

            /* Get person updated from JSON file
            Person personUpdated = updatePersonFromJSONFile(jsonO, person);


            FileWriter file = new FileWriter(filePath);
            file.write(persons.toJSONString());
            file.flush();
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }*/
        return person;
    }

    private Person updatePersonFromJSONFile(JSONObject jsonO, Person person) {
        Person personToUpdate = new Person();
        JSONArray persons = (JSONArray) jsonO.get("persons");
        for(Object o : persons) {
            personToUpdate = JsonIterator.deserialize(o.toString(), Person.class);
            if(personToUpdate.getFirstName().equals(person.getFirstName()) && personToUpdate.getLastName().equals(person.getLastName())){
                personToUpdate =person;
            }
        }
        return personToUpdate;
    }

    public List<String> getMailFromAllPersonsFromCity(String city) {
        ArrayList<String> emails = new ArrayList<>();
        JSONParser jsonP = new JSONParser();
        try {
            JSONObject jsonO = (JSONObject) jsonP.parse(new FileReader("src/main/resources/data.json"));

            /* Create a list of Person from JSON file*/
            ArrayList<Person> personListJSON = getPersonListFromJSONFile(jsonO);

            //For each Person, compare their city with the Requested City.
            //If the same, then add person's email to email list
            emails = createEmailListFromAddress(city, personListJSON);
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return emails;
    }

    public ArrayList<String> getPhoneNumberListFromFireStationNumber(String fireStationNumber) {
        ArrayList<String> phones = null;
        JSONParser jsonP = new JSONParser();
        try {
            JSONObject jsonO = (JSONObject) jsonP.parse(new FileReader("src/main/resources/data.json"));

            /* Create a list of Person from JSON file*/
            ArrayList<Person> personListJSON = getPersonListFromJSONFile(jsonO);

            /* Create a list of FireStation from JSON file*/
            ArrayList<FireStation> fireStationListJSON = getFireStationListFromJSONFile(jsonO);

            /* Gather all addresses under the fireStation number's jurisdiction */
            ArrayList<String> addresses = getAddressListFromFireStationNumber(fireStationNumber, fireStationListJSON);

            /* Add phone if inhabitant's address is in the Address list */
            phones = getPhonesFromAddressList(personListJSON, addresses);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return phones;
    }

    public PersonInfo getInfoFromPersonWithName(String firstName, String lastName) {
        PersonInfo personInfo = new PersonInfo();

        return personInfo;
    }

    public ChildrenInfo getChildListFromAddress(String address) {
        int numberOfChildren = 0;
        ArrayList<People> children = new ArrayList<>();
        ArrayList<People> adults = new ArrayList<>();
        ChildrenInfo childrenInfo = new ChildrenInfo(children, adults, numberOfChildren);
        JSONParser jsonP = new JSONParser();
        try {
            JSONObject jsonO = (JSONObject) jsonP.parse(new FileReader("src/main/resources/data.json"));

            /* Create a list of Person from JSON file*/
            ArrayList<Person> personListJSON = getPersonListFromJSONFile(jsonO);

            /* Create a list of MedicalRecord from JSON file*/
            ArrayList<MedicalRecord> medicalRecordListJSON = getMedicalRecordListFromJSONFile(jsonO);

            /* Get all Person From an Address */
            ArrayList<Person> personListFromAddress = getPersonListFromAddress(address, personListJSON);

            /* Get the medicalRecords from these persons */
            ArrayList<MedicalRecord> medicalRecordListFromPersonList = getMedicalRecordFromPersonList(personListFromAddress, medicalRecordListJSON);

            /* Calculate the age for each person, if minor (age <19) then add as a child */
            childrenInfo = calculateAgeForPersonListAndAddPersonToAdultOrChildList(personListFromAddress, medicalRecordListFromPersonList, numberOfChildren, children, adults);

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

    private ChildrenInfo calculateAgeForPersonListAndAddPersonToAdultOrChildList(ArrayList<Person> personListFromAddress, ArrayList<MedicalRecord> medicalRecordListFromPersonList, int numberOfChildren, ArrayList<People> children, ArrayList<People> adults) {
        for(int i = 0; i < personListFromAddress.size(); i++){
            int agePerson = getAgeFromPerson(medicalRecordListFromPersonList.get(i));
            if(agePerson<19){
                children.add(new People(personListFromAddress.get(i).getFirstName(),personListFromAddress.get(i).getLastName(), agePerson));
                numberOfChildren++;
            }
            else {
                adults.add(new People(personListFromAddress.get(i).getFirstName(),personListFromAddress.get(i).getLastName(), agePerson));
            }
        }
        ChildrenInfo childrenInfo = new ChildrenInfo(children, adults, numberOfChildren);
        return childrenInfo;
    }

    private ArrayList<MedicalRecord> getMedicalRecordListFromJSONFile(JSONObject jsonO) {
        ArrayList<MedicalRecord> medicalRecordListJSON = new ArrayList<>();
        JSONArray medicalRecords = (JSONArray) jsonO.get("medicalrecords");
        for(int i = 0; i<medicalRecords.size(); i++) {
            String medicalRecordToString = medicalRecords.get(i).toString();
            MedicalRecord med = JsonIterator.deserialize(medicalRecordToString, MedicalRecord.class);
            String birthDate = medicalRecordToString.substring(medicalRecordToString.indexOf("birthdate")+12, medicalRecordToString.indexOf("birthdate")+14)+
                    medicalRecordToString.substring(medicalRecordToString.indexOf("birthdate")+15, medicalRecordToString.indexOf("birthdate")+18)+
                    medicalRecordToString.substring(medicalRecordToString.indexOf("birthdate")+19, medicalRecordToString.indexOf("birthdate")+24) ;
            med.setBirthDate(birthDate);
            medicalRecordListJSON.add(med);
        }
        return medicalRecordListJSON;

    }

    private ArrayList<Person> getPersonListFromJSONFile(JSONObject jsonO) {
        ArrayList<Person> personListJSON = new ArrayList<>();
        JSONArray persons = (JSONArray) jsonO.get("persons");
        for(Object o : persons) {
            personListJSON.add(JsonIterator.deserialize(o.toString(), Person.class));
        }
        return personListJSON;
    }

    private ArrayList<FireStation> getFireStationListFromJSONFile(JSONObject jsonO) {
        ArrayList<FireStation> fireStationListJSON = new ArrayList<>();
        JSONArray persons = (JSONArray) jsonO.get("firestations");
        for(Object o : persons) {
            fireStationListJSON.add(JsonIterator.deserialize(o.toString(), FireStation.class));
        }
        return fireStationListJSON;
    }

    private ArrayList<String> createEmailListFromAddress(String city, ArrayList<Person> personListJSON) {

        ArrayList<String> emails = new ArrayList<>();
        for(Person person : personListJSON){
            if(person.getCity().equals(city)) {
                emails.add(person.getEmail());
            }
        }
        return emails;
    }

    private ArrayList<String> getAddressListFromFireStationNumber(String fireStationNumber, ArrayList<FireStation> fireStationList) {
        ArrayList<String> addresses = new ArrayList<>();
        for (FireStation fireStation : fireStationList){
            if(fireStationNumber.contains(fireStation.getStation())){
                addresses.add(fireStation.getAddress());
            }
        }
        return addresses;
    }

    private ArrayList<String> getPhonesFromAddressList(ArrayList<Person> personList, ArrayList<String> addresses) {
        ArrayList<String> phones = new ArrayList<>();
        for (Person person : personList){
            if(addresses.contains(person.getAddress())){
                phones.add(person.getPhone());
            }
        }
        return phones;
    }

    private int getAgeFromPerson(MedicalRecord medicalRecord) {
        int agePerson;

        /*Cast the Person's birthDate in LocalDate type */
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate birthDate = LocalDate.parse(medicalRecord.getBirthDate(), formatter);
        LocalDate currentDate = now();

        /*Calculate age in years */
        agePerson = Period.between(birthDate, currentDate).getYears();
        return agePerson;
    }

    private ArrayList<Person> getPersonListFromAddress(String address, ArrayList<Person> persons) {
        ArrayList<Person> personListFromAddress = new ArrayList<>();
        for(Person person : persons){
            if(person.getAddress().equals(address)){
                personListFromAddress.add(person);
            }
        }
        return personListFromAddress;
    }

    private ArrayList<MedicalRecord> getMedicalRecordFromPersonList(ArrayList<Person> personListFromAddress, ArrayList<MedicalRecord> medicalRecords) {
        ArrayList<MedicalRecord> medicalRecordListFromPersonList = new ArrayList<>();
        for(Person person : personListFromAddress){
            medicalRecordListFromPersonList.add(getMedicalRecord(person.getFirstName(), person.getLastName(), medicalRecords));
        }
        return medicalRecordListFromPersonList;
    }

    private MedicalRecord getMedicalRecord(String firstName, String lastName, ArrayList<MedicalRecord> medicalRecords) {
        MedicalRecord result = null;
        for(MedicalRecord medicalRecord : medicalRecords){
            if(medicalRecord.getFirstName().equals(firstName) && medicalRecord.getLastName().equals(lastName)){
                result = medicalRecord;
                break;
            }
        }
        return result;
    }


}
