package com.safetynet.apiSafetyNet.service;

import com.safetynet.apiSafetyNet.model.InputData.FireStation;
import com.safetynet.apiSafetyNet.model.OutputData.AddressInfo;
import com.safetynet.apiSafetyNet.model.OutputData.HomeInfo;
import com.safetynet.apiSafetyNet.model.OutputData.InhabitantInfo;
import com.safetynet.apiSafetyNet.repository.FireStationRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Data
@Service
public class FireStationService {

    private FireStationRepository fireStationRepository;

    public FireStation addFireStation(FireStation fireStation) {
        return null;
    }

    public FireStation modifyInfoFireStation(FireStation fireStation) {
        return null;
    }

    public void deleteFireStation(FireStation fireStation) {
    }

    public InhabitantInfo getInfoPersonFromFireStationNumber(String  stationNumber) {
        return fireStationRepository.getInfoPersonFromFireStationNumber(stationNumber);
    }

    public AddressInfo getInfoFromEachPersonFromAddressAndAppointedFireStationNumber(String address) {
        return fireStationRepository.getInfoFromEachPersonFromAddressAndAppointedFireStationNumber(address);
    }

    public ArrayList<HomeInfo> getHomeInfoListsFromFireStationNumbers(ArrayList<String> stations) {
        return fireStationRepository.getHomeInfoListsFromFireStationNumbers(stations);
    }
}
