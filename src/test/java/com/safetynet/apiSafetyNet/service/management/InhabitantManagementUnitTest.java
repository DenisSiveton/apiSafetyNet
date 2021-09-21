package com.safetynet.apiSafetyNet.service.management;

import com.safetynet.apiSafetyNet.model.Data.Inhabitant;
import com.safetynet.apiSafetyNet.model.InputData.MedicalRecord;
import com.safetynet.apiSafetyNet.model.InputData.Person;
import com.safetynet.apiSafetyNet.model.OutputData.InhabitantInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@Import(InhabitantManagement.class)
public class InhabitantManagementUnitTest {

    @Autowired
    private InhabitantManagement inhabitantManagement;

    @MockBean
    PersonManagement personManagement;

    @BeforeEach
    public void initializeTest(){
        inhabitantManagement = new InhabitantManagement(personManagement);
    }
    @Test
    public void calculateNumberOfAdultsAndChildrenInInhabitants_Test() {
        //ARRANGE
        ArrayList<MedicalRecord> medicalRecordListTest = generateMedicalRecordList();
        ArrayList<Inhabitant> listInhabitantTest = generateListInhabitant();
        InhabitantInfo inhabitantInfoTest = new InhabitantInfo(listInhabitantTest, 0, 0);
        when(personManagement.getAgeFromPerson(any(MedicalRecord.class))).thenReturn(29, 26, 1);

        //ACT
        inhabitantManagement.calculateNumberOfAdultsAndChildrenInInhabitants(inhabitantInfoTest, medicalRecordListTest);

        //ASSERT

        assertThat(inhabitantInfoTest.getNumberOfAdults()).isEqualTo(2);
        assertThat(inhabitantInfoTest.getInhabitants().get(1).getFirstName()).isEqualTo("Laura");
        assertThat(inhabitantInfoTest.getInhabitants().size()).isEqualTo(3);
    }

    @Test
    public void getInhabitants() {
        //ARRANGE
        InhabitantInfo inhabitantInfoTest = new InhabitantInfo(new ArrayList<>(), 0, 0);
        ArrayList<Person> personListTest = generateListPerson();
        ArrayList<String> addressListTest = new ArrayList<>(Arrays.asList("address 1", "address 2"));
        //ACT

        inhabitantManagement.createInhabitantsWithAddressList(inhabitantInfoTest, personListTest, addressListTest);
        //ASSERT
        assertThat(inhabitantInfoTest.getInhabitants().size()).isEqualTo(3);
        assertThat(inhabitantInfoTest.getInhabitants().get(2).getFirstName()).isEqualTo("Eric");
        assertThat(inhabitantInfoTest.getNumberOfAdults()).isEqualTo(0);
    }

    private ArrayList<Inhabitant> generateListInhabitant() {
        Inhabitant inhabitant1 = new Inhabitant("Denis", "Siveton", "address 1", "741-874-6512");
        Inhabitant inhabitant2 = new Inhabitant("Laura", "Palandre", "address 2", "741-874-6513");
        Inhabitant inhabitant3 = new Inhabitant("Eric", "Palandre", "address 2", "741-874-6513");

        return new ArrayList<>(Arrays.asList(inhabitant1, inhabitant2, inhabitant3));
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
