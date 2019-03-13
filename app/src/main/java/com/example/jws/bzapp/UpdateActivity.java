package com.example.jws.bzapp;

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

import org.json.JSONException;
import org.json.JSONObject;

import static android.text.TextUtils.isEmpty;

public class UpdateActivity extends AppCompatActivity {
    ImageButton btnBack;
    Button btnCancel;
    Button btnSave;
    String question = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        //입력받을 EditText 선언
        final EditText Eid = (EditText) findViewById(R.id.upid);
        final EditText Epw = (EditText) findViewById(R.id.uppw);
        final EditText Ename = (EditText) findViewById(R.id.upname);
        final EditText Eemail = (EditText) findViewById(R.id.upemail);
        final EditText Eanswer = (EditText) findViewById(R.id.upanswer);
        final EditText Ephone1 = (EditText) findViewById(R.id.upphone1);
        final EditText Ephone2 = (EditText) findViewById(R.id.upphone2);
        final EditText Ephone3 = (EditText) findViewById(R.id.upphone3);

        Intent intent = getIntent();
        final String Fid = intent.getStringExtra("ID");
        String name = intent.getStringExtra("name");
        Eid.setText(Fid);
        Ename.setText(name);
        Eemail.setText(intent.getStringExtra("email"));
        Ephone2.setText(intent.getStringExtra("tel2"));
        Ephone3.setText(intent.getStringExtra("tel3"));

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
                String id = Eid.getText().toString();
                String name = Ename.getText().toString();
                String email = Eemail.getText().toString();
                String pw = Epw.getText().toString();
                String answer = Eanswer.getText().toString();
                String phone = Ephone1.getText().toString() + Ephone2.getText().toString() + Ephone3.getText().toString();

                //결과 리스너 생성
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                //등록후 응답받은 값이 true이면 성공 다이얼로그 출력
                                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateActivity.this);
                                builder.setMessage("회원정보 수정을 완료했습니다.")
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                finish();
                                            }
                                        })
                                        .create()
                                        .show();
                            } else {
                                //등록 실패 했을때 실패 다이얼로그 출력
                                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateActivity.this);
                                builder.setMessage("회원 정보 수정에 실패했습니다.")
                                        .setPositiveButton("다시 시도", null)
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
                } else if (isEmpty(pw)){
                    Toast.makeText(getApplicationContext(),"비밀번호를 입력해주세요.", Toast.LENGTH_LONG).show();
                    return;
                } else if (isEmpty(email)){
                    Toast.makeText(getApplicationContext(),"이메일을 입력해주세요.", Toast.LENGTH_LONG).show();
                    return;
                } else if (question.equals("선택하세요.")){
                    Toast.makeText(getApplicationContext(),"질문을 선택해주세요.", Toast.LENGTH_LONG).show();
                    return;
                } else if (isEmpty(answer)){
                    Toast.makeText(getApplicationContext(),"질문 대답을 입력해주세요.", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    //모든 값이 다 있으면 DB에 저장하는 메소드 실행
                    Setmypage setmypage = new Setmypage(id, pw, name, phone, email, question, answer, Fid, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(UpdateActivity.this);
                    queue.add(setmypage);
                }
            }
        });


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
