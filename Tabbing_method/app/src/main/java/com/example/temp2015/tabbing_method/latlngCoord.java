package com.example.temp2015.tabbing_method;

/**
 * Created by temp2015 on 22-Dec-16.
 */

public class latlngCoord {
    private double lat;
    private double lng;

    public latlngCoord(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public latlngCoord() {

    }

    public void setLat(double lat) {this.lat = lat;}
    public void setLng(double lng) {this.lng = lng;}
    public double getLat() {return lat;}
    public double getLng() {return lng;}

}