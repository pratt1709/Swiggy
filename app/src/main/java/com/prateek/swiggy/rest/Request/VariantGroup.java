package com.prateek.swiggy.rest.Request;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class VariantGroup implements Parcelable, Cloneable {

    public static final Parcelable.Creator<VariantGroup> CREATOR = new Parcelable.Creator<VariantGroup>() {
        @Override
        public VariantGroup createFromParcel(Parcel source) {
            return new VariantGroup(source);
        }

        @Override
        public VariantGroup[] newArray(int size) {
            return new VariantGroup[size];
        }
    };
    @SerializedName("group_id")
    private String groupId;
    private String name;
    private ArrayList<Variation> variations = new ArrayList<Variation>();

    public VariantGroup() {
    }

    protected VariantGroup(Parcel in) {
        this.groupId = in.readString();
        this.name = in.readString();
        this.variations = in.createTypedArrayList(Variation.CREATOR);
    }

    public void removeVariation(Variation variation) {
        variations.remove(variation);
    }

    public String getGroupId() {
        return groupId;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Variation> getVariations() {
        return variations;
    }

    public void setVariations(ArrayList<Variation> variations) {
        this.variations = variations;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.groupId);
        dest.writeString(this.name);
        dest.writeTypedList(this.variations);
    }

    @Override
    public Object clone() {
        try {
            VariantGroup group = (VariantGroup) super.clone();

            ArrayList<Variation> cloneList = new ArrayList<>();
            for (Variation variation : variations) {
                cloneList.add((Variation) variation.clone());
            }

            group.setVariations(cloneList);

            return group;
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VariantGroup that = (VariantGroup) o;

        if (!groupId.equals(that.groupId)) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return variations != null ? variations.equals(that.variations) : that.variations == null;

    }

    @Override
    public int hashCode() {
        int result = groupId.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (variations != null ? variations.hashCode() : 0);
        return result;
    }
}
