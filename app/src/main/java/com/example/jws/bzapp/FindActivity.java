package com.example.jws.bzapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
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
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.text.TextUtils.isEmpty;

public class FindActivity extends AppCompatActivity {

    Spinner spinner;
    ImageButton btnBack;
    Button idFind, pwFind;
    String question = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        //입력받을 EditText 선언
        final EditText Ename = (EditText) findViewById(R.id.name);
        final EditText Eemail = (EditText) findViewById(R.id.email);
        final EditText Ephone1 = (EditText) findViewById(R.id.phone1);
        final EditText Ephone2 = (EditText) findViewById(R.id.phone2);
        final EditText Ephone3 = (EditText) findViewById(R.id.phone3);
        final EditText Eid = (EditText) findViewById(R.id.id);
        final EditText Eanswer = (EditText) findViewById(R.id.answer);

        //비밀번호 찾기 질문 배열
        final String[] number = {"선택하세요.", "아버지 성함은?", "어머니 성함은?", "자신의 보물 1호는?", "첫사랑 이름은?"};

        final Spinner spinner = (Spinner) findViewById(R.id.spinner1);

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

        //ID찾기 버튼 클릭리스너
        idFind = (Button) findViewById(R.id.idFind);
        idFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = Ename.getText().toString();
                String email = Eemail.getText().toString();
                String phone = Ephone1.getText().toString() + Ephone2.getText().toString() + Ephone3.getText().toString();

                //결과 리스너 생성
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray jsonArray = jsonObject.getJSONArray("response");
                            int count = 0;
                            String fid = "아이디 : ";
                            while (count < jsonArray.length()) {
                                if(count >= 1) {
                                    fid = fid + ", ";
                                }
                                JSONObject object = jsonArray.getJSONObject(count);
                                fid = fid + object.getString("id");
                                count++;
                            }
                            if (jsonArray.length() > 0) {
                                //등록후 응답받은 값이 true이면 성공 다이얼로그 출력
                                AlertDialog.Builder builder = new AlertDialog.Builder(FindActivity.this);
                                builder.setMessage(fid)
                                        .setPositiveButton("로그인 이동", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = new Intent(FindActivity.this, LoginActivity.class);
                                                startActivity(intent);
                                            }
                                        })
                                        .create()
                                        .show();
                            } else {
                                //등록 실패 했을때 실패 다이얼로그 출력
                                AlertDialog.Builder builder = new AlertDialog.Builder(FindActivity.this);
                                builder.setMessage("아이디를 찾을수 없습니다. 다시 확인 부탁드립니다.")
                                        .setPositiveButton("다시 시도", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Ename.setText("");
                                                Eemail.setText("");
                                                Ephone2.setText("");
                                                Ephone3.setText("");
                                            }
                                        })
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                //각 정보를 입력안했을때는 Toast메세지 출력 후 리턴
                if (isEmpty(name)){
                    Toast.makeText(getApplicationContext(),"이름을 입력해주새요.", Toast.LENGTH_LONG).show();
                    return;
                } else if (isEmpty(email)){
                    Toast.makeText(getApplicationContext(),"이메일을 입력해주세요.", Toast.LENGTH_LONG).show();
                    return;
                } else if (isEmpty(Ephone2.getText().toString())){
                    Toast.makeText(getApplicationContext(),"휴대폰 번호를 확인하세요", Toast.LENGTH_LONG).show();
                    return;
                } else if (isEmpty(Ephone3.getText().toString())){
                    Toast.makeText(getApplicationContext(),"휴대폰 번호를 확인하세요", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    //모든 값이 다 있으면 DB에 저장하는 메소드 실행
                    Findid findid = new Findid(name, email, phone,responseListener );
                    RequestQueue queue = Volley.newRequestQueue(FindActivity.this);
                    queue.add(findid);
                }
            }
        });

        pwFind = (Button)findViewById(R.id.pwFind);
        pwFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = Eid.getText().toString();
                String answer = Eanswer.getText().toString();

                //결과 리스너 생성
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray jsonArray = jsonObject.getJSONArray("response");
                            int count = 0;
                            String fid = "비밀번호 : ";
                            while (count < jsonArray.length()) {
                                if(count >= 1) {
                                    fid = fid + ", ";
                                }
                                JSONObject object = jsonArray.getJSONObject(count);
                                fid = fid + object.getString("pw");
                                count++;
                            }
                            if (jsonArray.length() > 0) {
                                //등록후 응답받은 값이 true이면 성공 다이얼로그 출력
                                AlertDialog.Builder builder = new AlertDialog.Builder(FindActivity.this);
                                builder.setMessage(fid)
                                        .setPositiveButton("로그인 이동", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = new Intent(FindActivity.this, LoginActivity.class);
                                                startActivity(intent);
                                            }
                                        })
                                        .create()
                                        .show();
                            } else {
                                //등록 실패 했을때 실패 다이얼로그 출력
                                AlertDialog.Builder builder = new AlertDialog.Builder(FindActivity.this);
                                builder.setMessage("비밀번호를 찾을수 없습니다. 다시 확인 부탁드립니다.")
                                        .setPositiveButton("다시 시도", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Ename.setText("");
                                                Eemail.setText("");
                                                Ephone2.setText("");
                                                Ephone3.setText("");
                                                Eid.setText("");
                                                Eanswer.setText("");
                                            }
                                        })
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                //각 정보를 입력안했을때는 Toast메세지 출력 후 리턴
                if (isEmpty(id)){
                    Toast.makeText(getApplicationContext(),"아이디를 입력해주새요.", Toast.LENGTH_LONG).show();
                    return;
                } else if (question.equals("선택하세요.")){
                    Toast.makeText(getApplicationContext(),"질문을 선택해주세요.", Toast.LENGTH_LONG).show();
                    return;
                } else if (isEmpty(answer)){
                    Toast.makeText(getApplicationContext(),"답변을 입력해주세요.", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    //모든 값이 다 있으면 DB에 저장하는 메소드 실행
                    Findpw findpw = new Findpw(id, question, answer, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(FindActivity.this);
                    queue.add(findpw);
                }
            }
        });

    }
}
