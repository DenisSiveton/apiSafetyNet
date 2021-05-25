package com.safetynet.apiSafetyNet.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.apiSafetyNet.model.InputData.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.safetynet.apiSafetyNet.service.PersonService;

@WebMvcTest(controllers = PersonController.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    @Test
    public void testAddPerson() throws Exception {
        Person personTest = generatePerson();
        mockMvc.perform(post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(personTest)))
                .andExpect(status().isOk());
    }

    @Test
    public void testModifyInfoPerson() throws Exception {
        Person personTest = generatePerson();
        mockMvc.perform(patch("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(personTest)))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeletePerson() throws Exception {
        Person personTest = generatePerson();
        mockMvc.perform(delete("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(personTest)))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetChildListFromAddress() throws Exception {
        String address = "1509 Culver St";
        mockMvc.perform(get("/childAlert?address=<address>")
                .param("address", address))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetPhoneNumberListFromFireStationNumber() throws Exception {
        String fireStationNumber = "1";
        mockMvc.perform(get("/phoneAlert?firestation=<firestationNumber>")
                .param("firestationNumber",fireStationNumber))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetMailFromPersonFromCity() throws Exception {
        String city = "Culver";
        mockMvc.perform(get("/communityEmail?city=<city>")
                .param("city", city))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetIntoFromPersonWithName() throws Exception {
        String firstName = "John";
        String lastName = "Boyd";
        mockMvc.perform(get("/personInfo?firstName=<firstName>&lastName=<lastName>")
                .param("firstName", firstName)
                .param("lastName", lastName))
                .andExpect(status().isOk());
    }

    private static Person generatePerson() {
        Person personTest = new Person();

        personTest.setFirstName("Denis");
        personTest.setLastName("Siveton");
        personTest.setAddress("15 Fame Road");
        personTest.setCity("Culver");
        personTest.setZip(97451);
        personTest.setPhone("741-874-6512");
        personTest.setEmail("ds@email.com");
        return personTest;
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
