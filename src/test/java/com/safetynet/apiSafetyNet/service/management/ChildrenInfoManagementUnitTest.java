package com.safetynet.apiSafetyNet.service.management;

import com.safetynet.apiSafetyNet.model.InputData.MedicalRecord;
import com.safetynet.apiSafetyNet.model.InputData.Person;
import com.safetynet.apiSafetyNet.model.OutputData.ChildrenInfo;
import com.safetynet.apiSafetyNet.service.FireStationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

import static java.time.LocalDate.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@Import({ChildrenInfoManagement.class, PersonManagement.class})
public class ChildrenInfoManagementUnitTest {

    @Autowired
    private ChildrenInfoManagement childrenInfoManagement;

    @MockBean
    private PersonManagement personManagement;


    @Test
    public void calculateAgeForPersonListAndAddPersonToAdultOrChildList_Test(){
        //ARRANGE
        ArrayList<Person> listPersonTest = generateListPerson();
        ArrayList<MedicalRecord> listMRTest = generateMedicalRecordList();
        ChildrenInfo childrenInfoTest = new ChildrenInfo(new ArrayList<>(), new ArrayList<>(), 0);
        when(personManagement.getAgeFromPerson(any())).thenReturn(getAgeFromPerson(listMRTest.get(0).getBirthDate()),getAgeFromPerson(listMRTest.get(1).getBirthDate()),getAgeFromPerson(listMRTest.get(2).getBirthDate()));

        ChildrenInfoManagement childrenInfoManagement = new ChildrenInfoManagement(personManagement);

        //ACT
        childrenInfoManagement.calculateAgeForPersonListAndAddPersonToAdultOrChildList(listPersonTest, listMRTest, childrenInfoTest);

        //ASSERT

        assertThat(childrenInfoTest.getNumberOfChildren()).isEqualTo(1);
        assertThat(childrenInfoTest.getOtherMembers().get(0).getFirstName()).isEqualTo("Denis");
    }

    private ArrayList<MedicalRecord> generateMedicalRecordList() {
        MedicalRecord firstMR = new MedicalRecord("Denis", "Siveton", "06/01/1992",
                new ArrayList<String>(Arrays.asList("aznol:200mg")), new ArrayList<String>(Arrays.asList("Peanut")));
        MedicalRecord secondMR = new MedicalRecord("Laura", "Siveton", "07/15/1994",
                new ArrayList<String>(Arrays.asList("aznol:200mg")), new ArrayList<String>(Arrays.asList("Shellfish")));
        MedicalRecord thirdMR = new MedicalRecord("Eric", "Siveton", "01/12/2020",
                new ArrayList<String>(), new ArrayList<String>());

        return new ArrayList<>(Arrays.asList(firstMR, secondMR, thirdMR));
    }

    private ArrayList<Person> generateListPerson() {
        Person firstPerson = new Person("Denis","Siveton","15 Fame Road","Culver","97451","741-874-6512","ds@email.com");
        Person secondPerson = new Person("Laura","Siveton","15 Fame Road","Culver","97451","741-874-6513","ls@email.com");
        Person thirdPerson = new Person("Eric","Siveton","15 Fame Road","Culver","97451","741-874-6512","ds@email.com");

        return new ArrayList<Person>(Arrays.asList(firstPerson, secondPerson, thirdPerson));
    }

    private int getAgeFromPerson(String Date) {
        int agePerson;

        /*Cast the Person's birthDate in LocalDate type */
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate birthDate = LocalDate.parse(Date, formatter);
        LocalDate currentDate = now();

        /*Calculate age in years */
        agePerson = Period.between(birthDate, currentDate).getYears();
        return agePerson;
    }
}
