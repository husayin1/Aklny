package com.example.foodfusion.features.home.search.view.result;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class SearchType implements Parcelable {
    public final static String country = "COUNTRY";
    public final static String ingredient = "INGREDIENT";

    public final static String category = "CATEGORY";
    private String name;
    private String type;

    public SearchType() {
        this.name = "";
        this.type = "";
    }

    protected SearchType(Parcel in) {
        name = in.readString();
        type = in.readString();
    }

    public static final Creator<SearchType> CREATOR = new Creator<SearchType>() {
        @Override
        public SearchType createFromParcel(Parcel in) {
            return new SearchType(in);
        }

        @Override
        public SearchType[] newArray(int size) {
            return new SearchType[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public SearchType(String type, String name) {
        this.name = name;
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(type);
    }
}
