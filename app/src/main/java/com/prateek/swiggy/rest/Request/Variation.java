package com.prateek.swiggy.rest.Request;

import android.os.Parcel;
import android.os.Parcelable;

public class Variation implements Parcelable, Cloneable {

    public static final Parcelable.Creator<Variation> CREATOR = new Parcelable.Creator<Variation>() {
        @Override
        public Variation createFromParcel(Parcel source) {
            return new Variation(source);
        }

        @Override
        public Variation[] newArray(int size) {
            return new Variation[size];
        }
    };
    public String name;
    public Integer price;
    public Integer _default;
    public String id;
    public Integer inStock;
    public Integer isVeg;

    private boolean isEnabled = true;

    private boolean isSelected = false;

    public Variation() {
    }

    protected Variation(Parcel in) {
        this.name = in.readString();
        this.price = (Integer) in.readValue(Integer.class.getClassLoader());
        this._default = (Integer) in.readValue(Integer.class.getClassLoader());
        this.id = in.readString();
        this.inStock = (Integer) in.readValue(Integer.class.getClassLoader());
        this.isVeg = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer get_default() {
        return _default;
    }

    public String getId() {
        return id;
    }

    public Integer getInStock() {
        return inStock;
    }

    public Integer getIsVeg() {
        return isVeg;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeValue(this.price);
        dest.writeValue(this._default);
        dest.writeString(this.id);
        dest.writeValue(this.inStock);
        dest.writeValue(this.isVeg);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Variation variation = (Variation) o;

        if (isEnabled != variation.isEnabled) return false;
        if (name != null ? !name.equals(variation.name) : variation.name != null) return false;
        if (price != null ? !price.equals(variation.price) : variation.price != null) return false;
        if (_default != null ? !_default.equals(variation._default) : variation._default != null)
            return false;
        if (id != null ? !id.equals(variation.id) : variation.id != null) return false;
        if (inStock != null ? !inStock.equals(variation.inStock) : variation.inStock != null)
            return false;
        return isVeg != null ? isVeg.equals(variation.isVeg) : variation.isVeg == null;

    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (_default != null ? _default.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (inStock != null ? inStock.hashCode() : 0);
        result = 31 * result + (isVeg != null ? isVeg.hashCode() : 0);
        result = 31 * result + (isEnabled ? 1 : 0);
        return result;
    }
}
