package com.example.jws.bzapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
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
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Recommenddetail extends AppCompatActivity {

    private GoogleMap mMap;

    //주소 저장하는 변수
    String Location;

    //정보 불러오는 대기시간에 띄어줄 창
    ProgressDialog progressDialog;

    //API 아이디, 키값
    String consumer_key = "280b76187800465f82e7";
    String consumer_secret = "0b1a959895d74aa2a418";
    //API 토큰
    String token;
    //주소 코드 값
    String addrcd, sidonm;
    //UTM형식 좌표
    String UTM_KX, UTM_KY;
    //LatLng형식 좌표
    Double Lat, Lng;

    //상권 점수 변수 & 등급, 점수 표시
    ImageView ivgrage;
    TextView tvgrade, tvlow, tvhigh, tvsungscore, tvstabscore, tvbuyscore, tvpopulscore, tvtotalscore;
    String max = "성장성", min = "성장성";
    Double sungscore, stabscore, buyscore, populscore, totalscore, maxscore, minscore;

    //백, 홈 버튼, 상단 텍스트 뷰
    ImageButton btnBack;
    ImageButton btnHome;
    TextView tvretitle;

    //상권개요
    LinearLayout pLayout1;
    ImageView pbtn1;
    ShopApi shopApi;
    TextView jumpo;
    //페업자 정보
    TextView tvsido, tvclosure, tvsclosure;
    //평균업력 차트
    PieChart pieChartavgcareer;
    //상하반기 매출 평균
    BarChart barChartAvg;

    //매출분석
    LinearLayout pLayout2;
    ImageView pbtn2;
    //상하반기 업종별 평균 매출
    BarChart barChartAvgsales;
    //상하반기 업종별 1회 평균 결제 금액
    BarChart barChartPercost;

    //인구분석
    LinearLayout pLayout3;
    ImageView pbtn3;
    //해당 지역 주거인구
    TextView tvtotal;
    //해당 지역 주거인구 수
    Double totalpopul;
    //해당 지역 1인 가구
    TextView tvonehouse;
    //해당 지역 연령별 비율
    PieChart pieChartPopulation;

    //점포현황
    String Alllat, Alllong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommenddetail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //선택 지역 주소 값 불러오기
        Intent intent = getIntent();
        Location = intent.getStringExtra("Location");

        btnBack =(ImageButton)findViewById(R.id.btnBack);
        btnHome=(ImageButton)findViewById(R.id.btnHome);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Recommenddetail.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        tvretitle = (TextView)findViewById(R.id.tvretitle);
        tvretitle.setText(Location);

        ivgrage = (ImageView) findViewById(R.id.ivgrage);
        tvgrade = (TextView) findViewById(R.id.tvgrade);
        tvhigh = (TextView) findViewById(R.id.tvhigh);
        tvlow = (TextView) findViewById(R.id.tvlow);
        tvsungscore = (TextView) findViewById(R.id.tvsungscore);
        tvstabscore = (TextView) findViewById(R.id.tvstabscore);
        tvbuyscore = (TextView) findViewById(R.id.tvbuyscore);
        tvpopulscore = (TextView) findViewById(R.id.tvpopulscore);
        tvtotalscore = (TextView) findViewById(R.id.tvtotalscore);

        getToken getToken = new getToken();
        getToken.execute();

        Sung sung = new Sung();
        sung.execute(Location);

        //상권개요
        pLayout1 = (LinearLayout) findViewById(R.id.pLayout1);
        pbtn1 = (ImageView) findViewById(R.id.pbtn1);
        shopApi=new ShopApi();
        jumpo = (TextView)findViewById(R.id.jumpo);

        //페업자
        tvsido = (TextView) findViewById(R.id.tvsido);
        tvclosure = (TextView) findViewById(R.id.tvclosure);
        tvsclosure = (TextView) findViewById(R.id.tvsclosure);
        //평균업력 차트 생성
        pieChartavgcareer = (PieChart) findViewById(R.id.pieChartavgcareer);
        //상하반기 매출 평균
        barChartAvg = (BarChart) findViewById(R.id.barChartAvg);

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
        pLayout2 = (LinearLayout) findViewById(R.id.pLayout2);
        pbtn2 = (ImageView) findViewById(R.id.pbtn2);
        //상하반기 업종별 평균 매출
        barChartAvgsales = (BarChart) findViewById(R.id.barChartAvgsales);
        //상하반기 업종별 1회 평균 결제 금액
        barChartPercost = (BarChart) findViewById(R.id.barChartPercost);
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


        //인구분석 버튼
        pLayout3 = (LinearLayout) findViewById(R.id.pLayout3);
        pbtn3 = (ImageView) findViewById(R.id.pbtn3);
        //해당 지역 총 주거인구
        tvtotal = (TextView) findViewById(R.id.tvtotal);
        //해당 지역 1인 가구
        tvonehouse = (TextView) findViewById(R.id.tvonehouse);
        //연령별 차트 생성
        pieChartPopulation = (PieChart) findViewById(R.id.pieChartPopulation);
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

    }


    //API 토큰
    public class getToken extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
//            progressDialog = ProgressDialog.show(Recommenddetail.this,
//                    "안내", "정보를 불러오는 중입니다.", true, true);
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                //서버에 있는 php 실행
                URL url = new URL("https://sgisapi.kostat.go.kr/OpenAPI3/auth/authentication.json?consumer_key=" + consumer_key + "&consumer_secret=" + consumer_secret);
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

                Changecode changecode = new Changecode();
                changecode.execute();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //주솟값 >> 코드&좌표
    public class Changecode extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... voids) {
            try {
                //서버에 있는 php 실행
                URL url = new URL("https://sgisapi.kostat.go.kr/OpenAPI3/addr/geocode.json?accessToken=" + token + "&address=" + Location);
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
                JSONArray jsonArray = object.getJSONArray("resultdata");

                JSONObject item = jsonArray.getJSONObject(0);

                addrcd = item.getString("sido_cd") + item.getString("sgg_cd");
                sidonm = item.getString("sido_nm");
                UTM_KX = item.getString("x");
                UTM_KY = item.getString("y");

                ChangeLatLng changeLatLng = new ChangeLatLng();
                changeLatLng.execute();


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //UTM식 좌표 LatLng식 좌표로 변환 후 지도
    public class ChangeLatLng extends AsyncTask<String, Void, String> implements OnMapReadyCallback {

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... voids) {
            try {
                //서버에 있는 php 실행
                URL url = new URL("https://sgisapi.kostat.go.kr/OpenAPI3/transformation/transcoord.json?accessToken=" + token + "&src=5179&dst=4326&posX=" + UTM_KX + "&posY=" + UTM_KY);
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

                Lat = object.getDouble("posY");
                Lng = object.getDouble("posX");

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        Alllat=String.valueOf(Lat);
                        Alllong=String.valueOf(Lng);
                        final String location[]=shopApi.location("1000",Alllong,Alllat);
                        final String hangjung[];
                        hangjung=ShopApi.hangjungData("signguCd", location[1]);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                jumpo.setText( hangjung[1]+ "개");
                            }
                        });

                    }
                }).start();

                //지도 중심을 변환한 위도경도로 설정
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
                mapFragment.getMapAsync(this);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;
            LatLng center = new LatLng(Lat, Lng);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(center, 12));
        }
    }

    // 성장성 1,2
    class Sung extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {
            String serverURL = "https://qwerr784.cafe24.com/sung.php";
            String postParameters = "location=" + params[0];

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

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                bufferedReader.close();
                return sb.toString().trim();
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
            double FH_avg = 0, LH_avg = 0;
            ArrayList<Double> FH_avgList = new ArrayList<>();
            ArrayList<Double> LH_avgList = new ArrayList<>();

            try {
                JSONObject jsonObject = new JSONObject(s);
                //전체 값 입력
                JSONArray jsonArray = jsonObject.getJSONArray("list");
                int count = 0;
                while (count < jsonArray.length()) {
                    JSONObject object = jsonArray.getJSONObject(count);
                    FH_avgList.add(object.getDouble("FH_avg"));
                    LH_avgList.add(object.getDouble("LH_avg"));
                    count++;
                }
                //선택지역 값 입력
                JSONArray jsonArray2 = jsonObject.getJSONArray("response");
                count = 0;
                while (count < jsonArray2.length()) {
                    JSONObject object = jsonArray2.getJSONObject(count);
                    FH_avg = object.getDouble("FH_avg");
                    LH_avg = object.getDouble("LH_avg");
                    count++;
                }
                //성장성 1번 최대, 최소, 증감, 점수 구함
                double sung1_max = sung1_max(FH_avgList, LH_avgList);
                double sung1_min = sung1_min(FH_avgList, LH_avgList);
                double sung1_updown = sung1_updown(FH_avg, LH_avg);
                double sung1_point = per_point(sung1_updown, sung1_max, sung1_min);

                //성장성 2번 최대, 최소, 증감, 점수
                double sum_FH = add(FH_avgList);
                double sum_LH = add(LH_avgList);
                double sung2_max = sung2_max(FH_avgList, LH_avgList, sum_FH, sum_LH);
                double sung2_min = sung2_min(FH_avgList, LH_avgList, sum_FH, sum_LH);
                double sung2_updown = sung2_updown(FH_avg, LH_avg, sum_FH, sum_LH);
                double sung2_point = per_point(sung2_updown, sung2_max, sung2_min);

                sungscore = (sung1_point + sung2_point) * 0.125;
                sungscore = (Math.round(sungscore * 10) / 10.0);
                tvsungscore.setText(String.valueOf(sungscore) + "점");

                maxscore = sungscore;
                minscore = sungscore;

                Stability stability = new Stability();
                stability.execute(Location);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    //안정성 1,2
    class Stability extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {
            String serverURL = "https://qwerr784.cafe24.com/Stability.php";
            String postParameters = "location=" + params[0];

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

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                bufferedReader.close();
                return sb.toString().trim();
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
            double Closure = 0, Career = 0;
            ArrayList<Double> ClosureList = new ArrayList<>();
            ArrayList<Double> CareerList = new ArrayList<>();

            try {
                JSONObject jsonObject = new JSONObject(s);
                //전체 값 입력
                JSONArray closurelist = jsonObject.getJSONArray("closurelist");
                int count = 0;
                while (count < closurelist.length()) {
                    JSONObject object = closurelist.getJSONObject(count);
                    ClosureList.add(object.getDouble("cnt"));
                    count++;
                }
                //선택지역 값 입력
                JSONArray closure = jsonObject.getJSONArray("closure");
                count = 0;
                while (count < closure.length()) {
                    JSONObject object = closure.getJSONObject(count);
                    Closure = object.getDouble("cnt");
                    count++;
                }
                JSONArray careerlist = jsonObject.getJSONArray("careerlist");
                count = 0;
                while (count < careerlist.length()) {
                    JSONObject object = careerlist.getJSONObject(count);
                    CareerList.add(((0.5 * object.getDouble("one")) + (1.5 * object.getDouble("two")) + (2.5 * object.getDouble("three"))
                            + (4 * object.getDouble("four")) + (15 * object.getDouble("five"))) / 100);
                    count++;
                }
                //선택지역 값 입력
                JSONArray career = jsonObject.getJSONArray("career");
                count = 0;
                while (count < career.length()) {
                    JSONObject object = career.getJSONObject(count);
                    Career = ((0.5 * object.getDouble("one")) + (1.5 * object.getDouble("two")) + (2.5 * object.getDouble("three"))
                            + (4 * object.getDouble("four")) + (15 * object.getDouble("five"))) / 100;
                    count++;
                }

                //안정성 1번 최대, 최소, 증감 점수
                double sum_closure = add(ClosureList);
                double stability1_max = per_max(ClosureList, sum_closure);
                double stability1_min = per_min(ClosureList, sum_closure);
                double stability1_per = per(Closure, sum_closure);
                double stability1_point = point_stability(stability1_per, stability1_max, stability1_min);

                //안정선 2번
                double stability2_max = max(CareerList);
                double stability2_min = min(CareerList);
                double stability2_point = per_point(Career, stability2_max, stability2_min);

                stabscore = (stability1_point + stability2_point) * 0.125;
                stabscore = (Math.round(stabscore * 10) / 10.0);
                tvstabscore.setText(String.valueOf(stabscore) + "점");

                if (stabscore > maxscore) {
                    maxscore = stabscore;
                    max = "안정성";
                } else if (stabscore < minscore) {
                    minscore = stabscore;
                    min = "안정성";
                }

                Buy buy = new Buy();
                buy.execute(Location);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //구매력 1,2
    class Buy extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {
            String serverURL = "https://qwerr784.cafe24.com/buy.php";
            String postParameters = "location=" + params[0];

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

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                bufferedReader.close();
                return sb.toString().trim();
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
            double Sale = 0, PC = 0;
            ArrayList<Double> Sale_List = new ArrayList<>();
            ArrayList<Double> PC_List = new ArrayList<>();

            try {
                JSONObject jsonObject = new JSONObject(s);
                //전체 값 입력
                JSONArray salelist = jsonObject.getJSONArray("salelist");
                int count = 0;
                while (count < salelist.length()) {
                    JSONObject object = salelist.getJSONObject(count);
                    Sale_List.add((object.getDouble("FH_sum") + object.getDouble("LH_sum")) / 14.0);
                    count++;
                }
                //선택지역 값 입력
                JSONArray sale = jsonObject.getJSONArray("sale");
                count = 0;
                while (count < sale.length()) {
                    JSONObject object = sale.getJSONObject(count);
                    Sale = (object.getDouble("FH_sum") + object.getDouble("LH_sum")) / 14.0;
                    count++;
                }
                //전체 값 입력
                JSONArray percostlist = jsonObject.getJSONArray("percostlist");
                count = 0;
                while (count < percostlist.length()) {
                    JSONObject object = percostlist.getJSONObject(count);
                    PC_List.add((object.getDouble("FH_retail") + object.getDouble("LH_retail") + object.getDouble("FH_life") + object.getDouble("LH_life")
                            + object.getDouble("FH_tour") + object.getDouble("LH_tour") + object.getDouble("FH_stay") + object.getDouble("LH_stay")
                            + object.getDouble("FH_sports") + object.getDouble("LH_sports") + object.getDouble("FH_food") + object.getDouble("LH_food")
                            + object.getDouble("FH_edu") + object.getDouble("LH_edu")) / 14.0);
                    count++;
                }
                //선택지역 값 입력
                JSONArray percost = jsonObject.getJSONArray("percost");
                count = 0;
                while (count < percost.length()) {
                    JSONObject object = percost.getJSONObject(count);
                    PC = (object.getDouble("FH_retail") + object.getDouble("LH_retail") + object.getDouble("FH_life") + object.getDouble("LH_life")
                            + object.getDouble("FH_tour") + object.getDouble("LH_tour") + object.getDouble("FH_stay") + object.getDouble("LH_stay")
                            + object.getDouble("FH_sports") + object.getDouble("LH_sports") + object.getDouble("FH_food") + object.getDouble("LH_food")
                            + object.getDouble("FH_edu") + object.getDouble("LH_edu")) / 14.0;
                    count++;
                }
                //구매력 1번 최대, 최소, 증감 점수
                double sum_sale = add(Sale_List);
                double buy1_max = per_max(Sale_List, sum_sale);
                double buy1_min = per_min(Sale_List, sum_sale);
                double buy1_per = per(Sale, sum_sale);
                double buy1_point = per_point(buy1_per, buy1_max, buy1_min);

                //구매력 2번 최대, 최소, 증감 점수
                double sum_percost = add(PC_List);
                double buy2_max = per_max(PC_List, sum_percost);
                double buy2_min = per_min(PC_List, sum_percost);
                double buy2_per = per(PC, sum_percost);
                double buy2_point = per_point(buy2_per, buy2_max, buy2_min);

                buyscore = (buy1_point + buy2_point) * 0.125;
                buyscore = (Math.round(buyscore * 10) / 10.0);
                tvbuyscore.setText(String.valueOf(buyscore) + "점");

                if (buyscore > maxscore) {
                    maxscore = buyscore;
                    max = "구매력";
                } else if (buyscore < minscore) {
                    minscore = buyscore;
                    min = "구매력";
                }

                Population population = new Population();
                population.execute(Location);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //접객력
    class Population extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String serverURL = "https://qwerr784.cafe24.com/population.php";
            String postParameters = "location=" + params[0];

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

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                bufferedReader.close();
                return sb.toString().trim();
            } catch (Exception e) {
                e.printStackTrace();
            }
            getClosure();
            getavgcareer();
            getavgsales();
            getPercost();
            getPopulation();
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            double Population = 0;
            ArrayList<Double> Population_List = new ArrayList<>();

            try {
                JSONObject jsonObject = new JSONObject(s);
                //전체 값 입력
                JSONArray list = jsonObject.getJSONArray("list");
                int count = 0;
                while (count < list.length()) {
                    JSONObject object = list.getJSONObject(count);
                    Population_List.add(object.getDouble("total"));
                    count++;
                }
                //선택지역 값 입력
                JSONArray response = jsonObject.getJSONArray("response");
                count = 0;
                while (count < response.length()) {
                    JSONObject object = response.getJSONObject(count);
                    Population = object.getDouble("total");
                    count++;
                }
                //집객력 1번 최대, 최소, 증감 점수
                double sum_population = add(Population_List);
                double population_max = per_max(Population_List, sum_population);
                double population_min = per_min(Population_List, sum_population);
                double population_per = per(Population, sum_population);
                double population_point = per_point(population_per, population_max, population_min);

                populscore = population_point * 0.25;
                populscore = (Math.round(populscore * 10) / 10.0);
                tvpopulscore.setText(String.valueOf(populscore) + "점");

                if (populscore > maxscore) {
                    maxscore = populscore;
                    max = "집객력";
                } else if (populscore < minscore) {
                    minscore = populscore;
                    min = "집객력";
                }

                tvhigh.setText(max);
                tvlow.setText(min);

                totalscore = sungscore + stabscore + buyscore + populscore;
                tvtotalscore.setText(String.valueOf(totalscore) + "점");
                if (totalscore > 80) {
                    ivgrage.setBackgroundResource(R.drawable.grade1);
                    tvgrade.setText("1등급");
                } else if (totalscore > 60) {
                    ivgrage.setBackgroundResource(R.drawable.grade2);
                    tvgrade.setText("2등급");
                } else if (totalscore > 40) {
                    ivgrage.setBackgroundResource(R.drawable.grade3);
                    tvgrade.setText("3등급");
                } else if (totalscore > 20) {
                    ivgrage.setBackgroundResource(R.drawable.grade4);
                    tvgrade.setText("4등급");
                } else {
                    ivgrage.setBackgroundResource(R.drawable.grade5);
                    tvgrade.setText("5등급");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 성장성1 증감률 계산 메서드
    public double sung1_updown(double sang, double ha) {
        // 매개 변수 값 이용해서 계산한 결과 (증감률)
        double result;
        // 증감률 계산
        result = ((ha - sang) / ha) * 100;
        return result;
    }

    // 성장성1 최대 반환하는 메서드
    public double sung1_max(ArrayList<Double> FH, ArrayList<Double> LH) {
        double temp;
        double max = -100;

        for (int i = 0; i < FH.size(); i++) {
            temp = sung1_updown(FH.get(i), LH.get(i));

            if (temp > max) {
                max = temp;
            }
        }
        return max;
    }

    // 성장성1 최소 반환하는 메서드
    public double sung1_min(ArrayList<Double> FH, ArrayList<Double> LH) {
        double temp;
        double min = 100;

        for (int i = 0; i < FH.size(); i++) {
            temp = sung1_updown(FH.get(i), LH.get(i));

            if (temp < min) {
                min = temp;
            }
        }
        return min;
    }

    // 성장성2 증감률 계산 메서드
    public double sung2_updown(double sang, double ha, double sum_FH, double sum_LH) {
        // 매개 변수 값 이용해서 계산한 결과 (증감률)
        double result;
        // 증감률 계산
        result = (((ha / sum_LH) - (sang / sum_FH)) / ((ha / sum_LH))) * 100;
        return result;
    }

    // 성장성2 최대 반환하는 메서드
    public double sung2_max(ArrayList<Double> FH, ArrayList<Double> LH, double sum_FH, double sum_LH) {
        double temp;
        double max = -100;

        for (int i = 0; i < FH.size(); i++) {
            temp = sung2_updown(FH.get(i), LH.get(i), sum_FH, sum_LH);

            if (temp > max) {
                max = temp;
            }
        }
        return max;
    }

    // 성장성2 최소 반환하는 메서드
    public double sung2_min(ArrayList<Double> FH, ArrayList<Double> LH, double sum_FH, double sum_LH) {
        double temp;
        double min = 100;

        for (int i = 0; i < FH.size(); i++) {
            temp = sung2_updown(FH.get(i), LH.get(i), sum_FH, sum_LH);

            if (temp < min) {
                min = temp;
            }
        }
        return min;
    }

    //리스트의 총 합
    public double add(ArrayList<Double> arrayList) {
        double sum = 0;

        for (int i = 0; i < arrayList.size(); i++) {
            sum = sum + arrayList.get(i);
        }

        return sum;
    }

    // 해당지역 전국 대비 % 구하기
    public double per(double cost, double sum) {
        double result;
        // 해당지역 전국 대비 % 구하기
        result = cost / sum * 100;
        return result;
    }

    // 최대 비율 반환하는 메서드
    public double per_max(ArrayList<Double> List, double sum) {
        double temp;
        double max = -100;

        for (int i = 0; i < List.size(); i++) {
            temp = per(List.get(i), sum);

            if (temp > max) {
                max = temp;
            }
        }
        return max;
    }

    // 최소 비율 반환하는 메서드
    public double per_min(ArrayList<Double> List, double sum) {
        double temp;
        double min = 100;

        for (int i = 0; i < List.size(); i++) {
            temp = per(List.get(i), sum);

            if (temp < min) {
                min = temp;
            }
        }
        return min;
    }

    // 특정 지역의 안정성 점수 구하는 메서드
    public double point_stability(double cl, double max, double min) {
        double range = max - min;
        if (cl == max) {
            return 100;
        }

        if (cl == min) {
            return 0;
        }

        double result;
        result = (cl - min) * (100 / range);
        return 100 - result;
    }

    // 특정 지역의 점수 구하는 메서드
    public double per_point(double cost, double max, double min) {
        double range = max - min;
        if (cost == max) {
            return 100;
        }

        if (cost == min) {
            return 0;
        }

        double result;
        result = (cost - min) * (100 / range);
        return result;
    }

    // 최대 반환하는 메서드
    public double max(ArrayList<Double> List) {
        double max = -100;

        for (int i = 0; i < List.size(); i++) {

            if (List.get(i) > max) {
                max = List.get(i);
            }
        }
        return max;
    }

    // 최소 반환하는 메서드
    public double min(ArrayList<Double> List) {
        double min = 100;

        for (int i = 0; i < List.size(); i++) {
            if (List.get(i) < min) {
                min = List.get(i);
            }
        }
        return min;
    }

    //폐업자
    public void getClosure() {
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

                    tvsido.setText(sidonm);
                    tvclosure.setText(String.format("%.2f", (ccnt / ctotal) * 100) + " %");
                    tvsclosure.setText(String.format("%.2f", (ccnt / cstotal) * 100) + " %");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        Findclosure findclosure = new Findclosure(Location, responseListener);
        RequestQueue queue = Volley.newRequestQueue(Recommenddetail.this);
        queue.add(findclosure);
    }

    //평균업력
    public void getavgcareer() {
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
                    pieChartavgcareer.setUsePercentValues(true);
                    pieChartavgcareer.setTouchEnabled(false);
                    ArrayList<Entry> yvalues2 = new ArrayList<Entry>();
                    yvalues2.add(new Entry(Float.parseFloat(one), 0));
                    yvalues2.add(new Entry(Float.parseFloat(two), 1));
                    yvalues2.add(new Entry(Float.parseFloat(three), 2));
                    yvalues2.add(new Entry(Float.parseFloat(four), 3));
                    yvalues2.add(new Entry(Float.parseFloat(five), 4));
                    //라벨에 텍스트 추가하면 출처 및 설명 가능
                    PieDataSet dataSet2 = new PieDataSet(yvalues2, "");
                    int color1 = Color.rgb(229, 70, 90);
                    int color2 = Color.rgb(249, 159, 89);
                    int color3 = Color.rgb(255, 222, 106);
                    int color4 = Color.rgb(101, 218, 173);
                    int color5 = Color.rgb(28, 120, 177);
                    int color6 = Color.rgb(52, 178, 228);
                    int color7 = Color.rgb(184, 126, 247);
                    //새롭게 color 지정하는 방법
                    dataSet2.setColors(new int[]{color1, color2, color3, color4, color5, color6, color7});
                    ArrayList<String> xVals2 = new ArrayList<String>();
                    xVals2.add("1년 미만");
                    xVals2.add("1년 이상 2년 미만");
                    xVals2.add("2년 이상 3년 미만");
                    xVals2.add("3년 이상 5년 미만");
                    xVals2.add("5년 이상");
                    PieData data2 = new PieData(xVals2, dataSet2);
                    data2.setValueFormatter(new PercentFormatter());
                    pieChartavgcareer.setData(data2);
                    pieChartavgcareer.setDescription("");
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
                    data2.setValueTextSize(12f);
                    data2.setValueTextColor(Color.WHITE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        Findavgcareer findavgcareer = new Findavgcareer(sidonm, responseListener);
        RequestQueue queue = Volley.newRequestQueue(Recommenddetail.this);
        queue.add(findavgcareer);
    }

    //상하반기 매출 평균 & 상하반기 업종별 평균 매출
    public void getavgsales() {
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
                        AFH_avg = object.getString("FH_avg");
                        ALH_avg = object.getString("LH_avg");
                        count++;
                    }
                    //업종별 상하반기 평균 매출 그래프
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
                    //업종별 상반기 평균 매출
                    bargroup1.add(new BarEntry(Float.parseFloat(String.format("%.1f", Float.parseFloat(AFH_retail) / 100)), 0));
                    bargroup1.add(new BarEntry(Float.parseFloat(String.format("%.1f", Float.parseFloat(AFH_life) / 100)), 1));
                    bargroup1.add(new BarEntry(Float.parseFloat(String.format("%.1f", Float.parseFloat(AFH_tour) / 100)), 2));
                    bargroup1.add(new BarEntry(Float.parseFloat(String.format("%.1f", Float.parseFloat(AFH_stay) / 100)), 3));
                    bargroup1.add(new BarEntry(Float.parseFloat(String.format("%.1f", Float.parseFloat(AFH_sports) / 100)), 4));
                    bargroup1.add(new BarEntry(Float.parseFloat(String.format("%.1f", Float.parseFloat(AFH_food) / 100)), 5));
                    bargroup1.add(new BarEntry(Float.parseFloat(String.format("%.1f", Float.parseFloat(AFH_edu) / 100)), 6));
                    //업종별 하반기 평균 매출
                    bargroup2.add(new BarEntry(Float.parseFloat(String.format("%.1f", Float.parseFloat(ALH_retail) / 100)), 0));
                    bargroup2.add(new BarEntry(Float.parseFloat(String.format("%.1f", Float.parseFloat(ALH_life) / 100)), 1));
                    bargroup2.add(new BarEntry(Float.parseFloat(String.format("%.1f", Float.parseFloat(ALH_tour) / 100)), 2));
                    bargroup2.add(new BarEntry(Float.parseFloat(String.format("%.1f", Float.parseFloat(ALH_stay) / 100)), 3));
                    bargroup2.add(new BarEntry(Float.parseFloat(String.format("%.1f", Float.parseFloat(ALH_sports) / 100)), 4));
                    bargroup2.add(new BarEntry(Float.parseFloat(String.format("%.1f", Float.parseFloat(ALH_food) / 100)), 5));
                    bargroup2.add(new BarEntry(Float.parseFloat(String.format("%.1f", Float.parseFloat(ALH_edu) / 100)), 6));
                    BarDataSet barDataSet1 = new BarDataSet(bargroup1, "상반기");
                    BarDataSet barDataSet2 = new BarDataSet(bargroup2, "하반기");
                    barDataSet1.setColor(Color.rgb(65, 105, 225));
                    barDataSet2.setColor(Color.rgb(128, 128, 128));
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

                    //상하반기 매출 평균 그래프
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
                    ArrayList<String> labels2 = new ArrayList<String>();
                    labels2.add("2017년");
                    ArrayList<BarDataSet> dataSets2 = new ArrayList<>();  // mbined all dataset into an arraylistco
                    dataSets2.add(barDataSet3);
                    dataSets2.add(barDataSet4);
                    Legend i2 = barChartAvg.getLegend();
                    i2.setPosition(Legend.LegendPosition.ABOVE_CHART_RIGHT);
                    i2.setTextSize(13f);
                    // 막대그래프 디자인
                    BarData data2 = new BarData(labels2, dataSets2);
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
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        Findavgsales findavgsales = new Findavgsales(Location, responseListener);
        RequestQueue queue = Volley.newRequestQueue(Recommenddetail.this);
        queue.add(findavgsales);
    }

    //상하반기 업종별 1회 평균 결제 금액
    public void getPercost() {
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
                    //상하반기 업종별 1회 평균 결제 금액 그래프
                    YAxis leftAxis = barChartPercost.getAxisLeft();
                    YAxis rightAxis = barChartPercost.getAxisRight();
                    XAxis xAxis = barChartPercost.getXAxis();
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xAxis.setTextSize(12f);
                    xAxis.setDrawAxisLine(true);
                    xAxis.setDrawGridLines(true);
                    leftAxis.setTextSize(10f);
                    //y축 텍스트 표시 (값), 그래프 맨 왼쪽 세로선 유무, 그래프 내부 가로선  유무
                    leftAxis.setDrawLabels(true);
                    leftAxis.setDrawAxisLine(true);
                    leftAxis.setDrawGridLines(false);
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
        FindPercost findPercost = new FindPercost(Location, responseListener);
        RequestQueue queue = Volley.newRequestQueue(Recommenddetail.this);
        queue.add(findPercost);
    }

    //해당 지역 주거 인구 수 & 연령별 비율
    public void getPopulation() {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArray = jsonObject.getJSONArray("response");
                    int count = 0;
                    Double child = null, teenage = null, twenty = null, thirty = null, forty = null, fifty = null, sixty = null;
                    while (count < jsonArray.length()) {
                        JSONObject object = jsonArray.getJSONObject(count);
                        totalpopul = object.getDouble("total");
                        tvtotal.setText(String.valueOf(Math.round(totalpopul)) + "명");
                        child = object.getDouble("child");
                        teenage = object.getDouble("teenage");
                        twenty = object.getDouble("twenty");
                        thirty = object.getDouble("thirty");
                        forty = object.getDouble("forty");
                        fifty = object.getDouble("fifty");
                        sixty = object.getDouble("sixty");
                        count++;
                    }
                    //해당 지역 주거 인구 연령별 비율
                    pieChartPopulation.setUsePercentValues(true);
                    pieChartPopulation.setTouchEnabled(false);
                    ArrayList<Entry> yvalues = new ArrayList<Entry>();
                    yvalues.add(new Entry(Float.parseFloat(String.format("%.2f", child / totalpopul * 100)), 0));
                    yvalues.add(new Entry(Float.parseFloat(String.format("%.2f", teenage / totalpopul * 100)), 1));
                    yvalues.add(new Entry(Float.parseFloat(String.format("%.2f", twenty / totalpopul * 100)), 2));
                    yvalues.add(new Entry(Float.parseFloat(String.format("%.2f", thirty / totalpopul * 100)), 3));
                    yvalues.add(new Entry(Float.parseFloat(String.format("%.2f", forty / totalpopul * 100)), 4));
                    yvalues.add(new Entry(Float.parseFloat(String.format("%.2f", fifty / totalpopul * 100)), 5));
                    yvalues.add(new Entry(Float.parseFloat(String.format("%.2f", sixty / totalpopul * 100)), 6));
                    PieDataSet dataSet = new PieDataSet(yvalues, "");
                    int color1 = Color.rgb(229, 70, 90);
                    int color2 = Color.rgb(249, 159, 89);
                    int color3 = Color.rgb(255, 222, 106);
                    int color4 = Color.rgb(101, 218, 173);
                    int color5 = Color.rgb(28, 120, 177);
                    int color6 = Color.rgb(52, 178, 228);
                    int color7 = Color.rgb(184, 126, 247);
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
        FindApopulation findApopulation = new FindApopulation(Location, responseListener);
        RequestQueue queue = Volley.newRequestQueue(Recommenddetail.this);
        queue.add(findApopulation);
    }

    //해당 지역 1인 가구 비율
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
                    Double onehouse_cnt = item.getDouble("household_cnt");
                    String percent = String.format("%.2f", (onehouse_cnt / totalpopul) * 100) + " %";
                    tvonehouse.setText(percent);
                }
//                progressDialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
