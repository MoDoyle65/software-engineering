package com.google.firebase.codelab.friendlychat;

import com.google.firebase.database.DatabaseError;

/**
 * Created by johnny on 13/12/2016.
 */

public interface PinCallback {

    public void onPinResult(String key, PinData pindata);

    public void onCancelled(DatabaseError databaseError);

}