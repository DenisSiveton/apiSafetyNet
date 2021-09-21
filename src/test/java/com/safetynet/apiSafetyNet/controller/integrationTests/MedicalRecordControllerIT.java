package com.safetynet.apiSafetyNet.controller.integrationTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.apiSafetyNet.model.InputData.MedicalRecord;
import com.safetynet.apiSafetyNet.model.InputData.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MedicalRecordControllerIT {

    @Autowired
    public MockMvc mockMvc;

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

    @Test
    public void testAddMedicalRecord() throws Exception {

        //ARRANGE
        MedicalRecord medicalRecordToAddTest = generateMedicalRecord();

        //ACT
        mockMvc.perform(post("/medicalrecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(medicalRecordToAddTest)))

        //ASSERT
                .andExpect(status().isCreated())
                .andExpect(jsonPath("firstName", is(medicalRecordToAddTest.getFirstName())));
    }

    @Test
    public void testModifyInfoMedicalRecord() throws Exception {

        //ARRANGE
        MedicalRecord medicalRecordToModifyTest = generateMedicalRecord();

        //ACT
        mockMvc.perform(patch("/medicalrecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(medicalRecordToModifyTest)))

        //ASSERT
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("firstName", is(medicalRecordToModifyTest.getFirstName())));
    }

    @Test
    public void testDeleteMedicalRecord() throws Exception {

        //ARRANGE
        MedicalRecord medicalRecordToDeleteTest = generateMedicalRecord();

        //ACT
        mockMvc.perform(patch("/medicalrecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(medicalRecordToDeleteTest)))

        //ASSERT
                .andExpect(status().isNoContent());
    }
}
