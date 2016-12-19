package com.example.temp2015.sign_in_and_ui;

/**
 * Created by johnny on 17/12/2016.
 */
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class FriendActivity extends AppCompatActivity {

    public static class FriendViewHolder extends RecyclerView.ViewHolder {
        public TextView emailTextView;
        public TextView nameTextView;
        public CircleImageView friendImageView;

        public FriendViewHolder(View v) {
            super(v);
            emailTextView = (TextView) itemView.findViewById(R.id.emailTextView);
            nameTextView = (TextView) itemView.findViewById(R.id.nameTextView);
            friendImageView = (CircleImageView) itemView.findViewById(R.id.friendImageView);
        }
    }

    private static final String TAG = "FriendActivity";
    public static final int DEFAULT_EMAIL_LENGTH_LIMIT = 100;
    //public static final String ANONYMOUS = "anonymous";
    //private static final String MESSAGE_SENT_EVENT = "message_sent";
    //private String mUsername;
    //private String mPhotoUrl;
    //private String key;
    private SharedPreferences mSharedPreferences;

    private Button mAddButton;
    private RecyclerView mEmailRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private ProgressBar mProgressBar;
    private EditText mEmailEditText;
    private DatabaseReference mFirebaseDatabaseReference;
    private FirebaseConnection mFirebaseConnection;
    private FirebaseRecyclerAdapter<User, FriendViewHolder> mFirebaseAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendlist);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        // Set default username is anonymous.
        Bundle extras = getIntent().getExtras();
        String uid = extras.getString("uid");
        // Initialize ProgressBar and RecyclerView.
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mEmailRecyclerView = (RecyclerView) findViewById(R.id.emailRecyclerView);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setStackFromEnd(false);
        mEmailRecyclerView.setLayoutManager(mLinearLayoutManager);
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mFirebaseConnection = new FirebaseConnection(mFirebaseDatabaseReference, mFirebaseDatabaseReference.child(uid));


        mFirebaseAdapter = new FirebaseRecyclerAdapter<User,
                FriendViewHolder>(
                User.class,
                R.layout.item_friend,
                FriendViewHolder.class,
                mFirebaseDatabaseReference.child(uid).child("Friends")) {

            @Override
            protected void populateViewHolder(FriendViewHolder viewHolder,
                                              final User friend, int position) {
                mProgressBar.setVisibility(ProgressBar.INVISIBLE);
                viewHolder.emailTextView.setText(friend.getEmail());
                viewHolder.nameTextView.setText(friend.getName());
                viewHolder.friendImageView.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        //v.getId() will give you the image id
                        mFirebaseConnection.removeFriend(friend.getUid());
                    }
                });

                viewHolder.friendImageView
                        .setImageDrawable(ContextCompat
                                .getDrawable(FriendActivity.this,
                                            R.drawable.ic_account_circle_black_36dp));

            }

        };
        mFirebaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                int emailCount = mFirebaseAdapter.getItemCount();

            }
        });

        mEmailRecyclerView.setLayoutManager(mLinearLayoutManager);
        mEmailRecyclerView.setAdapter(mFirebaseAdapter);

        mEmailEditText = (EditText) findViewById(R.id.emailEditText);
        mEmailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    mAddButton.setEnabled(true);
                } else {
                    mAddButton.setEnabled(false);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });


        mAddButton = (Button) findViewById(R.id.addButton);
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User friend = new
                        User("dummy",mEmailEditText.getText().toString(),"dummy");
                Log.d("friend", friend.getEmail());
                mFirebaseConnection.checkFriendisUser(friend);
                mEmailEditText.setText("");
            }
        });


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




}








