package com.safetynet.apiSafetyNet.controller;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.apiSafetyNet.model.InputData.MedicalRecord;
import com.safetynet.apiSafetyNet.service.MedicalRecordService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;

@WebMvcTest(controllers = MedicalRecordController.class)
public class MedicalRecordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MedicalRecordService medicalRecordService;

    @Test
    public void testAddMedicalRecord() throws Exception {
        MedicalRecord medicalRecordTest = generateMedicalRecord();
        mockMvc.perform(post("/medicalrecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(medicalRecordTest)))
                .andExpect(status().isOk());
    }

    @Test
    public void testModifyInfoMedicalRecord() throws Exception {
        MedicalRecord medicalRecordTest = generateMedicalRecord();
        mockMvc.perform(patch("/medicalrecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(medicalRecordTest)))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteMedicalRecord() throws Exception {
        MedicalRecord medicalRecordTest = generateMedicalRecord();
        mockMvc.perform(delete("/medicalrecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(medicalRecordTest)))
                .andExpect(status().isOk());
    }

    private static MedicalRecord generateMedicalRecord() {
        MedicalRecord medicalRecordTest = new MedicalRecord();

        medicalRecordTest.setFirstName("Denis");
        medicalRecordTest.setLastName("Siveton");
        medicalRecordTest.setBirthDate("06/01/1992");
        ArrayList<String> allergies = new ArrayList<>(Arrays.asList("Peanut"));
        medicalRecordTest.setAllergies(allergies);
        ArrayList<String> medications = new ArrayList<>(Arrays.asList("aznol:200mg"));
        medicalRecordTest.setMedications(medications);
        return medicalRecordTest;
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
