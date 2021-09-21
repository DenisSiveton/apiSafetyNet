package com.safetynet.apiSafetyNet.model.OutputData;

import com.safetynet.apiSafetyNet.model.Data.PeopleDetailed;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class HomeInfo {
    public HomeInfo(String address, ArrayList<PeopleDetailed> peopleInHouse){
        this.address = address;
        this.peopleInHouse = peopleInHouse;
    }

    private String address;
    private ArrayList<PeopleDetailed> peopleInHouse;

    @Override
    public String toString() {
        return "HomeInfo{" +
                "address='" + address + '\'' +
                ", peopleInHouse=" + peopleInHouse +
                '}';
    }
}
