package com.safetynet.apiSafetyNet.model.viewModel.OutputData;

import com.safetynet.apiSafetyNet.model.viewModel.Data.PeopleDetailed;
import java.util.List;
import lombok.Data;

@Data
public class AddressInfo {

    private int stationNumber;
    private List<PeopleDetailed> personsInfo;
}
