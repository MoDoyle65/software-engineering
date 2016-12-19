package com.example.temp2015.tabbing_method;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;

public class MainActivity extends AppCompatActivity {
    TabHost tabHost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabHost host = (TabHost)findViewById(R.id.tabHost);
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("USER");
        spec.setContent(R.id.tab1);
        spec.setIndicator("USER",getResources().getDrawable(R.drawable.tab1));
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("Notification");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Notification");
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

}