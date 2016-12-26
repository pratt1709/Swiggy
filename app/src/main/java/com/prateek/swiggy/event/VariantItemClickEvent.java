package com.prateek.swiggy.event;

import com.prateek.swiggy.rest.Request.GroupChoice;

public class VariantItemClickEvent extends ItemClickEvent {


    GroupChoice groupChoice;

    public VariantItemClickEvent(int index, GroupChoice groupChoice) {
        this.index = index;
        this.groupChoice = groupChoice;
    }

    public GroupChoice getGroupChoice() {
        return groupChoice;
    }
}
