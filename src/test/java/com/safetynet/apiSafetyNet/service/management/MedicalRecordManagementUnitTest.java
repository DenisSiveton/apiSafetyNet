package com.safetynet.apiSafetyNet.service.management;

import com.safetynet.apiSafetyNet.model.InputData.MedicalRecord;
import com.safetynet.apiSafetyNet.model.InputData.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@Import(MedicalRecordManagement.class)
public class MedicalRecordManagementUnitTest {

    @Autowired
    private MedicalRecordManagement medicalRecordManagement;

    @BeforeEach
    public void initializeTest(){
        medicalRecordManagement = new MedicalRecordManagement();
    }
    @Test
    public void getMedicalRecord_Test() {
        //ARRANGE
        String firstNameTest = "Laura";
        String lastNameTest = "Palandre";
        ArrayList<MedicalRecord> medicalRecordListTest = generateMedicalRecordList();

        //ACT
        MedicalRecord result = medicalRecordManagement.getMedicalRecord(firstNameTest, lastNameTest, medicalRecordListTest);

        //ASSERT
        assertThat(result.getFirstName()).isEqualTo(firstNameTest);
        assertThat(result.getAllergies().get(0)).isEqualTo("Shellfish");
    }

    @Test
    public void filterListMedicalRecordWithFirstName_Test() {
        //ARRANGE
        String firstNameTest = "Laura";
        String lastNameTest = "Palandre";
        ArrayList<MedicalRecord> medicalRecordListTest = generateMedicalRecordList();

        //ACT
        ArrayList<MedicalRecord> medicalRecordListFilteredTest =  medicalRecordManagement.filterListMedicalRecordWithFirstNameAndLastName(medicalRecordListTest, lastNameTest, firstNameTest);

        //ASSERT
        assertThat(medicalRecordListFilteredTest.size()).isEqualTo(2);
        assertThat(medicalRecordListFilteredTest.get(0).getFirstName()).isEqualTo(firstNameTest);

    }

    @Test
    public void getMedicalRecordFromPersonList_Test() {
        //ARRANGE
        ArrayList<MedicalRecord> medicalRecordListTest = generateMedicalRecordList();
        ArrayList<Person> personListTest = generateListPerson();

        //ACT
        ArrayList<MedicalRecord> medicalRecordListFromPersonListTest = medicalRecordManagement.getMedicalRecordFromPersonList(personListTest, medicalRecordListTest);

        //ASSERT
        assertThat(medicalRecordListFromPersonListTest.size()).isEqualTo(2);
        assertThat(medicalRecordListFromPersonListTest.get(0).getLastName()).isEqualTo("Palandre");
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
        Person firstPerson = new Person("Laura","Palandre","address 2","Culver","97451","741-874-6513","ls@email.com");
        Person secondPerson = new Person("Eric","Palandre","address 2","Culver","97451","741-874-6513","ls@email.com");

        return new ArrayList<Person>(Arrays.asList(firstPerson, secondPerson));
    }
}
