package com.safetynet.apiSafetyNet.controller;
import com.safetynet.apiSafetyNet.model.FireStation;
import com.safetynet.apiSafetyNet.model.Person;
import com.safetynet.apiSafetyNet.service.FireStationService;
import com.safetynet.apiSafetyNet.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class FireStationController {

    @Autowired
    private FireStationService fireStationService;

    @PostMapping("/firestation")
    public FireStation addFireStation() {
        return fireStationService.addFireStation();
    }

    @PatchMapping("/firestation")
    public FireStation modifyInfoFireStation() {
        return fireStationService.modifyInfoFireStation();
    }

    @DeleteMapping("/firestation")
    public void deleteFireStation() {
        fireStationService.deleteFireStation();
    }
}
