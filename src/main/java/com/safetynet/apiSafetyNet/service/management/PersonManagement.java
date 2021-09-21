package com.safetynet.apiSafetyNet.service.management;

import com.safetynet.apiSafetyNet.model.Data.PeopleDetailed;
import com.safetynet.apiSafetyNet.model.InputData.FireStation;
import com.safetynet.apiSafetyNet.model.InputData.MedicalRecord;
import com.safetynet.apiSafetyNet.model.InputData.Person;
import com.safetynet.apiSafetyNet.model.OutputData.AddressInfo;
import com.safetynet.apiSafetyNet.model.OutputData.PersonInfo;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static java.time.LocalDate.now;

@Service
public class PersonManagement {

    private MedicalRecordManagement medicalRecordManagement;

    public PersonManagement(MedicalRecordManagement medicalRecordManagement) {
        this.medicalRecordManagement = medicalRecordManagement;
    }

    /**
     *
     * This method searches the Persons living at a specific address, creates the equivalent PeopleDetailed and adds it to the AddressInfo data.
     *
     * @see AddressInfo
     * @see Person
     * @see MedicalRecord
     * @see MedicalRecordManagement#getMedicalRecord(String, String, ArrayList)
     *
     * @param address a specific address.
     * @param addressInfo the object that needs to be completed.
     * @param medicalRecords the list of the MedicalRecords from the Database.
     * @param persons the list of the Persons from the Database.
     *
     * @author denisSiveton
     * @version 1.0
     */
    public void getPersonsFromAddress(String address, AddressInfo addressInfo, ArrayList<MedicalRecord> medicalRecords, ArrayList<Person> persons) {
        for(Person person : persons){
            if(person.getAddress().equals(address)){
                MedicalRecord medicalRecordPerson = medicalRecordManagement.getMedicalRecord(person.getFirstName(), person.getLastName(), medicalRecords);
                PeopleDetailed peopleDetailed = new PeopleDetailed(person.getLastName(), person.getPhone(), getAgeFromPerson(medicalRecordPerson),medicalRecordPerson.getMedications(), medicalRecordPerson.getAllergies());
                addressInfo.getPeopleDetailedArrayList().add(peopleDetailed);
            }
        }
    }

    /**
     *
     * This method creates a phone list based on an address list. If the person's address is within the address list then his phone number is added.
     * It returns the phone list.
     *
     * @see Person
     *
     * @param personList the list of the Persons from the Database
     * @param addresses a list of addresses.
     * @return the list of phone number or NULL if no one lived at any address.
     *
     * @author denisSiveton
     * @version 1.0
     */
    public ArrayList<String> getPhonesFromAddressList(ArrayList<Person> personList, ArrayList<String> addresses) {
        ArrayList<String> phones = new ArrayList<>();
        for (Person person : personList){
            if(addresses.contains(person.getAddress())){
                phones.add(person.getPhone());
            }
        }
        if(phones.size() == 0){
            phones = null;
        }
        return phones;
    }

    /**
     *
     * This method creates a email list based on an city. If the person lives in the specified city then his email address is added.
     * It returns the email list.
     *
     * @see Person
     *
     * @param city the name of the city.
     * @param personListJSON the list of the Persons from the Database.
     * @return the list of email or NULL if no one lived in the specified city.
     *
     * @author denisSiveton
     * @version 1.0
     */
    public ArrayList<String> createEmailListFromAddress(String city, ArrayList<Person> personListJSON) {

        ArrayList<String> emails = new ArrayList<>();
        for(Person person : personListJSON){
            if(person.getCity().equals(city)) {
                emails.add(person.getEmail());
            }
        }
        if(emails.size() == 0){
            emails = null;
        }
        return emails;
    }

    /**
     *
     * This method calculates the age of a person based on his birth date.
     * It returns the age in years.
     *
     * @see MedicalRecord
     *
     * @param medicalRecord the MedicalRecord of the person.
     * @return the age of the person.
     *
     * @author denisSiveton
     * @version 1.0
     */
    public int getAgeFromPerson(MedicalRecord medicalRecord) {
        int agePerson;

        /*Cast the Person's birthDate in LocalDate type */
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate birthDate = LocalDate.parse(medicalRecord.getBirthDate(), formatter);
        LocalDate currentDate = now();

        /*Calculate age in years */
        agePerson = Period.between(birthDate, currentDate).getYears();
        return agePerson;
    }

    /**
     *
     * This method creates a list of Persons from the wanted Person and its relatives (same last name). It returns this new list.
     *
     * @see Person
     *
     * @param personList the list of all the Persons from the Database.
     * @param firstName the first name of the person who is searched.
     * @param lastName the last name of the person who is searched.
     * @return the list of Persons.
     *
     * @author denisSiveton
     * @version 1.0
     */
    public ArrayList<Person> filterListPersonWithFirstNameAndLastName(ArrayList<Person> personList, String lastName, String firstName) {
        ArrayList<Person> personListFiltered = new ArrayList<>();
        //First run : add the person with matching firstName and LastName
        //Second run : add the person with only matching lastName
        for (int run = 0; run <2;run++){
            for(int i = 0;i<personList.size();i++){
                if(run == 0) {
                    if (personList.get(i).getFirstName().equals(firstName) && personList.get(i).getLastName().equals(lastName)) {
                        personListFiltered.add(personList.get(i));
                        personList.remove(i);
                    }
                }
                else if(personList.size()!=0){
                    if (personList.get(i).getLastName().equals(lastName)) {
                        personListFiltered.add(personList.get(i));
                    }
                }
            }
        }

        return personListFiltered;
    }

    /**
     *
     * This method creates a list of PersonInfo using two filtered lists of Persons and MedicalRecords.
     *
     * @see Person
     * @see MedicalRecord
     * @see PersonInfo
     *
     * @param personInfoList the list of PersonInfo that has to be filled.
     * @param filteredMedicalRecordList the list of MedicalRecords previously sorted.
     * @param filteredPersonList the list of Persons previously sorted.
     * @return the completed and up to date list of PersonInfo.
     *
     * @author denisSiveton
     * @version 1.0
     */
    public void createPersonInfoListFromFilteredLists(ArrayList<PersonInfo> personInfoList, ArrayList<MedicalRecord> filteredMedicalRecordList, ArrayList<Person> filteredPersonList) {
        for(int i = 0; i<filteredPersonList.size(); i++){
            Person person = filteredPersonList.get(i);
            MedicalRecord medicalRecord = filteredMedicalRecordList.get(i);
            personInfoList.add(new PersonInfo(person.getLastName(), person.getAddress(),getAgeFromPerson(medicalRecord), person.getEmail(), medicalRecord.getMedications(), medicalRecord.getAllergies()));
        }
    }

    /**
     *
     * This method creates a list of Persons that live at a specified address.
     *
     * @see Person
     *
     * @param address the address.
     * @param persons the list of the Persons from the Database.
     * @return the list of Persons that live the specified address.
     *
     * @author denisSiveton
     * @version 1.0
     */
    public ArrayList<Person> getPersonListFromAddress(String address, ArrayList<Person> persons) {
        ArrayList<Person> personListFromAddress = new ArrayList<>();
        for(Person person : persons){
            if(person.getAddress().equals(address)){
                personListFromAddress.add(person);
            }
        }
        return personListFromAddress;
    }
}
