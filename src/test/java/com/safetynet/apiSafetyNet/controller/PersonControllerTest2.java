package com.safetynet.apiSafetyNet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.apiSafetyNet.model.InputData.Person;
import com.safetynet.apiSafetyNet.service.PersonService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@Import(PersonController.class)
public class PersonControllerTest2 {

    @MockBean
    private PersonService personService;

    @Autowired
    private PersonController personController;

    @Test
    public void testModifyInfoPerson2(){
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

    private static Person generatePerson() {
        Person personTest = new Person();

        personTest.setFirstName("Denis");
        personTest.setLastName("Siveton");
        personTest.setAddress("15 Fame Road");
        personTest.setCity("Culver");
        personTest.setZip("97451");
        personTest.setPhone("741-874-6512");
        personTest.setEmail("ds@email.com");
        return personTest;
    }
}
