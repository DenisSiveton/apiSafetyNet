package com.safetynet.apiSafetyNet.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.apiSafetyNet.model.InputData.FireStation;
import com.safetynet.apiSafetyNet.repository.FireStationCRUD;
import com.safetynet.apiSafetyNet.repository.MedicalRecordCRUD;
import com.safetynet.apiSafetyNet.repository.PersonCRUD;
import com.safetynet.apiSafetyNet.service.management.FireStationManagement;
import com.safetynet.apiSafetyNet.service.management.HomeInfoManagement;
import com.safetynet.apiSafetyNet.service.management.InhabitantManagement;
import com.safetynet.apiSafetyNet.service.management.PersonManagement;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@Import(FireStationService.class)
public class FireStationServiceUnitTest {
    @MockBean
    private FireStationCRUD fireStationCRUD;
    @MockBean
    private PersonCRUD personCRUD;
    @MockBean
    private MedicalRecordCRUD medicalRecordCRUD;
    @MockBean
    private FireStationManagement fireStationManagement;
    @MockBean
    private PersonManagement personManagement;
    @MockBean
    private InhabitantManagement inhabitantManagement;
    @MockBean
    private HomeInfoManagement homeInfoManagement;

    @Autowired
    private FireStationService fireStationService;

    @BeforeEach
    public void endTest() throws IOException {
        InputStream input = new FileInputStream("src/test/resources/dataTestOrigin.json");
        OutputStream output = new FileOutputStream("src/test/resources/dataTest.json");
        IOUtils.copy(input, output);
    }

    @Test
    public void testAddFireStation_VerifyMethodIsCalledWithCorrectArgument() throws Exception {
        FireStation fireStationTest = generateFireStation();
        when(fireStationCRUD.addFireStation(any())).thenReturn(fireStationTest);
        fireStationService = new FireStationService("src/test/resources/dataTest.json",fireStationCRUD, personCRUD, medicalRecordCRUD, fireStationManagement, personManagement, inhabitantManagement, homeInfoManagement);

        //ACT
        fireStationService.addFireStation(fireStationTest);

        //ASSERT
        verify(fireStationCRUD).addFireStation(fireStationTest);
    }

    @Test
    public void testModifyInfoFireStation_VerifyMethodIsCalledWithCorrectArgument() throws Exception {
        FireStation fireStationTest = generateFireStation();
        when(fireStationCRUD.modifyInfoFireStation(any())).thenReturn(fireStationTest);
        fireStationService = new FireStationService("src/test/resources/dataTest.json",fireStationCRUD, personCRUD, medicalRecordCRUD, fireStationManagement,
                personManagement, inhabitantManagement, homeInfoManagement);

        //ACT
        fireStationService.modifyInfoFireStation(fireStationTest);

        //ASSERT
        verify(fireStationCRUD).modifyInfoFireStation(fireStationTest);
    }

    @Test
    public void testDeleteFireStation_VerifyMethodIsCalledWithCorrectArgument() throws Exception {
        FireStation fireStationTest = generateFireStation();
        fireStationService = new FireStationService("src/test/resources/dataTest.json",fireStationCRUD, personCRUD, medicalRecordCRUD, fireStationManagement,
                personManagement, inhabitantManagement, homeInfoManagement);

        //ACT
        fireStationService.deleteFireStation(fireStationTest);

        //ASSERT
        verify(fireStationCRUD).deleteFireStation(fireStationTest);
    }

    @Test
    public void testGetInfoPersonFromFireStationNumber_VerifyMethodIsCalledWithCorrectArgument() throws Exception {
        //ARRANGE
        String stationNumberTest = "1";

        when(fireStationCRUD.getFireStationsFromJSONFile(any())).thenReturn(null);
        when(personCRUD.getPersonsFromJSONFile(any())).thenReturn(null);
        when(medicalRecordCRUD.getAllMedicalRecords(any())).thenReturn(null);
        when(fireStationManagement.getAddressesFromFireStationNumber(eq(stationNumberTest), any())).thenReturn(null);

        fireStationService = new FireStationService("src/test/resources/dataTest.json",fireStationCRUD, personCRUD, medicalRecordCRUD, fireStationManagement,
                personManagement, inhabitantManagement, homeInfoManagement);

        //ACT
        fireStationService.getInfoPersonFromFireStationNumber(stationNumberTest);

        //ASSERT
        verify(fireStationCRUD, times(1)).getFireStationsFromJSONFile(any());
        verify(personCRUD, times(1)).getPersonsFromJSONFile(any());
        verify(medicalRecordCRUD, times(1)).getAllMedicalRecords(any());
        verify(fireStationManagement, times(1)).getAddressesFromFireStationNumber(eq(stationNumberTest), any());
        verify(inhabitantManagement, times(1)).createInhabitantsWithAddressList(any(), any(), any());
        verify(inhabitantManagement, times(1)).calculateNumberOfAdultsAndChildrenInInhabitants(any(), any());
    }

    @Test
    public void getHomeListsFromFiresStationNumbers_VerifyMethodIsCalledWithCorrectArgument() throws Exception {
        //ARRANGE
        String stationsTest = "1,2";
        ArrayList<String> stationListTest = new ArrayList<>(Arrays.asList(stationsTest.split(",")));

        when(fireStationCRUD.getFireStationsFromJSONFile(any())).thenReturn(null);
        when(personCRUD.getPersonsFromJSONFile(any())).thenReturn(null);
        when(medicalRecordCRUD.getAllMedicalRecords(any())).thenReturn(null);
        ArrayList<ArrayList<String >> listOfAddressList = new ArrayList<>();
        listOfAddressList.add(new ArrayList<String >(Arrays.asList("address 1")));
        when(fireStationManagement.addAddressesToListForEachStationNumber(eq(stationListTest), any())).thenReturn(listOfAddressList);


        fireStationService = new FireStationService("src/test/resources/dataTest.json",fireStationCRUD, personCRUD, medicalRecordCRUD, fireStationManagement,
                personManagement, inhabitantManagement, homeInfoManagement);

        //ACT
        fireStationService.getHomeInfoListsFromFireStationNumbers(stationListTest);

        //ASSERT
        verify(fireStationCRUD, times(1)).getFireStationsFromJSONFile(any());
        verify(personCRUD, times(1)).getPersonsFromJSONFile(any());
        verify(medicalRecordCRUD, times(1)).getAllMedicalRecords(any());
        verify(fireStationManagement, times(1)).addAddressesToListForEachStationNumber(eq(stationListTest), any());
        verify(homeInfoManagement, times(1)).createHomeInfoListBasedOnAddressList(eq(stationListTest), any(), any(), any(), any());
    }

    @Test
    public void getInfoFromEachPersonFromAddressAndAppointedFireStationNumber_VerifyMethodIsCalledWithCorrectArgument() throws Exception {
        //ARRANGE
        String addressTest = "1509 Culver St";

        when(fireStationCRUD.getFireStationsFromJSONFile(any())).thenReturn(null);
        when(personCRUD.getPersonsFromJSONFile(any())).thenReturn(null);
        when(medicalRecordCRUD.getAllMedicalRecords(any())).thenReturn(null);

        fireStationService = new FireStationService("src/test/resources/dataTest.json",fireStationCRUD, personCRUD, medicalRecordCRUD, fireStationManagement,
                personManagement, inhabitantManagement, homeInfoManagement);

        //ACT
        fireStationService.getInfoFromEachPersonFromAddressAndAppointedFireStationNumber(addressTest);

        //ASSERT
        verify(fireStationCRUD, times(1)).getFireStationsFromJSONFile(any());
        verify(personCRUD, times(1)).getPersonsFromJSONFile(any());
        verify(medicalRecordCRUD, times(1)).getAllMedicalRecords(any());
        verify(fireStationManagement, times(1)).getFireStationNumberFromAddress(eq(addressTest), any(), any());
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
