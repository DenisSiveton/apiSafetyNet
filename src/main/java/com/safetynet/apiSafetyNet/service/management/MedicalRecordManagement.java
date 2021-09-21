package com.safetynet.apiSafetyNet.repository.management;

import com.safetynet.apiSafetyNet.model.InputData.MedicalRecord;

import java.util.ArrayList;

public class MedicalRecordManagement {

    public MedicalRecord getMedicalRecord(String firstName, String lastName, ArrayList<MedicalRecord> medicalRecords) {
        MedicalRecord result = null;
        for(MedicalRecord medicalRecord : medicalRecords){
            if(medicalRecord.getFirstName().equals(firstName) && medicalRecord.getLastName().equals(lastName)){
                result = medicalRecord;
                break;
            }
        }
        return result;
    }

    public ArrayList<MedicalRecord> filterListMedicalRecordWithFirstName(ArrayList<MedicalRecord> medicalRecordListJSON, String lastName, String firstName) {
        ArrayList<MedicalRecord> medicalRecordListFiltered = new ArrayList<>();
        for(int run = 0; run <2;run++){
            for(int i =0; i <medicalRecordListJSON.size(); i++){
                if(run == 0) {
                    if (medicalRecordListJSON.get(i).getFirstName().equals(firstName) && medicalRecordListJSON.get(i).getLastName().equals(lastName)) {
                        medicalRecordListFiltered.add(medicalRecordListJSON.get(i));
                        medicalRecordListJSON.remove(i);
                    }
                }
                else{
                    if (medicalRecordListJSON.get(i).getLastName().equals(lastName)) {
                        medicalRecordListFiltered.add(medicalRecordListJSON.get(i));
                    }
                }
            }
        }
        return medicalRecordListFiltered;
    }

}
