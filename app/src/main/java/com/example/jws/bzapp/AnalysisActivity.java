package com.example.jws.bzapp;

import android.content.Intent;
import android.graphics.Color;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class AnalysisActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    double mLat = 0;
    double mLong = 0;
    ImageButton btnBack;
    ImageButton btnHome;
    Geocoder geocoder;
    int a;
    Button btnArea;
    Button btnCategory;
    ShopApi shopApi;

    //인구분석
    PieChart pieChart;
    TextView tvtotal, tvonehouse, jumposu;
    String UTM_KX, UTM_KY, addr, token, addrcd, addrnm, sido_nm, onehouse_cnt;

    //건단가 및 월평균 매출
    TextView PFH_retail, PLH_retail, PFH_life, PLH_life, PFH_tour, PLH_tour, PFH_stay, PLH_stay, PFH_sports, PLH_sports, PFH_food, PLH_food, PFH_edu, PLH_edu;
    TextView AFH_retail, ALH_retail, AFH_life, ALH_life, AFH_tour, ALH_tour, AFH_stay, ALH_stay, AFH_sports, ALH_sports, AFH_food, ALH_food, AFH_edu, ALH_edu, AFH_sum, AFH_avg, ALH_sum, ALH_avg;

    //페업자
    TextView tvsido, tvclosure, tvsclosure;
    //평균업력 차트
    PieChart pieChart2;


    String RtotalCount, sido, hangjung, hangjungNm, sidoNm;
    String jumpoRadius;
    String LargeCode[] = new String[21];
    String LargeName[] = new String[21];
    String Alllat, Alllong, AllRadius;

    // 파이차트 사용 색상변수
    int color1, color2, color3, color4, color5, color6, color7;

    String tv1 = "10f";
    String tv2 = "10f";
    String tv3 = "10f";
    String tv4 = "10f";
    String tv5 = "20f";
    String tv6 = "20f";
    String tv7 = "20f";

    //테이블레이아웃 요소
    TextView tvsi, tvgu, tvarea, tvsiResult, tvguResult, tvareaResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);
        geocoder = new Geocoder(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        mLat = intent.getDoubleExtra("mLat", 0);
        mLong = intent.getDoubleExtra("mLong", 0);
        a = intent.getIntExtra("a", 0);
        shopApi = new ShopApi();
        Alllat = String.valueOf(mLat);
        Alllong = String.valueOf(mLong);
        AllRadius = String.valueOf(a);


        jumposu = (TextView) findViewById(R.id.jumpo);

//        Toast.makeText(getApplicationContext(), String.valueOf(mLong) + " " + String.valueOf(mLat), Toast.LENGTH_LONG).show();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        btnCategory = (Button) findViewById(R.id.btnCategory);
        btnArea = (Button) findViewById(R.id.btnArea);

        if (a == 100) {
            btnArea.setText("100m");
            jumpoRadius = "100";
        } else if (a == 200) {
            btnArea.setText("200m");
            jumpoRadius = "200";
        } else if (a == 500) {
            btnArea.setText("500m");
            jumpoRadius = "500";
        } else if (a == 1000) {
            btnArea.setText("1km");
            jumpoRadius = "1000";
        }

        //테이블레이아웃 변수
        tvsi = (TextView) findViewById(R.id.tvSi);
        tvgu = (TextView) findViewById(R.id.tvGu);
        tvarea = (TextView) findViewById(R.id.tvArea);
        tvsiResult = (TextView) findViewById(R.id.tvsiResult);
        tvguResult = (TextView) findViewById(R.id.tvguResult);
        tvareaResult = (TextView) findViewById(R.id.tvareaResult);


                /*
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(AnalysisActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_spinner, null);
                mBuilder.setTitle("업종 선택");
                final Spinner mSpinner = (Spinner) mView.findViewById(R.id.spinnertest);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(AnalysisActivity.this,android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.category));
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinner.setAdapter(adapter);

                mBuilder.setPositiveButton("완료", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        if (!mSpinner.getSelectedItem().toString().equalsIgnoreCase("대분류")){
                            test2 = mSpinner.getSelectedItem().toString();
                            txtcategory.setText(test2);
                            Toast.makeText(AnalysisActivity.this,mSpinner.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();
                            dialogInterface.dismiss();
                        }
                    }
                });

                mBuilder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                });

                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();*/


        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnHome = (ImageButton) findViewById(R.id.btnHome);
        /* final Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);

        //범위 배열
        final String[] number1 = {"범위를 선택해주세요.", "100m", "200m", "500m", "1km"};

        //스피너에 배열 입력
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, number1);
        spinner1.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //범위 값 변수
                test1 = spinner1.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        }); */

        //인구분석
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnHome = (ImageButton) findViewById(R.id.btnHome);
        tvtotal = (TextView) findViewById(R.id.tvtotal);
        tvonehouse = (TextView) findViewById(R.id.tvonehouse);

        //연령별 차트 생성
        pieChart = (PieChart) findViewById(R.id.piechart);

        //평균업력 차트 생성
        pieChart2 = (PieChart) findViewById(R.id.piechart2);

        getToken getToken = new getToken();
        getToken.execute();


        // 인구분석 버튼
        final LinearLayout pLayout1 = (LinearLayout) findViewById(R.id.pLayout1);
        final ImageView pbtn1 = (ImageView) findViewById(R.id.pbtn1);

        pbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pLayout1.getVisibility() == View.VISIBLE) {
                    pLayout1.setVisibility(View.GONE);
                    pbtn1.setImageResource(R.drawable.under);
                } else {
                    pLayout1.setVisibility(View.VISIBLE);
                    pbtn1.setImageResource(R.drawable.over);
                }
            }
        });

        //건단가불러오기
        PFH_retail = (TextView) findViewById(R.id.PFH_retail);
        PLH_retail = (TextView) findViewById(R.id.PLH_retail);
        PFH_life = (TextView) findViewById(R.id.PFH_life);
        PLH_life = (TextView) findViewById(R.id.PLH_life);
        PFH_tour = (TextView) findViewById(R.id.PFH_tour);
        PLH_tour = (TextView) findViewById(R.id.PLH_tour);
        PFH_stay = (TextView) findViewById(R.id.PFH_stay);
        PLH_stay = (TextView) findViewById(R.id.PLH_stay);
        PFH_sports = (TextView) findViewById(R.id.PFH_sports);
        PLH_sports = (TextView) findViewById(R.id.PLH_sports);
        PFH_food = (TextView) findViewById(R.id.PFH_food);
        PLH_food = (TextView) findViewById(R.id.PLH_food);
        PFH_edu = (TextView) findViewById(R.id.PFH_edu);
        PLH_edu = (TextView) findViewById(R.id.PLH_edu);

        //월평균매출
        AFH_retail = (TextView) findViewById(R.id.AFH_retail);
        ALH_retail = (TextView) findViewById(R.id.ALH_retail);
        AFH_life = (TextView) findViewById(R.id.AFH_life);
        ALH_life = (TextView) findViewById(R.id.ALH_life);
        AFH_tour = (TextView) findViewById(R.id.AFH_tour);
        ALH_tour = (TextView) findViewById(R.id.ALH_tour);
        AFH_stay = (TextView) findViewById(R.id.AFH_stay);
        ALH_stay = (TextView) findViewById(R.id.ALH_stay);
        AFH_sports = (TextView) findViewById(R.id.AFH_sports);
        ALH_sports = (TextView) findViewById(R.id.ALH_sports);
        AFH_food = (TextView) findViewById(R.id.AFH_food);
        ALH_food = (TextView) findViewById(R.id.ALH_food);
        AFH_edu = (TextView) findViewById(R.id.AFH_edu);
        ALH_edu = (TextView) findViewById(R.id.ALH_edu);
        AFH_sum = (TextView) findViewById(R.id.AFH_sum);
        ALH_sum = (TextView) findViewById(R.id.ALH_sum);
        AFH_avg = (TextView) findViewById(R.id.AFH_avg);
        ALH_avg = (TextView) findViewById(R.id.ALH_avg);


        // 매출분석 버튼
        final LinearLayout pLayout2 = (LinearLayout) findViewById(R.id.pLayout2);
        final ImageView pbtn2 = (ImageView) findViewById(R.id.pbtn2);
        //총점포수
        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                final String alljumpo = shopApi.RadiuAll(AllRadius, Alllong, Alllat);
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        jumposu.setText(alljumpo + "개");
                    }
                });

            }
        }).start();
        pbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pLayout2.getVisibility() == View.VISIBLE) {
                    pLayout2.setVisibility(View.GONE);
                    pbtn2.setImageResource(R.drawable.under);
                } else {
                    pLayout2.setVisibility(View.VISIBLE);
                    pbtn2.setImageResource(R.drawable.over);
                }
            }
        });

        //페업자
        tvsido = (TextView) findViewById(R.id.tvsido);
        tvclosure = (TextView) findViewById(R.id.tvclosure);
        tvsclosure = (TextView) findViewById(R.id.tvsclosure);
        // 상권분석 버튼
        final LinearLayout pLayout3 = (LinearLayout) findViewById(R.id.pLayout3);
        final ImageView pbtn3 = (ImageView) findViewById(R.id.pbtn3);

        pbtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pLayout3.getVisibility() == View.VISIBLE) {
                    pLayout3.setVisibility(View.GONE);
                    pbtn3.setImageResource(R.drawable.under);
                } else {
                    pLayout3.setVisibility(View.VISIBLE);
                    pbtn3.setImageResource(R.drawable.over);
                }
            }
        });

        // 점포현황 버튼
        final LinearLayout pLayout4 = (LinearLayout) findViewById(R.id.pLayout4);
        final ImageView pbtn4 = (ImageView) findViewById(R.id.pbtn4);

        pbtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pLayout4.getVisibility() == View.VISIBLE) {
                    pLayout4.setVisibility(View.GONE);
                    pbtn4.setImageResource(R.drawable.under);
                } else {
                    pLayout4.setVisibility(View.VISIBLE);
                    pbtn4.setImageResource(R.drawable.over);
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnalysisActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnalysisActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }


    //anal.xml의 android:onClick 이용해서 메서드 정의
    public void mOnPopupClick(View v) {
        //데이터 담아서 팝업(액티비티) 호출
        Intent intent = new Intent(this, Dialog_Area.class);
        intent.putExtra("area", a);
        startActivityForResult(intent, 1);
    }

    public void mOnPopupClick2(View v) {
        //데이터 담아서 팝업(액티비티) 호출
        final Intent intent = new Intent(this, Dialog_Category.class);
        new Thread(new Runnable() {
            @Override
            public void run() {
                LgetData();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        intent.putExtra("JumpoRadius", jumpoRadius);
                        intent.putExtra("LargeName", LargeName);
                        intent.putExtra("LargeCode", LargeCode);
                        intent.putExtra("mLong", Alllong);
                        intent.putExtra("mLat", Alllat);
                        startActivityForResult(intent, 2);
                    }
                });
            }
        }).start();

    }

    //다이얼로그 실행후 결과값 받는 메서드
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                //데이터 받기
                jumpoRadius = data.getStringExtra("result");
                btnArea.setText(jumpoRadius);
            }
        } else if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                final LinearLayout jumpoResult = (LinearLayout) findViewById(R.id.jumpoResult);
                jumpoResult.setVisibility(View.VISIBLE);
                //데이터 받기
                String result = data.getStringExtra("category");
                RtotalCount = data.getStringExtra("RtotalCount");
                sido = data.getStringExtra("sidosu");
                hangjung = data.getStringExtra("hangjungsu");
                sidoNm = data.getStringExtra("sidoNm");
                hangjungNm = data.getStringExtra("hangjungNm");
                btnCategory.setText(result);

                // 테이블 레이아웃 세팅
                tvsi.setText(sidoNm);
                tvsiResult.setText(sido + "개");
                tvgu.setText(hangjungNm);
                tvguResult.setText(hangjung + "개");
                tvarea.setText("반경 내");
                tvareaResult.setText(RtotalCount + "개");

            }
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng sydney = new LatLng(mLat, mLong);
        if (a == 100) {
            onAddCircle100(100);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 17));
        } else if (a == 200) {
            onAddCircle100(200);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 16));
        } else if (a == 500) {
            onAddCircle100(500);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15));
        } else if (a == 1000) {
            onAddCircle100(1000);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 14));
        }
        // Add a marker in Sydney and move the camera


//        onAddCircle(100);
    }

    //
    public void onAddCircle100(int a) {
        LatLng position = new LatLng(mLat, mLong);

        //나의 위치 마커
        //MarkerOptions mymarker = new MarkerOptions().position(position);

        //사이클 반경 설정
        CircleOptions circle = new CircleOptions().center(position).radius(a)//반경
                .strokeWidth(3)//선 넓이
                .fillColor(0x44808080)
                .strokeColor(Color.GRAY);
        //this.mGoogleMap.addMarker(mymarker);
        this.mMap.addCircle(circle);
    }

    public void onAddCircle200(int a) {
        LatLng position = new LatLng(mLat, mLong);

        //나의 위치 마커
        //MarkerOptions mymarker = new MarkerOptions().position(position);

        //사이클 반경 설정
        CircleOptions circle = new CircleOptions().center(position).radius(a)//반경
                .strokeWidth(3)//선 넓이
                .fillColor(0x44808080)
                .strokeColor(Color.GRAY);
        //this.mGoogleMap.addMarker(mymarker);
        this.mMap.addCircle(circle);
    }

    public void onAddCircle500(int a) {
        LatLng position = new LatLng(mLat, mLong);

        //나의 위치 마커
        //MarkerOptions mymarker = new MarkerOptions().position(position);

        //사이클 반경 설정
        CircleOptions circle = new CircleOptions().center(position).radius(a)//반경
                .strokeWidth(3)//선 넓이
                .fillColor(0x44808080)
                .strokeColor(Color.GRAY);
        //this.mGoogleMap.addMarker(mymarker);
        this.mMap.addCircle(circle);
    }

    public void onAddCircle1000(int a) {
        LatLng position = new LatLng(mLat, mLong);

        //나의 위치 마커
        //MarkerOptions mymarker = new MarkerOptions().position(position);

        //사이클 반경 설정
        CircleOptions circle = new CircleOptions().center(position).radius(a)//반경
                .strokeWidth(3)//선 넓이
                .fillColor(0x44808080)
                .strokeColor(Color.GRAY);
        //this.mGoogleMap.addMarker(mymarker);
        this.mMap.addCircle(circle);

    }

    public class getToken extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                //서버에 있는 php 실행
                URL url = new URL("https://sgisapi.kostat.go.kr/OpenAPI3/auth/authentication.json?consumer_key=6140fd53e79c44e08321&consumer_secret=2a95a807ff5b4fc48414");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while ((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                //결과 값을 리턴
                return stringBuilder.toString().trim();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONObject object = jsonObject.getJSONObject("result");

                token = object.getString("accessToken");
//                Toast.makeText(getApplicationContext(), token, Toast.LENGTH_LONG).show();
                Change change = new Change();
                change.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class Change extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... voids) {
            try {
                //서버에 있는 php 실행
                URL url = new URL("https://sgisapi.kostat.go.kr/OpenAPI3/transformation/transcoord.json?accessToken=" + token + "&src=4326&dst=5179&posX=" + mLong + "&posY=" + mLat);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while ((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                //결과 값을 리턴
                return stringBuilder.toString().trim();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {

            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONObject object = jsonObject.getJSONObject("result");

                UTM_KX = object.getString("posX");
                UTM_KY = object.getString("posY");
//                Toast.makeText(getApplicationContext(), UTM_KX + "  " + UTM_KY, Toast.LENGTH_LONG).show();
                Achange achange = new Achange();
                achange.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    }

    public class Achange extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... voids) {
            try {
                //서버에 있는 php 실행
                URL url = new URL("https://sgisapi.kostat.go.kr/OpenAPI3/addr/rgeocode.json?accessToken=" + token + "&x_coor=" + UTM_KX + "&y_coor=" + UTM_KY + "&addr_type=20");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while ((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                //결과 값을 리턴
                return stringBuilder.toString().trim();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("result");

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject item = jsonArray.getJSONObject(i);

                    addr = item.getString("full_addr");
                    addrcd = item.getString("sido_cd") + item.getString("sgg_cd") + item.getString("emdong_cd");
                    sido_nm = item.getString("sido_nm");
                    addrnm = item.getString("sido_nm") + " " + item.getString("sgg_nm");

                }

                tvsido.setText(sido_nm);
                getavgcareer();
                Population();
                getClosure();
                getPercost();
                getavgsales();
//                Toast.makeText(getApplicationContext(), addrcd, Toast.LENGTH_LONG).show();
//                Toast.makeText(getApplicationContext(), addr, Toast.LENGTH_LONG).show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    public class OneHouse extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... voids) {
            try {
                //서버에 있는 php 실행
                URL url = new URL("https://sgisapi.kostat.go.kr/OpenAPI3/stats/household.json?accessToken=" + token + "&year=2017&adm_cd=" + addrcd + "&household_type=A0");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while ((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                //결과 값을 리턴
                return stringBuilder.toString().trim();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {

            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("result");

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject item = jsonArray.getJSONObject(i);

                    onehouse_cnt = item.getString("household_cnt");
//                    Toast.makeText(getApplicationContext(), onehouse_cnt + tvtotal.getText().toString(), Toast.LENGTH_LONG).show();
                    double onehouse = Integer.parseInt(onehouse_cnt);
                    String Ptotal = tvtotal.getText().toString();
                    Ptotal = Ptotal.substring(0, Ptotal.length() - 1);
                    double Pdtotal = Integer.parseInt(Ptotal);
//                    Toast.makeText(getApplicationContext(), onehouse + "  " + total, Toast.LENGTH_LONG).show();
                    double one = (onehouse / Pdtotal) * 100;
                    String percent = String.format("%.2f", one) + " %";
                    tvonehouse.setText(percent);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    }

    public void Population() {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArray = jsonObject.getJSONArray("response");
                    int count = 0;
                    String child = null, teenage = null, twenty = null, thirty = null, forty = null, fifty = null, sixty = null;
                    while (count < jsonArray.length()) {
                        JSONObject object = jsonArray.getJSONObject(count);
                        object.getString("address");
                        tvtotal.setText(object.getString("total") + "명");
                        child = object.getString("child");
                        teenage = object.getString("teenage");
                        twenty = object.getString("twenty");
                        thirty = object.getString("thirty");
                        forty = object.getString("forty");
                        fifty = object.getString("fifty");
                        sixty = object.getString("sixty");
                        count++;
                    }

                    String Ptotal2 = tvtotal.getText().toString();
                    Ptotal2 = Ptotal2.substring(0, Ptotal2.length() - 1);

                    //차트 드래그 비활성화
                    pieChart.setUsePercentValues(true);
                    pieChart.setTouchEnabled(false);
                    ArrayList<Entry> yvalues = new ArrayList<Entry>();
                    yvalues.add(new Entry(Float.parseFloat(String.format("%.2f", Double.valueOf(child) / Double.valueOf(Ptotal2) * 100)), 0));
                    yvalues.add(new Entry(Float.parseFloat(String.format("%.2f", Double.valueOf(teenage) / Double.valueOf(Ptotal2) * 100)), 1));
                    yvalues.add(new Entry(Float.parseFloat(String.format("%.2f", Double.valueOf(twenty) / Double.valueOf(Ptotal2) * 100)), 2));
                    yvalues.add(new Entry(Float.parseFloat(String.format("%.2f", Double.valueOf(thirty) / Double.valueOf(Ptotal2) * 100)), 3));
                    yvalues.add(new Entry(Float.parseFloat(String.format("%.2f", Double.valueOf(forty) / Double.valueOf(Ptotal2) * 100)), 4));
                    yvalues.add(new Entry(Float.parseFloat(String.format("%.2f", Double.valueOf(fifty) / Double.valueOf(Ptotal2) * 100)), 5));
                    yvalues.add(new Entry(Float.parseFloat(String.format("%.2f", Double.valueOf(sixty) / Double.valueOf(Ptotal2) * 100)), 6));
                    PieDataSet dataSet = new PieDataSet(yvalues, "");
                    color1 = Color.rgb(204, 93, 221);
                    color2 = Color.rgb(95, 204, 221);
                    color3 = Color.rgb(224, 96, 122);
                    color4 = Color.rgb(87, 121, 168);
                    color5 = Color.rgb(198, 137, 83);
                    color6 = Color.rgb(50, 65, 163);
                    color7 = Color.rgb(43, 140, 133);
                    dataSet.setColors(new int[]{color1, color2, color3, color4, color5, color6, color7});
                    ArrayList<String> xVals = new ArrayList<String>();
                    xVals.add("10대 미만");
                    xVals.add("10대");
                    xVals.add("20대");
                    xVals.add("30대");
                    xVals.add("40대");
                    xVals.add("50대");
                    xVals.add("60대 이상");
                    PieData data = new PieData(xVals, dataSet);
                    data.setValueFormatter(new PercentFormatter());
                    pieChart.setData(data);
                    pieChart.setDrawSliceText(false);
                    pieChart.setDescription("");
                    pieChart.setDrawHoleEnabled(true);
                    pieChart.setTransparentCircleRadius(40f);
                    Legend i = pieChart.getLegend();
                    i.setPosition(Legend.LegendPosition.RIGHT_OF_CHART_CENTER);
                    i.setTextSize(13f);
                    pieChart.setHoleRadius(40f);
                    data.setValueTextSize(12f);
                    data.setValueTextColor(Color.WHITE);
                    OneHouse oneHouse = new OneHouse();
                    oneHouse.execute();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        FindApopulation findApopulation = new FindApopulation(addr, responseListener);
        RequestQueue queue = Volley.newRequestQueue(AnalysisActivity.this);
        queue.add(findApopulation);
    }

    public void getavgcareer() {

//        Toast.makeText(getApplicationContext(), addrnm, Toast.LENGTH_LONG).show();
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArray = jsonObject.getJSONArray("response");
                    int count = 0;
                    String one = null, two = null, three = null, four = null, five = null;
                    while (count < jsonArray.length()) {
                        JSONObject object = jsonArray.getJSONObject(count);
                        object.getString("location");
                        one = object.getString("one");
                        two = object.getString("two");
                        three = object.getString("three");
                        four = object.getString("four");
                        five = object.getString("five");
                        count++;
                    }
                    //차트 드래그 비활성화
                    pieChart2.setUsePercentValues(true);
                    pieChart2.setTouchEnabled(false);
                    //pieChart.setDragDecelerationEnabled(false);

                    // IMPORTANT: In a PieChart, no values (Entry) should have the same
                    // xIndex (even if from different DataSets), since no values can be
                    // drawn above each other.
                    ArrayList<Entry> yvalues2 = new ArrayList<Entry>();
                    yvalues2.add(new Entry(Float.parseFloat(one), 0));
                    yvalues2.add(new Entry(Float.parseFloat(two), 1));
                    yvalues2.add(new Entry(Float.parseFloat(three), 2));
                    yvalues2.add(new Entry(Float.parseFloat(four), 3));
                    yvalues2.add(new Entry(Float.parseFloat(five), 4));

                    //라벨에 텍스트 추가하면 출처 및 설명 가능
                    PieDataSet dataSet2 = new PieDataSet(yvalues2, "");
                    color1 = Color.rgb(204, 93, 221);
                    color2 = Color.rgb(95, 204, 221);
                    color3 = Color.rgb(224, 96, 122);
                    color4 = Color.rgb(87, 121, 168);
                    color5 = Color.rgb(198, 137, 83);
                    color6 = Color.rgb(50, 65, 163);
                    color7 = Color.rgb(43, 140, 133);

                    //새롭게 color 지정하는 방법
                    dataSet2.setColors(new int[]{color1, color2, color3, color4, color5, color6, color7});

                    ArrayList<String> xVals2 = new ArrayList<String>();

                    xVals2.add("1년 미만");
                    xVals2.add("1년 이상 2년 미만");
                    xVals2.add("2년 이상 3년 미만");
                    xVals2.add("3년 이상 5년 미만");
                    xVals2.add("5년 이상");

                    PieData data2 = new PieData(xVals2, dataSet2);

                    // In Percentage
                    data2.setValueFormatter(new PercentFormatter());
                    // Default value
                    //data.setValueFormatter(new DefaultValueFormatter(0));
                    pieChart2.setData(data2);


                    pieChart2.setDescription("");
                    /*pieChart.setDescriptionPosition(550,100);*/
                    //출처 및 설명
                    //pieChart.setDescription("This is Pie Chart");
                    //구멍뚫기
                    pieChart2.setDrawHoleEnabled(true);
                    pieChart2.setTransparentCircleRadius(40f);

                    //원그래프 텍스트 없애기
                    pieChart2.setDrawSliceText(false);

                    // 각각의 요소설명 위치 지정
                    Legend i2 = pieChart2.getLegend();
                    i2.setPosition(Legend.LegendPosition.RIGHT_OF_CHART_CENTER);
                    i2.setTextSize(13f);

                    pieChart2.setHoleRadius(40f);
                    //dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);

                    //dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

                    //dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

                    //dataSet.setColors(ColorTemplate.LIBERTY_COLORS);

                    //dataSet.setColors(ColorTemplate.PASTEL_COLORS);

                    data2.setValueTextSize(12f);
                    data2.setValueTextColor(Color.WHITE);

                    //pieChart.setOnChartValueSelectedListener(this);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        Findavgcareer findavgcareer = new Findavgcareer(sido_nm, responseListener);
        RequestQueue queue = Volley.newRequestQueue(AnalysisActivity.this);
        queue.add(findavgcareer);
    }

    public void getClosure() {

//        Toast.makeText(getApplicationContext(), addrnm, Toast.LENGTH_LONG).show();
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArray = jsonObject.getJSONArray("response");
                    int count = 0;
                    double ctotal = 0, cstotal = 0, ccnt = 0;
                    while (count < jsonArray.length()) {
                        JSONObject object = jsonArray.getJSONObject(count);
                        object.getString("location");
                        ctotal = Double.parseDouble(object.getString("total"));
                        cstotal = Double.parseDouble(object.getString("stotal"));
                        ccnt = Double.parseDouble(object.getString("cnt"));
                        count++;
                    }

                    tvclosure.setText(String.format("%.2f", (ccnt / ctotal) * 100) + " %");
                    tvsclosure.setText(String.format("%.2f", (ccnt / cstotal) * 100) + " %");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        Findclosure findclosure = new Findclosure(addrnm, responseListener);
        RequestQueue queue = Volley.newRequestQueue(AnalysisActivity.this);
        queue.add(findclosure);
    }


    public void getPercost() {

//        Toast.makeText(getApplicationContext(), addrnm, Toast.LENGTH_LONG).show();
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArray = jsonObject.getJSONArray("response");
                    int count = 0;
                    while (count < jsonArray.length()) {
                        JSONObject object = jsonArray.getJSONObject(count);
                        object.getString("location");
                        PFH_retail.setText(object.getString("FH_retail"));
                        PLH_retail.setText(object.getString("LH_retail"));
                        PFH_life.setText(object.getString("FH_life"));
                        PLH_life.setText(object.getString("LH_life"));
                        PFH_tour.setText(object.getString("FH_tour"));
                        PLH_tour.setText(object.getString("LH_tour"));
                        PFH_stay.setText(object.getString("FH_stay"));
                        PLH_stay.setText(object.getString("LH_stay"));
                        PFH_sports.setText(object.getString("FH_sports"));
                        PLH_sports.setText(object.getString("LH_sports"));
                        PFH_food.setText(object.getString("FH_food"));
                        PLH_food.setText(object.getString("LH_food"));
                        PFH_edu.setText(object.getString("FH_edu"));
                        PLH_edu.setText(object.getString("LH_edu"));
                        count++;
                    }

                    OneHouse oneHouse = new OneHouse();
                    oneHouse.execute();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        FindPercost findPercost = new FindPercost(addrnm, responseListener);
        RequestQueue queue = Volley.newRequestQueue(AnalysisActivity.this);
        queue.add(findPercost);
    }

    public void getavgsales() {

//        Toast.makeText(getApplicationContext(), addrnm, Toast.LENGTH_LONG).show();
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArray = jsonObject.getJSONArray("response");
                    int count = 0;
                    while (count < jsonArray.length()) {
                        JSONObject object = jsonArray.getJSONObject(count);
                        object.getString("location");
                        AFH_retail.setText(object.getString("FH_retail"));
                        ALH_retail.setText(object.getString("LH_retail"));
                        AFH_life.setText(object.getString("FH_life"));
                        ALH_life.setText(object.getString("LH_life"));
                        AFH_tour.setText(object.getString("FH_tour"));
                        ALH_tour.setText(object.getString("LH_tour"));
                        AFH_stay.setText(object.getString("FH_stay"));
                        ALH_stay.setText(object.getString("LH_stay"));
                        AFH_sports.setText(object.getString("FH_sports"));
                        ALH_sports.setText(object.getString("LH_sports"));
                        AFH_food.setText(object.getString("FH_food"));
                        ALH_food.setText(object.getString("LH_food"));
                        AFH_edu.setText(object.getString("FH_edu"));
                        ALH_edu.setText(object.getString("LH_edu"));
                        AFH_sum.setText(object.getString("FH_sum"));
                        ALH_sum.setText(object.getString("LH_sum"));
                        AFH_avg.setText(object.getString("FH_avg"));
                        ALH_avg.setText(object.getString("LH_avg"));
                        count++;
                    }

                    OneHouse oneHouse = new OneHouse();
                    oneHouse.execute();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        Findavgsales findavgsales = new Findavgsales(addrnm, responseListener);
        RequestQueue queue = Volley.newRequestQueue(AnalysisActivity.this);
        queue.add(findavgsales);
    }


    public void LgetData() {
        StringBuffer buffer = new StringBuffer();
        String queryUrl = "http://apis.data.go.kr/B553077/api/open/sdsc/largeUpjongList?" +
                "ServiceKey=MxfED6C3Sd6Ja7QuU2BNU8xqBX5Yiy26t4sWS0PWUm%2B6WFjChgI3KoNQRMdO9LM5xvKfXOtMIh40XqadzCbTfw%3D%3D";
        int su = 0;
        try {
            URL url = new URL(queryUrl);//문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is = url.openStream(); //url위치로 입력스트림 연결

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new InputStreamReader(is, "UTF-8")); //inputstream 으로부터 xml 입력받기
            String tag;
            xpp.next();
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        buffer.append("파싱 시작...\n\n");
                        break;

                    case XmlPullParser.START_TAG:
                        tag = xpp.getName();//태그 이름 얻어오기

                        if (tag.equals("item")) ;// 첫번째 검색결과
                        else if (tag.equals("indsLclsCd")) {
                            xpp.next();
                            LargeCode[su] = xpp.getText();
                        } else if (tag.equals("indsLclsNm")) {
                            xpp.next();
                            LargeName[su] = xpp.getText();
                            su++;
                        }
                        break;
                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag = xpp.getName(); //태그 이름 얻어오기
                        if (tag.equals("item")) buffer.append("\n");// 첫번째 검색결과종료..줄바꿈
                        break;
                }
                eventType = xpp.next();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch blocke.printStackTrace();
        }
    }


}