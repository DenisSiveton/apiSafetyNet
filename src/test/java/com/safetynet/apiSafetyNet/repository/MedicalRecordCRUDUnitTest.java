package com.safetynet.apiSafetyNet.repository;

import com.safetynet.apiSafetyNet.model.InputData.MedicalRecord;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@Import(MedicalRecordCRUD.class)
public class MedicalRecordCRUDUnitTest {

    @Autowired
    private MedicalRecordCRUD medicalRecordCRUD;


    @AfterEach
    public void endTest() throws IOException {
        InputStream input = new FileInputStream("src/test/resources/dataTestOrigin.json");
        OutputStream output = new FileOutputStream(medicalRecordCRUD.getPathData());
        IOUtils.copy(input, output);
    }
    @Test
    public void addMedicalRecord_Test() {
        //ARRANGE
        MedicalRecord medicalRecordToAdd = new MedicalRecord("PM", "Mazin", "03/25/1990",
                new ArrayList<String>(Arrays.asList("aznol:200mg")), new ArrayList<String>(Arrays.asList("Peanut")));

        //ACT
        medicalRecordCRUD.addMedicalRecord(medicalRecordToAdd);
        ArrayList<MedicalRecord> medicalRecordListTest = getMedicalRecordList();

        //ASSERT
        assertThat(medicalRecordListTest.size()).isEqualTo(4);
        assertThat(medicalRecordListTest.get(3).getFirstName()).isEqualTo(medicalRecordToAdd.getFirstName());
    }

    @Test
    public void modifyInfoMedicalRecord_Test() {
        //ARRANGE
        MedicalRecord medicalRecordToModify = new MedicalRecord("Denis", "Siveton", "06/01/1992",
                new ArrayList<String>(Arrays.asList("aznol:200mg")), new ArrayList<String>(Arrays.asList("Shellfish")));

        //ACT
        medicalRecordCRUD.modifyInfoMedicalRecord(medicalRecordToModify);
        ArrayList<MedicalRecord> medicalRecordListUpdatedTest = getMedicalRecordList();

        //ASSERT
        assertThat(medicalRecordListUpdatedTest.size()).isEqualTo(3);
        assertThat(medicalRecordListUpdatedTest.get(0).getAllergies().get(0)).isEqualTo("Shellfish");

    }

    @Test
    public void deleteMedicalRecord_Test() {
        //ARRANGE
        MedicalRecord medicalRecordToDelete = new MedicalRecord("Denis", "Siveton", "06/01/1992",
                new ArrayList<String>(Arrays.asList("aznol:200mg")), new ArrayList<String>(Arrays.asList("Peanut")));

        //ACT
        medicalRecordCRUD.deleteMedicalRecord(medicalRecordToDelete);
        ArrayList<MedicalRecord> medicalRecordListUpdatedTest = getMedicalRecordList();

        //ASSERT
        assertThat(medicalRecordListUpdatedTest.size()).isEqualTo(2);
        assertThat(medicalRecordListUpdatedTest.get(0).getFirstName()).isEqualTo("Laura");
    }

    @Test
    public void getAllMedicalRecords_Test() throws IOException, ParseException {
        //ARRANGE
        JSONParser jsonP = new JSONParser();
        JSONObject jsonOTest = (JSONObject) jsonP.parse(new FileReader(medicalRecordCRUD.getPathData()));

        //ACT
        ArrayList<MedicalRecord> medicalRecordList = medicalRecordCRUD.getAllMedicalRecords(jsonOTest);

        //ASSERT
        assertThat(medicalRecordList.size()).isEqualTo(3);
        assertThat(medicalRecordList.get(1).getFirstName()).isEqualTo("Laura");
    }

    private ArrayList<MedicalRecord> getMedicalRecordList() {
        JSONParser jsonP = new JSONParser();
        ArrayList<MedicalRecord> medicalRecordListJSON =  new ArrayList<>();
        try {
            JSONObject jsonO = (JSONObject) jsonP.parse(new FileReader(medicalRecordCRUD.getPathData()));

            /* Create a list of Person from JSON file*/
            medicalRecordListJSON = medicalRecordCRUD.getAllMedicalRecords(jsonO);

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return medicalRecordListJSON;
    }
}
