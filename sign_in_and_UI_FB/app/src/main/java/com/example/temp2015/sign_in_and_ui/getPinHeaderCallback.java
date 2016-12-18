package com.example.temp2015.sign_in_and_ui;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.android.gms.maps.GoogleMap;

/**
 * Created by johnny on 16/12/2016.
 */

public class getPinHeaderCallback implements PinHeaderCallback {

    public void onPinHeaderResult(PinData pindata, GoogleMap googleMap) {


        double lat = pindata.getCoord().getLat();
        double lng = pindata.getCoord().getLng();
        LatLng newMarker = new LatLng(lat,lng);

        googleMap.addMarker(new MarkerOptions().position(newMarker)
                .title(pindata.getReview()));

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(newMarker));
    }

    public void onCancelled(DatabaseError databaseError) {
        System.out.println("Error encountered");

    }
}
