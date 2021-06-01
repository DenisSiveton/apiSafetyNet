package com.safetynet.apiSafetyNet.repository;

import com.jsoniter.JsonIterator;
import com.safetynet.apiSafetyNet.model.Data.Inhabitant;
import com.safetynet.apiSafetyNet.model.Data.PeopleDetailed;
import com.safetynet.apiSafetyNet.model.InputData.FireStation;
import com.safetynet.apiSafetyNet.model.InputData.MedicalRecord;
import com.safetynet.apiSafetyNet.model.InputData.Person;
import com.safetynet.apiSafetyNet.model.OutputData.AddressInfo;
import com.safetynet.apiSafetyNet.model.OutputData.HomeInfo;
import com.safetynet.apiSafetyNet.model.OutputData.InhabitantInfo;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Repository;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.time.LocalDate.now;

@Repository
public class FireStationRepository {


    public InhabitantInfo getInfoPersonFromFireStationNumber(String stationNumber) {
        JSONParser jsonP = new JSONParser();
        InhabitantInfo inhabitantInfo = new InhabitantInfo(null, 0, 0);
        try {
            JSONObject jsonO = (JSONObject) jsonP.parse(new FileReader("src/main/resources/data.json"));
            JSONArray fireStations = (JSONArray) jsonO.get("firestations");
            JSONArray persons = (JSONArray) jsonO.get("persons");
            JSONArray medicalRecords = (JSONArray) jsonO.get("medicalrecords");

            /* Gather all addresses under the fireStation number's jurisdiction */
            ArrayList<String> addresses = getAddressesFromFireStationNumber(stationNumber, fireStations);

            /* Create all the inhabitants and put them in InhabitantInfo */
            getInhabitants(inhabitantInfo, persons, addresses);

            /*Calculate the number of Adults and Children in the inhabitants */
            calculateNumberOfAdultsAndChildrenInInhabitants(inhabitantInfo, medicalRecords);

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return inhabitantInfo;
    }

    public AddressInfo getInfoFromEachPersonFromAddressAndAppointedFireStationNumber(String address) {
        AddressInfo addressInfo = new AddressInfo("", null);
        JSONParser jsonP = new JSONParser();
        try {
            JSONObject jsonO = (JSONObject) jsonP.parse(new FileReader("src/main/resources/data.json"));
            JSONArray medicalRecords = (JSONArray) jsonO.get("medicalrecords");
            JSONArray fireStations = (JSONArray) jsonO.get("firestations");
            JSONArray persons = (JSONArray) jsonO.get("persons");

            /* Get the number of FireStation responsible for the appointed Address */
            getFireStationNumberFromAddress(address, addressInfo, fireStations);

            /* Gather all persons that live at the address */
            ArrayList<MedicalRecord> medicalRecordListJSON = new ArrayList<>();
            for(Object o : medicalRecords) {
                medicalRecordListJSON.add(JsonIterator.deserialize((String) o, MedicalRecord.class));
            }
            getPersonsFromAddress(address, addressInfo, medicalRecordListJSON, persons);

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return addressInfo;
    }

    public ArrayList<HomeInfo> getHomeInfoListsFromFireStationNumbers(ArrayList<String> stations) {
        ArrayList<HomeInfo> homesInfo = new ArrayList<>();
        JSONParser jsonP = new JSONParser();
        try {
            JSONObject jsonO = (JSONObject) jsonP.parse(new FileReader("src/main/resources/data.json"));

             /* Create a list of FireStation */
            ArrayList<FireStation> fireStationListJSON = getFireStationListFromJSONFile(jsonO);

            /* Create a list of Person in which we can remove item by item can as we go on */
            ArrayList<Person> personListJSON = getPersonListFromJSONFile(jsonO);

            /* Create a list of MedicalRecord in which we can remove item by item can as we go on */
            ArrayList<MedicalRecord> medicalRecordListJSON = getMedicalRecordListFromJSONFile(jsonO);

            /* For each fire station number, gather correspondent addresses in list */
            ArrayList<ArrayList<String>> addressListByFireStationNumber = addAddressesToListForEachStationNumber(stations, fireStationListJSON);

            /* Create HomeInfo Object for each Address */
            createHomeInfoListBasedOnAddressList(stations, homesInfo, addressListByFireStationNumber, personListJSON, medicalRecordListJSON);
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return homesInfo;
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
            personListJSON.add(JsonIterator.deserialize((String) o, Person.class));
        }
        return personListJSON;
    }

    private ArrayList<FireStation> getFireStationListFromJSONFile(JSONObject jsonO) {
        ArrayList<FireStation> fireStationListJSON = new ArrayList<>();
        JSONArray persons = (JSONArray) jsonO.get("firestations");
        for(Object o : persons) {
            fireStationListJSON.add(JsonIterator.deserialize((String) o, FireStation.class));
        }
        return fireStationListJSON;
    }

    private ArrayList<ArrayList<String>> addAddressesToListForEachStationNumber(ArrayList<String> stations, ArrayList<FireStation> fireStationListJSON) {
        ArrayList<ArrayList<String>> addressListByFireStationNumber = new ArrayList<>();
        Collections.sort(stations);
        for(int i =0; i<stations.size();i++){
            addressListByFireStationNumber.add(getAddressesFromFireStationNumber(stations.get(i), fireStationListJSON));
        }
        return addressListByFireStationNumber;
    }

    private void createHomeInfoListBasedOnAddressList(ArrayList<String > stations, ArrayList<HomeInfo> homesInfo, ArrayList<ArrayList<String>> addressListByFireStationNumber, ArrayList<Person> personListJSON, ArrayList<MedicalRecord> medicalRecordListJSON) {
        for(int i =0; i<stations.size();i++){
            for(String address : addressListByFireStationNumber.get(i)){
                ArrayList<PeopleDetailed> peopleInHouse = new ArrayList<>();
                for(Person person : personListJSON) {
                    if (person.getAddress().equals(address)){
                        MedicalRecord medicalRecordPerson = getMedicalRecord(person.getFirstName(), person.getLastName(), medicalRecordListJSON);
                        peopleInHouse.add(new PeopleDetailed(person.getLastName(), person.getPhone(), getAgeFromPerson(medicalRecordPerson), medicalRecordPerson.getMedications(), medicalRecordPerson.getAllergies()));
                        personListJSON.remove(person);
                        medicalRecordListJSON.remove(medicalRecordPerson);
                    }
                }
                homesInfo.add(new HomeInfo(address, peopleInHouse));
            }
        }
    }

    private void getPersonsFromAddress(String address, AddressInfo addressInfo, ArrayList<MedicalRecord> medicalRecords, JSONArray persons) {
        for(Object o : persons){
            Person person = JsonIterator.deserialize((String) o, Person.class);
            if(person.getAddress().equals(address)){
                MedicalRecord medicalRecordPerson = getMedicalRecord(person.getFirstName(), person.getLastName(), medicalRecords);
                PeopleDetailed peopleDetailed = new PeopleDetailed(person.getLastName(), person.getPhone(), getAgeFromPerson(medicalRecordPerson),medicalRecordPerson.getMedications(), medicalRecordPerson.getAllergies());
                addressInfo.getPeopleDetailedArrayList().add(peopleDetailed);
            }
        }
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

    private void getFireStationNumberFromAddress(String address, AddressInfo addressInfo, JSONArray fireStations) {
        for(Object o : fireStations){
            FireStation fireStation = JsonIterator.deserialize((String) o, FireStation.class);
            if(fireStation.getAddress().equals(address)){
                addressInfo.setStationNumber(fireStation.getStation());
            }
        }
    }

    private void calculateNumberOfAdultsAndChildrenInInhabitants(InhabitantInfo inhabitantInfo, JSONArray medicalRecords) {
        for(Object o : medicalRecords){
            MedicalRecord medicalRecord = JsonIterator.deserialize((String) o, MedicalRecord.class);
            for(Inhabitant inhabitant :inhabitantInfo.getInhabitants()){
                if (inhabitant.getLastName().equals(medicalRecord.getLastName()) && inhabitant.getFirstName().equals(medicalRecord.getFirstName())){
                    int ageInhabitant = getAgeFromPerson(medicalRecord);
                    if(ageInhabitant>18){
                        inhabitantInfo.setNumberOfAdults(inhabitantInfo.getNumberOfAdults()+1);
                    }
                    else {
                        inhabitantInfo.setNumberOfChildren((inhabitantInfo.getNumberOfChildren()+1));
                    }
                }
            }
        }
    }

    private void getInhabitants(InhabitantInfo inhabitantInfo, JSONArray persons, ArrayList<String> addresses) {
        for(Object o : persons){
            Person person = JsonIterator.deserialize((String) o, Person.class);
            if(addresses.contains(person.getAddress())){
                inhabitantInfo.getInhabitants().add(new Inhabitant(person.getFirstName(), person.getLastName(),person.getAddress(), person.getPhone()));
            }
        }
    }

    private ArrayList<String> getAddressesFromFireStationNumber(String fireStationNumber, ArrayList<FireStation> fireStationList) {
        ArrayList<String> addresses = null;
        for (FireStation fireStation : fireStationList){
            if(fireStation.getStation() == fireStationNumber){
                addresses.add(fireStation.getAddress());
            }
        }
        return addresses;
    }

    private int getAgeFromPerson(MedicalRecord medicalRecord) {
        int agePerson;

        /*Cast the Person's birthDate in LocalDate type */
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy");
        LocalDate birthDate = LocalDate.parse(medicalRecord.getBirthDate(), formatter);
        LocalDate currentDate = now();

        /*Calculate age in years */
        agePerson = Period.between(birthDate, currentDate).getYears();
        return agePerson;
    }
}
