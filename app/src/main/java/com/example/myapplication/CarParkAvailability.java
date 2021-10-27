package com.example.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CarParkAvailability implements Parcelable {

    private String lotsAvailable;
    private String lotType;
    private String carparkNo;
    private List<Geometry> geometries = null;

    protected CarParkAvailability(Parcel in) {
        lotsAvailable = in.readString();
        lotType = in.readString();
        carparkNo = in.readString();
    }

    public static final Creator<CarParkAvailability> CREATOR = new Creator<CarParkAvailability>() {
        @Override
        public CarParkAvailability createFromParcel(Parcel in) {
            return new CarParkAvailability(in);
        }

        @Override
        public CarParkAvailability[] newArray(int size) {
            return new CarParkAvailability[size];
        }
    };

    public String getLotsAvailable() {
        return lotsAvailable;
    }

    public void setLotsAvailable(String lotsAvailable) {
        this.lotsAvailable = lotsAvailable;
    }

    public String getLotType() {
        return lotType;
    }

    public void setLotType(String lotType) {
        this.lotType = lotType;
    }

    public String getCarparkNo() {
        return carparkNo;
    }

    public void setCarparkNo(String carparkNo) {
        this.carparkNo = carparkNo;
    }

    public List<Geometry> getGeometries() {
        return geometries;
    }

    public void setGeometries(List<Geometry> geometries) {
        this.geometries = geometries;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(lotsAvailable);
        parcel.writeString(lotType);
        parcel.writeString(carparkNo);
    }
}
