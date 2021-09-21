package com.safetynet.apiSafetyNet.service;

import com.safetynet.apiSafetyNet.model.InputData.Person;
import com.safetynet.apiSafetyNet.model.OutputData.ChildrenInfo;
import com.safetynet.apiSafetyNet.repository.FireStationCRUD;
import com.safetynet.apiSafetyNet.repository.MedicalRecordCRUD;
import com.safetynet.apiSafetyNet.repository.PersonCRUD;
import com.safetynet.apiSafetyNet.service.management.ChildrenInfoManagement;
import com.safetynet.apiSafetyNet.service.management.FireStationManagement;
import com.safetynet.apiSafetyNet.service.management.MedicalRecordManagement;
import com.safetynet.apiSafetyNet.service.management.PersonManagement;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.*;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
@Import(PersonService.class)
public class PersonServiceUnitTest {

    @MockBean
    private PersonCRUD personCRUD;
    @MockBean
    private FireStationCRUD fireStationCRUD;
    @MockBean
    private MedicalRecordCRUD medicalRecordCRUD;
    @MockBean
    private PersonManagement personManagement;
    @MockBean
    private FireStationManagement fireStationManagement;
    @MockBean
    private MedicalRecordManagement medicalRecordManagement;
    @MockBean
    private ChildrenInfoManagement childrenInfoManagement;

    @Autowired
    private PersonService personService;

    private static Person generatePerson() {
        return new Person("Denis","Siveton","15 Fame Road","Culver","97451","741-874-6512","ds@email.com");
    }

    @BeforeEach
    public void endTest() throws IOException {
        InputStream input = new FileInputStream("src/test/resources/dataTestOrigin.json");
        OutputStream output = new FileOutputStream("src/test/resources/dataTest.json");
        IOUtils.copy(input, output);
    }

    @Test
    public void testAddPerson_VerifyMethodIsCalledWithCorrectArgument() throws FileNotFoundException {
        //ARRANGE
        Person personTest = generatePerson();
        when(personCRUD.addPerson(any())).thenReturn(personTest);
        personService = new PersonService("src/test/resources/dataTest.json", personCRUD, fireStationCRUD, medicalRecordCRUD, personManagement, fireStationManagement, medicalRecordManagement, childrenInfoManagement);

        //ACT
        personService.addPerson(personTest);

        //ASSERT

        verify(personCRUD, times(1)).addPerson(personTest);
    }

    @Test
    public void testModifyInfoPerson_VerifyMethodIsCalledWithCorrectArgument(){
        //ARRANGE
        Person personTest = generatePerson();
        when(personCRUD.modifyInfoPerson(any())).thenReturn(personTest);
        personService = new PersonService("src/test/resources/dataTest.json", personCRUD, fireStationCRUD, medicalRecordCRUD, personManagement, fireStationManagement, medicalRecordManagement, childrenInfoManagement);

        //ACT
        personService.modifyInfoPerson(personTest);

        //ASSERT
        verify(personCRUD, times(1)).modifyInfoPerson(personTest);
    }

    @Test
    public void testDeletePerson_VerifyMethodIsCalledWithCorrectArgument(){
        //ARRANGE
        Person personTest = generatePerson();
        personService = new PersonService("src/test/resources/dataTest.json", personCRUD, fireStationCRUD, medicalRecordCRUD, personManagement, fireStationManagement, medicalRecordManagement, childrenInfoManagement);

        //ACT
        personService.deletePerson(personTest);

        //ASSERT
        verify(personCRUD, times(1)).deletePerson(personTest);
    }

    @Test
    public void testGetChildListFromAddress_VerifyMethodIsCalledWithCorrectArgument(){
        //ARRANGE
        String addressTest = "15 Road St";

        when(personCRUD.getPersonsFromJSONFile(any())).thenReturn(null);
        when(medicalRecordCRUD.getAllMedicalRecords(any())).thenReturn(null);
        when(personManagement.getPersonListFromAddress(eq(addressTest), any())).thenReturn(null);
        when(medicalRecordManagement.getMedicalRecordFromPersonList(any(), any())).thenReturn(null);

        personService = new PersonService("src/test/resources/dataTest.json", personCRUD, fireStationCRUD, medicalRecordCRUD, personManagement, fireStationManagement, medicalRecordManagement, childrenInfoManagement);

        //ACT
        personService.getChildListFromAddress(addressTest);

        //ASSERT
        verify(personCRUD, times(1)).getPersonsFromJSONFile(any());
        verify(medicalRecordCRUD, times(1)).getAllMedicalRecords(any());
        verify(personManagement, times(1)).getPersonListFromAddress(eq(addressTest), any());
        verify(medicalRecordManagement, times(1)).getMedicalRecordFromPersonList(any(), any());
        verify(childrenInfoManagement, times(1)).calculateAgeForPersonListAndAddPersonToAdultOrChildList(any(), any(), any(ChildrenInfo.class));

    }

    @Test
    public void testGetPhoneNumberListFromFireStationNumber_VerifyMethodIsCalledWithCorrectArgument(){
        //ARRANGE
        String fireStationNumberTest = "1";

        when(personCRUD.getPersonsFromJSONFile(any())).thenReturn(null);
        when(fireStationCRUD.getFireStationsFromJSONFile(any())).thenReturn(null);
        when(fireStationManagement.getAddressesFromFireStationNumber(eq(fireStationNumberTest), any())).thenReturn(null);
        when(personManagement.getPhonesFromAddressList(any(), any())).thenReturn(null);

        personService = new PersonService("src/test/resources/dataTest.json", personCRUD, fireStationCRUD, medicalRecordCRUD, personManagement, fireStationManagement, medicalRecordManagement, childrenInfoManagement);

        //ACT
        personService.getPhoneNumberListFromFireStationNumber(fireStationNumberTest);

        //ASSERT

        verify(personCRUD, times(1)).getPersonsFromJSONFile(any());
        verify(fireStationCRUD, times(1)).getFireStationsFromJSONFile(any());
        verify(fireStationManagement, times(1)).getAddressesFromFireStationNumber(eq(fireStationNumberTest), any());
        verify(personManagement, times(1)).getPhonesFromAddressList(any(), any());
    }

    @Test
    public void testGetMailFromPersonFromCity_VerifyMethodIsCalledWithCorrectArgument(){
        //ARRANGE
        String cityTest = "Culver";

        when(personCRUD.getPersonsFromJSONFile(any())).thenReturn(null);
        when(personManagement.createEmailListFromAddress(eq(cityTest), any())).thenReturn(null);

        personService = new PersonService("src/test/resources/dataTest.json", personCRUD, fireStationCRUD, medicalRecordCRUD, personManagement, fireStationManagement, medicalRecordManagement, childrenInfoManagement);

        //ACT
        personService.getMailFromAllPersonsFromCity(cityTest);

        //ASSERT
        verify(personCRUD, times(1)).getPersonsFromJSONFile(any());
        verify(personManagement, times(1)).createEmailListFromAddress(eq(cityTest), any());
    }

    @Test
    public void testGetInfoFromPersonWithName_VerifyMethodIsCalledWithCorrectArgument(){
        //ARRANGE
        String firstName = "Oliver";
        String lastName = "Queen";

        when(personCRUD.getPersonsFromJSONFile(any())).thenReturn(null);
        when(medicalRecordCRUD.getAllMedicalRecords(any())).thenReturn(null);
        ArrayList<Person> listFilteredTest = new ArrayList<>();
        listFilteredTest.add(generatePerson());
        when(personManagement.filterListPersonWithFirstNameAndLastName(any(), eq(lastName), eq(firstName))).thenReturn(listFilteredTest);
        when(medicalRecordManagement.filterListMedicalRecordWithFirstNameAndLastName(any(), eq(lastName), eq(firstName))).thenReturn(new ArrayList<>());

        personService = new PersonService("src/test/resources/dataTest.json", personCRUD, fireStationCRUD, medicalRecordCRUD, personManagement, fireStationManagement, medicalRecordManagement, childrenInfoManagement);

        //ACT
        personService.getInfoFromPersonWithName(firstName, lastName);

        //ASSERT
        verify(personCRUD, times(1)).getPersonsFromJSONFile(any());
        verify(medicalRecordCRUD, times(1)).getAllMedicalRecords(any());
        verify(personManagement, times(1)).filterListPersonWithFirstNameAndLastName(any(), eq(lastName), eq(firstName));
        verify(medicalRecordManagement, times(1)).filterListMedicalRecordWithFirstNameAndLastName(any(), eq(lastName), eq(firstName));
        verify(personManagement, times(1)).createPersonInfoListFromFilteredLists(any(), any(), any());
    }
}
