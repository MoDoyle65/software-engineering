package com.example.temp2015.tabbing_method;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, Linker {
    private TabHost tabHost;
    private TabHost host;
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
    private String uid;
    public String name_field;
    public String address_field;
    public String review_field;
    private GoogleMap googleMap;
    private Map<String, Marker> markerMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUsername = ANONYMOUS;
        Map<String, Marker> markerMap = new HashMap<String, Marker>();
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
            uid = mFirebaseUser.getUid();
            mUser = new User(mFirebaseUser.getDisplayName(),mFirebaseUser.getEmail(), mFirebaseUser.getUid());
            mFirebaseConnection = new FirebaseConnection(this, mFirebaseRef, uid);
            mFirebaseConnection.setUser(mUser);

            Log.d("uid",uid);
            if (mFirebaseUser.getPhotoUrl() != null) {
                mPhotoUrl = mFirebaseUser.getPhotoUrl().toString();
            }
        }

        setContentView(R.layout.activity_main);

        host = (TabHost) findViewById(R.id.tabHost);
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("MAP");
        spec.setContent(R.id.tab1);
        spec.setIndicator("MAP");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("Notify");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Notify");
        host.addTab(spec);

        //Tab 3
        spec = host.newTabSpec("Review");
        spec.setContent(R.id.tab3);
        spec.setIndicator("Review");
        host.addTab(spec);

        // Tab 4
        spec = host.newTabSpec("Friends");
        spec.setContent(R.id.tab4);
        spec.setIndicator("Friends");
        host.addTab(spec);



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.signout:
                mFirebaseAuth.signOut();
                Auth.GoogleSignInApi.signOut(mGoogleApiClient);
                mUsername = ANONYMOUS;
                startActivity(new Intent(this, SignInActivity.class));
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }


    public void setString(String uid) {
        this.uid = uid;

    }
    public String getString() {
        Log.d("String get", this.uid);
        return this.uid;
    }


}

