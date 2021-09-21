package com.safetynet.apiSafetyNet.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.apiSafetyNet.model.InputData.FireStation;
import com.safetynet.apiSafetyNet.model.OutputData.AddressInfo;
import com.safetynet.apiSafetyNet.model.OutputData.InhabitantInfo;
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

public class FireStationControllerRequestTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FireStationService fireStationService;

    @Test
    public void testAddFireStation() throws Exception {
        FireStation fireStationTest = generateFireStation();
        when(fireStationService.addFireStation(fireStationTest)).thenReturn(fireStationTest);
        mockMvc.perform(post("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(fireStationTest)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testModifyInfoFireStation() throws Exception {
        FireStation fireStationTest = generateFireStation();
        when(fireStationService.modifyInfoFireStation(fireStationTest)).thenReturn(fireStationTest);
        mockMvc.perform(patch("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(fireStationTest)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteFireStation() throws Exception {
        FireStation fireStationTest = generateFireStation();
        when(fireStationService.deleteFireStation(fireStationTest)).thenReturn(fireStationTest);
        mockMvc.perform(delete("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(fireStationTest)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetInfoPersonFromFireStationNumber() throws Exception {
        String stationNumber = "1";
        when(fireStationService.getInfoPersonFromFireStationNumber(stationNumber)).thenReturn(new InhabitantInfo(new ArrayList<>(), 0, 0));
        mockMvc.perform(get("/firestation")
                .param("stationNumber", stationNumber))
                .andExpect(status().isOk());
    }

    @Test
    public void getHomeListsFromFiresStationNumbers() throws Exception {
        ArrayList<String> stations = new ArrayList<>(Arrays.asList("1", "2"));
        mockMvc.perform(get("/flood/stations")
                .param("stations", stations.get(0))
                .param("stations", stations.get(1)))
                .andExpect(status().isOk());
    }

    @Test
    public void getInfoFromEachPersonFromAddressAndAppointedFireStationNumber() throws Exception {
        String address = "1509 Culver St";
        when(fireStationService.getInfoFromEachPersonFromAddressAndAppointedFireStationNumber(address)).thenReturn(new AddressInfo("0", new ArrayList<>()));
        mockMvc.perform(get("/fire")
                .param("address", address))
                .andExpect(status().isOk());
    }

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
}
