package com.example.temp2015.tabbing_method;

/**
 * Created by johnny on 13/12/2016.
 */
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FirebaseConnection {

    private static final String TAG = "FBConnectionActivity";
    private static DatabaseReference pushRef;
    private static DatabaseReference rootRef;
    private static String uid;
    private static Context context;


    public FirebaseConnection(Context context, DatabaseReference rootRef, String uid) {
        this.uid = uid;
        this.rootRef = rootRef;
        this.pushRef = rootRef.child(uid);
        this.context = context;
    }

    public void createPin(PinData pindata) {
        DatabaseReference keyRef = pushRef.child("Pins").push();
        keyRef.setValue(pindata);

    }

    public void setUser(User mUser) {
        pushRef.child("UserProfile").setValue(mUser);
    }

    public void removeFriend(String friend){
        pushRef.child("Friends").child(friend).removeValue();

    }
    public void checkFriendisUser(User friend){
        FriendListener friendListener = new FriendListener(pushRef, friend);
        rootRef.addListenerForSingleValueEvent(friendListener);
    }


    private static class FriendListener implements ValueEventListener {

        private final User friend;
        private final DatabaseReference pushRef;
        private boolean isFriend = false;

        FriendListener(DatabaseReference pushRef, User friend) {
            this.pushRef = pushRef;
            this.friend = friend;

        }

        @Override
        public void onDataChange(DataSnapshot dataSnapshot){
            String email = friend.getEmail();
            for (DataSnapshot childSnap: dataSnapshot.getChildren()) {
                User friendFB = childSnap.child("UserProfile").getValue(User.class);
                Log.d("friend", friendFB.getEmail());
                if (friendFB.getEmail().equals(email)) {
                    pushRef.child("Friends").child(friendFB.getUid()).setValue(friendFB);
                    isFriend = true;
                    break;
                }
            //TODO: What if user email entered?
            }
            if (isFriend) { Toast.makeText(context, "Friend added", Toast.LENGTH_SHORT).show(); }
            else { Toast.makeText(context, "Friend not in Database", Toast.LENGTH_SHORT).show();}
        }
        @Override
        public void onCancelled(DatabaseError databaseError){
            Toast.makeText(context, "Error Occurred", Toast.LENGTH_SHORT).show();
        }
    }

    public void getPins(PinHeaderCallback callback, GoogleMap googleMap) {
        PinDownloadListener pinListener = new PinDownloadListener(callback, googleMap);
        pushRef.child("Friends").addListenerForSingleValueEvent(pinListener);


    }

    private static class PinDownloadListener implements ValueEventListener {

        private final PinHeaderCallback callback;
        private final GoogleMap googleMap;

        PinDownloadListener(PinHeaderCallback callback, GoogleMap googleMap) {
            this.callback = callback;
            this.googleMap = googleMap;
        }

        @Override
        public void onDataChange(DataSnapshot dataSnapshot){
            for (DataSnapshot childSnap: dataSnapshot.getChildren()) {
                User myFriend = childSnap.getValue(User.class);
                String uidFriend = myFriend.getUid();
                UserPinDownloadListener pinFriendListener = new UserPinDownloadListener(uidFriend,callback, googleMap);
                rootRef.child(uidFriend).child("Pins").addValueEventListener(pinFriendListener);

            }
            UserPinDownloadListener myPinsListener = new UserPinDownloadListener(uid, callback, googleMap);
            pushRef.child("Pins").addValueEventListener(myPinsListener);
        }
        @Override
        public void onCancelled(DatabaseError databaseError){
            this.callback.onCancelled(databaseError);
        }

    }

    private static class UserPinDownloadListener implements ValueEventListener {

        private final PinHeaderCallback callback;
        private final GoogleMap googleMap;


        UserPinDownloadListener(String uid, PinHeaderCallback callback, GoogleMap googleMap) {
            this.callback = callback;
            this.googleMap = googleMap;
        }

        @Override
        public void onDataChange(DataSnapshot dataSnapshot){
            ArrayList<PinData> pinDataList = new ArrayList<PinData>();
            for (DataSnapshot childSnap: dataSnapshot.getChildren()) {
                PinData pindata = childSnap.getValue(PinData.class);
                pinDataList.add(pindata);
            }

            this.callback.onPinHeaderResult(uid, pinDataList, googleMap);
        }
        @Override
        public void onCancelled(DatabaseError databaseError){
            this.callback.onCancelled(databaseError);
        }

    }
    
}