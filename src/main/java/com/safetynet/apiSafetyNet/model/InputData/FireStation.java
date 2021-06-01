package com.safetynet.apiSafetyNet.model.InputData;
import lombok.Data;

@Data
public class FireStation {

    private String address;

    private String station;

    @Override
    public String toString() {
        return "FireStation{" +
                "address='" + address + '\'' +
                ", station=" + station +
                '}';
    }
}
