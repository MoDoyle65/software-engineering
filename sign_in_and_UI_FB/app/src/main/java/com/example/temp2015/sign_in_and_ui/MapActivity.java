package com.example.temp2015.sign_in_and_ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private FirebaseConnection mFirebaseConnection;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mFirebaseRef;
    private DatabaseReference pushRef;
    private getPinHeaderCallback pincb = new getPinHeaderCallback();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Bundle extras = getIntent().getExtras();
        String uid = extras.getString("uid");
        String url = extras.getString("url");
        mFirebaseRef = FirebaseDatabase.getInstance().getReference();
        pushRef = mFirebaseRef.child(uid).child("Pins");
        mFirebaseConnection = new FirebaseConnection(mFirebaseRef,pushRef);
        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map when it's available.
     * The API invokes this callback when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user receives a prompt to install
     * Play services inside the SupportMapFragment. The API invokes this method after the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Move the map's camera to UCD Dublin.
        LatLng ucd = new LatLng(53.3053439,-6.2206539);

        mFirebaseConnection.getUserPinHeaders(pincb, googleMap);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(ucd));

    }
}






