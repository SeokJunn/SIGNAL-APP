package com.example.myoungin10.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.example.myoungin10.R;

import java.util.ArrayList;

public class Tab3ActivityMemo extends AppCompatActivity {

    private ListView listView;
    private ArrayList<ProfileModel> models;
    private IndexAdapterMemo mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab3_memo);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);


        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "메에에에에모", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent(Tab3ActivityMemo.this, Memo2.class);
                startActivity(intent);

            }
        });



        listView = (ListView)findViewById(R.id.tab3_list_view);

        models = new ArrayList<ProfileModel>();
        for(int i=0; i < 100 ; i++) {
            models.add(ProfileModel.newInstance(R.drawable.plus, "NAME " + (i+1), "밥은 먹고 다니냐", System.currentTimeMillis() ));
        }

        mAdapter = new IndexAdapterMemo(getBaseContext(), R.layout.activity_tab3_item, models);
        listView.setAdapter(mAdapter);
    }

}
