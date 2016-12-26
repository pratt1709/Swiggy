package com.prateek.swiggy.event;

public class ItemClickEvent {

    protected int index;

    public ItemClickEvent() {

    }

    public ItemClickEvent(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
