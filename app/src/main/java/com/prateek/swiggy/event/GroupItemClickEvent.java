package com.prateek.swiggy.event;

/**
 * Created by prateek.kesarwani on 22/10/16.
 */

public class GroupItemClickEvent extends ItemClickEvent {

    public GroupItemClickEvent(int index) {
        this.index = index;
    }

}
