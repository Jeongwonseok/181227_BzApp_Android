package com.example.jws.bzapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class Mypage extends AppCompatActivity {
    ImageButton btnBack;
    Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);


        //입력받을 EditText 선언(회원가입이랑 다르게 id값앞에 my붙임)
        final EditText Eid = (EditText) findViewById(R.id.myid);
        final EditText Epw = (EditText) findViewById(R.id.mypw);
        final EditText Ename = (EditText) findViewById(R.id.myname);
        final EditText Eemail = (EditText) findViewById(R.id.myemail);
        final EditText Equestion = (EditText) findViewById(R.id.myquestion);
        final EditText Eanswer = (EditText) findViewById(R.id.myanswer);
        final EditText Ephone1 = (EditText) findViewById(R.id.myphone1);
        final EditText Ephone2 = (EditText) findViewById(R.id.myphone2);
        final EditText Ephone3 = (EditText) findViewById(R.id.myphone3);

        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Mypage.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        update = (Button) findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Mypage.this, UpdateActivity.class);
                intent.putExtra("ID",Eid.getText().toString());
                intent.putExtra("name",Ename.getText().toString());
                intent.putExtra("email",Eemail.getText().toString());
                intent.putExtra("tel2",Ephone2.getText().toString());
                intent.putExtra("tel3",Ephone3.getText().toString());
                startActivity(intent);
            }
        });


        SharedPreferences test = getSharedPreferences("check", Activity.MODE_PRIVATE);
        final String loginID = test.getString("id", null);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    String id = jsonObject.getString("ID");
                    String PW = jsonObject.getString("PW");
                    String name = jsonObject.getString("name");
                    String tel = jsonObject.getString("tel");
                    String email = jsonObject.getString("email");
                    String pwQ = jsonObject.getString("pwQ");
                    String pwA = jsonObject.getString("pwA");
                    if (success) {
                        Eid.setText(id);
//                        Epw.setText(PW);
                        Ename.setText(name);
                        Eemail.setText(email);
                        Equestion.setText(pwQ);
                        Eanswer.setText(pwA);
                        Ephone1.setText(tel.substring(0,3));
                        Ephone2.setText(tel.substring(3,7));
                        Ephone3.setText(tel.substring(7,11));
                    } else {
                        Toast.makeText(getApplicationContext(),"정보가 없습니다.",Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        GetMypage getMypage = new GetMypage(loginID, responseListener);
        RequestQueue queue = Volley.newRequestQueue(Mypage.this);
        queue.add(getMypage);

    }
}
