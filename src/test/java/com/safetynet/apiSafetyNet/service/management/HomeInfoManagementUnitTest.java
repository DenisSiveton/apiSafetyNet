package com.safetynet.apiSafetyNet.service.management;

import com.safetynet.apiSafetyNet.model.Data.PeopleDetailed;
import com.safetynet.apiSafetyNet.model.InputData.MedicalRecord;
import com.safetynet.apiSafetyNet.model.InputData.Person;
import com.safetynet.apiSafetyNet.model.OutputData.HomeInfo;
import com.safetynet.apiSafetyNet.service.FireStationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@Import(HomeInfoManagement.class)
public class HomeInfoManagementUnitTest {

    @Autowired
    private HomeInfoManagement homeInfoManagement;

    @MockBean
    private MedicalRecordManagement medicalRecordManagement;
    @MockBean
    private PersonManagement personManagement;

    @Test
    public void createHomeInfoListBasedOnAddressList_Test() {
        //ARRANGE
        ArrayList<String> stationListTest = new ArrayList<>(Arrays.asList("1", "2"));
        ArrayList<HomeInfo> homeInfoListTest = new ArrayList<>();
        ArrayList<ArrayList<String>> addressesListTest = generateListOfAddress();
        ArrayList<Person> personListTest = generateListPerson();
        ArrayList<MedicalRecord> medicalRecordListTest = generateMedicalRecordList();
        homeInfoManagement = new HomeInfoManagement(medicalRecordManagement, personManagement);

        when(medicalRecordManagement.getMedicalRecord(any(), any(), any())).thenReturn(medicalRecordListTest.get(0), medicalRecordListTest.get(1), medicalRecordListTest.get(2));
        when(personManagement.getAgeFromPerson(any())).thenReturn(29, 26, 1);

        //ACT
        homeInfoManagement.createHomeInfoListBasedOnAddressList(stationListTest, homeInfoListTest, addressesListTest, personListTest, medicalRecordListTest);

        //ASSERT
        assertThat(homeInfoListTest.size()).isEqualTo(2);
        assertThat(homeInfoListTest.get(0).getAddress()).isEqualTo("address 1");
        assertThat(homeInfoListTest.get(1).getPeopleInHouse().get(0).getLastName()).isEqualTo("Palandre");

    }

    private ArrayList<ArrayList<String>> generateListOfAddress() {
        ArrayList<ArrayList<String >> listOfAddressList = new ArrayList<>();
        listOfAddressList.add(new ArrayList<>(Arrays.asList("address 1")));
        listOfAddressList.add(new ArrayList<>(Arrays.asList("address 2")));

        return listOfAddressList;
    }

    private ArrayList<MedicalRecord> generateMedicalRecordList() {
        MedicalRecord firstMR = new MedicalRecord("Denis", "Siveton", "06/01/1992",
                new ArrayList<String>(Arrays.asList("aznol:200mg")), new ArrayList<String>(Arrays.asList("Peanut")));
        MedicalRecord secondMR = new MedicalRecord("Laura", "Palandre", "07/15/1994",
                new ArrayList<String>(Arrays.asList("aznol:200mg")), new ArrayList<String>(Arrays.asList("Shellfish")));
        MedicalRecord thirdMR = new MedicalRecord("Eric", "Palandre", "01/12/2020",
                new ArrayList<String>(), new ArrayList<String>());

        return new ArrayList<>(Arrays.asList(firstMR, secondMR, thirdMR));
    }

    private ArrayList<Person> generateListPerson() {
        Person firstPerson = new Person("Denis","Siveton","address 1","Culver","97451","741-874-6512","ds@email.com");
        Person secondPerson = new Person("Laura","Palandre","address 2","Culver","97451","741-874-6513","ls@email.com");
        Person thirdPerson = new Person("Eric","Palandre","address 2","Culver","97451","741-874-6513","ls@email.com");

        return new ArrayList<Person>(Arrays.asList(firstPerson, secondPerson, thirdPerson));
    }

}
