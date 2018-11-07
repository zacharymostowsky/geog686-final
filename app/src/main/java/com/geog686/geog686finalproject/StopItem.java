package com.geog686.geog686finalproject;

import java.util.ArrayList;

public class StopItem {

    private double lat;
    private double lon;
    private String address;

    private static ArrayList<StopItem> stopItems = new ArrayList<>();

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

    public static ArrayList<StopItem> getStopItems() {
        return stopItems;
    }

    public static void setStopItems(ArrayList<StopItem> stopItems) {
        StopItem.stopItems = stopItems;
    }

    public static ArrayList<String> getStopNames(){
        ArrayList<String> stopStrings = new ArrayList<String>();
        for(int i = 0; i < stopItems.size(); i++){
            stopStrings.add(stopItems.get(i).getAddress());
        }
        return stopStrings;
    }
}
