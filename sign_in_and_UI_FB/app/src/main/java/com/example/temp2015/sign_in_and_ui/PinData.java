package com.example.temp2015.sign_in_and_ui;

/**
 * Created by johnny on 10/12/2016.
 */

public class PinData {
    private latlngCoord coord;
    private String title;
    private String address;
    private String type;
    private String review;

    public PinData(latlngCoord coord, String title, String address, String type, String review) {
        this.coord = coord;
        this.title = title;
        this.address = address;
        this.type = type;
        this.review = review;
    }

    public PinData() {

    }

    public String getTitle() { return title;}
    public String getAddress() { return address;}
    public String getType() { return type;}
    public String getReview() { return review;}
    public latlngCoord getCoord() { return coord;}
    public void setTitle (String title) {this.title = title; }
    public void setAddress (String address) {this.address = address; }
    public void setType (String type) {this.type = type; }
    public void setReview (String review) {this.review = review; }
    public void setCoord(latlngCoord coord) {this.coord = coord;}

}