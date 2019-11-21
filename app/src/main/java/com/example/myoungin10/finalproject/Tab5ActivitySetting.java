package com.example.myoungin10.finalproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.myoungin10.R;

import java.util.ArrayList;

public class Tab5ActivitySetting extends AppCompatActivity {

    private ListView listView;
    private ArrayList<ProfileModel> models;
    private IndexAdapterSetting mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab5_setting);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);





        listView = (ListView)findViewById(R.id.tab5_list_view);

        models = new ArrayList<ProfileModel>();
        models.add(ProfileModel.newInstance(R.drawable.plus, "NAME " , "글자체 바꾸기", System.currentTimeMillis() ));
        models.add(ProfileModel.newInstance(R.drawable.plus, "NAME " , "글자크기 바꾸기", System.currentTimeMillis() ));
        models.add(ProfileModel.newInstance(R.drawable.plus, "NAME " , "테마 바꾸기", System.currentTimeMillis() ));


        mAdapter = new IndexAdapterSetting(getBaseContext(), R.layout.activity_tab5_item, models);
        listView.setAdapter(mAdapter);
    }

}
