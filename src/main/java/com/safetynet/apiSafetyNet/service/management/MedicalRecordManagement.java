package com.safetynet.apiSafetyNet.service.management;

import com.safetynet.apiSafetyNet.model.InputData.FireStation;
import com.safetynet.apiSafetyNet.model.InputData.MedicalRecord;
import com.safetynet.apiSafetyNet.model.InputData.Person;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MedicalRecordManagement {

    /**
     *
     * This method searches for a MedicalRecord in particular from a first name and a last name then returns it.
     * If no MedicalRecord is found then it returns NULL.
     *
     * @see MedicalRecord
     *
     * @param firstName the first name of the person who is searched.
     * @param lastName the last name of the person who is searched.
     * @param medicalRecords the list of the MedicalRecords from the Database.
     * @return the found MedicalRecord or NULL if no MedicalRecord is found in the Database.
     *
     * @author denisSiveton
     * @version 1.0
     */
    public MedicalRecord getMedicalRecord(String firstName, String lastName, ArrayList<MedicalRecord> medicalRecords) {
        MedicalRecord result = null;
        for(MedicalRecord medicalRecord : medicalRecords){
            if(medicalRecord.getFirstName().equals(firstName) && medicalRecord.getLastName().equals(lastName)){
                result = medicalRecord;
                break;
            }
        }
        return result;
    }

    /**
     *
     * This method creates a list of MedicalRecords from the wanted person and its relatives. It returns this new list.
     *
     * @see FireStation
     * @see FireStationManagement#getAddressesFromFireStationNumber(String, ArrayList)
     *
         * @param medicalRecordListJSON the list of all the MedicalRecords from the Database.
     * @param firstName the first name of the person who is searched.
     * @param lastName the last name of the person who is searched.
     * @return the list of MedicalRecords.
     *
     * @author denisSiveton
     * @version 1.0
     */
    public ArrayList<MedicalRecord> filterListMedicalRecordWithFirstNameAndLastName(ArrayList<MedicalRecord> medicalRecordListJSON, String lastName, String firstName) {
        ArrayList<MedicalRecord> medicalRecordListFiltered = new ArrayList<>();
        for(int run = 0; run <2;run++){
            for(int i =0; i <medicalRecordListJSON.size(); i++){
                if(run == 0) {
                    if (medicalRecordListJSON.get(i).getFirstName().equals(firstName) && medicalRecordListJSON.get(i).getLastName().equals(lastName)) {
                        medicalRecordListFiltered.add(medicalRecordListJSON.get(i));
                        medicalRecordListJSON.remove(i);
                    }
                }
                else{
                    if (medicalRecordListJSON.get(i).getLastName().equals(lastName)) {
                        medicalRecordListFiltered.add(medicalRecordListJSON.get(i));
                    }
                }
            }
        }
        return medicalRecordListFiltered;
    }

    /**
     *
     * This method creates a list of MedicalRecords based on the list of person. It returns this new list.
     *
     * @see MedicalRecord
     * @see Person
     *
     * @param personListFromAddress a list of Persons.
     * @param medicalRecords the list of all the MedicalRecords from the Database.
     * @return the list of MedicalRecords based on the list of person.
     *
     * @author denisSiveton
     * @version 1.0
     */
    public ArrayList<MedicalRecord> getMedicalRecordFromPersonList(ArrayList<Person> personListFromAddress, ArrayList<MedicalRecord> medicalRecords) {
        ArrayList<MedicalRecord> medicalRecordListFromPersonList = new ArrayList<>();
        for(Person person : personListFromAddress){
            medicalRecordListFromPersonList.add(getMedicalRecord(person.getFirstName(), person.getLastName(), medicalRecords));
        }
        return medicalRecordListFromPersonList;
    }
}
