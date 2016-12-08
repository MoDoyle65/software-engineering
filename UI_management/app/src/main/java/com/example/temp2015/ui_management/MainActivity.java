package com.example.temp2015.ui_management;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

/**
 * Created by temp2015 on 28-Nov-16.
 */
public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentUserManagment fragmentUserManagment = new FragmentUserManagment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().replace(android.R.id.content,fragmentUserManagment);
        fragmentTransaction.commit();
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
        FragmentFriendManager fragmentFriendManager = new FragmentFriendManager();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().replace(android.R.id.content,fragmentFriendManager);
        fragmentTransaction.commit();
    }

    public void user(View view) {
        FragmentUserManagment fragmentUserManagment =new FragmentUserManagment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().replace(android.R.id.content,fragmentUserManagment);
        fragmentTransaction.commit();
    }

    public void map(View view) {
        FragmentMapManager fragmentMapManager = new FragmentMapManager();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().replace(android.R.id.content,fragmentMapManager);
        fragmentTransaction.commit();
    }

    public void create_review(View view) {
        FragmentCreateReview fragmentCreateReview  = new FragmentCreateReview();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().replace(android.R.id.content,fragmentCreateReview);
        fragmentTransaction.commit();
    }
}
