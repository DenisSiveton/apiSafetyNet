package com.safetynet.apiSafetyNet.controller;

import com.safetynet.apiSafetyNet.model.InputData.Person;
import com.safetynet.apiSafetyNet.service.PersonService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.FileNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@Import(PersonController.class)
public class PersonControllerTest2 {

    @MockBean
    private PersonService personService;

    @Autowired
    private PersonController personController;

    private static Person generatePerson() {
        return new Person("Denis","Siveton","15 Fame Road","Culver","97451","741-874-6512","ds@email.com");
    }

    @Test
    public void testAddPerson_VerifyMethodIsCalledWithCorrectArgument() throws FileNotFoundException {
        //ARRANGE
        Person personTest = generatePerson();
        when(personService.addPerson(any())).thenReturn(personTest);
        personController = new PersonController(personService);

        //ACT
        personController.addPerson(personTest);

        //ASSERT
        ArgumentCaptor<Person> argumentCaptor = ArgumentCaptor.forClass(Person.class);
        verify(personService).addPerson(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getFirstName()).isEqualTo(personTest.getFirstName());
    }

    @Test
    public void testModifyInfoPerson_VerifyMethodIsCalledWithCorrectArgument(){
        //ARRANGE
        Person personTest = generatePerson();
        when(personService.modifyInfoPerson(any())).thenReturn(personTest);
        personController = new PersonController(personService);

        //ACT
        personController.modifyInfoPerson(personTest);
        
        //ASSERT
        ArgumentCaptor<Person> argumentCaptor = ArgumentCaptor.forClass(Person.class);
        verify(personService).modifyInfoPerson(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getFirstName()).isEqualTo(personTest.getFirstName());
    }

    @Test
    public void testDeletePerson_VerifyMethodIsCalledWithCorrectArgument(){
        //ARRANGE
        Person personTest = generatePerson();
        personController = new PersonController(personService);

        //ACT
        personController.deletePerson(personTest);

        //ASSERT
        ArgumentCaptor<Person> argumentCaptor = ArgumentCaptor.forClass(Person.class);
        verify(personService).deletePerson(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getFirstName()).isEqualTo(personTest.getFirstName());
    }

    @Test
    public void testGetChildListFromAddress_VerifyMethodIsCalledWithCorrectArgument(){
        //ARRANGE
        String addressTest = "15 Road St";
        when(personService.getChildListFromAddress(any())).thenReturn(null);
        personController = new PersonController(personService);

        //ACT
        personController.getChildListFromAddress(addressTest);

        //ASSERT
        ArgumentCaptor<String > argumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(personService).getChildListFromAddress(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue()).isEqualTo(addressTest);
    }

    @Test
    public void testGetPhoneNumberListFromFireStationNumber_VerifyMethodIsCalledWithCorrectArgument(){
        //ARRANGE
        String fireStationNumberTest = "1";
        when(personService.getPhoneNumberListFromFireStationNumber(any())).thenReturn(null);
        personController = new PersonController(personService);

        //ACT
        personController.getPhoneNumberListFromFireStationNumber(fireStationNumberTest);

        //ASSERT
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(personService).getPhoneNumberListFromFireStationNumber(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue()).isEqualTo(fireStationNumberTest);
    }

    @Test
    public void testGetMailFromPersonFromCity_VerifyMethodIsCalledWithCorrectArgument(){
        //ARRANGE
        String cityTest = "Culver";
        when(personService.getMailFromAllPersonsFromCity(any())).thenReturn(null);
        personController = new PersonController(personService);

        //ACT
        personController.getMailFromPersonFromCity(cityTest);

        //ASSERT
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(personService).getMailFromAllPersonsFromCity(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue()).isEqualTo(cityTest);
    }

    @Test
    public void testGetInfoFromPersonWithName_VerifyMethodIsCalledWithCorrectArgument(){
        //ARRANGE
        String firstName = "Oliver";
        String lastName = "Queen";
        when(personService.getInfoFromPersonWithName(any(), any())).thenReturn(null);
        personController = new PersonController(personService);

        //ACT
        personController.getInfoFromPersonWithName(firstName, lastName);

        //ASSERT
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(personService).getInfoFromPersonWithName(argumentCaptor.capture(), argumentCaptor.capture());
        assertThat(argumentCaptor.getAllValues().get(0)).isEqualTo(firstName);
        assertThat(argumentCaptor.getAllValues().get(1)).isEqualTo(lastName);
    }
}
