package com.safetynet.apiSafetyNet.controller;
import com.safetynet.apiSafetyNet.model.InputData.FireStation;
import com.safetynet.apiSafetyNet.model.OutputData.AddressInfo;
import com.safetynet.apiSafetyNet.model.OutputData.HomeInfo;
import com.safetynet.apiSafetyNet.model.OutputData.InhabitantInfo;
import com.safetynet.apiSafetyNet.service.FireStationService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class FireStationController {


    private FireStationService fireStationService;

    public FireStationController(FireStationService fireStationService) {
        this.fireStationService = fireStationService;
    }

    @PostMapping("/firestation")
    public FireStation addFireStation(@RequestBody FireStation fireStation) {
        return fireStationService.addFireStation(fireStation);
    }

    @PatchMapping("/firestation")
    public FireStation modifyInfoFireStation(@RequestBody FireStation fireStation) {
        return fireStationService.modifyInfoFireStation(fireStation);
    }

    @DeleteMapping("/firestation")
    public void deleteFireStation(@RequestBody FireStation fireStation) {
        fireStationService.deleteFireStation(fireStation);
    }

    @GetMapping("/firestation")
    public InhabitantInfo getInfoPersonFromFireStationNumber(@RequestParam( name = "stationNumber") String stationNumber) {
        return fireStationService.getInfoPersonFromFireStationNumber(stationNumber);
    }

    @GetMapping("/flood/stations")
    public ArrayList<HomeInfo> getHomeListsFromFiresStationNumbers(@RequestParam(name = "stations") ArrayList<String> stations) {
        return fireStationService.getHomeInfoListsFromFireStationNumbers(stations);
    }

    @GetMapping("/fire")
    public AddressInfo getInfoFromEachPersonFromAddressAndAppointedFireStationNumber(@RequestParam(name = "address") String address) {
        return fireStationService.getInfoFromEachPersonFromAddressAndAppointedFireStationNumber(address);
    }
}
