package com.example.temp2015.sign_in_and_ui;

/**
 * Created by temp2015 on 15-Dec-16.
 */
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

public class PlacePickerActivity extends AppCompatActivity
       {

    private PlacePicker.IntentBuilder builder;
    private PlacesAutoCompleteAdapter mPlacesAdapter;
    private Button pickerBtn;
    private AutoCompleteTextView myLocation;
    private static final int PLACE_PICKER_FLAG = 1;

    //    private static final LatLngBounds BOUNDS_GREATER_SYDNEY = new LatLngBounds(
//            new LatLng(-34.041458, 150.790100), new LatLng(-33.682247, 151.383362));
    protected GoogleApiClient mGoogleApiClientPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("abc"," we are here.................");
        super.onCreate(savedInstanceState);
        mGoogleApiClientPlace = new GoogleApiClient.Builder(this)
                .addApi(Places.GEO_DATA_API)
                .build();
        Log.d("abcdef"," we are here........");
        setContentView(R.layout.activity_placepicker);
        builder = new PlacePicker.IntentBuilder();
        myLocation = (AutoCompleteTextView) findViewById(R.id.myLocation);
        Log.d("abc"," we are here.................");
//        mPlacesAdapter = new PlacesAutoCompleteAdapter(this, android.R.layout.simple_list_item_1,
//                mGoogleApiClientPlace, BOUNDS_GREATER_SYDNEY, null);
        myLocation.setOnItemClickListener(mAutocompleteClickListener);
        myLocation.setAdapter(mPlacesAdapter);
        pickerBtn = (Button) findViewById(R.id.pickerBtn);

        Log.d("abcd"," we are here");
        pickerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    builder = new PlacePicker.IntentBuilder();
                    Intent intent = builder.build(PlacePickerActivity.this);
                    // Start the Intent by requesting a result, identified by a request code.
                    startActivityForResult(intent, PLACE_PICKER_FLAG);

                } catch (GooglePlayServicesRepairableException e) {
                    GooglePlayServicesUtil
                            .getErrorDialog(e.getConnectionStatusCode(), PlacePickerActivity.this, 0);
                } catch (GooglePlayServicesNotAvailableException e) {
                    Toast.makeText(PlacePickerActivity.this, "Google Play Services is not available.",
                            Toast.LENGTH_LONG)
                            .show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PLACE_PICKER_FLAG:
                    Place place = PlacePicker.getPlace(data, this);
                    myLocation.setText(place.getName() + ", " + place.getAddress() + "," + place.getLatLng());
                    LatLng newLatLong = place.getLatLng();

                    break;
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClientPlace.connect();
    }

    @Override
    protected void onStop() {
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
}