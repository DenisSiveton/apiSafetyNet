package com.safetynet.apiSafetyNet.service;

import com.safetynet.apiSafetyNet.model.Data.Inhabitant;
import com.safetynet.apiSafetyNet.model.Data.PeopleDetailed;
import com.safetynet.apiSafetyNet.model.InputData.FireStation;
import com.safetynet.apiSafetyNet.model.InputData.MedicalRecord;
import com.safetynet.apiSafetyNet.model.InputData.Person;
import com.safetynet.apiSafetyNet.model.OutputData.AddressInfo;
import com.safetynet.apiSafetyNet.model.OutputData.HomeInfo;
import com.safetynet.apiSafetyNet.model.OutputData.InhabitantInfo;
import com.safetynet.apiSafetyNet.repository.FireStationCRUD;
import com.safetynet.apiSafetyNet.repository.MedicalRecordCRUD;
import com.safetynet.apiSafetyNet.repository.PersonCRUD;
import com.safetynet.apiSafetyNet.service.management.FireStationManagement;
import com.safetynet.apiSafetyNet.service.management.HomeInfoManagement;
import com.safetynet.apiSafetyNet.service.management.InhabitantManagement;
import com.safetynet.apiSafetyNet.service.management.PersonManagement;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

@Service
public class FireStationService {

    @Value("${safetynet.path-data}")
    private String filePath;
    @Autowired
    private FireStationCRUD fireStationCRUD;
    @Autowired
    private PersonCRUD personCRUD;
    @Autowired
    private MedicalRecordCRUD medicalRecordCRUD;
    @Autowired
    private FireStationManagement fireStationManagement;
    @Autowired
    private PersonManagement personManagement;
    @Autowired
    private InhabitantManagement inhabitantManagement;
    @Autowired
    private HomeInfoManagement homeInfoManagement;

    public FireStationService() {}

    public FireStationService(String filePath,FireStationCRUD fireStationCRUD, PersonCRUD personCRUD, MedicalRecordCRUD medicalRecordCRUD,
                              FireStationManagement fireStationManagement, PersonManagement personManagement,
                              InhabitantManagement inhabitantManagement, HomeInfoManagement homeInfoManagement) {
        this.filePath = filePath;
        this.fireStationCRUD = fireStationCRUD;
        this.personCRUD = personCRUD;
        this.medicalRecordCRUD = medicalRecordCRUD;
        this.fireStationManagement = fireStationManagement;
        this.personManagement = personManagement;
        this.inhabitantManagement = inhabitantManagement;
        this.homeInfoManagement = homeInfoManagement;
    }

    /**
     *
     * This method adds a fire station to the Database
     *
     * @param fireStation : the fire station to be added to the Database
     * @return the fire station that was added to the Database.
     *
     * @author denisSiveton
     * @version 1.0
     */
    public FireStation addFireStation(FireStation fireStation) {
        return fireStationCRUD.addFireStation(fireStation);
    }

    /**
     *
     * This method updates a fire station in the database
     *
     * @param fireStation the fire station with up to date data
     * @return the fire station with the updated data or NULL if the fire station wasn't found in the Database.
     *
     * @author denisSiveton
     * @version 1.0
     */
    public FireStation modifyInfoFireStation(FireStation fireStation) {
        return fireStationCRUD.modifyInfoFireStation(fireStation);
    }

    /**
     *
     * This method deletes a fire station from the database
     *
     * @param fireStation the fire station to be deleted from the Database.
     * @return the fire station that was deleted or NULL if the fire station wasn't found in the Database.
     *
     * @author denisSiveton
     * @version 1.0
     */
    public FireStation deleteFireStation(FireStation fireStation) {
        return fireStationCRUD.deleteFireStation(fireStation);
    }

    /**
     *
     * This method creates a list of "inhabitant" and counts the number of children and adults from a fire station number.
     * @see InhabitantInfo
     * @see Inhabitant
     *
     * @param stationNumber the fire station number that will identify the addresses for which the fire stations are responsible for.
     * @return an instantiation of InhabitantInfo that are under the responsibility of the fire station.
     *
     * @author denisSiveton
     * @version 1.0
     */
    public InhabitantInfo getInfoPersonFromFireStationNumber(String stationNumber) {

        JSONParser jsonP = new JSONParser();
        InhabitantInfo inhabitantInfo = new InhabitantInfo(new ArrayList<>(), 0, 0);
        try {
            JSONObject jsonO = (JSONObject) jsonP.parse(new FileReader(filePath));
            ArrayList<FireStation> fireStations = fireStationCRUD.getFireStationsFromJSONFile(jsonO);
            ArrayList<Person> persons = personCRUD.getPersonsFromJSONFile(jsonO);
            ArrayList<MedicalRecord> medicalRecords = medicalRecordCRUD.getAllMedicalRecords(jsonO);

            /* Gather all addresses under the fireStation number's jurisdiction */
            ArrayList<String> addresses = fireStationManagement.getAddressesFromFireStationNumber(stationNumber, fireStations);

            /* Create all the inhabitants and put them in InhabitantInfo */
            inhabitantManagement.createInhabitantsWithAddressList(inhabitantInfo, persons, addresses);

            /*Calculate the number of Adults and Children in the inhabitants */
            inhabitantManagement.calculateNumberOfAdultsAndChildrenInInhabitants(inhabitantInfo, medicalRecords);

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return inhabitantInfo;
    }

    /**
     *
     * This method creates a list of "people detailed" that live at a specified address and gives the number of the fire station responsible for this address.
     *
     * @see AddressInfo
     * @see PeopleDetailed
     *
     * @param address the address from which we want to find the people information.
     * @return an instantiation of AddressInfo from the address.
     *
     * @author denisSiveton
     * @version 1.0
     */
    public AddressInfo getInfoFromEachPersonFromAddressAndAppointedFireStationNumber(String address) {
        AddressInfo addressInfo = new AddressInfo("", new ArrayList<>());
        JSONParser jsonP = new JSONParser();
        try {
            JSONObject jsonO = (JSONObject) jsonP.parse(new FileReader(filePath));

            //Gather all the data under different lists of Primary Data
            ArrayList<FireStation> fireStations = fireStationCRUD.getFireStationsFromJSONFile(jsonO);
            ArrayList<Person> persons = personCRUD.getPersonsFromJSONFile(jsonO);
            ArrayList<MedicalRecord> medicalRecords = medicalRecordCRUD.getAllMedicalRecords(jsonO);

            /* Get the number of FireStation responsible for the appointed Address */
            fireStationManagement.getFireStationNumberFromAddress(address, addressInfo, fireStations);
            if(addressInfo.getStationNumber() != ""){
                /* Gather all persons that live at the address */
                personManagement.getPersonsFromAddress(address, addressInfo, medicalRecords, persons);
                if (addressInfo.getPeopleDetailedArrayList().size() == 0){
                    addressInfo = null;
                }
            }
            else{
                addressInfo = null;
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return addressInfo;
    }

    /**
     *
     * This method creates a list of "HomeInfo" (the address and a list of people living in each home) based on a list of fire station numbers.
     *
     * @see HomeInfo
     * @see PeopleDetailed
     *
     * @param stations a list of fire station numbers that are responsible for the wanted addresses.
     * @return a list of HomeInfo for all the address managed by the fire stations with the number form the list.
     *
     * @author denisSiveton
     * @version 1.0
     */
    public ArrayList<HomeInfo> getHomeInfoListsFromFireStationNumbers(ArrayList<String> stations) {
        ArrayList<HomeInfo> homesInfo = new ArrayList<>();
        JSONParser jsonP = new JSONParser();
        try {
            JSONObject jsonO = (JSONObject) jsonP.parse(new FileReader(filePath));

            /* Create a list of FireStation, Person and Medical Record */
            ArrayList<FireStation> fireStationListJSON = fireStationCRUD.getFireStationsFromJSONFile(jsonO);
            ArrayList<Person> personListJSON = personCRUD.getPersonsFromJSONFile(jsonO);
            ArrayList<MedicalRecord> medicalRecordListJSON = medicalRecordCRUD.getAllMedicalRecords(jsonO);

            /* For each fire station number, gather correspondent addresses in list */
            ArrayList<ArrayList<String>> addressListByFireStationNumber = fireStationManagement.addAddressesToListForEachStationNumber(stations, fireStationListJSON);

            /* Create HomeInfo Object for each Address if the address list are not null */
            if(isListOfAddressNotEmpty(addressListByFireStationNumber)) {
                homeInfoManagement.createHomeInfoListBasedOnAddressList(stations, homesInfo, addressListByFireStationNumber, personListJSON, medicalRecordListJSON);
            }
            else{
                homesInfo = null;
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return homesInfo;
    }

    private boolean isListOfAddressNotEmpty(ArrayList<ArrayList<String>> addressListByFireStationNumber) {
        boolean isListOfAddressNotEmpty = true;
        for(int i =0; i <addressListByFireStationNumber.size(); i++){
            if(addressListByFireStationNumber.get(i).size()==0){
                isListOfAddressNotEmpty = false;
            }
        }
        return isListOfAddressNotEmpty;
    }

}


