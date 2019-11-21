package com.example.myoungin10.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myoungin10.R;
import com.example.myoungin10.finalproject.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class LoginActivity extends AppCompatActivity {

    private EditText idText;
    private EditText passText;
    private Button loginBtn;
    private TextView registerText;
    private TextView findIdText;
    private TextView findPassText;
    private static Socket mSocket;
    {
        try {
            IO.Options options = new IO.Options();
            options.forceNew = true;
            options.reconnection = true;
            mSocket = IO.socket("http://180.189.105.169:3000/", options);
        } catch (URISyntaxException e) {
//            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static Socket getSocket() {
        return mSocket;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        idText = (EditText)findViewById(R.id.idText);
        passText = (EditText)findViewById(R.id.passwordText);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        registerText = (TextView) findViewById(R.id.registerText);
        findIdText = (TextView)findViewById(R.id.findIdText);
        findPassText = (TextView)findViewById(R.id.findPasswordText);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject data = new JSONObject();
                try {
                    data.put("id", idText.getText().toString());
                    data.put("password", passText.getText().toString());
                    mSocket.emit("login", data);
                } catch(JSONException e) {
                    Toast.makeText(getApplicationContext(), "에러 발생!", Toast.LENGTH_LONG).show();
                }
            }
        });

        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        findIdText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FindIDActivity.class);
                startActivity(intent);
            }
        });

        findPassText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FindPASSActivity.class);
                startActivity(intent);
            }
        });
        mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("connect timeout");
            }
        }).on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
//                try {
//                    System.out.println(args[0]);
//                    JSONObject jsonObject = (JSONObject) args[0];
//                    System.out.println(jsonObject);
//                } catch (ClassCastException e) {}
                System.out.println("connect error");

            }
        }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("disconnect");
            }
        });

        mSocket.on("connection", onConnection);
        mSocket.on("account", onAccount);
        mSocket.connect();
    }

    private final Emitter.Listener onConnection = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            // your code...
            // 서버로 이벤트를 전송하는 부분에 적절히 추가하시면 될 것 같아요.
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONObject data = (JSONObject) args[0];
                        String type;
                        try {
                            type = data.getString("msg");
                            switch (type) {
                                case "successed": {
                                    // 친구 목록 요청
                                    // 블랙리스트 목록 요청
                                    // 방 리스트 요청
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    Toast.makeText(getApplicationContext(), "로그인 성공!", Toast.LENGTH_LONG).show();
                                }
                                break;

                                case "failed":
                                    Toast.makeText(getApplicationContext(), "로그인 실패!", Toast.LENGTH_LONG).show();
                                    break;

                                case "error":
                                    Toast.makeText(getApplicationContext(), "에러가 발생하였습니다.", Toast.LENGTH_LONG).show();
                                    break;
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), "에러 발생!", Toast.LENGTH_LONG).show();
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                    }
                }
            });
            // 메시지를 받으면 data에 담고,
            // username와 message라는 키값으로 들어왔다는 가정으로 작성된 코드다.
            // addMessage(username, message); 이런 식으로 코드를 실행시키면 addMessage 쪽으로 인자를 담아 보내니 화면에 노출하게 만들면 될 것이다.
        }
    };

    private final Emitter.Listener onAccount = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONObject data = (JSONObject) args[0];
                        String type;
                        String msg;
                        try {
                            type = data.getString("type");
                            msg = data.getString("msg");
                            switch (type) {
                                case "register": {
                                    switch(msg) {
                                        case "successed":
                                            Toast.makeText(getApplicationContext(), "회원가입 성공!", Toast.LENGTH_LONG).show();
                                            break;

                                        case "failed":
                                            Toast.makeText(getApplicationContext(), "회원가입 실패!", Toast.LENGTH_LONG).show();
                                            break;

                                        case "error":
                                            Toast.makeText(getApplicationContext(), "에러가 발생하였습니다.", Toast.LENGTH_LONG).show();
                                            break;
                                    }
                                }
                                break;

                                case "find id":
                                    switch(msg) {
                                        case "successed":
                                            // dialog 스타일 테마 보여주기 ( 찾은 계정 )
                                            String id = data.getString("id");
                                            Intent intent = new Intent(getApplicationContext(), LoginDialogActivity.class);
                                            intent.putExtra("content", "당신의 계정명\n" + id);
                                            intent.putExtra("ispass", false);
                                            startActivity(intent);

                                            Toast.makeText(getApplicationContext(), "계정 찾기 성공!", Toast.LENGTH_LONG).show();
                                            break;

                                        case "failed":
                                            Toast.makeText(getApplicationContext(), "계정 찾기 실패!", Toast.LENGTH_LONG).show();
                                            break;

                                        case "error":
                                            Toast.makeText(getApplicationContext(), "에러가 발생하였습니다.", Toast.LENGTH_LONG).show();
                                            break;
                                    }
                                    break;

                                case "find pass":
                                    switch(msg) {
                                        case "successed":
                                            // dialog 스타일 테마 보여주기 ( 수정할 비밀번호 )
                                            String id = data.getString("id");
                                            String key = data.getString("key");
                                            Intent intent = new Intent(getApplicationContext(), LoginDialogActivity.class);
                                            intent.putExtra("content", "수정할 비밀번호 입력");
                                            intent.putExtra("id", id);
                                            intent.putExtra("key", key);
                                            intent.putExtra("ispass", true);
                                            startActivity(intent);
                                            Toast.makeText(getApplicationContext(), "비번 찾기 성공!", Toast.LENGTH_LONG).show();
                                            break;

                                        case "failed":
                                            Toast.makeText(getApplicationContext(), "비번 찾기 실패!", Toast.LENGTH_LONG).show();
                                            break;

                                        case "error":
                                            Toast.makeText(getApplicationContext(), "에러가 발생하였습니다.", Toast.LENGTH_LONG).show();
                                            break;
                                    }
                                    break;

                                case "update pass":
                                    switch(msg) {
                                        case "successed":
                                            // dialog 스타일 테마 보여주기 ( 수정할 비밀번호 )
                                            Toast.makeText(getApplicationContext(), "비번 수정 성공!", Toast.LENGTH_LONG).show();
                                            break;

                                        case "failed":
                                            Toast.makeText(getApplicationContext(), "비번 수정 실패!", Toast.LENGTH_LONG).show();
                                            break;

                                        case "error":
                                            Toast.makeText(getApplicationContext(), "에러가 발생하였습니다.", Toast.LENGTH_LONG).show();
                                            break;
                                    }
                                    break;

                                case "error":
                                    Toast.makeText(getApplicationContext(), "에러가 발생하였습니다.", Toast.LENGTH_LONG).show();
                                    break;
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), "에러 발생!", Toast.LENGTH_LONG).show();
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                    }
                }
            });
            // 메시지를 받으면 data에 담고,
            // username와 message라는 키값으로 들어왔다는 가정으로 작성된 코드다.
            // addMessage(username, message); 이런 식으로 코드를 실행시키면 addMessage 쪽으로 인자를 담아 보내니 화면에 노출하게 만들면 될 것이다.
        }
    };
}
