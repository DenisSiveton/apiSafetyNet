package com.safetynet.apiSafetyNet.repository.management;

import com.safetynet.apiSafetyNet.model.InputData.FireStation;

import java.util.ArrayList;

public class FireStationManagement {

    public ArrayList<String> getAddressesFromFireStationNumber(String fireStationNumber, ArrayList<FireStation> fireStationList) {
        ArrayList<String> addresses = new ArrayList<>();
        for (FireStation fireStation : fireStationList){
            if(fireStation.getStation().equals(fireStationNumber)){
                addresses.add(fireStation.getAddress());
            }
        }
        return addresses;
    }


}
