package com.example.temp2015.sign_in_and_ui;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.android.gms.maps.GoogleMap;
/**
 * Created by johnny on 16/12/2016.
 */

public interface PinHeaderCallback {

    public void onPinHeaderResult(PinData pindata, GoogleMap googleMap);
    public void onCancelled(DatabaseError databaseError);
}
