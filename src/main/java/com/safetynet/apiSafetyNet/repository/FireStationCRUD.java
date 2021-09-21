package com.safetynet.apiSafetyNet.repository;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.jsoniter.JsonIterator;
import com.safetynet.apiSafetyNet.model.Data.PeopleDetailed;
import com.safetynet.apiSafetyNet.model.InputData.FireStation;
import com.safetynet.apiSafetyNet.model.InputData.Person;
import com.safetynet.apiSafetyNet.model.OutputData.HomeInfo;
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
public class FireStationCRUD {

    @Value("${safetynet.path-data}")
    private String pathData;

    /**
     *
     * This method retrieves the list of fire stations from the Database,
     * adds the new fire station and updates the Database.
     *
     * @see FireStation
     *
     * @param fireStation the new fire station to be added.
     * @return the new added fire station.
     *
     * @author denisSiveton
     * @version 1.0
     */
    public FireStation addFireStation(FireStation fireStation) {
        JSONObject fireStationToAddToJSON = new JSONObject();
        fireStationToAddToJSON.put("address", fireStation.getAddress());
        fireStationToAddToJSON.put("station", fireStation.getStation());

        JSONParser jsonP = new JSONParser();
        try {
            JSONObject jsonO = (JSONObject) jsonP.parse(new FileReader(pathData));

            //Get all Person from JSON file
            JSONArray fireStations = (JSONArray) jsonO.get("firestations");

            fireStations.add(fireStationToAddToJSON);
            jsonO.put("firestations", fireStations);
            writeInFile(jsonO);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return fireStation;
    }

    /**
     *
     * This method retrieves the list of fire stations from the Database,
     * searches for the given fire station, updates it and updates the Database.
     *
     * @see FireStation
     *
     * @param fireStation the fire station to update.
     * @return the updated fire station or NULL if the given fire station wasn't found in the Database.
     *
     * @author denisSiveton
     * @version 1.0
     */
    public FireStation modifyInfoFireStation(FireStation fireStation) {
        JSONParser jsonP = new JSONParser();
        FireStation result = null;
        try {
            JSONObject jsonO = (JSONObject) jsonP.parse(new FileReader(pathData));

            //Get all FireStation from JSON file
            ArrayList<FireStation> fireStationList = getFireStationsFromJSONFile(jsonO);

            boolean isFireStationToModifyPresent = false;
            for(int i =0; i<fireStationList.size();i++) {
                if (fireStationList.get(i).getAddress().equals(fireStation.getAddress()) ) {
                    isFireStationToModifyPresent = true;
                    fireStationList.remove(i);
                    fireStationList.add(i, fireStation);
                    break;
                }
            }
            if (isFireStationToModifyPresent){
                JsonArray personListUpdated = new Gson().toJsonTree(fireStationList).getAsJsonArray();
                jsonO.put("firestations", personListUpdated);
                writeInFile(jsonO);
                result = fireStation;
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
     * This method retrieves the list of fire stations from the Database,
     * searches for the given fire station, deletes it and updates the Database.
     *
     * @see FireStation
     *
     * @param fireStation the fire station to delete.
     * @return the deleted fire station or NULL if the given fire station wasn't found in the Database.
     *
     * @author denisSiveton
     * @version 1.0
     */
    public FireStation deleteFireStation(FireStation fireStation) {
        FireStation fireStationToDelete = null;
        JSONParser jsonP = new JSONParser();
        try {
            JSONObject jsonO = (JSONObject) jsonP.parse(new FileReader(pathData));

            //Get all Person from JSON file
            ArrayList<FireStation> fireStationList = getFireStationsFromJSONFile(jsonO);

            boolean isFireStationToDeletePresent = false;
            for(int i =0; i<fireStationList.size();i++) {
                if (fireStationList.get(i).getAddress().equals(fireStation.getAddress())) {
                    isFireStationToDeletePresent = true;
                    fireStationToDelete = fireStationList.get(i);
                    fireStationList.remove(i);
                    break;
                }
            }
            if (isFireStationToDeletePresent){
                JsonArray personListUpdated = new Gson().toJsonTree(fireStationList).getAsJsonArray();
                jsonO.put("firestations", personListUpdated);
                writeInFile(jsonO);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fireStationToDelete;
    }

    /**
     *
     * This method returns the list of all the fire stations stored in the Database.
     *
     * @see FireStation
     *
     * @param jsonO the JSONObject containing all the data.
     * @return the list of all the fire stations in the Database.
     *
     * @author denisSiveton
     * @version 1.0
     */
    public ArrayList<FireStation> getFireStationsFromJSONFile(JSONObject jsonO) {
        ArrayList<FireStation> fireStationListJSON = new ArrayList<>();
        JSONArray persons = (JSONArray) jsonO.get("firestations");
        for(Object o : persons) {
            fireStationListJSON.add(JsonIterator.deserialize(o.toString(), FireStation.class));
        }
        return fireStationListJSON;
    }

    private void writeInFile(JSONObject jsonO) throws IOException {
        String jsonObjectToString = String.valueOf(jsonO);
        FileWriter file = new FileWriter(pathData);
        file.write(jsonObjectToString.replace("\\", ""));
        file.flush();
    }
}
