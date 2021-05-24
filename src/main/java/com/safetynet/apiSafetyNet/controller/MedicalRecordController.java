package com.safetynet.apiSafetyNet.controller;
import com.safetynet.apiSafetyNet.model.MedicalRecord;
import com.safetynet.apiSafetyNet.model.Person;
import com.safetynet.apiSafetyNet.service.MedicalRecordService;
import org.springframework.web.bind.annotation.*;

@RestController
public class MedicalRecordController {


    private MedicalRecordService medicalRecordService;

    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

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
