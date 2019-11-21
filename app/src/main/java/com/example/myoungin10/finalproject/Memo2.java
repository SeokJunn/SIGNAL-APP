package com.example.myoungin10.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class Memo2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_memo);


        /////////메인 레이아웃 3개
        LinearLayout mainLayout1 = new LinearLayout(this);
        mainLayout1 .setOrientation(LinearLayout.VERTICAL);
        LinearLayout mainLayout2 = new LinearLayout(this);
        mainLayout2 .setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout mainLayout3 = new LinearLayout(this);
        mainLayout3 .setOrientation(LinearLayout.HORIZONTAL);
        /////////////////////////////////////////////////////////
        ////////버튼레이아웃,뷰레이아웃
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        ///////////////////////////////////////////////////////////
        ///////////버튼들
        Button button1 = new Button(this);
        button1.setText("검");
        button1.setLayoutParams(params);
        mainLayout2.addView(button1);

        Button button2 = new Button(this);
        button2.setText("빨");
        button2.setLayoutParams(params);
        mainLayout2.addView(button2);

        Button button3 = new Button(this);
        button3.setText("파");
        button3.setLayoutParams(params);
        mainLayout2.addView(button3);

        Button button4 = new Button(this);
        button4.setText("노");
        button4.setLayoutParams(params);
        mainLayout2.addView(button4);
/*
         <view

        class="com.example.myoungin10.finalproject.Memo"
        />
  */

        View viewdraw = new View(this);
        viewdraw.setLayoutParams(params2);
       // viewdraw.findViewById(R.id.memo.class);
        mainLayout3.addView(viewdraw);
        Intent intent = new Intent(this, Memo2.class);


        mainLayout1.addView(mainLayout2);
        mainLayout1.addView(mainLayout3);
        setContentView(mainLayout1);



    }
}
