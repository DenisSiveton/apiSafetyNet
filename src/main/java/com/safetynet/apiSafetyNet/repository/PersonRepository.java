package com.safetynet.apiSafetyNet.repository;

import com.jsoniter.JsonIterator;
import com.safetynet.apiSafetyNet.model.FireStation;
import com.safetynet.apiSafetyNet.model.Person;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Repository;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

@Repository
public class PersonRepository {



    public List<String> getMailFromAllPersonsFromCity(String city) {
        Vector<String> emails = null;
        JSONParser jsonP = new JSONParser();
        try {
            JSONObject jsonO = (JSONObject) jsonP.parse(new FileReader("src/main/resources/data.json"));
            JSONArray persons = (JSONArray) jsonO.get("persons");

            for (Object o : persons){
                Person person = JsonIterator.deserialize((String) o, Person.class);
                if(person.getCity() == city) {
                    emails.add(person.getEmail());
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return emails;
    }

    public List<String> getPhoneNumberListFromFireStationNumber(int fireStationNumber) {
        Vector<String> phones = null;
        JSONParser jsonP = new JSONParser();
        try {
            JSONObject jsonO = (JSONObject) jsonP.parse(new FileReader("src/main/resources/data.json"));
            JSONArray persons = (JSONArray) jsonO.get("persons");
            JSONArray fireStations = (JSONArray) jsonO.get("firestations");


            //Gather all addresses under the fireStation number's jurisdiction
            Vector<String> addresses = null;
            for (Object o : fireStations){
                FireStation fireStation = JsonIterator.deserialize((String) o, FireStation.class);
                if(fireStation.getStation() == fireStationNumber){
                    addresses.add(fireStation.getAddress());
                }
            }

            //Add phone if inhabitant's address is in the Address list
            for (Object o : persons){
                Person person = JsonIterator.deserialize((String) o, Person.class);
                if(addresses.contains(person.getAddress())){
                    phones.add(person.getPhone());
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return phones;
    }
}
