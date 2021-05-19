package com.safetynet.apiSafetyNet.controller;
import com.safetynet.apiSafetyNet.model.MedicalRecord;
import com.safetynet.apiSafetyNet.model.Person;
import com.safetynet.apiSafetyNet.service.MedicalRecordService;
import com.safetynet.apiSafetyNet.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MedicalRecordController {

    @Autowired
    private MedicalRecordService medicalRecordService;

    @PostMapping("/medicalrecord")
    public MedicalRecord addMedicalRecord() {
        return medicalRecordService.addMedicalRecord();
    }

    @PatchMapping("/medicalrecord")
    public MedicalRecord modifyInfoMedicalRecord() {
        return medicalRecordService.modifyInfoMedicalRecord();
    }

    @DeleteMapping("/medicalrecord")
    public void deleteMedicalRecord() {
        medicalRecordService.deleteMedicalRecord();
    }
}
