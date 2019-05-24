package com.example.jws.bzapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SurveyActivity extends AppCompatActivity {

    ImageButton btnBack;
    Button btncancel, btnok;
    RadioGroup rgGender, rgLocation, rgLreason, rgType, rgTreason, rgSales;
    String ID, gender, location, age, lReason, type, tReason, sales;
    String Type;
    String Sales;
    String Location;
    String mJsonString;
    int UPdate;
    private static final String TAG = "Login";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        Intent intent = getIntent();
        ID = intent.getStringExtra("ID");
//        UPdate =intent.getIntExtra("Update",0);
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
                    case R.id.rbGyeongsang:
                        location = "경상도";
                        break;
                    case R.id.rbSeoul:
                        location = "서울특별시";
                        break;
                    case R.id.rbJeonra:
                        location = "전라도";
                        break;
                    case R.id.rbJeju:
                        location = "제주도";
                        break;
                    case R.id.rbchung:
                        location = "충청도";
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
                        type = "retail";
                        break;
                    case R.id.rbLife:
                        type = "life";
                        break;
                    case R.id.rbTour:
                        type = "tour";
                        break;
                    case R.id.rbStay:
                        type = "stay";
                        break;
                    case R.id.rbSports:
                        type = "sports";
                        break;
                    case R.id.rbFood:
                        type = "food";
                        break;
                    case R.id.rbEdu:
                        type = "edu";
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
                        sales = "1";
                        break;
                    case R.id.rbS2:
                        sales = "2";
                        break;
                    case R.id.rbS3:
                        sales = "3";
                        break;
                    case R.id.rbS4:
                        sales = "4";
                        break;
                    case R.id.rbS5:
                        sales = "5";
                        break;
                    case R.id.rbS6:
                        sales = "6";
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

        //insert문실행
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
                                    builder.setMessage("설문조사를 완료하시겠습니까?");

                                    SharedPreferences test = getSharedPreferences("check", Activity.MODE_PRIVATE);
                                    SharedPreferences.Editor checkLogin = test.edit();
                                    checkLogin.putBoolean("surveycheck", true);
                                    //꼭 commit()을 해줘야 값이 저장됩니다 ㅎㅎ
                                    checkLogin.commit();

                                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            finish();
                                            getsurvey task = new getsurvey();
                                            task.execute(ID);
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


//        else if(UPdate==1){  //UPdate문실행
//            btnok = (Button) findViewById(R.id.ok);
//            btnok.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (-1 == rgGender.getCheckedRadioButtonId()) {
//                        Toast.makeText(getApplicationContext(), "성별을 선택하지 않았습니다.", Toast.LENGTH_LONG).show();
//                    } else if (age == null) {
//                        Toast.makeText(getApplicationContext(), "나이를 선택하지 않았습니다.", Toast.LENGTH_LONG).show();
//                    } else if (-1 == rgLocation.getCheckedRadioButtonId()) {
//                        Toast.makeText(getApplicationContext(), "지역을 선택하지 않았습니다.", Toast.LENGTH_LONG).show();
//                    } else if (-1 == rgLreason.getCheckedRadioButtonId()) {
//                        Toast.makeText(getApplicationContext(), "지역 선택 이유를 선택하지 않았습니다.", Toast.LENGTH_LONG).show();
//                    } else if (-1 == rgType.getCheckedRadioButtonId()) {
//                        Toast.makeText(getApplicationContext(), "업종을 선택하지 않았습니다.", Toast.LENGTH_LONG).show();
//                    } else if (-1 == rgTreason.getCheckedRadioButtonId()) {
//                        Toast.makeText(getApplicationContext(), "업종 선택 이유를 선택하지 않았습니다.", Toast.LENGTH_LONG).show();
//                    } else if (-1 == rgSales.getCheckedRadioButtonId()) {
//                        Toast.makeText(getApplicationContext(), "원하는 매출을 선택하지 않았습니다.", Toast.LENGTH_LONG).show();
//                    } else {
//                        Response.Listener<String> responseListener = new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String response) {
//                                try {
//                                    JSONObject jsonResponse = new JSONObject(response);
//                                    boolean success = jsonResponse.getBoolean("success");
//                                    if (success) {
//                                        //등록후 응답받은 값이 true이면 성공 다이얼로그 출력
//                                        AlertDialog.Builder builder = new AlertDialog.Builder(SurveyActivity.this);
//                                        builder.setTitle("설문완료");
//                                        builder.setMessage("상권 분석 화면으로 넘어가시겠습니까?");
//
//                                        SharedPreferences test = getSharedPreferences("check", Activity.MODE_PRIVATE);
//                                        SharedPreferences.Editor checkLogin = test.edit();
//                                        checkLogin.putBoolean("surveycheck", true);
//                                        //꼭 commit()을 해줘야 값이 저장됩니다 ㅎㅎ
//                                        checkLogin.commit();
//
//                                        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialog, int which) {
//                                                finish();
//                                                getsurvey task = new getsurvey();
//                                                task.execute(ID);
//                                            }
//                                        });
//                                        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialog, int which) {
//                                                finish();
//                                            }
//                                        });
//                                        builder.show();
//                                    } else {
//                                        //등록 실패 했을때 실패 다이얼로그 출력
//                                        AlertDialog.Builder builder = new AlertDialog.Builder(SurveyActivity.this);
//                                        builder.setTitle("등록실패");
//                                        builder.setMessage("등록실패했습니다. 다시 한번 확인 해주세요.");
//                                        builder.setPositiveButton("확인", null);
//                                        builder.show();
//                                    }
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        };
//                        SUpdate Supdate = new SUpdate(ID, gender, age, location, lReason, type, tReason, sales, responseListener);
//                        RequestQueue queue = Volley.newRequestQueue(SurveyActivity.this);
//                        queue.add(Supdate);
//                    }
//                }
//            });
//        }

    }




    //등록한 설문조사 가지고오는 곳
    private class getsurvey extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(SurveyActivity.this,
                    "로딩중..", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            Log.d(TAG, "response - " + result);
            if (result == null){
                Toast.makeText(getApplicationContext(),"오류",Toast.LENGTH_SHORT).show();
            }
            else {
                mJsonString = result;
                showResult();
            }
        }


        @Override
        protected String doInBackground(String... params) {

            String ID = params[0];

            String serverURL = "http://qwerr784.cafe24.com/findsurvey.php";
            String postParameters = "ID=" + ID;

            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }


                bufferedReader.close();


                return sb.toString().trim();


            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);
                errorString = e.toString();

                return null;
            }

        }
    }


    private void showResult(){
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("response");

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                Location = item.getString("location");
                Type = item.getString("type");
                Sales = item.getString("sales");
                Intent intent = new Intent(SurveyActivity.this, RecommendActivity.class);
                intent.putExtra("location",Location);
                intent.putExtra("sales",Sales);
                intent.putExtra("type",Type);
                startActivity(intent);

            }
        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }

}


