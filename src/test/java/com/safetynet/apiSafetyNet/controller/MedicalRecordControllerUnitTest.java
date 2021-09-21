package com.safetynet.apiSafetyNet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.apiSafetyNet.model.InputData.MedicalRecord;
import com.safetynet.apiSafetyNet.model.InputData.Person;
import com.safetynet.apiSafetyNet.service.MedicalRecordService;
import com.safetynet.apiSafetyNet.service.PersonService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MedicalRecordController.class)
public class MedicalRecordControllerUnitTest {

    @MockBean
    private MedicalRecordService medicalRecordService;

    @Autowired
    private MedicalRecordController medicalRecordController;

    @Test
    public void testAddMedicalRecord_VerifyMethodIsCalledWithCorrectArgument() {
        MedicalRecord medicalRecordTest = generateMedicalRecord();
        when(medicalRecordService.addMedicalRecord(any())).thenReturn(medicalRecordTest);
        medicalRecordController = new MedicalRecordController(medicalRecordService);

        //ACT
        medicalRecordController.addMedicalRecord(medicalRecordTest);

        //ASSERT
        ArgumentCaptor<MedicalRecord> argumentCaptor = ArgumentCaptor.forClass(MedicalRecord.class);
        verify(medicalRecordService).addMedicalRecord(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getFirstName()).isEqualTo(medicalRecordTest.getFirstName());
    }

    @Test
    public void testModifyInfoMedicalRecord_VerifyMethodIsCalledWithCorrectArgument() {
        MedicalRecord medicalRecordTest = generateMedicalRecord();
        when(medicalRecordService.modifyInfoMedicalRecord(any())).thenReturn(medicalRecordTest);
        medicalRecordController = new MedicalRecordController(medicalRecordService);

        //ACT
        medicalRecordController.modifyInfoMedicalRecord(medicalRecordTest);

        //ASSERT
        ArgumentCaptor<MedicalRecord> argumentCaptor = ArgumentCaptor.forClass(MedicalRecord.class);
        verify(medicalRecordService).modifyInfoMedicalRecord(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getFirstName()).isEqualTo(medicalRecordTest.getFirstName());
    }

    @Test
    public void testDeleteMedicalRecord_VerifyMethodIsCalledWithCorrectArgument() {
        MedicalRecord medicalRecordTest = generateMedicalRecord();
        when(medicalRecordService.deleteMedicalRecord(any())).thenReturn(medicalRecordTest);
        medicalRecordController = new MedicalRecordController(medicalRecordService);

        //ACT
        medicalRecordController.deleteMedicalRecord(medicalRecordTest);

        //ASSERT
        ArgumentCaptor<MedicalRecord> argumentCaptor = ArgumentCaptor.forClass(MedicalRecord.class);
        verify(medicalRecordService).deleteMedicalRecord(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getFirstName()).isEqualTo(medicalRecordTest.getFirstName());
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
