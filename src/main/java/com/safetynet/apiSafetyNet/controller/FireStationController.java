package com.safetynet.apiSafetyNet.controller;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.safetynet.apiSafetyNet.model.FireStation;
import com.safetynet.apiSafetyNet.service.FireStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class FireStationController {

    @Autowired
    private FireStationService fireStationService;

    @PostMapping("/firestation")
    public JSONPObject addFireStation(@RequestBody FireStation fireStation) {
        return fireStationService.addFireStation(fireStation);
    }

    @PatchMapping("/firestation")
    public JSONPObject modifyInfoFireStation(@RequestBody FireStation fireStation) {
        return fireStationService.modifyInfoFireStation(fireStation);
    }

    @DeleteMapping("/firestation")
    public void deleteFireStation(@RequestBody FireStation fireStation) {
        fireStationService.deleteFireStation(fireStation);
    }

    @GetMapping("/firestation?stationNumber=<station_number>")
    public JSONPObject getInfoPersonFromFireStationNumber(@PathVariable("stationNumber") final int stationNumber) {
        return fireStationService.getInfoPersonFromFireStationNumber(stationNumber);
    }

    @GetMapping("/flood/stations?stations=<a_list_of_station_numbers>")
    public JSONPObject getHomeListsFromFiresStationNumbers(@PathVariable("stations") final ArrayList<Integer> stations) {
        return fireStationService.getHomeInfoListsFromFiresStationNumbers(stations);
    }

    @GetMapping("/fire?address=<address>")
    public JSONPObject getInfoFromEachPersonFromAddressAndAppointedFireStationNumber(@PathVariable("address") final String address) {
        return fireStationService.getInfoFromEachPersonFromAddressAndAppointedFireStationNumber(address);
    }
}
