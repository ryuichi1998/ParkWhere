package com.example.myapplication.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataMallCarParkAvailability {

    @SerializedName("CarParkID")
    @Expose
    private String carParkID;
    @SerializedName("Area")
    @Expose
    private String area;
    @SerializedName("Development")
    @Expose
    private String development;
    @SerializedName("Location")
    @Expose
    private String location;
    @SerializedName("AvailableLots")
    @Expose
    private Integer availableLots;
    @SerializedName("LotType")
    @Expose
    private String lotType;
    @SerializedName("Agency")
    @Expose
    private String agency;

    public String getCarParkID() {
        return carParkID;
    }

    public void setCarParkID(String carParkID) {
        this.carParkID = carParkID;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDevelopment() {
        return development;
    }

    public void setDevelopment(String development) {
        this.development = development;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getAvailableLots() {
        return availableLots;
    }

    public void setAvailableLots(Integer availableLots) {
        this.availableLots = availableLots;
    }

    public String getLotType() {
        return lotType;
    }

    public void setLotType(String lotType) {
        this.lotType = lotType;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }
}
