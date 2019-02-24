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
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class SurveyActivity extends AppCompatActivity {

    ImageButton btnBack;
    Button btncancel, btnok;
    RadioGroup rgGender, rgLocation, rgLreason, rgType, rgTreason;
    String ID, gender, location, age, lReason, Type, tReason;

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

        rgLreason = (RadioGroup)findViewById(R.id.rgLreason);
        rgLreason.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rbGender = (RadioButton) findViewById(checkedId);
                switch (checkedId) {
                    case R.id.rbL1:
                        lReason = "1";
                        break;
                    case R.id.rbL2:
                        lReason = "2";
                        break;
                    case R.id.rbL3:
                        lReason = "3";
                        break;
                    case R.id.rbL4:
                        lReason = "4";
                        break;
                }
            }
        });

        rgType = (RadioGroup)findViewById(R.id.rgType);
        rgType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rbGender = (RadioButton) findViewById(checkedId);
                switch (checkedId) {
                    case R.id.rbRetail:
                        Type = "소매";
                        break;
                    case R.id.rbLife:
                        Type = "생활서비스";
                        break;
                    case R.id.rbTour:
                        Type = "관광/여가/오락";
                        break;
                    case R.id.rbStay:
                        Type = "숙박";
                        break;
                    case R.id.rbSports:
                        Type = "스포츠";
                        break;
                    case R.id.rbFood:
                        Type = "음식";
                        break;
                    case R.id.rbEdu:
                        Type = "학문/교육";
                        break;
                }
            }
        });

        rgTreason = (RadioGroup)findViewById(R.id.rgTreason);
        rgTreason.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rbGender = (RadioButton) findViewById(checkedId);
                switch (checkedId) {
                    case R.id.rbT1:
                        tReason = "1";
                        break;
                    case R.id.rbT2:
                        tReason = "2";
                        break;
                    case R.id.rbT3:
                        tReason = "3";
                        break;
                    case R.id.rbT4:
                        tReason = "4";
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
                    Toast.makeText(getApplicationContext(), "지역선택이유를 선택하지 않았습니다.", Toast.LENGTH_LONG).show();
                }  else if (-1 == rgType.getCheckedRadioButtonId()) {
                    Toast.makeText(getApplicationContext(), "업종을 선택하지 않았습니다.", Toast.LENGTH_LONG).show();
                } else if (-1 == rgTreason.getCheckedRadioButtonId()) {
                    Toast.makeText(getApplicationContext(), "업종선택이유를 선택하지 않았습니다.", Toast.LENGTH_LONG).show();
                } else {
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
            }
        });
    }
}
