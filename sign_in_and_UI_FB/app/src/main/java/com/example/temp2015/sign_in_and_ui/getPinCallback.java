package com.example.temp2015.sign_in_and_ui;

import com.google.firebase.database.DatabaseError;

import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by johnny on 13/12/2016.
 */

public class getPinCallback implements PinCallback {

    public void onPinResult(String email) {

    }

    public void onCancelled(DatabaseError databaseError){
        System.out.println("Error encountered");
    };

}

