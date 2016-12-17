package com.google.firebase.codelab.friendlychat;


/**
 * Created by johnny on 14/12/2016.
 */

public class PinHeader {
    private String key;
    private latlngCoord latlng;

    public PinHeader(String key, latlngCoord latlng) {
        this.key = key;
        this.latlng = latlng;
    }
    public PinHeader() {

    }

    public void setKey(String key) {
        this.key = key;
    }
    public void setLatLng(latlngCoord latlng) {
        this.latlng = latlng;
    }
    public String getkey() {
        return key;
    }
    public latlngCoord getCoord() {
        return latlng;
    }
}