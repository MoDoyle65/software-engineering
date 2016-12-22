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
    private static DatabaseReference pushRef;
    private static DatabaseReference rootRef;

    public FirebaseConnection(DatabaseReference rootRef, DatabaseReference pushRef) {
        this.pushRef = pushRef;
        this.rootRef = rootRef;
    }

    public void createPin(PinData pindata) {
        DatabaseReference keyRef = pushRef.child("Pins").push();
        keyRef.setValue(pindata);

    }

    public void deletePin(String key) {
        pushRef.child("Pins").child(key).removeValue();

    }

    public void modifyPin(String key, String field, String change) {
        pushRef.child("Pins").child(key).child(field).setValue(change);
    }

    public void setUser(User mUser) {
        pushRef.child("UserProfile").setValue(mUser);

    }
    public void getFriendsPins(PinHeaderCallback callback, GoogleMap googleMap) {
        FriendDownloadListener friendListener = new FriendDownloadListener(callback, googleMap);
        pushRef.child("Friends").addValueEventListener(friendListener);

    }

    public void getUserPinHeaders(PinHeaderCallback callback, GoogleMap googleMap) {
        PinHeaderDownloadListener headListener = new PinHeaderDownloadListener(callback, googleMap);
        pushRef.child("Pins").addValueEventListener(headListener);
    }

    public void removeFriend(String friend){
        pushRef.child("Friends").child(friend).removeValue();

    }
    public void checkFriendisUser(User friend){
        PinValueEventListener pinListener = new PinValueEventListener(pushRef, friend);
        rootRef.addListenerForSingleValueEvent(pinListener);
    }

    //public void getFriendsPinHeaders(FriendsPinHeaderCallback callback, GoogleMap googleMap) {
     //   PinHeaderDownloadListener headListener = new PinHeaderDownloadListener(callback, googleMap);
      //  pushRef.addValueEventListener(headListener);
   // }


    private static class PinValueEventListener implements ValueEventListener {

        private final User friend;
        private final DatabaseReference pushRef;

        PinValueEventListener(DatabaseReference pushRef, User friend) {
            this.pushRef = pushRef;
            this.friend = friend;

        }

        @Override
        public void onDataChange(DataSnapshot dataSnapshot){
            String email = friend.getEmail();
            for (DataSnapshot childSnap: dataSnapshot.getChildren()) {
                User friendFB = childSnap.child("UserProfile").getValue(User.class);
                if (friendFB.getEmail().equals(email)) {
                    pushRef.child("Friends").child(friendFB.getUid()).setValue(friendFB);
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
            System.out.println(dataSnapshot.getValue());
            for (DataSnapshot childSnap: dataSnapshot.getChildren()) {
                PinData pindata = childSnap.getValue(PinData.class);
                this.callback.onPinHeaderResult(pindata, googleMap);

            }
        }
        @Override
        public void onCancelled(DatabaseError databaseError){
            this.callback.onCancelled(databaseError);
        }

    }

    private static class FriendDownloadListener implements ValueEventListener {

        private final PinHeaderCallback callback;
        private final GoogleMap googleMap;

        FriendDownloadListener(PinHeaderCallback callback, GoogleMap googleMap) {
            this.callback = callback;
            this.googleMap = googleMap;
        }

        @Override
        public void onDataChange(DataSnapshot dataSnapshot){
            for (DataSnapshot childSnap: dataSnapshot.getChildren()) {
                User myFriend = childSnap.getValue(User.class);
                Log.d("User:", myFriend.getEmail());
                System.out.println(myFriend.getEmail());
                PinHeaderDownloadListener pinfriendListener = new PinHeaderDownloadListener(callback, googleMap);
                rootRef.child(myFriend.getUid()).child("Pins").addListenerForSingleValueEvent(pinfriendListener);

            }
        }
        @Override
        public void onCancelled(DatabaseError databaseError){
            this.callback.onCancelled(databaseError);
        }

    }
}














