package com.safetynet.apiSafetyNet.repository;

import com.jsoniter.JsonIterator;
import com.safetynet.apiSafetyNet.model.InputData.MedicalRecord;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Repository;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

@Repository
public class MedicalRecordRepository {

    public MedicalRecord addMedicalRecord(MedicalRecord medicalRecord) {
        //Create new JSONObject from JAVAObject
        JSONObject medicalRecordToAddToJSONFile = getJsonObject(medicalRecord);

        JSONParser jsonP = new JSONParser();
        try {
            String filePath ="src/main/resources/data.json";
            JSONObject jsonO = (JSONObject) jsonP.parse(new FileReader(filePath));

            /* Get all MedicalRecords from JSON file*/
            JSONArray medicalRecords = (JSONArray) jsonO.get("medicalrecords");

            /* Add new medicalRecord to the JSONArray list */
            medicalRecords.add(medicalRecordToAddToJSONFile);

            /* Write the updated list in the json file */
            FileWriter file = new FileWriter(filePath);
            file.write(medicalRecords.toJSONString());
            file.flush();
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return medicalRecord;
    }

    public MedicalRecord modifyInfoMedicalRecord(MedicalRecord medicalRecord) {
        return medicalRecord;
    }

    public void deleteMedicalRecord(MedicalRecord medicalRecord) {
    }

    private ArrayList<MedicalRecord> getMedicalRecordListFromJSONFile(JSONObject jsonO) {
        ArrayList<MedicalRecord> medicalRecordListJSON = new ArrayList<>();
        JSONArray medicalRecords = (JSONArray) jsonO.get("medicalrecords");
        for(int i = 0; i<medicalRecords.size(); i++) {
            String medicalRecordToString = medicalRecords.get(i).toString();
            MedicalRecord med = JsonIterator.deserialize(medicalRecordToString, MedicalRecord.class);
            String birthDate = medicalRecordToString.substring(medicalRecordToString.indexOf("birthdate")+12, medicalRecordToString.indexOf("birthdate")+14)+
                    medicalRecordToString.substring(medicalRecordToString.indexOf("birthdate")+15, medicalRecordToString.indexOf("birthdate")+18)+
                    medicalRecordToString.substring(medicalRecordToString.indexOf("birthdate")+19, medicalRecordToString.indexOf("birthdate")+24) ;
            med.setBirthDate(birthDate);
            medicalRecordListJSON.add(med);
        }
        return medicalRecordListJSON;

    }

    private JSONObject getJsonObject(MedicalRecord medicalRecord) {
        JSONObject medicalRecordToAddToJSONFile = new JSONObject();
        medicalRecordToAddToJSONFile.put("firstName", medicalRecord.getFirstName());
        medicalRecordToAddToJSONFile.put("lastName", medicalRecord.getLastName());
        medicalRecordToAddToJSONFile.put("birthdate", medicalRecord.getBirthDate());
        medicalRecordToAddToJSONFile.put("allergies", medicalRecord.getAllergies().toString());
        medicalRecordToAddToJSONFile.put("medications", medicalRecord.getMedications().toString());
        return medicalRecordToAddToJSONFile;
    }
}
