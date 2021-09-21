package com.safetynet.apiSafetyNet.service.management;

import com.safetynet.apiSafetyNet.model.InputData.FireStation;
import com.safetynet.apiSafetyNet.model.OutputData.AddressInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@Import(FireStationManagement.class)
public class FireStationManagementUnitTest {

    @Autowired
    private FireStationManagement fireStationManagement;

    @Test
    public void getAddressesFromFireStationNumber_Test() {
        //ARRANGE
        String fireStationNumberTest = "1";
        ArrayList<FireStation> fireStationListTest = generateFireStationList();
        fireStationManagement = new FireStationManagement();

        //ACT
        ArrayList<String> addressListResult = fireStationManagement.getAddressesFromFireStationNumber(fireStationNumberTest, fireStationListTest);
        //ASSERT

        assertThat(addressListResult.size()).isEqualTo(2);
        assertThat(addressListResult.get(1)).isEqualTo("address 3");
    }

    @Test
    public void getFireStationNumberFromAddress_Test() {
        //ARRANGE
        String addressTest = "address 2";
        AddressInfo addressInfoTest = new AddressInfo(null, new ArrayList<>());
        ArrayList<FireStation> fireStationListTest = generateFireStationList();
        fireStationManagement = new FireStationManagement();

        //ACT
        fireStationManagement.getFireStationNumberFromAddress(addressTest, addressInfoTest, fireStationListTest);
        //ASSERT
        assertThat(addressInfoTest.getStationNumber()).isEqualTo("2");
    }

    @Test
    public void addAddressesToListForEachStationNumber_Test() {
        //ARRANGE
        ArrayList<String> stationNumberList = new ArrayList<>(Arrays.asList("1","2"));
        ArrayList<FireStation> fireStationListTest = generateFireStationList();
        fireStationManagement = new FireStationManagement();

        // ACT
        ArrayList<ArrayList<String>> addressesListResult = fireStationManagement.addAddressesToListForEachStationNumber(stationNumberList, fireStationListTest);

        // ASSERT

        assertThat(addressesListResult.get(0).size()).isEqualTo(2);
        assertThat(addressesListResult.get(0).get(1)).isEqualTo("address 3");
        assertThat(addressesListResult.get(1).get(0)).isEqualTo("address 2");
    }

    private ArrayList<FireStation> generateFireStationList() {
        ArrayList<FireStation> fireStationList = new ArrayList<>();
        fireStationList.add(new FireStation("address 1", "1"));
        fireStationList.add(new FireStation("address 2", "2"));
        fireStationList.add(new FireStation("address 3", "1"));

        return fireStationList;
    }
}
