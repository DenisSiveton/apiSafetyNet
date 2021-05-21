package com.safetynet.apiSafetyNet.controller;
import com.safetynet.apiSafetyNet.model.MedicalRecord;
import com.safetynet.apiSafetyNet.model.Person;
import com.safetynet.apiSafetyNet.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MedicalRecordController {

    @Autowired
    private MedicalRecordService medicalRecordService;

    @PostMapping("/medicalrecord")
    public MedicalRecord addMedicalRecord(@RequestBody MedicalRecord medicalRecord, @RequestBody Person person) {
        return medicalRecordService.addMedicalRecord(medicalRecord, person);
    }

    @PatchMapping("/medicalrecord")
    public MedicalRecord modifyInfoMedicalRecord(@RequestBody MedicalRecord medicalRecord, @RequestBody Person person) {
        return medicalRecordService.modifyInfoMedicalRecord(medicalRecord, person);
    }

    @DeleteMapping("/medicalrecord")
    public void deleteMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        medicalRecordService.deleteMedicalRecord(medicalRecord);
    }
}
