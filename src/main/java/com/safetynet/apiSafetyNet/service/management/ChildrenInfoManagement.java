package com.safetynet.apiSafetyNet.service.management;

import com.safetynet.apiSafetyNet.model.Data.People;
import com.safetynet.apiSafetyNet.model.Data.PeopleDetailed;
import com.safetynet.apiSafetyNet.model.InputData.MedicalRecord;
import com.safetynet.apiSafetyNet.model.InputData.Person;
import com.safetynet.apiSafetyNet.model.OutputData.ChildrenInfo;
import com.safetynet.apiSafetyNet.model.OutputData.HomeInfo;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ChildrenInfoManagement {

    @Autowired
    private PersonManagement personManagement;

    public ChildrenInfoManagement() {
    }

    public ChildrenInfoManagement(PersonManagement personManagement) {
        this.personManagement = personManagement;
    }

    /**
     *
     * This method fills the ChildrenInfo data from the list of Persons and MedicalRecords that live at a specified address.
     *
     * @see Person
     * @see MedicalRecord
     * @see ChildrenInfo
     *
     * @param personListFromAddress the list of the person that live at a specified address
     * @param medicalRecordListFromPersonList the list of the MedicalRecord that are in "personListFromAddress".
     * @param childrenInfo the new ChilrenInfo that will be updated.
     * @return the ChildrenInfo updated with the content of the people living at the address.
     *
     * @author denisSiveton
     * @version 1.0
     */
    public void calculateAgeForPersonListAndAddPersonToAdultOrChildList(ArrayList<Person> personListFromAddress, ArrayList<MedicalRecord> medicalRecordListFromPersonList, ChildrenInfo childrenInfo) {
        for(int i = 0; i < personListFromAddress.size(); i++){
            int agePerson = personManagement.getAgeFromPerson(medicalRecordListFromPersonList.get(i));
            if(agePerson<19){
                childrenInfo.getChildren().add(new People(personListFromAddress.get(i).getFirstName(),personListFromAddress.get(i).getLastName(), agePerson));
                childrenInfo.setNumberOfChildren(childrenInfo.getNumberOfChildren()+1);
            }
            else {
                childrenInfo.getOtherMembers().add(new People(personListFromAddress.get(i).getFirstName(),personListFromAddress.get(i).getLastName(), agePerson));
            }
        }
    }
}
