package com.example.myapplication;

public class CarPark {
    private String name;
    private String address;
    private String hdb_car_parking_rate;
    private String mall_weekday_rate1;
    private String mall_weekday_rate2;
    private String mall_sat_rate;
    private String mall_sun_rate;

    //updated price fields after Abhishek's inputs
    private String mall_weekday_24rate;
    private String mall_weekday_rates;

    private String mall_saturday_24rate;
    private String mall_saturday_rates;

    private String mall_sunday_24rate;
    private String mall_sunday_rates;

    public double getHourly_price() {
        return hourly_price;
    }

    public void setHourly_price(double hourly_price) {
        this.hourly_price = hourly_price;
    }

    private double hourly_price;

    public int getAvail_lots() {
        return avail_lots;
    }

    public void setAvail_lots(int avail_lots) {
        this.avail_lots = avail_lots;
    }

    private int avail_lots;


    public int getTotal_lots() {
        return total_lots;
    }

    public void setTotal_lots(int total_lots) {
        this.total_lots = total_lots;
    }

    private int total_lots;


    public String getLot_type() {
        return lot_type;
    }

    public void setLot_type(String lot_type) {
        this.lot_type = lot_type;
    }

    private String lot_type;


    private double reccIndex;

    public double getReccIndex() {
        return reccIndex;
    }

    public void setReccIndex(double reccIndex) {
        this.reccIndex = reccIndex;
    }

    public String getMall_weekday_24rate() {
        return mall_weekday_24rate;
    }

    public void setMall_weekday_24rate(String mall_weekday_24rate) {
        this.mall_weekday_24rate = mall_weekday_24rate;
    }

    public String getMall_weekday_rates() {
        return mall_weekday_rates;
    }

    public void setMall_weekday_rates(String mall_weekday_rates) {
        this.mall_weekday_rates = mall_weekday_rates;
    }

    public String getMall_saturday_24rate() {
        return mall_saturday_24rate;
    }

    public void setMall_saturday_24rate(String mall_saturday_24rate) {
        this.mall_saturday_24rate = mall_saturday_24rate;
    }

    public String getMall_saturday_rates() {
        return mall_saturday_rates;
    }

    public void setMall_saturday_rates(String mall_saturday_rates) {
        this.mall_saturday_rates = mall_saturday_rates;
    }

    public String getMall_sunday_24rate() {
        return mall_sunday_24rate;
    }

    public void setMall_sunday_24rate(String mall_sunday_24rate) {
        this.mall_sunday_24rate = mall_sunday_24rate;
    }

    public String getMall_sunday_rates() {
        return mall_sunday_rates;
    }

    public void setMall_sunday_rates(String mall_sunday_rates) {
        this.mall_sunday_rates = mall_sunday_rates;
    }
}