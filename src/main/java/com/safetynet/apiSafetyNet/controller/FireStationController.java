package com.safetynet.apiSafetyNet.controller;
import com.safetynet.apiSafetyNet.exceptions.FireStationNotFoundException;
import com.safetynet.apiSafetyNet.exceptions.NoInhabitantForThisAddressException;
import com.safetynet.apiSafetyNet.exceptions.NoInhabitantForThisFireStationNumberException;
import com.safetynet.apiSafetyNet.exceptions.PersonNotFoundException;
import com.safetynet.apiSafetyNet.model.Data.Inhabitant;
import com.safetynet.apiSafetyNet.model.Data.PeopleDetailed;
import com.safetynet.apiSafetyNet.model.InputData.FireStation;
import com.safetynet.apiSafetyNet.model.OutputData.AddressInfo;
import com.safetynet.apiSafetyNet.model.OutputData.HomeInfo;
import com.safetynet.apiSafetyNet.model.OutputData.InhabitantInfo;
import com.safetynet.apiSafetyNet.service.FireStationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.html.HTMLHeadElement;

import javax.validation.Valid;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class FireStationController {

    @Autowired
    private FireStationService fireStationService;

    private static final Logger logger = LogManager.getLogger(FireStationController.class);


    public FireStationController(FireStationService fireStationService) {
        this.fireStationService = fireStationService;
    }

    /**
     * This method receives a POST Request with a FireStation as a body to add in the Database.
     * It calls the Service layer to do so.
     * It returns the added FireStation with the HTTP Code 201.
     *
     * @see FireStation
     * @param fireStation the FireStation to be added into the Database.
     * @return the new added FireStation.
     *
     * @author Denis Siveton
     * @version 1.0
     */
    @PostMapping("/firestation")
    public ResponseEntity<Void> addFireStation(@Valid @RequestBody FireStation fireStation) {
        FireStation fireStationAdded = fireStationService.addFireStation(fireStation);
        if (fireStationAdded == null) {
            return ResponseEntity.noContent().build();
        }
        else{
            return new ResponseEntity(fireStationAdded, HttpStatus.CREATED);
        }
    }

    /**
     * This method receives a PATCH Request with a FireStation as a body to update in the Database.
     * It calls the Service layer to do so.
     * It returns the updated FireStation with the HTTP Code 204 or 404 if the FireStation wasn't found in the Database.
     *
     * @see FireStation
     * @param fireStation the FireStation to be updated into the Database.
     * @return the updated FireStation or NULL if no FireStation was found in the Database.
     *
     * @author Denis Siveton
     * @version 1.0
     */
    @PatchMapping("/firestation")
    public ResponseEntity<Void> modifyInfoFireStation(@Valid @RequestBody FireStation fireStation) throws FireStationNotFoundException {
        FireStation fireStationModified = fireStationService.modifyInfoFireStation(fireStation);
        if(fireStationModified==null) {
            FireStationNotFoundException exception = new FireStationNotFoundException("The FireStation with the Address : '" +fireStation.getAddress()+ "', was NOT FOUND. Please search with another address.");
            logger.error("Request ended up in ERROR :'" + exception.getMessage() + "'.");
            throw exception;
        }

        else{
            return new ResponseEntity(fireStationModified, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * This method receives a DELETE Request with a FireStation as a body to delete from the Database.
     * It calls the Service layer to do so.
     * It returns the deleted FireStation with the HTTP Code 204 or 404 if the FireStation wasn't found in the Database.
     *
     * @see FireStation
     * @param fireStation the FireStation to be deleted from the Database.
     * @return the deleted FireStation or NULL if no FireStation was found in the Database.
     *
     * @author Denis Siveton
     * @version 1.0
     */
    @DeleteMapping("/firestation")
    public ResponseEntity<Void> deleteFireStation(@Valid @RequestBody FireStation fireStation) {
        FireStation fireStationDeleted = fireStationService.deleteFireStation(fireStation);
        if (fireStationDeleted == null) {
            FireStationNotFoundException exception = new FireStationNotFoundException("The FireStation with the Address : '" +fireStation.getAddress()+ "', was NOT FOUND. Please search with another address.");
            logger.error("Request ended up in ERROR :'" + exception.getMessage() + "'.");
            throw exception;
        }
        else{
            return new ResponseEntity(fireStationDeleted, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * This method receives a GET Request with a FireStation's number as a parameter.
     * It calls the Service layer to proceed.
     * It returns a InhabitantInfo data (with all the information about the people living at the addresses managed
     * by the fire stations with the given fire station number) with the HTTP Code 200 or 404 if no address is managed by that fire station's number.
     *
     * @see InhabitantInfo
     * @see Inhabitant
     *
     * @param stationNumber the FireStation's number.
     * @return the new InhabitantInfo data or NULL if no addresses in the Database are managed by the firestation's number.
     *
     * @author Denis Siveton
     * @version 1.0
     */
    @GetMapping("/firestation")
    public ResponseEntity<Void> getInfoPersonFromFireStationNumber(@RequestParam( name = "stationNumber") String stationNumber) {
        InhabitantInfo inhabitantInfo =  fireStationService.getInfoPersonFromFireStationNumber(stationNumber);
        if (inhabitantInfo == null) {
            NoInhabitantForThisFireStationNumberException exception = new NoInhabitantForThisFireStationNumberException("There are no addresses under the responsability of the given firestation with the following station :'" + stationNumber + "'. Please try another station");
            logger.error("Request ended up in ERROR :'" +exception.getMessage() + "'.");
            throw exception;
        }
        else{
            return new ResponseEntity(inhabitantInfo, HttpStatus.OK);
        }
    }

    /**
     * This method receives a GET Request with a list of FireStation's number as a parameter.
     * It calls the Service layer to proceed.
     * It returns a list of HomeInfo data (with all the detailed information about the people living at the addresses managed
     * by the fire stations with the given list of FireStation's number) with the HTTP Code 200 or 404 if no address is managed by these fire station's numbers.
     *
     * @see HomeInfo
     * @see PeopleDetailed
     *
     * @param stations the list of the FireStation's numbers.
     * @return the new list of HomeInfo data or NULL if no addresses in the Database are managed by the firestation's numbers.
     *
     * @author Denis Siveton
     * @version 1.0
     */
    @GetMapping("/flood/stations")
    public ResponseEntity<Void> getHomeListsFromFiresStationNumbers(@RequestParam(name = "stations") String stations) {
        //Create the list of stations from request parameter
        ArrayList<String > stationList = new ArrayList<String>(Arrays.asList(stations.split(",")));

        ArrayList<HomeInfo> listHomeInfo =  fireStationService.getHomeInfoListsFromFireStationNumbers(stationList);

        if (listHomeInfo == null) {
            NoInhabitantForThisFireStationNumberException exception = new NoInhabitantForThisFireStationNumberException("None of the given stations ("+ String.join(", ", stationList) + "), are responsible for any address. Please try another list of stations");
            logger.error("Request ended up in ERROR :'" +exception.getMessage() + "'.");
            throw exception;
        }
        else{
            return new ResponseEntity(listHomeInfo, HttpStatus.OK);
        }
    }

    /**
     * This method receives a GET Request with an address as a parameter.
     * It calls the Service layer to proceed.
     * It returns a addressInfo data (information of the people living at the specified address)
     * with the HTTP Code 200 or 404 if the address is not found in the Database.
     *
     * @see AddressInfo
     * @see PeopleDetailed
     *
     * @param address the address from which we want the information.
     * @return the new AddressInfo data or NULL if the address is not found in the Database.
     *
     * @author Denis Siveton
     * @version 1.0
     */
    @GetMapping("/fire")
    public ResponseEntity<Void> getInfoFromEachPersonFromAddressAndAppointedFireStationNumber(@RequestParam(name = "address") String address) {
        AddressInfo addressInfo = fireStationService.getInfoFromEachPersonFromAddressAndAppointedFireStationNumber(address);
        if (addressInfo == null) {
            NoInhabitantForThisAddressException exception = new NoInhabitantForThisAddressException("There is no people living at the following address :'" + address + "'. Please enter another address.");
            logger.error("Request ended up in ERROR :'" +exception.getMessage() + "'.");
            throw exception;
        }
        else{
            return new ResponseEntity(addressInfo, HttpStatus.OK);
        }
    }
}
