package com.safetynet.apiSafetyNet.repository;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.jsoniter.JsonIterator;
import com.safetynet.apiSafetyNet.model.InputData.FireStation;
import com.safetynet.apiSafetyNet.model.InputData.MedicalRecord;
import com.safetynet.apiSafetyNet.model.InputData.Person;
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
public class PersonCRUD {

    @Value("${safetynet.path-data}")
    private String pathData;

    public PersonCRUD() {
    }

    public PersonCRUD(String pathData) {
        this.pathData = pathData;
    }

    /**
     *
     * This method retrieves the list of persons from the Database,
     * adds the new person and updates the Database.
     *
     * @see Person
     *
     * @param person the new person to be added.
     * @return the new added person.
     *
     * @author denisSiveton
     * @version 1.0
     */
    public Person addPerson(Person person){

        JSONObject personToAddToJSON = new JSONObject();
        personToAddToJSON.put("firstName", person.getFirstName());
        personToAddToJSON.put("lastName", person.getLastName());
        personToAddToJSON.put("address", person.getAddress());
        personToAddToJSON.put("city", person.getCity());
        personToAddToJSON.put("zip", person.getZip());
        personToAddToJSON.put("phone", person.getPhone());
        personToAddToJSON.put("email", person.getEmail());

        JSONParser jsonP = new JSONParser();
        try {
            JSONObject jsonO = (JSONObject) jsonP.parse(new FileReader(pathData));

            //Get all Person from JSON file
            JSONArray persons = (JSONArray) jsonO.get("persons");


            persons.add(personToAddToJSON);
            jsonO.put("persons", persons);
            writeInFile(jsonO);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return person;
    }

    /**
     *
     * This method retrieves the list of persons from the Database,
     * searches for the given person, updates it and updates the Database.
     *
     * @see Person
     *
     * @param person the person to update.
     * @return the updated person or NULL if the given person wasn't found in the Database.
     *
     * @author denisSiveton
     * @version 1.0
     */
    public Person modifyInfoPerson(Person person) {
        JSONParser jsonP = new JSONParser();
        Person result = null;
        try {
            JSONObject jsonO = (JSONObject) jsonP.parse(new FileReader(pathData));

            //Get all Person from JSON file
            ArrayList<Person> personList = getPersonsFromJSONFile(jsonO);

            boolean isPersonToModifyPresent = false;
            for(int i =0; i<personList.size();i++) {
                if (personList.get(i).getFirstName().equals(person.getFirstName()) && personList.get(i).getLastName().equals(person.getLastName())) {
                    isPersonToModifyPresent = true;
                    personList.remove(i);
                    personList.add(i, person);
                    break;
                }
            }
            if (isPersonToModifyPresent){
                JsonArray personListUpdated = new Gson().toJsonTree(personList).getAsJsonArray();
                jsonO.put("persons", personListUpdated);
                writeInFile(jsonO);
                result = person;
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
     * This method retrieves the list of persons from the Database,
     * searches for the given person, deletes it and updates the Database.
     *
     * @see Person
     *
     * @param person the person to delete.
     * @return the deleted person or NULL if the given person wasn't found in the Database.
     *
     * @author denisSiveton
     * @version 1.0
     */
    public Person deletePerson(Person person)
    {
        JSONParser jsonP = new JSONParser();
        Person deletedPerson = null;
        try {
            JSONObject jsonO = (JSONObject) jsonP.parse(new FileReader(pathData));

            //Get all Person from JSON file
            ArrayList<Person> personList = getPersonsFromJSONFile(jsonO);

            boolean isPersonToDeletePresent = false;
            for(int i =0; i<personList.size();i++) {
                if (personList.get(i).getFirstName().equals(person.getFirstName()) && personList.get(i).getLastName().equals(person.getLastName())) {
                    isPersonToDeletePresent = true;
                    deletedPerson = personList.get(i);
                    personList.remove(i);
                    break;
                }
            }
            if (isPersonToDeletePresent){
                JsonArray personListUpdated = new Gson().toJsonTree(personList).getAsJsonArray();
                jsonO.put("persons", personListUpdated);
                writeInFile(jsonO);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return deletedPerson;
    }

    /**
     *
     * This method returns the list of all the persons stored in the Database.
     *
     * @see Person
     *
     * @param jsonO the JSONObject containing all the data.
     * @return the list of all the persons in the Database.
     *
     * @author denisSiveton
     * @version 1.0
     */
    public ArrayList<Person> getPersonsFromJSONFile(JSONObject jsonO) {
        ArrayList<Person> personListJSON = new ArrayList<>();
        JSONArray persons = (JSONArray) jsonO.get("persons");
        for(Object o : persons) {
            personListJSON.add(JsonIterator.deserialize(o.toString(), Person.class));
        }
        return personListJSON;
    }

    private void writeInFile(JSONObject jsonO) throws IOException {
        String jsonObjectToString = String.valueOf(jsonO);
        FileWriter file = new FileWriter(pathData);
        file.write(jsonObjectToString.replace("\\", ""));
        file.flush();
    }
}
