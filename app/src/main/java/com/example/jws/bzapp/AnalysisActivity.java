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
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
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
    PieChart pieChartPopulation;
    TextView tvtotal, tvonehouse, jumposu;
    String UTM_KX, UTM_KY, addr, token, addrcd, addrnm, sido_nm, onehouse_cnt;

    //평균 매출, 건단가 차트
    BarChart barChartAvgsales, barChartPercost, barChartAvg;

    //페업자
    TextView tvsido, tvclosure, tvsclosure;
    //평균업력 차트
    PieChart pieChartavgcareer;


    String RtotalCount, sido, hangjung, hangjungNm, sidoNm;
    String jumpoRadius;
    String LargeCode[] = new String[21];
    String LargeName[] = new String[21];
    String Alllat, Alllong, AllRadius;

    // 파이차트 사용 색상변수
    int color1, color2, color3, color4, color5, color6, color7;

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

        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnHome = (ImageButton) findViewById(R.id.btnHome);


        //인구분석
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnHome = (ImageButton) findViewById(R.id.btnHome);
        tvtotal = (TextView) findViewById(R.id.tvtotal);
        tvonehouse = (TextView) findViewById(R.id.tvonehouse);

        //연령별 차트 생성
        pieChartPopulation = (PieChart) findViewById(R.id.piechart);

        //평균업력 차트 생성
        pieChartavgcareer = (PieChart) findViewById(R.id.piechart2);

        //평균 매출, 건단가 차트 생성
        barChartAvgsales = (BarChart) findViewById(R.id.barChart);
        barChartPercost = (BarChart) findViewById(R.id.barChart2);
        barChartAvg = (BarChart) findViewById(R.id.barChart3);

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

        // 매출분석 버튼
        final LinearLayout pLayout2 = (LinearLayout) findViewById(R.id.pLayout2);
        final ImageView pbtn2 = (ImageView) findViewById(R.id.pbtn2);
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
                URL url = new URL("https://sgisapi.kostat.go.kr/OpenAPI3/auth/authentication.json?consumer_key=ec449a7bec3b40f0b0a2&consumer_secret=b4a68932a59f4284b4de");
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
                    pieChartPopulation.setUsePercentValues(true);
                    pieChartPopulation.setTouchEnabled(false);
                    ArrayList<Entry> yvalues = new ArrayList<Entry>();
                    yvalues.add(new Entry(Float.parseFloat(String.format("%.2f", Double.valueOf(child) / Double.valueOf(Ptotal2) * 100)), 0));
                    yvalues.add(new Entry(Float.parseFloat(String.format("%.2f", Double.valueOf(teenage) / Double.valueOf(Ptotal2) * 100)), 1));
                    yvalues.add(new Entry(Float.parseFloat(String.format("%.2f", Double.valueOf(twenty) / Double.valueOf(Ptotal2) * 100)), 2));
                    yvalues.add(new Entry(Float.parseFloat(String.format("%.2f", Double.valueOf(thirty) / Double.valueOf(Ptotal2) * 100)), 3));
                    yvalues.add(new Entry(Float.parseFloat(String.format("%.2f", Double.valueOf(forty) / Double.valueOf(Ptotal2) * 100)), 4));
                    yvalues.add(new Entry(Float.parseFloat(String.format("%.2f", Double.valueOf(fifty) / Double.valueOf(Ptotal2) * 100)), 5));
                    yvalues.add(new Entry(Float.parseFloat(String.format("%.2f", Double.valueOf(sixty) / Double.valueOf(Ptotal2) * 100)), 6));
                    PieDataSet dataSet = new PieDataSet(yvalues, "");
                    color1 = Color.rgb(229, 70, 90);
                    color2 = Color.rgb(249, 159, 89);
                    color3 = Color.rgb(255, 222, 106);
                    color4 = Color.rgb(101, 218, 173);
                    color5 = Color.rgb(28, 120, 177);
                    color6 = Color.rgb(52, 178, 228);
                    color7 = Color.rgb(184, 126, 247);
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
                    pieChartPopulation.setData(data);
                    pieChartPopulation.setDrawSliceText(false);
                    pieChartPopulation.setDescription("");
                    pieChartPopulation.setDrawHoleEnabled(true);
                    pieChartPopulation.setTransparentCircleRadius(40f);
                    Legend i = pieChartPopulation.getLegend();
                    i.setPosition(Legend.LegendPosition.RIGHT_OF_CHART_CENTER);
                    i.setTextSize(13f);
                    pieChartPopulation.setHoleRadius(40f);
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
                    pieChartavgcareer.setUsePercentValues(true);
                    pieChartavgcareer.setTouchEnabled(false);
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
                    color1 = Color.rgb(229, 70, 90);
                    color2 = Color.rgb(249, 159, 89);
                    color3 = Color.rgb(255, 222, 106);
                    color4 = Color.rgb(101, 218, 173);
                    color5 = Color.rgb(28, 120, 177);
                    color6 = Color.rgb(52, 178, 228);
                    color7 = Color.rgb(184, 126, 247);
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
                    pieChartavgcareer.setData(data2);


                    pieChartavgcareer.setDescription("");
                    /*pieChart.setDescriptionPosition(550,100);*/
                    //출처 및 설명
                    //pieChart.setDescription("This is Pie Chart");
                    //구멍뚫기
                    pieChartavgcareer.setDrawHoleEnabled(true);
                    pieChartavgcareer.setTransparentCircleRadius(40f);

                    //원그래프 텍스트 없애기
                    pieChartavgcareer.setDrawSliceText(false);

                    // 각각의 요소설명 위치 지정
                    Legend i2 = pieChartavgcareer.getLegend();
                    i2.setPosition(Legend.LegendPosition.RIGHT_OF_CHART_CENTER);
                    i2.setTextSize(13f);

                    pieChartavgcareer.setHoleRadius(40f);
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
                String PFH_retail = null, PLH_retail = null, PFH_life = null, PLH_life = null, PFH_tour = null, PLH_tour = null, PFH_stay = null,
                        PLH_stay = null, PFH_sports = null, PLH_sports = null, PFH_food = null, PLH_food = null, PFH_edu = null, PLH_edu = null;
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArray = jsonObject.getJSONArray("response");
                    int count = 0;
                    while (count < jsonArray.length()) {
                        JSONObject object = jsonArray.getJSONObject(count);
                        object.getString("location");
                        PFH_retail = object.getString("FH_retail");
                        PLH_retail = object.getString("LH_retail");
                        PFH_life = object.getString("FH_life");
                        PLH_life = object.getString("LH_life");
                        PFH_tour = object.getString("FH_tour");
                        PLH_tour = object.getString("LH_tour");
                        PFH_stay = object.getString("FH_stay");
                        PLH_stay = object.getString("LH_stay");
                        PFH_sports = object.getString("FH_sports");
                        PLH_sports = object.getString("LH_sports");
                        PFH_food = object.getString("FH_food");
                        PLH_food = object.getString("LH_food");
                        PFH_edu = object.getString("FH_edu");
                        PLH_edu = object.getString("LH_edu");
                        count++;
                    }

                    YAxis leftAxis = barChartPercost.getAxisLeft();
                    YAxis rightAxis = barChartPercost.getAxisRight();
                    XAxis xAxis = barChartPercost.getXAxis();

                    // 텍스트 위치 지정
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xAxis.setTextSize(12f);

                    //그래프 맨 아래 가로선 유무, 그래프 내부 세로선 유무,
                    xAxis.setDrawAxisLine(true);
                    xAxis.setDrawGridLines(true);
                    leftAxis.setTextSize(10f);

                    //y축 텍스트 표시 (값), 그래프 맨 왼쪽 세로선 유무, 그래프 내부 가로선  유무
                    leftAxis.setDrawLabels(true);
                    leftAxis.setDrawAxisLine(true);
                    leftAxis.setDrawGridLines(false);

                    //나머지 전부다 false 해야함
                    rightAxis.setDrawAxisLine(false);
                    rightAxis.setDrawGridLines(false);
                    rightAxis.setDrawLabels(false);

                    // 그룹에 추가
                    ArrayList<BarEntry> bargroup1 = new ArrayList<>();
                    ArrayList<BarEntry> bargroup2 = new ArrayList<>();
                    //상반기 건단가
                    bargroup1.add(new BarEntry(Float.parseFloat(String.format("%.1f", Float.parseFloat(PFH_retail) / 1000)), 0));
                    bargroup1.add(new BarEntry(Float.parseFloat(String.format("%.1f", Float.parseFloat(PFH_life) / 1000)), 1));
                    bargroup1.add(new BarEntry(Float.parseFloat(String.format("%.1f", Float.parseFloat(PFH_tour) / 1000)), 2));
                    bargroup1.add(new BarEntry(Float.parseFloat(String.format("%.1f", Float.parseFloat(PFH_stay) / 1000)), 3));
                    bargroup1.add(new BarEntry(Float.parseFloat(String.format("%.1f", Float.parseFloat(PFH_sports) / 1000)), 4));
                    bargroup1.add(new BarEntry(Float.parseFloat(String.format("%.1f", Float.parseFloat(PFH_food) / 1000)), 5));
                    bargroup1.add(new BarEntry(Float.parseFloat(String.format("%.1f", Float.parseFloat(PFH_edu) / 1000)), 6));
                    //하반기 건단가
                    bargroup2.add(new BarEntry(Float.parseFloat(String.format("%.1f", Float.parseFloat(PLH_retail) / 1000)), 0));
                    bargroup2.add(new BarEntry(Float.parseFloat(String.format("%.1f", Float.parseFloat(PLH_life) / 1000)), 1));
                    bargroup2.add(new BarEntry(Float.parseFloat(String.format("%.1f", Float.parseFloat(PLH_tour) / 1000)), 2));
                    bargroup2.add(new BarEntry(Float.parseFloat(String.format("%.1f", Float.parseFloat(PLH_stay) / 1000)), 3));
                    bargroup2.add(new BarEntry(Float.parseFloat(String.format("%.1f", Float.parseFloat(PLH_sports) / 1000)), 4));
                    bargroup2.add(new BarEntry(Float.parseFloat(String.format("%.1f", Float.parseFloat(PLH_food) / 1000)), 5));
                    bargroup2.add(new BarEntry(Float.parseFloat(String.format("%.1f", Float.parseFloat(PLH_edu) / 1000)), 6));

                    BarDataSet barDataSet1 = new BarDataSet(bargroup1, "상반기");
                    BarDataSet barDataSet2 = new BarDataSet(bargroup2, "하반기");

                    //바 색상
                    barDataSet1.setColor(Color.rgb(65, 105, 225));
                    barDataSet2.setColor(Color.rgb(128, 128, 128));
                    // barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);


                    ArrayList<String> labels = new ArrayList<String>();
                    labels.add("소매");
                    labels.add("생활서비스");
                    labels.add("관광/여가/오락");
                    labels.add("숙박");
                    labels.add("스포츠");
                    labels.add("음식");
                    labels.add("학문/교육");

                    ArrayList<BarDataSet> dataSets = new ArrayList<>();  // mbined all dataset into an arraylistco
                    dataSets.add(barDataSet1);
                    dataSets.add(barDataSet2);

                    //그래프 아래 카테고리 표시 지우기
                    Legend i = barChartPercost.getLegend();
                    //i.setEnabled(false);
                    i.setPosition(Legend.LegendPosition.ABOVE_CHART_RIGHT);
                    i.setTextSize(13f);
                    // 막대그래프 디자인
                    BarData data = new BarData(labels, dataSets);
                    data.setValueTextColor(Color.BLACK);
                    barChartPercost.setData(data);
                    barChartPercost.setTouchEnabled(false);
                    barChartPercost.setDescription("");
                    barChartPercost.invalidate(); // refresh
                    barChartPercost.setScaleEnabled(false);
                    // 그래프 배경
                    barChartPercost.setGridBackgroundColor(Color.rgb(248, 248, 248));
                    barChartPercost.animateXY(2000, 2000);
                    barChartPercost.setDrawBorders(false);
                    barChartPercost.setDrawValueAboveBar(true);


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
                String AFH_retail = null, ALH_retail = null, AFH_life = null, ALH_life = null, AFH_tour = null, ALH_tour = null, AFH_stay = null,
                        ALH_stay = null, AFH_sports = null, ALH_sports = null, AFH_food = null, ALH_food = null, AFH_edu = null, ALH_edu = null,
                AFH_avg = null, ALH_avg = null;

                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArray = jsonObject.getJSONArray("response");
                    int count = 0;
                    while (count < jsonArray.length()) {
                        JSONObject object = jsonArray.getJSONObject(count);
                        object.getString("location");
                        AFH_retail = object.getString("FH_retail");
                        ALH_retail = object.getString("LH_retail");
                        AFH_life = object.getString("FH_life");
                        ALH_life = object.getString("LH_life");
                        AFH_tour = object.getString("FH_tour");
                        ALH_tour = object.getString("LH_tour");
                        AFH_stay = object.getString("FH_stay");
                        ALH_stay = object.getString("LH_stay");
                        AFH_sports = object.getString("FH_sports");
                        ALH_sports = object.getString("LH_sports");
                        AFH_food = object.getString("FH_food");
                        ALH_food = object.getString("LH_food");
                        AFH_edu = object.getString("FH_edu");
                        ALH_edu = object.getString("LH_edu");
                        AFH_avg=object.getString("FH_avg");
                        ALH_avg=object.getString("LH_avg");
                        count++;
                    }

                    YAxis leftAxis = barChartAvgsales.getAxisLeft();
                    YAxis rightAxis = barChartAvgsales.getAxisRight();
                    XAxis xAxis = barChartAvgsales.getXAxis();

                    // 텍스트 위치 지정
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xAxis.setTextSize(12f);

                    //그래프 맨 아래 가로선 유무, 그래프 내부 세로선 유무,
                    xAxis.setDrawAxisLine(true);
                    xAxis.setDrawGridLines(true);
                    leftAxis.setTextSize(10f);

                    //y축 텍스트 표시 (값), 그래프 맨 왼쪽 세로선 유무, 그래프 내부 가로선  유무
                    leftAxis.setDrawLabels(true);
                    leftAxis.setDrawAxisLine(true);
                    leftAxis.setDrawGridLines(false);

                    //나머지 전부다 false 해야함
                    rightAxis.setDrawAxisLine(false);
                    rightAxis.setDrawGridLines(false);
                    rightAxis.setDrawLabels(false);

                    // 그룹에 추가
                    ArrayList<BarEntry> bargroup1 = new ArrayList<>();
                    ArrayList<BarEntry> bargroup2 = new ArrayList<>();
                    //상반기 건단가
                    bargroup1.add(new BarEntry(Float.parseFloat(String.format("%.1f", Float.parseFloat(AFH_retail) / 100)), 0));
                    bargroup1.add(new BarEntry(Float.parseFloat(String.format("%.1f", Float.parseFloat(AFH_life) / 100)), 1));
                    bargroup1.add(new BarEntry(Float.parseFloat(String.format("%.1f", Float.parseFloat(AFH_tour) / 100)), 2));
                    bargroup1.add(new BarEntry(Float.parseFloat(String.format("%.1f", Float.parseFloat(AFH_stay) / 100)), 3));
                    bargroup1.add(new BarEntry(Float.parseFloat(String.format("%.1f", Float.parseFloat(AFH_sports) / 100)), 4));
                    bargroup1.add(new BarEntry(Float.parseFloat(String.format("%.1f", Float.parseFloat(AFH_food) / 100)), 5));
                    bargroup1.add(new BarEntry(Float.parseFloat(String.format("%.1f", Float.parseFloat(AFH_edu) / 100)), 6));
                    //하반기 건단가
                    bargroup2.add(new BarEntry(Float.parseFloat(String.format("%.1f", Float.parseFloat(ALH_retail) / 100)), 0));
                    bargroup2.add(new BarEntry(Float.parseFloat(String.format("%.1f", Float.parseFloat(ALH_life) / 100)), 1));
                    bargroup2.add(new BarEntry(Float.parseFloat(String.format("%.1f", Float.parseFloat(ALH_tour) / 100)), 2));
                    bargroup2.add(new BarEntry(Float.parseFloat(String.format("%.1f", Float.parseFloat(ALH_stay) / 100)), 3));
                    bargroup2.add(new BarEntry(Float.parseFloat(String.format("%.1f", Float.parseFloat(ALH_sports) / 100)), 4));
                    bargroup2.add(new BarEntry(Float.parseFloat(String.format("%.1f", Float.parseFloat(ALH_food) / 100)), 5));
                    bargroup2.add(new BarEntry(Float.parseFloat(String.format("%.1f", Float.parseFloat(ALH_edu) / 100)), 6));

                    BarDataSet barDataSet1 = new BarDataSet(bargroup1, "상반기");
                    BarDataSet barDataSet2 = new BarDataSet(bargroup2, "하반기");

                    //바 색상
                    barDataSet1.setColor(Color.rgb(65, 105, 225));
                    barDataSet2.setColor(Color.rgb(128, 128, 128));
                    // barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);


                    ArrayList<String> labels = new ArrayList<String>();
                    labels.add("소매");
                    labels.add("생활서비스");
                    labels.add("관광/여가/오락");
                    labels.add("숙박");
                    labels.add("스포츠");
                    labels.add("음식");
                    labels.add("학문/교육");

                    ArrayList<BarDataSet> dataSets = new ArrayList<>();  // mbined all dataset into an arraylistco
                    dataSets.add(barDataSet1);
                    dataSets.add(barDataSet2);

                    //그래프 아래 카테고리 표시 지우기
                    Legend i = barChartAvgsales.getLegend();
                    //i.setEnabled(false);
                    i.setPosition(Legend.LegendPosition.ABOVE_CHART_RIGHT);
                    i.setTextSize(13f);
                    // 막대그래프 디자인
                    BarData data = new BarData(labels, dataSets);
                    data.setValueTextColor(Color.BLACK);
                    barChartAvgsales.setData(data);
                    barChartAvgsales.setTouchEnabled(false);
                    barChartAvgsales.setDescription("");
                    barChartAvgsales.invalidate(); // refresh
                    barChartAvgsales.setScaleEnabled(false);
                    // 그래프 배경
                    barChartAvgsales.setGridBackgroundColor(Color.rgb(248, 248, 248));
                    barChartAvgsales.animateXY(2000, 2000);
                    barChartAvgsales.setDrawBorders(false);
                    barChartAvgsales.setDrawValueAboveBar(true);

                    YAxis leftAxis2 = barChartAvg.getAxisLeft();
                    YAxis rightAxis2 = barChartAvg.getAxisRight();
                    XAxis xAxis2 = barChartAvg.getXAxis();

                    // 텍스트 위치 지정
                    xAxis2.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xAxis2.setTextSize(12f);

                    //그래프 맨 아래 가로선 유무, 그래프 내부 세로선 유무,
                    xAxis2.setDrawAxisLine(true);
                    xAxis2.setDrawGridLines(true);
                    leftAxis2.setTextSize(10f);

                    //y축 텍스트 표시 (값), 그래프 맨 왼쪽 세로선 유무, 그래프 내부 가로선  유무
                    leftAxis2.setDrawLabels(true);
                    leftAxis2.setDrawAxisLine(true);
                    leftAxis2.setDrawGridLines(false);

                    //나머지 전부다 false 해야함
                    rightAxis2.setDrawAxisLine(false);
                    rightAxis2.setDrawGridLines(false);
                    rightAxis2.setDrawLabels(false);

                    // 그룹에 추가
                    ArrayList<BarEntry> bargroup3 = new ArrayList<>();
                    ArrayList<BarEntry> bargroup4 = new ArrayList<>();
                    bargroup3.add(new BarEntry(Float.parseFloat(String.format("%.1f", Float.parseFloat(AFH_avg) / 100)), 0));
                    bargroup4.add(new BarEntry(Float.parseFloat(String.format("%.1f", Float.parseFloat(ALH_avg) / 100)), 0));


                    BarDataSet barDataSet3 = new BarDataSet(bargroup3, "상반기");
                    BarDataSet barDataSet4 = new BarDataSet(bargroup4, "하반기");

                    //바 색상
                    barDataSet3.setColor(Color.rgb(65, 105, 225));
                    barDataSet4.setColor(Color.rgb(128, 128, 128));
                    // barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);


                    ArrayList<String> labels2 = new ArrayList<String>();
                    labels2.add("2017년");

                    ArrayList<BarDataSet> dataSets2 = new ArrayList<>();  // mbined all dataset into an arraylistco
                    dataSets2.add(barDataSet3);
                    dataSets2.add(barDataSet4);

                    //그래프 아래 카테고리 표시 지우기
                    Legend i2 = barChartAvg.getLegend();
                    //i.setEnabled(false);
                    i2.setPosition(Legend.LegendPosition.ABOVE_CHART_RIGHT);
                    i2.setTextSize(13f);

                    // 막대그래프 디자인
                    BarData data2 = new BarData(labels2,dataSets2);
                    data2.setValueTextColor(Color.WHITE);
                    barChartAvg.setData(data2);
                    barChartAvg.setTouchEnabled(false);
                    barChartAvg.setDescription("");
                    barChartAvg.invalidate(); // refresh
                    barChartAvg.setScaleEnabled(false);
                    barChartAvg.setGridBackgroundColor(Color.rgb(248, 248, 248));
                    barChartAvg.animateXY(2000, 2000);
                    barChartAvg.setDrawBorders(false);
                    barChartAvg.setDrawValueAboveBar(false);

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