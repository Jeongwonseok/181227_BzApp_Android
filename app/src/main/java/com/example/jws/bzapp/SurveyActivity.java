package com.example.jws.bzapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class SurveyActivity extends AppCompatActivity {

    ImageButton btnBack;
    Button btncancel, btnok;
    RadioGroup rgGender, rgLocation, rgLreason, rgType, rgTreason, rgSales;
    String ID, gender, location, age, lReason, type, tReason, sales;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        Intent intent = getIntent();
        ID = intent.getStringExtra("ID");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final String[] ages = {"선택", "10대", "20대", "30대", "40대", "50대", "60대 이상"};

        final Spinner spinner = (Spinner) findViewById(R.id.age);

        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, ages);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                age = spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        rgGender = (RadioGroup) findViewById(R.id.rgGender);
        rgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rbGender = (RadioButton) findViewById(checkedId);
                switch (checkedId) {
                    case R.id.rbMan:
                        gender = "남자";
                        break;
                    case R.id.rbWoman:
                        gender = "여자";
                        break;
                }
            }
        });

        rgLocation = (RadioGroup) findViewById(R.id.rgLocation);
        rgLocation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rbLocation = (RadioButton) findViewById(checkedId);
                switch (checkedId) {
                    case R.id.rbGangwon:
                        location = "강원도";
                        break;
                    case R.id.rbGyeonggi:
                        location = "경기도";
                        break;
                    case R.id.rbGyeongsangS:
                        location = "경상남도";
                        break;
                    case R.id.rbGyeongsangN:
                        location = "경상북도";
                        break;
                    case R.id.rbGwangju:
                        location = "광주광역시";
                        break;
                    case R.id.rbDeagu:
                        location = "대구";
                        break;
                    case R.id.rbDeajeon:
                        location = "대전";
                        break;
                    case R.id.rbBusan:
                        location = "부산광역시";
                        break;
                    case R.id.rbSeoul:
                        location = "서울특별시";
                        break;
                    case R.id.rbSejong:
                        location = "세종특별시";
                        break;
                    case R.id.rbUlsan:
                        location = "울산광역시";
                        break;
                    case R.id.rbIncheon:
                        location = "인천광역시";
                        break;
                    case R.id.rbJeonraS:
                        location = "전라남도";
                        break;
                    case R.id.rbJeonraN:
                        location = "전라북도";
                        break;
                    case R.id.rbJeju:
                        location = "제주특별시";
                        break;
                    case R.id.rbchungS:
                        location = "충청남도";
                        break;
                    case R.id.rbchungN:
                        location = "충청북도";
                        break;
                }
            }
        });

        rgLreason = (RadioGroup) findViewById(R.id.rgLreason);
        rgLreason.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rbGender = (RadioButton) findViewById(checkedId);
                switch (checkedId) {
                    case R.id.rbL1:
                        lReason = "집과 가까워서서";
                        break;
                   case R.id.rbL2:
                        lReason = "해당 지역이 창업에 유리할 것 같아서 (풍부한 유동인구)";
                        break;
                    case R.id.rbL3:
                        lReason = "초기 비용이 적게 들 것 같아서";
                        break;
                    case R.id.rbL4:
                        lReason = "이 지역을 잘 알아서";
                        break;
                }
            }
        });

        rgType = (RadioGroup) findViewById(R.id.rgType);
        rgType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rbGender = (RadioButton) findViewById(checkedId);
                switch (checkedId) {
                    case R.id.rbRetail:
                        type = "소매";
                        break;
                    case R.id.rbLife:
                        type = "생활서비스";
                        break;
                    case R.id.rbTour:
                        type = "관광/여가/오락";
                        break;
                    case R.id.rbStay:
                        type = "숙박";
                        break;
                    case R.id.rbSports:
                        type = "스포츠";
                        break;
                    case R.id.rbFood:
                        type = "음식";
                        break;
                    case R.id.rbEdu:
                        type = "학문/교육";
                        break;
                }
            }
        });

        rgTreason = (RadioGroup) findViewById(R.id.rgTreason);
        rgTreason.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rbGender = (RadioButton) findViewById(checkedId);
                switch (checkedId) {
                    case R.id.rbT1:
                        tReason = "매출 수익이 높아서";
                        break;
                    case R.id.rbT2:
                        tReason = "이 업종에 대하여 잘 알아서";
                        break;
                    case R.id.rbT3:
                        tReason = "창업 후 운영이 상대적으로 쉬워서";
                        break;
                    case R.id.rbT4:
                        tReason = "초기 비용이 적게 들 것 같아서";
                        break;
                    case R.id.rbT5:
                        tReason = "이 지역을 잘 알아서";
                        break;
                }
            }
        });

        rgSales = (RadioGroup) findViewById(R.id.rgSales);
        rgSales.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rbGender = (RadioButton) findViewById(checkedId);
                switch (checkedId) {
                    case R.id.rbS1:
                        sales = "2000미만";
                        break;
                    case R.id.rbS2:
                        sales = "2000 ~ 2500";
                        break;
                    case R.id.rbS3:
                        sales = "2500 ~ 3000";
                        break;
                    case R.id.rbS4:
                        sales = "3000 ~ 3500";
                        break;
                    case R.id.rbS5:
                        sales = "3500 ~ 4000";
                        break;
                    case R.id.rbS6:
                        sales = "4000이상";
                        break;
                }
            }
        });

        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
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

        btncancel = (Button) findViewById(R.id.cancel);
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

        btnok = (Button) findViewById(R.id.ok);
        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (-1 == rgGender.getCheckedRadioButtonId()) {
                    Toast.makeText(getApplicationContext(), "성별을 선택하지 않았습니다.", Toast.LENGTH_LONG).show();
                } else if (age == null) {
                    Toast.makeText(getApplicationContext(), "나이를 선택하지 않았습니다.", Toast.LENGTH_LONG).show();
                } else if (-1 == rgLocation.getCheckedRadioButtonId()) {
                    Toast.makeText(getApplicationContext(), "지역을 선택하지 않았습니다.", Toast.LENGTH_LONG).show();
                } else if (-1 == rgLreason.getCheckedRadioButtonId()) {
                    Toast.makeText(getApplicationContext(), "지역 선택 이유를 선택하지 않았습니다.", Toast.LENGTH_LONG).show();
                } else if (-1 == rgType.getCheckedRadioButtonId()) {
                    Toast.makeText(getApplicationContext(), "업종을 선택하지 않았습니다.", Toast.LENGTH_LONG).show();
                } else if (-1 == rgTreason.getCheckedRadioButtonId()) {
                    Toast.makeText(getApplicationContext(), "업종 선택 이유를 선택하지 않았습니다.", Toast.LENGTH_LONG).show();
                } else if (-1 == rgSales.getCheckedRadioButtonId()) {
                    Toast.makeText(getApplicationContext(), "원하는 매출을 선택하지 않았습니다.", Toast.LENGTH_LONG).show();
                } else {
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if (success) {
                                    //등록후 응답받은 값이 true이면 성공 다이얼로그 출력
                                    AlertDialog.Builder builder = new AlertDialog.Builder(SurveyActivity.this);
                                    builder.setTitle("설문완료");
                                    builder.setMessage("상권 분석 화면으로 넘어가시겠습니까?");

                                    SharedPreferences test = getSharedPreferences("check", Activity.MODE_PRIVATE);
                                    SharedPreferences.Editor checkLogin = test.edit();
                                    checkLogin.putBoolean("surveycheck", true);
                                    //꼭 commit()을 해줘야 값이 저장됩니다 ㅎㅎ
                                    checkLogin.commit();

                                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            finish();
                                            Intent intent = new Intent(SurveyActivity.this, RecommendActivity.class);
                                            startActivity(intent);
                                        }
                                    });
                                    builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            finish();
                                        }
                                    });
                                    builder.show();
                                } else {
                                    //등록 실패 했을때 실패 다이얼로그 출력
                                    AlertDialog.Builder builder = new AlertDialog.Builder(SurveyActivity.this);
                                    builder.setTitle("등록실패");
                                    builder.setMessage("등록실패했습니다. 다시 한번 확인 해주세요.");
                                    builder.setPositiveButton("확인", null);
                                    builder.show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    SRegister sRegister = new SRegister(ID, gender, age, location, lReason, type, tReason, sales, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(SurveyActivity.this);
                    queue.add(sRegister);
                }
            }
        });


    }
}
