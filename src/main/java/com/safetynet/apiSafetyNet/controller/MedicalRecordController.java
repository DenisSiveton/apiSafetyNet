package com.safetynet.apiSafetyNet.controller;
import com.safetynet.apiSafetyNet.exceptions.MedicalRecordNotFoundException;
import com.safetynet.apiSafetyNet.exceptions.PersonNotFoundException;
import com.safetynet.apiSafetyNet.model.InputData.FireStation;
import com.safetynet.apiSafetyNet.model.InputData.MedicalRecord;
import com.safetynet.apiSafetyNet.model.InputData.Person;
import com.safetynet.apiSafetyNet.service.MedicalRecordService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class MedicalRecordController {

    @Autowired
    private final MedicalRecordService medicalRecordService;

    private static final Logger logger = LogManager.getLogger(MedicalRecordController.class);

    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    /**
     * This method receives a POST Request with a MedicaLRecord as a body to add in the Database.
     * It calls the Service layer to do so.
     * It returns the added MedicalRecord with the HTTP Code 201.
     *
     * @see MedicalRecord
     * @param medicalRecord the MedicalRecord to be added into the Database.
     * @return the new added MedicalRecord.
     *
     * @author Denis Siveton
     * @version 1.0
     */
    @PostMapping("/medicalrecord")
    public ResponseEntity<Void> addMedicalRecord(@Valid  @RequestBody MedicalRecord medicalRecord) {
        MedicalRecord medicalRecordAdded = medicalRecordService.addMedicalRecord(medicalRecord);
        if (medicalRecordAdded == null) {
            return ResponseEntity.noContent().build();
        }
        else{
            return new ResponseEntity(medicalRecordAdded, HttpStatus.CREATED);
        }
    }

    /**
     * This method receives a PATCH Request with a MedicalRecord as a body to update in the Database.
     * It calls the Service layer to do so.
     * It returns the updated MedicalRecord with the HTTP Code 204 or 404 if the MedicalRecord wasn't found in the Database.
     *
     * @see MedicalRecord
     * @param medicalRecord the MedicalRecord to be updated into the Database.
     * @return the updated MedicalRecord or NULL if no MedicalRecord was found in the Database.
     *
     * @author Denis Siveton
     * @version 1.0
     */
    @PatchMapping("/medicalrecord")
    public ResponseEntity<Void> modifyInfoMedicalRecord(@Valid @RequestBody MedicalRecord medicalRecord) throws MedicalRecordNotFoundException {
        MedicalRecord medicalRecordModified = medicalRecordService.modifyInfoMedicalRecord(medicalRecord);

        if (medicalRecordModified == null){
            MedicalRecordNotFoundException exception = new MedicalRecordNotFoundException("The Medical record with FirstName : \"" + medicalRecord.getFirstName() + "\" and LastName : \"" + medicalRecord.getLastName() + "\", was NOT FOUND. Please search with another first name and last name.");
            logger.error("Request ended up in ERROR :'" + exception.getMessage() + "'.");
            throw exception;
        }
        else{
            return new ResponseEntity(medicalRecordModified, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * This method receives a DELETE Request with a MedicalRecord as a body to delete from the Database.
     * It calls the Service layer to do so.
     * It returns the deleted MedicalRecord with the HTTP Code 204 or 404 if the MedicalRecord wasn't found in the Database.
     *
     * @see MedicalRecord
     * @param medicalRecord the MedicalRecord to be deleted from the Database.
     * @return the deleted MedicalRecord or NULL if no MedicalRecord was found in the Database.
     *
     * @author Denis Siveton
     * @version 1.0
     */
    @DeleteMapping("/medicalrecord")
    public ResponseEntity<Void> deleteMedicalRecord(@Valid @RequestBody MedicalRecord medicalRecord) {
        MedicalRecord medicalRecordDeleted = medicalRecordService.deleteMedicalRecord(medicalRecord);

        if (medicalRecordDeleted == null) {
            MedicalRecordNotFoundException exception = new MedicalRecordNotFoundException("The Medical record with FirstName : \"" +medicalRecord.getFirstName()+ "\" and LastName : \"" +medicalRecord.getLastName()+ "\", was NOT FOUND. Please search with another first name and last name.");
            logger.error("Request ended up in ERROR :'" +exception.getMessage() + "'.");
            throw exception;
        }
        else{
            return new ResponseEntity(medicalRecordDeleted, HttpStatus.NO_CONTENT);
        }
    }
}
