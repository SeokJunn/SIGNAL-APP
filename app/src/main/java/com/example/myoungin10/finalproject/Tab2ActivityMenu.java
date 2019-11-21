package com.example.myoungin10.finalproject;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.example.myoungin10.R;

import java.util.ArrayList;

public class Tab2ActivityMenu extends AppCompatActivity {

    private ListView listView;
    private ArrayList<ProfileModel> models;
    private IndexAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab2_menu);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        listView = (ListView)findViewById(R.id.tab2_list_view);

        models = new ArrayList<ProfileModel>();
        for(int i=0; i < 100 ; i++) {
            models.add(ProfileModel.newInstance(R.drawable.plus, "NAME " + (i+1), "이이이이이이이", System.currentTimeMillis() ));
        }

        mAdapter = new IndexAdapter(getBaseContext(), R.layout.activity_tab2_item, models);
        listView.setAdapter(mAdapter);
    }

}
