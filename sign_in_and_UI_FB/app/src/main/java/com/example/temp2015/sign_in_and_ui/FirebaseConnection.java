package com.example.temp2015.sign_in_and_ui;

/**
 * Created by johnny on 13/12/2016.
 */
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseConnection {

    private static final String TAG = "FBConnectionActivity";
    private DatabaseReference pushRef;
    private DatabaseReference rootRef;

    public FirebaseConnection(DatabaseReference rootRef, DatabaseReference pushRef) {
        this.pushRef = pushRef;
        this.rootRef = rootRef;
    }

    public void createPin(PinData pindata) {
        DatabaseReference keyRef = pushRef.push();
        keyRef.setValue(pindata);

    }

    public void deletePin(String key) {
        pushRef.child(key).removeValue();

    }

    public void modifyPin(String key, String field, String change) {
        pushRef.child(key).child(field).setValue(change);
    }

    public void setEmail(String email) {
        pushRef.child("Email").setValue(email);

    }


    public void getUserPinHeaders(PinHeaderCallback callback, GoogleMap googleMap) {
        PinHeaderDownloadListener headListener = new PinHeaderDownloadListener(callback, googleMap);
        pushRef.addValueEventListener(headListener);
    }

    public void checkFriendisUser(Friend friend){
        PinValueEventListener pinListener = new PinValueEventListener(pushRef, friend);
        rootRef.addListenerForSingleValueEvent(pinListener);
    }


    private static class PinValueEventListener implements ValueEventListener {

        private final Friend friend;
        private final DatabaseReference pushRef;

        PinValueEventListener(DatabaseReference pushRef, Friend friend) {
            this.pushRef = pushRef;
            this.friend = friend;

        }

        @Override
        public void onDataChange(DataSnapshot dataSnapshot){
            String email = friend.getEmail();
            for (DataSnapshot childSnap: dataSnapshot.getChildren()) {
                Log.d("snap", childSnap.getKey());
                Log.d("friend", childSnap.child("Email").getValue().toString());
                if (childSnap.child("Email").getValue().toString().equals(email)) {
                    Friend friend = new Friend(email);
                    pushRef.child("Friends").push().setValue(friend);
                    break;
                }
                else {
                    System.out.println("Friend not in database");
                }

            }

        }
        @Override
        public void onCancelled(DatabaseError databaseError){
            System.out.println("Error occured");
        }
    }

    private static class PinHeaderDownloadListener implements ValueEventListener {

        private final PinHeaderCallback callback;
        private final GoogleMap googleMap;

        PinHeaderDownloadListener(PinHeaderCallback callback, GoogleMap googleMap) {
            this.callback = callback;
            this.googleMap = googleMap;
        }

        @Override
        public void onDataChange(DataSnapshot dataSnapshot){
            for (DataSnapshot childSnap: dataSnapshot.getChildren()) {
                this.callback.onPinHeaderResult(childSnap, googleMap);

            }
        }
        @Override
        public void onCancelled(DatabaseError databaseError){
            this.callback.onCancelled(databaseError);
        }

    }
}














