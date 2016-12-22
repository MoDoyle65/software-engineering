package com.example.temp2015.sign_in_and_ui;

import android.app.Activity;
import android.app.Fragment;
//import android.app.FragmentManager;
//import android.app.FragmentTransaction;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;

//import android.app.FragmentManager;
//import android.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.appinvite.AppInvite;
import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.google.firebase.appindexing.Action;
import com.google.firebase.appindexing.FirebaseAppIndex;
import com.google.firebase.appindexing.FirebaseUserActions;
import com.google.firebase.appindexing.Indexable;
import com.google.firebase.appindexing.builders.Indexables;
import com.google.firebase.appindexing.builders.PersonBuilder;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;



    public class MainActivity extends AppCompatActivity
            implements GoogleApiClient.OnConnectionFailedListener {


    private static final String TAG = "MainActivity";
    public static final String MESSAGES_CHILD = "Pins";
    private static final int REQUEST_INVITE = 1;
    public static final int DEFAULT_MSG_LENGTH_LIMIT = 10;
    public static final String ANONYMOUS = "anonymous";
    private static final String MESSAGE_SENT_EVENT = "message_sent";
    private String mUsername;
    private String mPhotoUrl;
    private SharedPreferences mSharedPreferences;
    private GoogleApiClient mGoogleApiClient;
    //private GoogleApiClient mGoogleApiClientPlace;


    private Button mSendButton;
    private RecyclerView mMessageRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private ProgressBar mProgressBar;
    private EditText mMessageEditText;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private FirebaseConnection mFirebaseConnection;
    private DatabaseReference mFirebaseDatabaseReference;
    private User mUser;

    private PlacePicker.IntentBuilder builder;
    private PlacesAutoCompleteAdapter mPlacesAdapter;
    private Button pickerBtn;
    private AutoCompleteTextView myLocation;
    private static final int PLACE_PICKER_FLAG = 1;
    private DatabaseReference mFirebaseRef;
    public String name_field;
    public String address_field;
    public String review_field;

    // Firebase instance variables


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentUserManagment fragmentUserManagment = new FragmentUserManagment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().replace(android.R.id.content, fragmentUserManagment);
        fragmentTransaction.commit();
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        // Set default username is anonymous.
        mUsername = ANONYMOUS;

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build();

        // Initialize ProgressBar and RecyclerView.

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseRef = FirebaseDatabase.getInstance().getReference();

        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        if (mFirebaseUser == null) {
            startActivity(new Intent(this, SignInActivity.class));
            finish();
            return;
        } else {
            mUsername = mFirebaseUser.getDisplayName();
            mUser = new User(mFirebaseUser.getDisplayName(),mFirebaseUser.getEmail(), mFirebaseUser.getUid());
            mFirebaseConnection = new FirebaseConnection(mFirebaseRef, mFirebaseRef.child(mFirebaseUser.getUid()));
            mFirebaseConnection.setUser(mUser);
            if (mFirebaseUser.getPhotoUrl() != null) {
                mPhotoUrl = mFirebaseUser.getPhotoUrl().toString();
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in.
        // TODO: Add code to check if user is signed in.
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out_menu:
                mFirebaseAuth.signOut();
                Auth.GoogleSignInApi.signOut(mGoogleApiClient);
                mUsername = ANONYMOUS;
                startActivity(new Intent(this, SignInActivity.class));
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }
*/
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }


    public void notification(View view) {
        FragmentNotificationManager fragmentNotificationManager = new FragmentNotificationManager();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().replace(android.R.id.content,fragmentNotificationManager);
        fragmentTransaction.commit();
    }

    public void review(View view) {
        FragmentReviewManager fragmentReviewManager =new FragmentReviewManager();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().replace(android.R.id.content,fragmentReviewManager);
        fragmentTransaction.commit();
    }

    public void friend(View view) {
        //FragmentFriendManager fragmentFriendManager = new FragmentFriendManager();
        //FragmentManager fragmentManager = getFragmentManager();
        //FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().replace(android.R.id.content,fragmentFriendManager);
        //fragmentTransaction.commit();
        String uid = mFirebaseUser.getUid();
        //mFirebaseConnection = new FirebaseConnection(mFirebaseDatabaseReference,mFirebaseUser);
        Intent i = new Intent(this, FriendActivity.class);
        i.putExtra("uid", uid);
        startActivity(i);

    }

    public void user(View view) {
        FragmentUserManagment fragmentUserManagment =new FragmentUserManagment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().replace(android.R.id.content,fragmentUserManagment);
        fragmentTransaction.commit();
    }

    public void map(View view) {

        String url = "https://friendlychat-8f0a0.firebaseio.com/";
        String uid = mFirebaseUser.getUid();

        //mFirebaseConnection = new FirebaseConnection(mFirebaseDatabaseReference,mFirebaseUser);
        Intent i = new Intent(this, MapActivity.class);
        i.putExtra("url", url);
        i.putExtra("uid", uid);
        startActivity(i);

    }

    public void add_review(View view) {

        EditText name = (EditText)findViewById(R.id.name_of_location);
        name_field =name.getText().toString();

        EditText address = (EditText)findViewById(R.id.Address_of_location);
        address_field =address.getText().toString();

        EditText new_review = (EditText)findViewById(R.id.name_of_location);
        review_field =new_review.getText().toString();


        FragmentMapManager fragmentMapManager = new FragmentMapManager();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().replace(android.R.id.content,fragmentMapManager);
        fragmentTransaction.commit();

    }
        public void sign_out(View view) {
            mFirebaseAuth.signOut();
            Auth.GoogleSignInApi.signOut(mGoogleApiClient);
            mUsername = ANONYMOUS;
            startActivity(new Intent(this, SignInActivity.class));
        }


        public void create_review(View view) {
            //FragmentPlacePickerManager fragmentpPlacePicker  = new FragmentPlacePickerManager();
            //FragmentManager fragmentManager = getFragmentManager();
            //FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().replace(android.R.id.content,fragmentpPlacePicker);
            //fragmentTransaction.commit();
            System.out.println("hello");
            Log.d("systemout_println hello", "addf");
            //startActivity(new Intent(this, PlacePickerActivity.class));
            //String k = mFirebaseDatabaseReference.child(mFirebaseUser.getUid()).child("Pins").toString();
            //System.out.println(k);


            String url = "https://friendlychat-8f0a0.firebaseio.com/";
            String uid = mFirebaseUser.getUid();

            //mFirebaseConnection = new FirebaseConnection(mFirebaseDatabaseReference,mFirebaseUser);
            Intent i = new Intent(this, PlacePickerActivityTest.class);
            i.putExtra("url", url);
            i.putExtra("uid", uid);
            startActivity(i);


        }


    public void new_review(View view) {
        FragmentCreateReviewManager fragmentCreateReview  = new FragmentCreateReviewManager();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().replace(android.R.id.content,fragmentCreateReview);
        fragmentTransaction.commit();


    }

}
