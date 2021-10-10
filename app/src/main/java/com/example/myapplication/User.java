package com.example.myapplication;

public class User {
    private String fName;
    private String lName;
    private String email;
    private String pass;
    private int vehicleType;

    public User(String fName, String lName, String email, String pass, int vehicleType) {
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.pass = pass;
        this.vehicleType = vehicleType;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(int vehicleType) {
        this.vehicleType = vehicleType;
    }
}
