package com.safetynet.apiSafetyNet.model.OutputData;

import com.safetynet.apiSafetyNet.model.Data.PeopleDetailed;

import java.util.List;
import lombok.Data;

@Data
public class AddressInfo {

    private int stationNumber;
    private List<PeopleDetailed> personsInfo;
}
