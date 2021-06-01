package com.safetynet.apiSafetyNet.service;

import com.safetynet.apiSafetyNet.model.InputData.MedicalRecord;
import com.safetynet.apiSafetyNet.model.InputData.Person;
import com.safetynet.apiSafetyNet.repository.MedicalRecordRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
public class MedicalRecordService {

    private MedicalRecordRepository medicalRecordRepository;

    public MedicalRecord addMedicalRecord(MedicalRecord medicalRecord) {
        return medicalRecord;
    }

    public MedicalRecord modifyInfoMedicalRecord(MedicalRecord medicalRecord) {
        return medicalRecordRepository.modifyInfoMedicalRecord(medicalRecord);
    }

    public void deleteMedicalRecord(MedicalRecord medicalRecord) {
        medicalRecordRepository.deleteMedicalRecord(medicalRecord);
    }
}
