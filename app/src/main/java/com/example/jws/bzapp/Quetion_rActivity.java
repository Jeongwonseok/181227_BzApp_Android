package com.example.jws.bzapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import static android.text.TextUtils.isEmpty;

public class Quetion_rActivity extends AppCompatActivity {

    ImageButton btnBack;
    ImageButton btnList;
    Spinner spinner;
    Button btnResult;
    String questiontype = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quetion_r);

        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnList = (ImageButton) findViewById(R.id.btnList);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Quetion_rActivity.this);
                builder.setMessage("작성중인 문의 정보를 모두 잃습니다. 메인 화면으로 이동하시겠습니까?");
                builder.setPositiveButton("이동", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Quetion_rActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("취소", null);
                builder.show();
            }
        });

        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Quetion_rActivity.this);
                builder.setMessage("작성중인 문의 정보를 모두 잃습니다. 문의사항 목록 화면으로 이동하시겠습니까?");
                builder.setPositiveButton("이동", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Quetion_rActivity.this, QuestionActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                builder.setNegativeButton("취소", null);
                builder.show();
            }
        });

        //질문 타입 스피너 설정
        final String[] number = {"문의유형을 선택하세요.", "업데이트", "오류 및 버그", "기타"};

        final Spinner spinner = (Spinner) findViewById(R.id.spinner1);

        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, number);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //스피너 변경마다 questiontype 업데이트
                questiontype = spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnResult = (Button) findViewById(R.id.btnResult);
        final EditText Ename = (EditText) findViewById(R.id.Qname);
        final EditText Eemail = (EditText) findViewById(R.id.Qemail);
        final EditText EQcontent = (EditText) findViewById(R.id.Qcontents);
        btnResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //입력받은 정보 변수에 저장
                String name = Ename.getText().toString();
                String email = Eemail.getText().toString();
                String questioncontent = EQcontent.getText().toString();

                //결과 리스너 생성
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                //등록 성공시
                                AlertDialog.Builder builder = new AlertDialog.Builder(Quetion_rActivity.this);
                                builder.setMessage("문의 등록에 성공했습니다.")
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = new Intent(Quetion_rActivity.this, QuestionActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        })
                                        .create()
                                        .show();
                            } else {
                                //실패시
                                AlertDialog.Builder builder = new AlertDialog.Builder(Quetion_rActivity.this);
                                builder.setMessage("문의 등록에 실패했습니다.")
                                        .setPositiveButton("다시 시도", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                //입력받아야하는 값 중에서 사용자가 입력하지 않으면 토스트메세지 출력후 리턴
                if (isEmpty(name)) {
                    Toast.makeText(getApplicationContext(), "이름을 입력해주새요.", Toast.LENGTH_LONG).show();
                    return;
                } else if (isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "이메일을 입력해주세요.", Toast.LENGTH_LONG).show();
                    return;
                } else if (questiontype.equals("문의유형을 선택하세요.")) {
                    Toast.makeText(getApplicationContext(), "문의유형을 선택해주세요.", Toast.LENGTH_LONG).show();
                    return;
                } else if (isEmpty(questioncontent)) {
                    Toast.makeText(getApplicationContext(), "내용을 입력하세요.", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    //모든 값을 입력했으면 php실행 후 DB 저장
                    QRegister qregister = new QRegister(name, email, questiontype, questioncontent, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(Quetion_rActivity.this);
                    queue.add(qregister);
                }
            }
        });
    }
}