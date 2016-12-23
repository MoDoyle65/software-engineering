package com.example.temp2015.tabbing_method;

import android.app.Fragment;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;

public class FragmentFriendManager extends Fragment {
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
    private Button mAddButton;
    private RecyclerView mEmailRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private ProgressBar mProgressBar;
    private EditText mEmailEditText;
    private DatabaseReference mFirebaseDatabaseReference;
    private FirebaseConnection mFirebaseConnection;
    private FirebaseRecyclerAdapter<User, FriendViewHolder> mFirebaseAdapter;
    private Linker link;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View friendLayout = inflater.inflate(R.layout.fragment_friend_manager,container,false);
        link = (Linker) getActivity();
        String uid = link.getString();
        mProgressBar = (ProgressBar) friendLayout.findViewById(R.id.progressBar);
        mEmailRecyclerView = (RecyclerView) friendLayout.findViewById(R.id.emailRecyclerView);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setStackFromEnd(false);
        mEmailRecyclerView.setLayoutManager(mLinearLayoutManager);
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mFirebaseConnection = new FirebaseConnection(getActivity(), mFirebaseDatabaseReference, uid);
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
                        mFirebaseConnection.removeFriend(friend.getUid());
                    }
                });

                viewHolder.friendImageView
                        .setImageDrawable(ContextCompat
                                .getDrawable(getActivity(),
                                        R.drawable.ic_account_circle_black_36dp));

            }

        };
        mFirebaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);

            }
        });

        mEmailRecyclerView.setLayoutManager(mLinearLayoutManager);
        mEmailRecyclerView.setAdapter(mFirebaseAdapter);

        mEmailEditText = (EditText) friendLayout.findViewById(R.id.emailEditText);
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

        mAddButton = (Button) friendLayout.findViewById(R.id.addButton);
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

        return friendLayout;
    }
    @Override
    public void onStart() {
        super.onStart();
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
