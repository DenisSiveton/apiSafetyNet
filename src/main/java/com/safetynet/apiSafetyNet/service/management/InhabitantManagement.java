package com.safetynet.apiSafetyNet.service.management;

import com.safetynet.apiSafetyNet.model.Data.Inhabitant;
import com.safetynet.apiSafetyNet.model.InputData.MedicalRecord;
import com.safetynet.apiSafetyNet.model.InputData.Person;
import com.safetynet.apiSafetyNet.model.OutputData.ChildrenInfo;
import com.safetynet.apiSafetyNet.model.OutputData.InhabitantInfo;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class InhabitantManagement {
    private PersonManagement personManagement;

    public InhabitantManagement(PersonManagement personManagement) {
        this.personManagement = personManagement;
    }

    /**
     *
     * This method calculates the amount of adults and children by calculating their age then it updates the data of InhabitantInfo.
     *
     * @see InhabitantInfo
     * @see MedicalRecord
     * @see PersonManagement#getAgeFromPerson(MedicalRecord) 
     *
     * @param inhabitantInfo the object that will be returned to the user.
     * @param medicalRecords the list of MedicalRecords.
     *
     * @author denisSiveton
     * @version 1.0
     */
    public void calculateNumberOfAdultsAndChildrenInInhabitants(InhabitantInfo inhabitantInfo, ArrayList<MedicalRecord> medicalRecords) {
        for(MedicalRecord medicalRecord : medicalRecords){
            for(Inhabitant inhabitant :inhabitantInfo.getInhabitants()){
                if (inhabitant.getLastName().equals(medicalRecord.getLastName()) && inhabitant.getFirstName().equals(medicalRecord.getFirstName())){
                    int ageInhabitant = personManagement.getAgeFromPerson(medicalRecord);
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

    /**
     *
     * This method creates all the inhabitants and adds them to the InhabitantInfo data.
     *
     * @see InhabitantInfo
     * @see Inhabitant
     * @see Person
     *
     * @param inhabitantInfo the object that will be returned to the user.
     * @param persons the list of all the Persons.
     * @param addresses a list of address.
     *
     * @author denisSiveton
     * @version 1.0
     */
    public void createInhabitantsWithAddressList(InhabitantInfo inhabitantInfo, ArrayList<Person> persons, ArrayList<String> addresses) {
        for(Person person : persons){
            if(addresses.contains(person.getAddress())){
                inhabitantInfo.getInhabitants().add(new Inhabitant(person.getFirstName(), person.getLastName(),person.getAddress(), person.getPhone()));
            }
        }
    }
}
