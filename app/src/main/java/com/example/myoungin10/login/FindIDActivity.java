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

public class FindIDActivity extends AppCompatActivity {

    private EditText keyText;
    private Button okBtn;
    private Button backBtn;
    private Socket mSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_id);

        keyText = (EditText)findViewById(R.id.find_id_keyText);
        okBtn = (Button) findViewById(R.id.find_id_okBtn);
        backBtn = (Button) findViewById(R.id.find_id_backBtn);
        mSocket = LoginActivity.getSocket();

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSocket != null) {
                    JSONObject data = new JSONObject();
                    try {
                        data.put("type", "find id");
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
