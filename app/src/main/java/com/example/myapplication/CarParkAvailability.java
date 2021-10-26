package com.example.myapplication;

public class CarParkAvailability {

    private String carparkNo;
    private char lotType;
    private int lotsAvailable;
    private String[] geometries;
    private String coordinates;

    public String getCarparkNo() {
        return carparkNo;
    }

    public void setCarparkNo(String carparkNo) {
        this.carparkNo = carparkNo;
    }

    public char getLotType() {
        return lotType;
    }

    public void setLotType(char lotType) {
        this.lotType = lotType;
    }

    public int getLotsAvailable() {
        return lotsAvailable;
    }

    public void setLotsAvailable(int lotsAvailable) {
        this.lotsAvailable = lotsAvailable;
    }

    public String[] getGeometries() {
        return geometries;
    }

    public void setGeometries(String[] geometries) {
        this.geometries = geometries;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }
}
