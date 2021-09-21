package com.safetynet.apiSafetyNet.repository;

import com.safetynet.apiSafetyNet.model.InputData.FireStation;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.*;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@Import(FireStationCRUD.class)
public class FireStationCRUDUnitTest {

    @Autowired
    private FireStationCRUD fireStationCRUD;

    @BeforeEach
    public void endTest() throws IOException {
        InputStream input = new FileInputStream("src/test/resources/dataTestOrigin.json");
        OutputStream output = new FileOutputStream(fireStationCRUD.getPathData());
        IOUtils.copy(input, output);
    }

    @Test
    public void addFireStation_Test() {
        //ARRANGE
        FireStation fireStationToAddTest = new FireStation("address 5", "1");

        //ACT
        fireStationCRUD.addFireStation(fireStationToAddTest);
        ArrayList<FireStation> fireStationListTest = getFireStationList();

        //ASSERT
        assertThat(fireStationListTest.size()).isEqualTo(5);
        assertThat(fireStationListTest.get(4).getAddress()).isEqualTo(fireStationToAddTest.getAddress());
    }

    @Test
    public void modifyInfoFireStation_Test() {
        //ARRANGE
        FireStation fireStationToModifyTest = new FireStation("address 1", "4");

        //ACT
        fireStationCRUD.modifyInfoFireStation(fireStationToModifyTest);
        ArrayList<FireStation> fireStationListTest = getFireStationList();

        //ASSERT
        assertThat(fireStationListTest.size()).isEqualTo(4);
        assertThat(fireStationListTest.get(0).getStation()).isEqualTo(fireStationToModifyTest.getStation());
    }

    @Test
    public void deleteFireStation_Test() {
        //ARRANGE
        FireStation fireStationToDeleteTest = new FireStation("address 1", "4");
        //ACT
        fireStationCRUD.deleteFireStation(fireStationToDeleteTest);
        ArrayList<FireStation> fireStationListTest = getFireStationList();

        //ASSERT
        assertThat(fireStationListTest.size()).isEqualTo(3);
        assertThat(fireStationListTest.get(0).getAddress()).isEqualTo("address 2");
    }

    @Test
    public void getFireStationsFromJSONFile_Test() throws IOException, ParseException {
        //ARRANGE
        JSONParser jsonP = new JSONParser();
        JSONObject jsonOTest = (JSONObject) jsonP.parse(new FileReader(fireStationCRUD.getPathData()));
        //ACT
        ArrayList<FireStation> fireStationListJSON = fireStationCRUD.getFireStationsFromJSONFile(jsonOTest);
        //ASSERT
        assertThat(fireStationListJSON.size()).isEqualTo(4);
        assertThat(fireStationListJSON.get(3).getAddress()).isEqualTo("address 4");
    }

    private ArrayList<FireStation> getFireStationList() {
        JSONParser jsonP = new JSONParser();
        ArrayList<FireStation> fireStationList =  new ArrayList<>();
        try {
            JSONObject jsonO = (JSONObject) jsonP.parse(new FileReader(fireStationCRUD.getPathData()));

            /* Create a list of Person from JSON file*/
            fireStationList = fireStationCRUD.getFireStationsFromJSONFile(jsonO);

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return fireStationList;
    }
}
