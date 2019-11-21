package com.example.myoungin10.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myoungin10.R;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.client.Socket;

public class LoginDialogActivity extends Activity implements View.OnClickListener {

    private Button okBtn;
    private Button cancelBtn;
    private TextView contentText;
    private EditText passwordText;
    private String id, key;
    private boolean isPass;
    private Socket mSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login_dialog);
        setContent();
    }

    private void setContent() {
        okBtn = (Button) findViewById(R.id.login_dialog_okBtn);
        cancelBtn = (Button) findViewById(R.id.login_dialog_cancelBtn);
        contentText = (TextView) findViewById(R.id.login_dialog_contentText);
        passwordText = (EditText) findViewById(R.id.login_dialog_passwordText);
        mSocket = LoginActivity.getSocket();

        Intent intent = getIntent();
        String content = intent.getStringExtra("content");
        id = intent.getStringExtra("id");
        key = intent.getStringExtra("key");
        isPass = intent.getBooleanExtra("ispass", false);
        contentText.setText(content);
        if (!isPass) {
            passwordText.setVisibility(View.INVISIBLE);
        } else {
            passwordText.setVisibility(View.VISIBLE);
        }

        okBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_dialog_okBtn:
                if (isPass) {
                    if (mSocket != null) {
                        JSONObject data = new JSONObject();
                        try {
                            data.put("type", "update pass");
                            data.put("id", id.toString());
                            data.put("key", key.toString());
                            data.put("pass", passwordText.getText().toString());
                            mSocket.emit("account", data);
                        } catch(JSONException e) {
                            Toast.makeText(getApplicationContext(), "에러 발생!", Toast.LENGTH_LONG).show();
                        }
                    }
                }
                finish();
                break;

            case R.id.login_dialog_cancelBtn:
                finish();
                break;

            default:
                break;
        }
    }
}
