package com.safetynet.apiSafetyNet.service.management;

import com.safetynet.apiSafetyNet.model.InputData.FireStation;
import com.safetynet.apiSafetyNet.model.InputData.MedicalRecord;
import com.safetynet.apiSafetyNet.model.InputData.Person;
import com.safetynet.apiSafetyNet.model.OutputData.AddressInfo;
import com.safetynet.apiSafetyNet.model.OutputData.ChildrenInfo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;


@Service
public class FireStationManagement {

    /**
     *
     * This method gathers in a list all the addresses that are managed by the specified FireStation's number.
     *
     * @see FireStation
     *
     * @param fireStationNumber the number of the station.
     * @param fireStationList the list of all the firestations from the Database.
     * @return the list of the addresses managed by the specified FireStation's number.
     *
     * @author denisSiveton
     * @version 1.0
     */
    public ArrayList<String> getAddressesFromFireStationNumber(String fireStationNumber, ArrayList<FireStation> fireStationList) {
        ArrayList<String> addresses = new ArrayList<>();
        for (FireStation fireStation : fireStationList){
            if(fireStation.getStation().equals(fireStationNumber)){
                addresses.add(fireStation.getAddress());
            }
        }
        return addresses;
    }

    /**
     *
     * This method fills the ChildrenInfo data from the list of Persons and MedicalRecords that live at a specified address.
     *
     * @see Person
     * @see MedicalRecord
     * @see ChildrenInfo
     *
     * @param address a specified address.
     * @param addressInfo the data that need to be filled to be returned to the user.
     * @param fireStations the list of all the fire stations.
     * @return the ChildrenInfo updated with the value of the fire station's number managing the specified address.
     *
     * @author denisSiveton
     * @version 1.0
     */
    public void getFireStationNumberFromAddress(String address, AddressInfo addressInfo, ArrayList<FireStation> fireStations) {
        for(FireStation fireStation : fireStations){
            if(fireStation.getAddress().equals(address)){
                addressInfo.setStationNumber(fireStation.getStation());
            }
        }
    }

    /**
     *
     * This method creates a list of lists of addresses by using the method "getAddressesFromFireStationNumber"
     * for each value of the list of FireStations'number.
     *
     * @see FireStation
     * @see FireStationManagement#getAddressesFromFireStationNumber(String, ArrayList) 
     *
     * @param stations the list of the FireStations's number.
     * @param fireStationListJSON the list of the FireStations from the Database.
     * @return the list of lists of addresses managed by the different FireStations's number.
     *
     * @author denisSiveton
     * @version 1.0
     */
    public ArrayList<ArrayList<String>> addAddressesToListForEachStationNumber(ArrayList<String> stations, ArrayList<FireStation> fireStationListJSON) {
        ArrayList<ArrayList<String>> addressListByFireStationNumber = new ArrayList<>();
        Collections.sort(stations);
        for(int i =0; i<stations.size();i++){
            addressListByFireStationNumber.add(getAddressesFromFireStationNumber(stations.get(i), fireStationListJSON));
        }
        return addressListByFireStationNumber;
    }

}
