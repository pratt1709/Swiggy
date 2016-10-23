package com.prateek.swiggy.rest.Request;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Variants {

    @SerializedName("variant_groups")
    public ArrayList<VariantGroup> variantGroups = new ArrayList<>();
    @SerializedName("exclude_list")
    public ArrayList<ArrayList<GroupChoice>> excludeList = new ArrayList<>();

    public Variants() {
    }

    public ArrayList<VariantGroup> getVariantGroups() {
        return variantGroups;
    }

    public ArrayList<ArrayList<GroupChoice>> getExcludeList() {
        return excludeList;
    }

    public int getGroupSize() {
        return variantGroups.size();
    }

    public VariantGroup getGroup(String groupId) {
        for (VariantGroup group : variantGroups) {
            if (group.getGroupId().equalsIgnoreCase(groupId)) {
                return group;
            }
        }
        return null;
    }

}
