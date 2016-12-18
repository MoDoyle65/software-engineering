package com.example.temp2015.sign_in_and_ui;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.android.gms.maps.GoogleMap;

/**
 * Created by johnny on 16/12/2016.
 */

public class getPinHeaderCallback implements PinHeaderCallback {

    public void onPinHeaderResult(DataSnapshot childSnap, GoogleMap googleMap) {
        String key = childSnap.getKey();
        String lat = childSnap.child("pinHeader").child("coord").child("lat").getValue().toString();
        String lng = childSnap.child("pinHeader").child("coord").child("lng").getValue().toString();
        double latd = Double.parseDouble(lat);
        double lngd = Double.parseDouble(lng);
        LatLng newMarker = new LatLng(latd,lngd);
        googleMap.addMarker(new MarkerOptions().position(newMarker)
                .title(key));
  //      LatLng dublin = new LatLng(53.350, -6.266);
        LatLngBounds dublin = new LatLngBounds(new LatLng(53.300000, -6.206000), new LatLng(53.400000, -6.306000));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(dublin,20));
    }

    public void onCancelled(DatabaseError databaseError) {
        System.out.println("Error encountered");

    }
}
