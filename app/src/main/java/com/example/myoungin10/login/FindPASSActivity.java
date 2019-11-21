package com.example.myoungin10.login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myoungin10.R;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.client.Socket;

public class FindPASSActivity extends AppCompatActivity {

    private EditText idText;
    private EditText keyText;
    private Button okBtn;
    private Button backBtn;
    private Socket mSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pass);

        idText = (EditText)findViewById(R.id.find_pass_idText);
        keyText = (EditText)findViewById(R.id.find_pass_keyText);
        okBtn = (Button) findViewById(R.id.find_pass_okBtn);
        backBtn = (Button) findViewById(R.id.find_pass_backBtn);
        mSocket = LoginActivity.getSocket();

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSocket != null) {
                    JSONObject data = new JSONObject();
                    try {
                        data.put("type", "find pass");
                        data.put("id", idText.getText().toString());
                        data.put("key", keyText.getText().toString());
                        mSocket.emit("account", data);
                    } catch(JSONException e) {
                        Toast.makeText(getApplicationContext(), "에러 발생!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
