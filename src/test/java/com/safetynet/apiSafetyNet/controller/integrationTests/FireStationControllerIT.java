package com.safetynet.apiSafetyNet.controller.integrationTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.apiSafetyNet.exceptions.FireStationNotFoundException;
import com.safetynet.apiSafetyNet.model.InputData.FireStation;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.io.*;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class FireStationControllerIT {

    @Autowired
    public MockMvc mockMvc;

    private static FireStation generateFireStation() {
        return new FireStation("15 Fame Road", "1");
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
    public void testAddFireStation() throws Exception {

        //ARRANGE
        FireStation fireStationToAddTest = generateFireStation();

        //ACT
        mockMvc.perform(post("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(fireStationToAddTest)))

        //ASSERT
                .andExpect(status().isCreated())
                .andExpect(jsonPath("address", is(fireStationToAddTest.getAddress())));
    }

    @Test
    public void testModifyInfoFireStation() throws Exception {

        //ARRANGE
        FireStation fireStationToAddTest = new FireStation("address 2", "1");

        //ACT
        mockMvc.perform(patch("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(fireStationToAddTest)))

        //ASSERT
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("address", is(fireStationToAddTest.getAddress())));
    }

    @Test
    public void testModifyInfoFireStationExpectPersonalisedError() throws Exception {

        //ARRANGE
        FireStation fireStationToAddTest = new FireStation("address 5", "1");

        //ACT
        mockMvc.perform(patch("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(fireStationToAddTest)))

                //ASSERT
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteFireStation() throws Exception {
        //ARRANGE
        FireStation fireStationToDeleteTest = new FireStation("address 2", "2");

        //ACT
        mockMvc.perform(delete("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(fireStationToDeleteTest)))

        //ASSERT
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetInfoPersonFromFireStationNumber() throws Exception {
        //ARRANGE
        String fireStationNumberTest = "1";

        //ACT
        mockMvc.perform(get("/firestation")
                .param("stationNumber",fireStationNumberTest))

        //ASSERT
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numberOfAdults", is(1)))
                .andExpect(jsonPath("$.inhabitants.[0].phone", is("741-874-6512")));
    }

    @Test
    public void testGetHomeListsFromFiresStationNumbers() throws Exception {
        //ARRANGE
        String stationsTest = "1,2";

        //ACT
        mockMvc.perform(get("/flood/stations")
                .param("stations", stationsTest))

        //ASSERT
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[1].address", is("address 2")))
                .andExpect(jsonPath("$[1].peopleInHouse.[0].age", is(27)));
    }

    @Test
    public void testGetInfoFromEachPersonFromAddressAndAppointedFireStationNumber() throws Exception {
        //ARRANGE
        String addressTest = "address 2";

        //ACT
        mockMvc.perform(get("/fire")
                .param("address", addressTest))

        //ASSERT
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.stationNumber", is("2")))
                .andExpect(jsonPath("$.peopleDetailedArrayList.[0].age", is(27)));
    }
}
