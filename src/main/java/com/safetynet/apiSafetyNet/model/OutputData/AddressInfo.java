package com.safetynet.apiSafetyNet.model.OutputData;

import com.safetynet.apiSafetyNet.model.Data.PeopleDetailed;

import java.util.ArrayList;
import java.util.Objects;

import lombok.Data;

@Data
public class AddressInfo {

    public AddressInfo(String stationNumber, ArrayList<PeopleDetailed> peopleDetailedArrayList){
        this.stationNumber = stationNumber;
        this.peopleDetailedArrayList = peopleDetailedArrayList;
    }

    private String stationNumber;
    private ArrayList<PeopleDetailed> peopleDetailedArrayList;

    @Override
    public String toString() {
        return "AddressInfo{" +
                "stationNumber=" + stationNumber +
                ", peopleDetailedArrayList=" + peopleDetailedArrayList +
                '}';
    }
}
