package com.safetynet.apiSafetyNet.model.OutputData;

import com.safetynet.apiSafetyNet.model.Data.Inhabitant;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class InhabitantInfo {
    public InhabitantInfo(ArrayList<Inhabitant> inhabitants, int numberOfAdults, int numberOfChildren){
        this.inhabitants = inhabitants;
        this.numberOfAdults = numberOfAdults;
        this.numberOfChildren = numberOfChildren;
    }

    private ArrayList<Inhabitant> inhabitants;

    private int numberOfAdults;

    private int numberOfChildren;

    @Override
    public String toString() {
        return "InhabitantInfo{" +
                "inhabitants=" + inhabitants +
                ", numberOfAdults=" + numberOfAdults +
                ", numberOfChildren=" + numberOfChildren +
                '}';
    }
}
