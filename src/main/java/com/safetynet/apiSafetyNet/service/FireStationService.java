package com.safetynet.apiSafetyNet.service;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.safetynet.apiSafetyNet.model.FireStation;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Data
@Service
public class FireStationService {

    public JSONPObject addFireStation(FireStation fireStation) {
        return null;
    }

    public void deleteFireStation(FireStation fireStation) {
    }

    public JSONPObject modifyInfoFireStation(FireStation fireStation) {
        return null;
    }

    public JSONPObject getInfoPersonFromFireStationNumber(int stationNumber) {
        return null;
    }

    public JSONPObject getInfoFromEachPersonFromAddressAndAppointedFireStationNumber(String address) {
        return null;
    }

    public JSONPObject getHomeInfoListsFromFiresStationNumbers(ArrayList<Integer> stations) {
        return null;
    }
}
