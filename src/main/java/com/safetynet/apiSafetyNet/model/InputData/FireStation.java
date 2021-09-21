package com.safetynet.apiSafetyNet.model.InputData;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class FireStation {

    @NotNull
    @Pattern(regexp = "^[A-Za-z0-9]+$")
    private String address;

    @Pattern(regexp = "^[0-9]+$")
    private String station;

    public FireStation() {
    }

    public FireStation(String address, String station) {
        this.address = address;
        this.station = station;
    }

    @Override
    public String toString() {
        return "FireStation{" +
                "address='" + address + '\'' +
                ", station=" + station +
                '}';
    }
}
