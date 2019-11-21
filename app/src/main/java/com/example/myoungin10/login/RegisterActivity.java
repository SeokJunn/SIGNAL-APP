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

public class RegisterActivity extends AppCompatActivity {

    private EditText idText;
    private EditText passText;
    private EditText nickText;
    private EditText keyText;
    private Button regBtn;
    private Button backBtn;
    private Socket mSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        idText = (EditText)findViewById(R.id.reg_idText);
        passText = (EditText)findViewById(R.id.reg_passwordText);
        nickText = (EditText)findViewById(R.id.reg_nickText);
        keyText = (EditText)findViewById(R.id.reg_keyText);
        regBtn = (Button) findViewById(R.id.reg_regBtn);
        backBtn = (Button) findViewById(R.id.reg_backBtn);
        mSocket = LoginActivity.getSocket();

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSocket != null) {
                    JSONObject data = new JSONObject();
                    try {
                        data.put("type", "register");
                        data.put("id", idText.getText().toString());
                        data.put("password", passText.getText().toString());
                        data.put("nickname", nickText.getText().toString());
                        data.put("key", keyText.getText().toString());
                        mSocket.emit("account", data);
                        finish();
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
