package com.safetynet.apiSafetyNet.controller;
import com.safetynet.apiSafetyNet.model.InputData.MedicalRecord;
import com.safetynet.apiSafetyNet.model.InputData.Person;
import com.safetynet.apiSafetyNet.service.MedicalRecordService;
import org.springframework.web.bind.annotation.*;

@RestController
public class MedicalRecordController {


    private MedicalRecordService medicalRecordService;

    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @PostMapping("/medicalrecord")
    public MedicalRecord addMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        return medicalRecordService.addMedicalRecord(medicalRecord);
    }

    @PatchMapping("/medicalrecord")
    public MedicalRecord modifyInfoMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        return medicalRecordService.modifyInfoMedicalRecord(medicalRecord);
    }

    @DeleteMapping("/medicalrecord")
    public void deleteMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        medicalRecordService.deleteMedicalRecord(medicalRecord);
    }
}
