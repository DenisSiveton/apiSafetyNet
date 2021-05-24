package com.safetynet.apiSafetyNet.model.viewModel.OutputData;

import com.safetynet.apiSafetyNet.model.viewModel.Data.People;
import java.util.List;
import lombok.Data;

@Data
public class ChildrenInfo {

    private List<People> children;

    private List<People> otherMembers;

    private int numberOfChildren;
}
