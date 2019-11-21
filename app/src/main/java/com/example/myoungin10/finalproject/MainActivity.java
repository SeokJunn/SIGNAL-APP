package com.example.myoungin10.finalproject;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

import com.example.myoungin10.R;

public class MainActivity extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Resources res = getResources();
        TabHost tabHost = getTabHost();
        TabHost.TabSpec spec;
        Intent intent;

        intent = new Intent(this, Tab1Activity.class);
        spec = tabHost.newTabSpec("tab1").setIndicator("친구목록").setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent(this, Tab2ActivityMenu.class);
        spec = tabHost.newTabSpec("tab2").setIndicator("대화창").setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent(this, Tab3ActivityMemo.class);
        spec = tabHost.newTabSpec("tab3").setIndicator("메모").setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent(this, Tab4Activity.class);
        spec = tabHost.newTabSpec("tab4").setIndicator("채널").setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent(this, Tab5ActivitySetting.class);
        spec = tabHost.newTabSpec("tab5").setIndicator("설정").setContent(intent);
        tabHost.addTab(spec);


        tabHost.setCurrentTab(0);
    }
}
