package com.safetynet.apiSafetyNet.controller;
import static org.mockito.Mockito.when;
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
public class MedicalRecordControllerRequestTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MedicalRecordService medicalRecordService;

    @Test
    public void testAddMedicalRecord() throws Exception {
        MedicalRecord medicalRecordTest = generateMedicalRecord();
        when(medicalRecordService.addMedicalRecord(medicalRecordTest)).thenReturn(medicalRecordTest);
        mockMvc.perform(post("/medicalrecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(medicalRecordTest)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testModifyInfoMedicalRecord() throws Exception {
        MedicalRecord medicalRecordTest = generateMedicalRecord();
        when(medicalRecordService.modifyInfoMedicalRecord(medicalRecordTest)).thenReturn(medicalRecordTest);
        mockMvc.perform(patch("/medicalrecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(medicalRecordTest)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteMedicalRecord() throws Exception {
        MedicalRecord medicalRecordTest = generateMedicalRecord();
        when(medicalRecordService.deleteMedicalRecord(medicalRecordTest)).thenReturn(medicalRecordTest);
        mockMvc.perform(delete("/medicalrecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(medicalRecordTest)))
                .andExpect(status().isNoContent());
    }

    private static MedicalRecord generateMedicalRecord() {
        return new MedicalRecord("Denis", "Siveton", "06/01/1992",
                new ArrayList<String>(Arrays.asList("aznol:200mg")), new ArrayList<String>(Arrays.asList("Peanut")));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
