package com.geog686.geog686finalproject;

public class StopItem {

    private double lat;
    private double lon;
    private String address;

    public StopItem(double lat, double lon, String address){
        this.lat = lat;
        this.lon = lon;
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
