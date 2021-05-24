package com.safetynet.apiSafetyNet.model.viewModel.OutputData;

import com.safetynet.apiSafetyNet.model.viewModel.Data.Inhabitant;
import java.util.List;
import lombok.Data;

@Data
public class InhabitantInfo {

    private List<Inhabitant> inhabitants;

    private int numberOfAdults;

    private int numberOfChildren;
}
