package com.safetynet.apiSafetyNet.repository.management;

import com.safetynet.apiSafetyNet.model.Data.PeopleDetailed;
import com.safetynet.apiSafetyNet.model.InputData.MedicalRecord;
import com.safetynet.apiSafetyNet.model.InputData.Person;
import com.safetynet.apiSafetyNet.model.OutputData.AddressInfo;
import com.safetynet.apiSafetyNet.repository.CRUD.MedicalRecordCRUD;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static java.time.LocalDate.now;

public class PersonManagement {

    private MedicalRecordManagement medicalRecordManagement;

    public PersonManagement(MedicalRecordManagement medicalRecordManagement) {
        this.medicalRecordManagement = medicalRecordManagement;
    }

    public void getPersonsFromAddress(String address, AddressInfo addressInfo, ArrayList<MedicalRecord> medicalRecords, ArrayList<Person> persons) {
        for(Person person : persons){
            if(person.getAddress().equals(address)){
                MedicalRecord medicalRecordPerson = medicalRecordManagement.getMedicalRecord(person.getFirstName(), person.getLastName(), medicalRecords);
                PeopleDetailed peopleDetailed = new PeopleDetailed(person.getLastName(), person.getPhone(), getAgeFromPerson(medicalRecordPerson),medicalRecordPerson.getMedications(), medicalRecordPerson.getAllergies());
                addressInfo.getPeopleDetailedArrayList().add(peopleDetailed);
            }
        }
    }

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

    public ArrayList<Person> filterListPersonWithFirstNameAndLastName(ArrayList<Person> personListJSON, String lastName, String firstName) {
        ArrayList<Person> personListFiltered = new ArrayList<>();
        //First run : add the person with matching firstName and LastName
        //Second run : add the person with only matching lastName
        for (int run = 0; run <2;run++){
            for(int i = 0;i<personListJSON.size();i++){
                if(run == 0) {
                    if (personListJSON.get(i).getFirstName().equals(firstName) && personListJSON.get(i).getLastName().equals(lastName)) {
                        personListFiltered.add(personListJSON.get(i));
                        personListJSON.remove(i);
                    }
                }
                else{
                    if (personListJSON.get(i).getLastName().equals(lastName)) {
                        personListFiltered.add(personListJSON.get(i));
                    }
                }
            }
        }

        return personListFiltered;
    }
}
