package com.safetynet.apiSafetyNet.controller.integrationTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.apiSafetyNet.model.InputData.Person;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.io.*;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerIT {

    @Autowired
    public MockMvc mockMvc;

    private static Person generatePerson() {
        return new Person("Denis","Siveton","15 Fame Road","Culver","97451","741-874-6512","ds@email.com");
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @AfterEach
    public void endTest() throws IOException {
        InputStream input = new FileInputStream("src/test/resources/dataTestOrigin.json");
        OutputStream output = new FileOutputStream("src/test/resources/dataTest.json");
        IOUtils.copy(input, output);
    }

    @Test
    public void testAddPerson() throws Exception {

        //ARRANGE
        Person personToAddTest = generatePerson();

        //ACT
        mockMvc.perform(post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(personToAddTest)))

        //ASSERT
                .andExpect(status().isCreated())
                .andExpect(jsonPath("firstName", is(personToAddTest.getFirstName())));
    }

    @Test
    public void testModifyInfoPerson() throws Exception {

        //ARRANGE
        Person personToModifyTest = generatePerson();

        //ACT
        mockMvc.perform(patch("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(personToModifyTest)))

        //ASSERT
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("firstName", is(personToModifyTest.getFirstName())));
    }

    @Test
    public void testDeletePerson() throws Exception {

        //ARRANGE
        Person personToDeleteTest = generatePerson();

        //ACT
        mockMvc.perform(patch("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(personToDeleteTest)))

        //ASSERT
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetChildListFromAddress() throws Exception {

        //ARRANGE
        String addressTest = "address 2";

        //ACT
        mockMvc.perform(get("/childAlert")
                .param("address",addressTest))

        //ASSERT
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.children.[0].firstName", is("Eric")))
                .andExpect(jsonPath("$.otherMembers.[0].firstName", is("Laura")))
                .andExpect(jsonPath("$.numberOfChildren", is(1)));
    }

    @Test
    public void testGetPhoneNumberListFromFireStationNumber() throws Exception {

        //ARRANGE
        String fireStationNumberTest = "1";

        //ACT
        mockMvc.perform(get("/phoneAlert")
                .param("firestation",fireStationNumberTest))

        //ASSERT
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]", is("741-874-6512")));
    }

    @Test
    public void getMailFromPersonFromCity() throws Exception {

        //ARRANGE
        String cityTest = "Culver";

        //ACT
        mockMvc.perform(get("/communityEmail")
                .param("city", cityTest))

        //ASSERT
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]", is("ds@email.com")));
    }

    @Test
    public void getInfoFromPersonWithName() throws Exception {

        //ARRANGE
        String firstName = "Laura";
        String lastName = "Palandre";

        //ACT
        mockMvc.perform(get("/personInfo")
                .param("firstName", firstName)
                .param("lastName", lastName))

        //ASSERT
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email", is("lp@email.com")))
                .andExpect(jsonPath("$[0].age", is(27)));
    }
}
