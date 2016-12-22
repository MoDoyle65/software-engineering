package com.example.temp2015.sign_in_and_ui;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

/**
 * Created by johnny on 13/12/2016.
 */

public interface PinCallback {

    public void onPinResult(String email);
    public void onCancelled(DatabaseError databaseError);

}