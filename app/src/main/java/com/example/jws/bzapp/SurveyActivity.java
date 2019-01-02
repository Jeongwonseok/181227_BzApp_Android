package com.example.jws.bzapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

public class SurveyActivity extends AppCompatActivity {

    ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String[] age = {"10대", "20대", "30대", "40대", "50대", "60대 이상"};

        Spinner spinner = (Spinner) findViewById(R.id.age);

        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, age);
        spinner.setAdapter(adapter);

        Button btncancel = (Button) findViewById(R.id.cancel);
        Button btnok = (Button) findViewById(R.id.ok);

        btnBack = (ImageButton) findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SurveyActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SurveyActivity.this);
                builder.setTitle("설문취소");
                builder.setMessage("지금까지 응답한 내용은 저장되지 않습니다. 정말 취소하시겠습니까? ");
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                builder.setNegativeButton("취소", null);
                builder.show();
            }
        });

        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SurveyActivity.this);
                builder.setTitle("설문완료");
                builder.setMessage("설문결과를 확인하시려면 '확인'버튼을 누르세요");
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        Intent intent = new Intent(SurveyActivity.this, RecommendActivity.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("취소", null);
                builder.show();
            }
        });
    }
}
