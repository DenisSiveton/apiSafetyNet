package com.safetynet.apiSafetyNet.service.management;

import com.safetynet.apiSafetyNet.model.Data.PeopleDetailed;
import com.safetynet.apiSafetyNet.model.InputData.MedicalRecord;
import com.safetynet.apiSafetyNet.model.InputData.Person;
import com.safetynet.apiSafetyNet.model.OutputData.AddressInfo;
import com.safetynet.apiSafetyNet.model.OutputData.HomeInfo;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class HomeInfoManagement {

    private MedicalRecordManagement medicalRecordManagement;
    private PersonManagement personManagement;

    public HomeInfoManagement(MedicalRecordManagement medicalRecordManagement, PersonManagement personManagement) {
        this.medicalRecordManagement = medicalRecordManagement;
        this.personManagement = personManagement;
    }

    /**
     *
     * This method fills the list of HomeInfo with the data based on list of the FireStation's number.
     *
     * @see AddressInfo
     * @see PeopleDetailed
     * @see FireStationManagement#addAddressesToListForEachStationNumber(ArrayList, ArrayList)
     *
     * @param stations the list of the FireStation's number.
     * @param homesInfo the object of the request that needs to be filled.
     * @param addressListByFireStationNumber the list of lists of addresses created by the method :"addAddressesToListForEachStationNumber".
     * @param personListJSON the list of all the Person from the Data.
     * @param medicalRecordListJSON the list of all the MedicalRecord from the Data.
     *
     * @author denisSiveton
     * @version 1.0
     */
    public void createHomeInfoListBasedOnAddressList(ArrayList<String> stations, ArrayList<HomeInfo> homesInfo, ArrayList<ArrayList<String>> addressListByFireStationNumber, ArrayList<Person> personListJSON, ArrayList<MedicalRecord> medicalRecordListJSON) {

        for(int i =0; i<stations.size();i++){
            // for each station, we run through the relevant list of address
            ArrayList<String> addressList = addressListByFireStationNumber.get(i);
            for(String address : addressList){
                ArrayList<PeopleDetailed> peopleInHouse = new ArrayList<>();
                for(Person person : personListJSON) {
                    //for each person, if it lives at the current address then it's added to the peopleInHouse data
                    if (person.getAddress().equals(address)){
                        MedicalRecord medicalRecordPerson = medicalRecordManagement.getMedicalRecord(person.getFirstName(), person.getLastName(), medicalRecordListJSON);
                        peopleInHouse.add(new PeopleDetailed(person.getLastName(), person.getPhone(), personManagement.getAgeFromPerson(medicalRecordPerson), medicalRecordPerson.getMedications(), medicalRecordPerson.getAllergies()));
                    }
                }
                homesInfo.add(new HomeInfo(address, peopleInHouse));
            }
        }
    }
}
