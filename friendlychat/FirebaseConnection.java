package com.google.firebase.codelab.friendlychat;

/**
 * Created by johnny on 13/12/2016.
 */
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseConnection {

    private DatabaseReference mFirebaseDatabaseReference;
    private FirebaseUser mFirebaseUser;
    private static final String TAG = "FBConnectionActivity";
    private static final String DB_CHILD = "Pins";
    private DatabaseReference pushRef;

    public FirebaseConnection(DatabaseReference mFirebaseDatabaseReference, FirebaseUser mFirebaseUser) {
        this.mFirebaseDatabaseReference = mFirebaseDatabaseReference;
        this.mFirebaseUser = mFirebaseUser;
        this.pushRef = mFirebaseDatabaseReference.child(mFirebaseUser.getUid());
    }

    public PinHeader createPin(PinData pindata) {
        DatabaseReference keyRef = pushRef.push();
        keyRef.setValue(pindata);
        return pindata.getPinHeader();
    }

    public void deletePin(String key) {
        pushRef.child(key).removeValue();

    }

    public void modifyPin(String key, String field, String change) {
        pushRef.child(key).child(field).setValue(change);
    }


    private static class PinValueEventListener implements ValueEventListener {

        private final PinCallback callback;

        PinValueEventListener(PinCallback callback) {
            this.callback = callback;
        }

        @Override
        public void onDataChange(DataSnapshot dataSnapshot){
            PinData pindata = dataSnapshot.getValue(PinData.class);
            if (pindata != null) {
                this.callback.onPinResult(dataSnapshot.getKey(), pindata);
                System.out.println(pindata);
            } else {
                String message = "data has invalid format: " + dataSnapshot.getValue();
                this.callback.onCancelled(DatabaseError.fromException(new Throwable(message)));
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError){
            this.callback.onCancelled(databaseError);
        }
    }

    public void getPin(String key, PinCallback callback) {
        PinValueEventListener pinListener = new PinValueEventListener(callback);
        pushRef.child(key).addListenerForSingleValueEvent(pinListener);


        }


}

