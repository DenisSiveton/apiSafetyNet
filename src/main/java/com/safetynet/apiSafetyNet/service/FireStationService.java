package com.safetynet.apiSafetyNet.service;

import com.safetynet.apiSafetyNet.model.FireStation;
import com.safetynet.apiSafetyNet.model.viewModel.OutputData.AddressInfo;
import com.safetynet.apiSafetyNet.model.viewModel.OutputData.HomeInfo;
import com.safetynet.apiSafetyNet.model.viewModel.OutputData.InhabitantInfo;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Data
@Service
public class FireStationService {

    public FireStation addFireStation(FireStation fireStation) {
        return null;
    }

    public void deleteFireStation(FireStation fireStation) {
    }

    public FireStation modifyInfoFireStation(FireStation fireStation) {
        return null;
    }

    public InhabitantInfo getInfoPersonFromFireStationNumber(int stationNumber) {
        return null;
    }

    public AddressInfo getInfoFromEachPersonFromAddressAndAppointedFireStationNumber(String address) {
        return null;
    }

    public List<HomeInfo> getHomeInfoListsFromFiresStationNumbers(ArrayList<Integer> stations) {
        return null;
    }
}
