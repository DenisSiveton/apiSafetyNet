package com.safetynet.apiSafetyNet.service.management;

import com.safetynet.apiSafetyNet.model.InputData.MedicalRecord;
import com.safetynet.apiSafetyNet.model.InputData.Person;
import com.safetynet.apiSafetyNet.model.OutputData.AddressInfo;
import com.safetynet.apiSafetyNet.model.OutputData.PersonInfo;
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
@Import(PersonManagement.class)
public class PersonManagementUnitTest {

    @Autowired
    private PersonManagement personManagement;

    @MockBean
     MedicalRecordManagement medicalRecordManagement;

    @BeforeEach
    public void initializeTest(){
        personManagement = new PersonManagement(medicalRecordManagement);
    }

    @Test
    public void getPersonsFromAddress_Test() {
        //ARRANGE
        String addressTest = "address 2";
        AddressInfo addressInfoTest = new AddressInfo(null, new ArrayList<>());
        ArrayList<MedicalRecord> medicalRecordListTest = generateMedicalRecordList();
        ArrayList<Person> personListTest = generateListPerson();

        when(medicalRecordManagement.getMedicalRecord(any(), any(), any())).thenReturn(medicalRecordListTest.get(1), medicalRecordListTest.get(2));

        //ACT
        personManagement.getPersonsFromAddress(addressTest, addressInfoTest, medicalRecordListTest, personListTest);

        //ASSERT
        assertThat(addressInfoTest.getPeopleDetailedArrayList().size()).isEqualTo(2);
        assertThat(addressInfoTest.getPeopleDetailedArrayList().get(0).getLastName()).isEqualTo(personListTest.get(1).getLastName());
    }

    @Test
    public void getPhonesFromAddressList_Test() {
        //ARRANGE
        ArrayList<Person> personListTest = generateListPerson();
        ArrayList<String> addressListTest = new ArrayList<>(Arrays.asList("address 2"));

        //ACT
        ArrayList<String> phoneListTest = personManagement.getPhonesFromAddressList(personListTest, addressListTest);

        //ASSERT
        assertThat(phoneListTest.size()).isEqualTo(2);
        assertThat(phoneListTest.get(0)).isEqualTo(personListTest.get(1).getPhone());
    }

    @Test
    public void createEmailListFromAddress_Test() {
        //ARRANGE
        String cityTest = "Culver";
        ArrayList<Person> personListTest = generateListPerson();

        //ACT
        ArrayList<String> emailListTest = personManagement.createEmailListFromAddress(cityTest, personListTest);

        //ASSERT
        assertThat(emailListTest.size()).isEqualTo(3);
        assertThat(emailListTest.get(2)).isEqualTo("lp@email.com");
    }

    @Test
    public void getAgeFromPerson_Test() {
        //ARRANGE
        MedicalRecord medicalRecordTest = new MedicalRecord("Denis", "Siveton", "06/01/1992",
                new ArrayList<String>(Arrays.asList("aznol:200mg")), new ArrayList<String>(Arrays.asList("Peanut")));
        //ACT
        int age = personManagement.getAgeFromPerson(medicalRecordTest);

        //ASSERT
        assertThat(age).isEqualTo(29);
    }

    @Test
    public void filterListPersonWithFirstNameAndLastName_Test() {
        //ARRANGE
        String firstNameTest = "Laura";
        String lastNameTest = "Palandre";
        ArrayList<Person> personListTest = generateListPerson();

        //ACT
        ArrayList<Person> personListFiltered = personManagement.filterListPersonWithFirstNameAndLastName(personListTest, lastNameTest, firstNameTest);

        //ASSERT
        assertThat(personListFiltered.size()).isEqualTo(2);
        assertThat(personListFiltered.get(0).getFirstName()).isEqualTo(firstNameTest);
    }

    @Test
    public void createPersonInfoListFromFilteredLists_Test() {
        //ARRANGE
        ArrayList<PersonInfo> personInfoListTest = new ArrayList<>();
        ArrayList<MedicalRecord> medicalRecordListTest = generateMedicalRecordList();
        medicalRecordListTest.remove(0);
        ArrayList<Person> personListTest = generateListPerson();
        personListTest.remove(0);
        //ACT
        personManagement.createPersonInfoListFromFilteredLists(personInfoListTest, medicalRecordListTest, personListTest);

        //ASSERT
        assertThat(personInfoListTest.size()).isEqualTo(2);
        assertThat(personInfoListTest.get(1).getLastName()).isEqualTo("Palandre");

    }

    @Test
    public void getPersonListFromAddress_Test() {
        //ARRANGE
        String addressTest = "address 2";
        ArrayList<Person> personListTest = generateListPerson();

        //ACT
        ArrayList<Person> personListFromAddress = personManagement.getPersonListFromAddress(addressTest, personListTest);

        //ASSERT
        assertThat(personListFromAddress.size()).isEqualTo(2);
        assertThat(personListFromAddress.get(1).getFirstName()).isEqualTo("Eric");
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
        Person secondPerson = new Person("Laura","Palandre","address 2","Culver","97451","741-874-6513","lp@email.com");
        Person thirdPerson = new Person("Eric","Palandre","address 2","Culver","97451","741-874-6513","lp@email.com");

        return new ArrayList<Person>(Arrays.asList(firstPerson, secondPerson, thirdPerson));
    }
}
