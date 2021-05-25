package com.safetynet.apiSafetyNet.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.apiSafetyNet.model.InputData.FireStation;
import com.safetynet.apiSafetyNet.service.FireStationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;

@WebMvcTest(controllers = FireStationController.class)

public class FireStationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FireStationService fireStationService;

    @Test
    public void testAddMedicalRecord() throws Exception {
        FireStation fireStationTest = generateFireStation();
        mockMvc.perform(post("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(fireStationTest)))
                .andExpect(status().isOk());
    }

    @Test
    public void testModifyInfoMedicalRecord() throws Exception {
        FireStation fireStationTest = generateFireStation();
        mockMvc.perform(patch("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(fireStationTest)))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteMedicalRecord() throws Exception {
        FireStation fireStationTest = generateFireStation();
        mockMvc.perform(delete("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(fireStationTest)))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetInfoPersonFromFireStationNumber() throws Exception {
        String stationNumber = "1";
        mockMvc.perform(delete("//firestation?stationNumber=<station_number>")
                .param("stationNumber", stationNumber))
                .andExpect(status().isOk());
    }

    @Test
    public void getHomeListsFromFiresStationNumbers() throws Exception {
        ArrayList<String> stations = new ArrayList<>(Arrays.asList("1", "2"));
        mockMvc.perform(delete("/flood/stations?stations=<a_list_of_station_numbers>")
                .param("stations", stations.get(0))
                .param("stations", stations.get(1)))
                .andExpect(status().isOk());
    }

    @Test
    public void getInfoFromEachPersonFromAddressAndAppointedFireStationNumber() throws Exception {
        String address = "1";
        mockMvc.perform(delete("/fire?address=<address>")
                .param("address", address))
                .andExpect(status().isOk());
    }

    private static FireStation generateFireStation() {
        FireStation fireStationTest = new FireStation();
        fireStationTest.setAddress("15 Fame Road");
        fireStationTest.setStation(1);
        return fireStationTest;
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
