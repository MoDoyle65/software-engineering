package com.google.firebase.codelab.friendlychat;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by johnny on 14/12/2016.
 */

public class FirebaseFriendManagement {

    private DatabaseReference mFirebaseDatabaseReference;
    private FirebaseUser mFirebaseUser;
    private static final String TAG = "FBConnectionActivity";
    //private DatabaseReference pushRef;

    public FirebaseFriendManagement(DatabaseReference mFirebaseDatabaseReference, FirebaseUser mFirebaseUser) {
        this.mFirebaseDatabaseReference = mFirebaseDatabaseReference;
        this.mFirebaseUser = mFirebaseUser;
    }

    public boolean searchFriend(String email) {
        return true;

    }

    private static class UsersListener implements ValueEventListener {

        @Override
        public void onDataChange(DataSnapshot dataSnapshot){
            for (DataSnapshot childSnap: dataSnapshot.getChildren()) {
                String key = childSnap.getKey();
                String lat = childSnap.child("pinHeader").child("coord").child("lat").getValue().toString();
                String lng = childSnap.child("pinHeader").child("coord").child("lng").getValue().toString();
                double latd = Double.parseDouble(lat);
                double lngd = Double.parseDouble(lng);
                //latlngCoord cod = new latlngCoord(latd, lngd);
                System.out.println(latd + ' ' + lngd);
                //googleMap.addMarker (new MarkerOptions().position(cod));
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError){

        }
    }

}

