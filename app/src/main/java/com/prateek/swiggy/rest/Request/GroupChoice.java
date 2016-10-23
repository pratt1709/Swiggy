package com.prateek.swiggy.rest.Request;

import com.google.gson.annotations.SerializedName;

public class GroupChoice {

    @SerializedName("group_id")
    public String groupId;
    @SerializedName("variation_id")
    public String variationId;

    public GroupChoice() {

    }

    public GroupChoice(String groupId, String variationId) {
        this.groupId = groupId;
        this.variationId = variationId;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getVariationId() {
        return variationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GroupChoice that = (GroupChoice) o;

        if (!groupId.equals(that.groupId)) return false;
        return variationId.equals(that.variationId);

    }

    @Override
    public int hashCode() {
        int result = groupId.hashCode();
        result = 31 * result + variationId.hashCode();
        return result;
    }

    public boolean containsGroupId(String groupId) {
        if (this.getGroupId().equalsIgnoreCase(groupId)) {
            return true;
        } else {
            return false;
        }
    }
}
