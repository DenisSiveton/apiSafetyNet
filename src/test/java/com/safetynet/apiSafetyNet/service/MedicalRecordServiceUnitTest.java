package com.safetynet.apiSafetyNet.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.apiSafetyNet.model.InputData.MedicalRecord;
import com.safetynet.apiSafetyNet.repository.MedicalRecordCRUD;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@Import(MedicalRecordService.class)
public class MedicalRecordServiceUnitTest {

    @MockBean
    private MedicalRecordCRUD medicalRecordCRUD;

    @Autowired
    private MedicalRecordService medicalRecordService;

    @BeforeEach
    public void endTest() throws IOException {
        InputStream input = new FileInputStream("src/test/resources/dataTestOrigin.json");
        OutputStream output = new FileOutputStream("src/test/resources/dataTest.json");
        IOUtils.copy(input, output);
    }

    @Test
    public void testAddMedicalRecord_VerifyMethodIsCalledWithCorrectArgument() {
        MedicalRecord medicalRecordTest = generateMedicalRecord();
        when(medicalRecordCRUD.addMedicalRecord(any())).thenReturn(medicalRecordTest);
        medicalRecordService = new MedicalRecordService(medicalRecordCRUD);

        //ACT
        medicalRecordService.addMedicalRecord(medicalRecordTest);

        //ASSERT
        ArgumentCaptor<MedicalRecord> argumentCaptor = ArgumentCaptor.forClass(MedicalRecord.class);
        verify(medicalRecordCRUD).addMedicalRecord(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getFirstName()).isEqualTo(medicalRecordTest.getFirstName());
    }

    @Test
    public void testModifyInfoMedicalRecord_VerifyMethodIsCalledWithCorrectArgument() {
        MedicalRecord medicalRecordTest = generateMedicalRecord();
        when(medicalRecordCRUD.modifyInfoMedicalRecord(any())).thenReturn(medicalRecordTest);
        medicalRecordService = new MedicalRecordService(medicalRecordCRUD);

        //ACT
        medicalRecordService.modifyInfoMedicalRecord(medicalRecordTest);

        //ASSERT
        ArgumentCaptor<MedicalRecord> argumentCaptor = ArgumentCaptor.forClass(MedicalRecord.class);
        verify(medicalRecordCRUD).modifyInfoMedicalRecord(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getFirstName()).isEqualTo(medicalRecordTest.getFirstName());
    }

    @Test
    public void testDeleteMedicalRecord_VerifyMethodIsCalledWithCorrectArgument() {
        MedicalRecord medicalRecordTest = generateMedicalRecord();
        medicalRecordService = new MedicalRecordService(medicalRecordCRUD);

        //ACT
        medicalRecordService.deleteMedicalRecord(medicalRecordTest);

        //ASSERT
        ArgumentCaptor<MedicalRecord> argumentCaptor = ArgumentCaptor.forClass(MedicalRecord.class);
        verify(medicalRecordCRUD).deleteMedicalRecord(argumentCaptor.capture());
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
