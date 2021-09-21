package com.safetynet.apiSafetyNet.model.OutputData;

import com.safetynet.apiSafetyNet.model.Data.People;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class ChildrenInfo {

    private List<People> children;

    private List<People> otherMembers;

    private int numberOfChildren;

    public ChildrenInfo(ArrayList<People> children, ArrayList<People> adults, int numberOfChildren) {
        this.children = children;
        this.otherMembers = adults;
        this.numberOfChildren = numberOfChildren;
    }


    @Override
    public String toString() {
        return "ChildrenInfo{" +
                "children=" + children +
                ", otherMembers=" + otherMembers +
                ", numberOfChildren=" + numberOfChildren +
                '}';
    }
}
