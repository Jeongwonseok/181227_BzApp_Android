package com.example.jws.bzapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

public class UpdateActivity extends AppCompatActivity {
    ImageButton btnBack;
    Button btnCancel;
    Button btnSave;
    String question = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateActivity.this, Mypage.class);
                startActivity(intent);
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnCancel = (Button) findViewById(R.id.no);
        btnSave = (Button) findViewById(R.id.yes);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateActivity.this, Mypage.class);
                startActivity(intent);
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 갱신된 내용 저장하기
            }
        });

        //입력받을 EditText 선언
        final EditText Eid = (EditText) findViewById(R.id.upid);
        final EditText Epw = (EditText) findViewById(R.id.uppw);
        final EditText Ename = (EditText) findViewById(R.id.upname);
        final EditText Eemail = (EditText) findViewById(R.id.upemail);
        final EditText Eanswer = (EditText) findViewById(R.id.upanswer);
        final EditText Ephone1 = (EditText) findViewById(R.id.upphone1);
        final EditText Ephone2 = (EditText) findViewById(R.id.upphone2);
        final EditText Ephone3 = (EditText) findViewById(R.id.upphone3);

        //비밀번호 찾기 질문 배열
        final String[] number = {"선택하세요.", "아버지 성함은?", "어머니 성함은?", "자신의 보물 1호는?", "첫사랑 이름은?"};

        final Spinner spinner = (Spinner) findViewById(R.id.upspinner1);

        //스피너에 배열 입력
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, number);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //질문 선택할때마다 question 변수 업데이트
                question = spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
