package com.safetynet.apiSafetyNet.model.OutputData;

import com.safetynet.apiSafetyNet.model.Data.Inhabitant;

import java.util.List;
import lombok.Data;

@Data
public class InhabitantInfo {

    private List<Inhabitant> inhabitants;

    private int numberOfAdults;

    private int numberOfChildren;
}
