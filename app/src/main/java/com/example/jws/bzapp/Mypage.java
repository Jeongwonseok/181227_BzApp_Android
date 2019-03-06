package com.example.jws.bzapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

public class Mypage extends AppCompatActivity {
    ImageButton btnBack;
    Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

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
                startActivity(intent);
            }
        });

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

    }
}
