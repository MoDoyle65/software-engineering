package com.example.temp2015.tabbing_method;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.content.Intent;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * Created by temp2015 on 28-Nov-16.
 */

public class FragmentReviewManager extends Fragment {
    private PlacePicker.IntentBuilder builder;
    private PlacesAutoCompleteAdapter mPlacesAdapter;
    private Button pickerBtn;
    private AutoCompleteTextView myLocation;
    private static final int PLACE_PICKER_FLAG = 1;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mFirebaseRef;
    private DatabaseReference pushRef;
    private GoogleApiClient mGoogleApiClientPlace;
    private RelativeLayout flayout;
    private FirebaseConnection mFirebaseConnection;
    private Linker link;
    private Button submitReview;
    private int REVIEW_LIMIT = 40;

    private EditText reviewCreation;
    private Place place;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        flayout = (RelativeLayout) inflater.inflate(R.layout.fragment_review_manager, container, false);
        mFirebaseRef = FirebaseDatabase.getInstance().getReference();
        link = (Linker) getActivity();
        String uid = link.getString();
        reviewCreation = (EditText) flayout.findViewById(R.id.Review_of_location);
        reviewCreation.setEnabled(false);

        submitReview = (Button) flayout.findViewById((R.id.ReviewCreation));
        submitReview.setEnabled(false);

        mFirebaseConnection = new FirebaseConnection(getActivity(), mFirebaseRef, uid);

        mGoogleApiClientPlace = new GoogleApiClient.Builder(getActivity())
                .addApi(Places.GEO_DATA_API)
                .build();

        builder = new PlacePicker.IntentBuilder();
        myLocation = (AutoCompleteTextView) flayout.findViewById(R.id.myLocation);
        myLocation.setEnabled(false);
        myLocation.setOnItemClickListener(mAutocompleteClickListener);
        myLocation.setOnKeyListener(null);
        pickerBtn = (Button) flayout.findViewById(R.id.pickerBtn);
        pickerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Toast toast = Toast.makeText(getActivity(), "Loading, please wait...", Toast.LENGTH_SHORT);
                    toast.show();
                    builder = new PlacePicker.IntentBuilder();
                    Intent intent = builder.build(getActivity());
                    // Start the Intent by requesting a result, identified by a request code.
                    startActivityForResult(intent, PLACE_PICKER_FLAG);

                } catch (GooglePlayServicesRepairableException e) {
                    GooglePlayServicesUtil
                            .getErrorDialog(e.getConnectionStatusCode(), getActivity(), 0);
                } catch (GooglePlayServicesNotAvailableException e) {
                    Toast.makeText(getActivity(), "Google Play Services is not available.",
                            Toast.LENGTH_LONG)
                            .show();
                }

            }
        });
        reviewCreation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    submitReview.setEnabled(true);
                } else {
                    submitReview.setEnabled(false);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        submitReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String review = reviewCreation.getEditableText().toString();

                if(!checkReviewUnderLimit(review , REVIEW_LIMIT)) {

                    Toast toast = Toast.makeText(getActivity(), "Review too long!", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    PinData pdata = createReview(review);
                    mFirebaseConnection.createPin(pdata);
                    myLocation.setText("");
                    reviewCreation.setText("");

                    Toast toast = Toast.makeText(getActivity(), "Review submitted", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
        return flayout;
    }

   public PinData createReview(String review) {
        latlngCoord latlng = new latlngCoord(place.getLatLng().latitude, place.getLatLng().longitude);
        return new PinData(latlng, place.getName().toString(), place.getAddress().toString(), place.getPlaceTypes().toString(),review);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case PLACE_PICKER_FLAG:
                    place = PlacePicker.getPlace(data, getActivity());
                    myLocation.setText(place.getName() + "\n " + place.getAddress() + "");
                    reviewCreation.setEnabled(true);
                    break;
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClientPlace.connect();
    }

    @Override
    public void onStop() {
        mGoogleApiClientPlace.disconnect();
        super.onStop();
    }

    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final PlacesAutoCompleteAdapter.PlaceAutocomplete item = mPlacesAdapter.getItem(position);
            final String placeId = String.valueOf(item.placeId);
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClientPlace, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
        }
    };
    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                Log.e("place", "Place query did not complete. Error: " +
                        places.getStatus().toString());
                return;
            }
            // Selecting the first object buffer.
            final Place place = places.get(0);
        }
    };

    public boolean checkReviewUnderLimit(String review, int limit) {
       return (review.length() <= limit);
    }

}

