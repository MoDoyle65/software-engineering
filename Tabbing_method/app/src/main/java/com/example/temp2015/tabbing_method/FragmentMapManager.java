package com.example.temp2015.tabbing_method;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by temp2015 on 28-Nov-16.
 */

public class FragmentMapManager extends Fragment {
    MapView mMapView;
    private GoogleMap googleMap;
    private Linker link;
    private FirebaseConnection mFirebaseConnection1, mFirebaseConnection2;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mFirebaseRef;
    private DatabaseReference pushRef;
    private getPinHeaderCallback pincb = new getPinHeaderCallback();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mapLayout = inflater.inflate(R.layout.fragment_map_manager, container, false);
        link = (Linker) getActivity();
        String uid = link.getString();
        mMapView = (MapView) mapLayout.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume(); // needed to get the map to display immediately
        mFirebaseRef = FirebaseDatabase.getInstance().getReference();
        pushRef = mFirebaseRef.child(uid);
        mFirebaseConnection1 = new FirebaseConnection(mFirebaseRef,pushRef);
        mFirebaseConnection2 = new FirebaseConnection(mFirebaseRef,mFirebaseRef.child(uid));

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
                googleMap.clear();
                mFirebaseConnection1.getUserPinHeaders(pincb, googleMap);
                mFirebaseConnection2.getFriendsPins(pincb, googleMap);

            }
        });

        return mapLayout;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
                googleMap.clear();
                mFirebaseConnection1.getUserPinHeaders(pincb, googleMap);
                mFirebaseConnection2.getFriendsPins(pincb, googleMap);
            }
        });
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();

        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}