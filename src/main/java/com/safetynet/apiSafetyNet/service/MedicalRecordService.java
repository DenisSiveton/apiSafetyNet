package com.safetynet.apiSafetyNet.service;

import com.safetynet.apiSafetyNet.model.MedicalRecord;
import com.safetynet.apiSafetyNet.model.Person;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
public class MedicalRecordService {
    public MedicalRecord addMedicalRecord(MedicalRecord medicalRecord, Person person) {
        return null;
    }

    public MedicalRecord modifyInfoMedicalRecord(MedicalRecord medicalRecord, Person person) {
        return null;
    }

    public void deleteMedicalRecord(MedicalRecord medicalRecord) {
    }
}
