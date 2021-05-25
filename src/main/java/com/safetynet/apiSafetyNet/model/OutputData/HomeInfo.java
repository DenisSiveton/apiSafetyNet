package com.safetynet.apiSafetyNet.model.OutputData;

import com.safetynet.apiSafetyNet.model.Data.PeopleDetailed;

import java.util.List;
import lombok.Data;

@Data
public class HomeInfo {

    private String address;
    private List<PeopleDetailed> personsInfo;
}
