package com.safetynet.apiSafetyNet.service;

import com.safetynet.apiSafetyNet.model.Data.PeopleDetailed;
import com.safetynet.apiSafetyNet.model.InputData.MedicalRecord;
import com.safetynet.apiSafetyNet.model.OutputData.HomeInfo;
import com.safetynet.apiSafetyNet.repository.MedicalRecordCRUD;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicalRecordService {
    public MedicalRecordService() {
    }
    @Autowired
    private MedicalRecordCRUD medicalRecordCRUD;


    public MedicalRecordService(MedicalRecordCRUD medicalRecordCRUD) {
        this.medicalRecordCRUD = medicalRecordCRUD;
    }

    /**
     *
     * This method adds a new Medical Record to the Database.
     *
     * @see MedicalRecord
     *
     * @param medicalRecord the new Medical Record to be added in the Database.
     * @return the new added Medical Record.
     *
     * @author denisSiveton
     * @version 1.0
     */
    public MedicalRecord addMedicalRecord(MedicalRecord medicalRecord) {
        return medicalRecordCRUD.addMedicalRecord(medicalRecord);
    }

    /**
     *
     * This method updates a Medical Record from the Database.
     *
     * @see MedicalRecord
     *
     * @param medicalRecord the Medical Record with the up to date in the Database.
     * @return the updated Medical Record or NULL if the Medical Record wasn't found in the Database.
     *
     * @author denisSiveton
     * @version 1.0
     */
    public MedicalRecord modifyInfoMedicalRecord(MedicalRecord medicalRecord) {
        return medicalRecordCRUD.modifyInfoMedicalRecord(medicalRecord);
    }

    /**
     *
     * This method deletes a Medical Record from the Database.
     *
     * @see MedicalRecord
     *
     * @param medicalRecord the Medical Record to be deleted from the Database.
     * @return the deleted Medical Record or NULL if the Medical Record wasn't found in the Database.
     *
     * @author denisSiveton
     * @version 1.0
     */
    public MedicalRecord deleteMedicalRecord(MedicalRecord medicalRecord) {
        return medicalRecordCRUD.deleteMedicalRecord(medicalRecord);
    }
}
