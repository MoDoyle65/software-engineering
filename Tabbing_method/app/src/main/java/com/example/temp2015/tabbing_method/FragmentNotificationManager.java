package com.example.temp2015.tabbing_method;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by temp2015 on 28-Nov-16.
 */

public class FragmentNotificationManager extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notification_manager,container,false);
    }
}
