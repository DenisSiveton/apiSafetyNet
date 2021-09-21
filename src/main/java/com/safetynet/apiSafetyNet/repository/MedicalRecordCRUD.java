package com.safetynet.apiSafetyNet.repository;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.jsoniter.JsonIterator;
import com.safetynet.apiSafetyNet.model.InputData.FireStation;
import com.safetynet.apiSafetyNet.model.InputData.MedicalRecord;
import lombok.Data;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

@Data
@Repository
public class MedicalRecordCRUD {

    @Value("${safetynet.path-data}")
    private String pathData;

    public MedicalRecordCRUD() {
    }

    public MedicalRecordCRUD(String pathData) {
        this.pathData = pathData;
    }

    /**
     *
     * This method retrieves the list of Medical Records from the Database,
     * adds the new Medical Record and updates the Database.
     *
     * @see MedicalRecord
     *
     * @param medicalRecord the new Medical Record to be added.
     * @return the new added Medical Record.
     *
     * @author denisSiveton
     * @version 1.0
     */
    public MedicalRecord addMedicalRecord(MedicalRecord medicalRecord) {
        //Create new medicalRecord JSONObject from a medicalRecord JAVAObject
        JSONObject medicalRecordToAdd = getJsonObject(medicalRecord);

        JSONParser jsonP = new JSONParser();
        try {
            JSONObject jsonO = (JSONObject) jsonP.parse(new FileReader(pathData));

            // Get all MedicalRecords from JSON file*
            JSONArray medicalRecords = (JSONArray) jsonO.get("medicalrecords");

            //Add new medicalRecord to the JSONArray list *
            medicalRecords.add(medicalRecordToAdd);
            jsonO.put("medicalrecords", medicalRecords);

            //Write the updated list in the json file *
            writeInFile(jsonO);
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return medicalRecord;
    }

    /**
     *
     * This method retrieves the list of Medical Records from the Database,
     * searches for the given Medical Record, updates it and updates the Database.
     *
     * @see MedicalRecord
     *
     * @param medicalRecord the Medical Record to update.
     * @return the updated Medical Record or NULL if the given Medical Record wasn't found in the Database.
     *
     * @author denisSiveton
     * @version 1.0
     */
    public MedicalRecord modifyInfoMedicalRecord(MedicalRecord medicalRecord) {
        JSONParser jsonP = new JSONParser();
        MedicalRecord result = null;
        try {
            JSONObject jsonO = (JSONObject) jsonP.parse(new FileReader(pathData));

            //Get all Person from JSON file
            ArrayList<MedicalRecord> medicalRecordList = getAllMedicalRecords(jsonO);

            boolean isMedicalRecordPresent = false;
            for(int i =0; i<medicalRecordList.size();i++) {
                if (medicalRecordList.get(i).getFirstName().equals(medicalRecord.getFirstName()) && medicalRecordList.get(i).getLastName().equals(medicalRecord.getLastName())) {
                    isMedicalRecordPresent = true;
                    medicalRecordList.remove(i);
                    medicalRecordList.add(i, medicalRecord);
                    break;
                }
            }
            if (isMedicalRecordPresent){
                JsonArray personListUpdated = new Gson().toJsonTree(medicalRecordList).getAsJsonArray();
                jsonO.put("medicalrecords", personListUpdated);
                writeInFile(jsonO);
                result = medicalRecord;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     *
     * This method retrieves the list of Medical Records from the Database,
     * searches for the given Medical Record, deletes it and updates the Database.
     *
     * @see MedicalRecord
     *
     * @param medicalRecord the Medical Record to delete.
     * @return the deleted Medical Record or NULL if the given fire station wasn't found in the Database.
     *
     * @author denisSiveton
     * @version 1.0
     */
    public MedicalRecord deleteMedicalRecord(MedicalRecord medicalRecord) {
        MedicalRecord medicalRecordToDelete = null;
        JSONParser jsonP = new JSONParser();
        try {
            JSONObject jsonO = (JSONObject) jsonP.parse(new FileReader(pathData));

            //Get all Person from JSON file
            ArrayList<MedicalRecord> medicalRecordList = getAllMedicalRecords(jsonO);

            boolean isMedicalRecordToModifyPresent = false;
            for(int i =0; i<medicalRecordList.size();i++) {
                if (medicalRecordList.get(i).getFirstName().equals(medicalRecord.getFirstName()) && medicalRecordList.get(i).getLastName().equals(medicalRecord.getLastName())) {
                    isMedicalRecordToModifyPresent = true;
                    medicalRecordToDelete = medicalRecordList.get(i);
                    medicalRecordList.remove(i);
                    break;
                }
            }
            if (isMedicalRecordToModifyPresent){
                JsonArray medicalReccordListUpdated = new Gson().toJsonTree(medicalRecordList).getAsJsonArray();
                jsonO.put("medicalrecords", medicalReccordListUpdated);
                writeInFile(jsonO);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return medicalRecordToDelete;
    }

    /**
     *
     * This method returns the list of all the Medical Records stored in the Database.
     *
     * @see MedicalRecord
     *
     * @param jsonO the JSONObject containing all the data.
     * @return the list of all the Medical Records in the Database.
     *
     * @author denisSiveton
     * @version 1.0
     */
    public ArrayList<MedicalRecord> getAllMedicalRecords(JSONObject jsonO) {
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
        medicalRecordToAddToJSONFile.put("allergies", new Gson().toJsonTree(medicalRecord.getAllergies()).getAsJsonArray());
        medicalRecordToAddToJSONFile.put("medications", new Gson().toJsonTree(medicalRecord.getMedications()).getAsJsonArray());
        return medicalRecordToAddToJSONFile;
    }

    private void writeInFile(JSONObject jsonO) throws IOException {
        String jsonObjectToString = String.valueOf(jsonO);
        FileWriter file = new FileWriter(pathData);
        file.write(jsonObjectToString.replace("\\", ""));
        file.flush();
    }
}

