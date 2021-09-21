package com.safetynet.apiSafetyNet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.apiSafetyNet.model.Data.People;
import com.safetynet.apiSafetyNet.model.Data.PeopleDetailed;
import com.safetynet.apiSafetyNet.model.InputData.FireStation;
import com.safetynet.apiSafetyNet.model.InputData.MedicalRecord;
import com.safetynet.apiSafetyNet.model.OutputData.AddressInfo;
import com.safetynet.apiSafetyNet.model.OutputData.HomeInfo;
import com.safetynet.apiSafetyNet.model.OutputData.InhabitantInfo;
import com.safetynet.apiSafetyNet.service.FireStationService;
import com.safetynet.apiSafetyNet.service.MedicalRecordService;
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
import static org.assertj.core.api.Assertions.in;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FireStationController.class)

public class FireStationControllerUnitTest {
    @MockBean
    private FireStationService fireStationService;

    @Autowired
    private FireStationController fireStationController;

    @Test
    public void testAddFireStation_VerifyMethodIsCalledWithCorrectArgument() throws Exception {
        FireStation fireStationTest = generateFireStation();
        when(fireStationService.addFireStation(any())).thenReturn(fireStationTest);
        fireStationController = new FireStationController(fireStationService);

        //ACT
        fireStationController.addFireStation(fireStationTest);

        //ASSERT
        ArgumentCaptor<FireStation> argumentCaptor = ArgumentCaptor.forClass(FireStation.class);
        verify(fireStationService).addFireStation(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getAddress()).isEqualTo(fireStationTest.getAddress());
    }

    @Test
    public void testModifyInfoFireStation_VerifyMethodIsCalledWithCorrectArgument() throws Exception {
        FireStation fireStationTest = generateFireStation();
        when(fireStationService.modifyInfoFireStation(any())).thenReturn(fireStationTest);
        fireStationController = new FireStationController(fireStationService);

        //ACT
        fireStationController.modifyInfoFireStation(fireStationTest);

        //ASSERT
        ArgumentCaptor<FireStation> argumentCaptor = ArgumentCaptor.forClass(FireStation.class);
        verify(fireStationService).modifyInfoFireStation(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getAddress()).isEqualTo(fireStationTest.getAddress());
    }

    @Test
    public void testDeleteFireStation_VerifyMethodIsCalledWithCorrectArgument() throws Exception {
        FireStation fireStationTest = generateFireStation();
        when(fireStationService.deleteFireStation(any())).thenReturn(fireStationTest);
        fireStationController = new FireStationController(fireStationService);

        //ACT
        fireStationController.deleteFireStation(fireStationTest);

        //ASSERT
        ArgumentCaptor<FireStation> argumentCaptor = ArgumentCaptor.forClass(FireStation.class);
        verify(fireStationService).deleteFireStation(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getAddress()).isEqualTo(fireStationTest.getAddress());
    }

    @Test
    public void testGetInfoPersonFromFireStationNumber_VerifyMethodIsCalledWithCorrectArgument() throws Exception {
        String stationNumberTest = "1";

        InhabitantInfo inhabitantInfoTest = new InhabitantInfo(new ArrayList<>(), 1, 2);
        when(fireStationService.getInfoPersonFromFireStationNumber(any())).thenReturn(inhabitantInfoTest);
        fireStationController = new FireStationController(fireStationService);

        //ACT
        fireStationController.getInfoPersonFromFireStationNumber(stationNumberTest);

        //ASSERT
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(fireStationService).getInfoPersonFromFireStationNumber(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue()).isEqualTo(stationNumberTest);
    }

    @Test
    public void getHomeListsFromFiresStationNumbers_VerifyMethodIsCalledWithCorrectArgument() throws Exception {
        String stationsTest = "1,2";
        ArrayList<String> stationListTest = new ArrayList<>(Arrays.asList(stationsTest.split(",")));

        ArrayList<HomeInfo> listOfHomeInfoTest = new ArrayList<>();
        ArrayList<PeopleDetailed> listOfPeopleDetailedForTest = new ArrayList<>();
        listOfHomeInfoTest.add(new HomeInfo("address 1", listOfPeopleDetailedForTest));
        when(fireStationService.getHomeInfoListsFromFireStationNumbers(any())).thenReturn(listOfHomeInfoTest);
        fireStationController = new FireStationController(fireStationService);

        //ACT
        fireStationController.getHomeListsFromFiresStationNumbers(stationsTest);

        //ASSERT
        ArgumentCaptor<ArrayList<String>> argumentCaptor = ArgumentCaptor.forClass(ArrayList.class);
        verify(fireStationService).getHomeInfoListsFromFireStationNumbers(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().get(0)).isEqualTo(stationListTest.get(0));
        assertThat(argumentCaptor.getValue().get(1)).isEqualTo(stationListTest.get(1));
    }

    @Test
    public void getInfoFromEachPersonFromAddressAndAppointedFireStationNumber_VerifyMethodIsCalledWithCorrectArgument() throws Exception {
        String addressTest = "1509 Culver St";
        AddressInfo addressInfoTest = new AddressInfo("1", new ArrayList<>());
        when(fireStationService.getInfoFromEachPersonFromAddressAndAppointedFireStationNumber(any())).thenReturn(addressInfoTest);
        fireStationController = new FireStationController(fireStationService);

        //ACT
        fireStationController.getInfoFromEachPersonFromAddressAndAppointedFireStationNumber(addressTest);

        //ASSERT
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(fireStationService).getInfoFromEachPersonFromAddressAndAppointedFireStationNumber(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue()).isEqualTo(addressTest);

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
