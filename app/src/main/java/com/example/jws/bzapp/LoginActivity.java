package com.example.jws.bzapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.kakao.auth.ErrorCode;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.exception.KakaoException;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    Button btnJoin, btnLogin;
    ImageButton btnBack;

    //카카오 콜백
    SessionCallback callback;
    TextView txtfind;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //찾기 텍스트뷰 클릭
        txtfind = (TextView) findViewById(R.id.txtfind);
        txtfind.setText(R.string.my_string);

        txtfind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, FindActivity.class);
                startActivity(intent);
            }
        });

//카카오 콜백
        callback = new SessionCallback();
        Session.getCurrentSession().addCallback(callback);
        //카카오 콜백


        final EditText etID = (EditText) findViewById(R.id.etID);
        final EditText etPW = (EditText) findViewById(R.id.etPW);

        btnJoin = (Button) findViewById(R.id.btnJoin);
        btnLogin = (Button) findViewById(R.id.btnlogin);
        btnBack = (ImageButton) findViewById(R.id.btnBack);


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, JoinActivity.class);
                startActivity(intent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //입력받은 ID,PW 값 배열에 저장
                final String ID = etID.getText().toString();
                final String PW = etPW.getText().toString();

                //결과 리스너 생성
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) {
                                //로그인 성공시 메인 액티비티 전환
                                String ID = jsonObject.getString("ID");
                                String questionYN = jsonObject.getString("questionYN");
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("ID", ID);
                                intent.putExtra("PW", PW);
                                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                SharedPreferences test = getSharedPreferences("check", Activity.MODE_PRIVATE);
                                SharedPreferences.Editor checkLogin = test.edit();
                                checkLogin.putBoolean("check", true);
                                checkLogin.putString("id",ID);
                                if (questionYN.equals("1")){
                                    checkLogin.putBoolean("surveycheck",true);
                                } else {
                                    checkLogin.putBoolean("surveycheck",false);
                                }
                                //꼭 commit()을 해줘야 값이 저장됩니다 ㅎㅎ
                                checkLogin.commit();
                                startActivity(intent);
                                finish();
                            } else {
                                //로그인 실패시 아이디와 비밀번호 다시 입력
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("로그인에 실패했습니다. 아이디와 비밀번호를 확인해주세요.")
                                        .setPositiveButton("다시 시도", null)
                                        .create()
                                        .show();
                                etID.setText("");
                                etPW.setText("");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };

                //php에 접근하는 메소드 선언후 실행
                Login login = new Login(ID, PW, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(login);
            }
        });
    }

    //카카오 콜백 클래스 추가
    private class SessionCallback implements ISessionCallback {

        @Override
        public void onSessionOpened() {

            UserManagement.requestMe(new MeResponseCallback() {

                @Override
                public void onFailure(ErrorResult errorResult) {
                    String message = "failed to get user info. msg=" + errorResult;

                    ErrorCode result = ErrorCode.valueOf(errorResult.getErrorCode());
                    if (result == ErrorCode.CLIENT_ERROR_CODE) {
                        //에러로 인한 로그인 실패
//                        finish();
                    } else {
                        //redirectMainActivity();
                    }
                }

                @Override
                public void onSessionClosed(ErrorResult errorResult) {
                }

                @Override
                public void onNotSignedUp() {

                }

                @Override
                public void onSuccess(UserProfile userProfile) {
                    //로그인에 성공하면 로그인한 사용자의 일련번호, 닉네임, 이미지url등을 리턴합니다.
                    //사용자 ID는 보안상의 문제로 제공하지 않고 일련번호는 제공합니다.

//                    Log.e("UserProfile", userProfile.toString());
//                    Log.e("UserProfile", userProfile.getId() + "");


                    long number = userProfile.getId();


                }
            });

        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            // 세션 연결이 실패했을때
            // 어쩔때 실패되는지는 테스트를 안해보았음

        }
    }


}
