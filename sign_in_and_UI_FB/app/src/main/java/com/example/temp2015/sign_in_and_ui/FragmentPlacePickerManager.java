package com.example.temp2015.sign_in_and_ui;

/**
 * Created by temp2015 on 15-Dec-16.
 */
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class FragmentPlacePickerManager extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_placepicker,container,false);
    }
}
