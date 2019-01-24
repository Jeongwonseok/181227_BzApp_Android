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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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
    TextView tvtotal, tvchild, tvteenage, tvtwenty, tvthirty, tvforty, tvfifty, tvsixty, tvonehouse, jumposu, jumpotest;
    String UTM_KX, UTM_KY, addr, token, addrcd, addrnm, onehouse_cnt;

    //건단가 불러오기
    TextView FH_retail, LH_retail, FH_life, LH_life, FH_tour, LH_tour, FH_stay, LH_stay, FH_sports, LH_sports, FH_food, LH_food, FH_edu, LH_edu;
    String RtotalCount, sido, hangjung, hangjungNm, sidoNm;
    String jumpoRadius;
    String LargeCode[] = new String[21];
    String LargeName[] = new String[21];
    String Alllat, Alllong, AllRadius;


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
        jumpotest = (TextView) findViewById(R.id.jumpotest);

//        Toast.makeText(getApplicationContext(), String.valueOf(mLong) + " " + String.valueOf(mLat), Toast.LENGTH_LONG).show();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        btnCategory = (Button) findViewById(R.id.btnCategory);
        btnArea = (Button) findViewById(R.id.btnArea);


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
        tvchild = (TextView) findViewById(R.id.tvchild);
        tvteenage = (TextView) findViewById(R.id.tvteenage);
        tvtwenty = (TextView) findViewById(R.id.tvtwenty);
        tvthirty = (TextView) findViewById(R.id.tvthirty);
        tvforty = (TextView) findViewById(R.id.tvforty);
        tvfifty = (TextView) findViewById(R.id.tvfifty);
        tvsixty = (TextView) findViewById(R.id.tvsixty);
        tvonehouse = (TextView) findViewById(R.id.tvonehouse);

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
        FH_retail = (TextView) findViewById(R.id.FH_retail);
        LH_retail = (TextView) findViewById(R.id.LH_retail);
        FH_life = (TextView) findViewById(R.id.FH_life);
        LH_life = (TextView) findViewById(R.id.LH_life);
        FH_tour = (TextView) findViewById(R.id.FH_tour);
        LH_tour = (TextView) findViewById(R.id.LH_tour);
        FH_stay = (TextView) findViewById(R.id.FH_stay);
        LH_stay = (TextView) findViewById(R.id.LH_stay);
        FH_sports = (TextView) findViewById(R.id.FH_sports);
        LH_sports = (TextView) findViewById(R.id.LH_sports);
        FH_food = (TextView) findViewById(R.id.FH_food);
        LH_food = (TextView) findViewById(R.id.LH_food);
        FH_edu = (TextView) findViewById(R.id.FH_edu);
        LH_edu = (TextView) findViewById(R.id.LH_edu);


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
                //데이터 받기
                String result = data.getStringExtra("category");
                RtotalCount = data.getStringExtra("RtotalCount");
                sido = data.getStringExtra("sidosu");
                hangjung = data.getStringExtra("hangjungsu");
                sidoNm = data.getStringExtra("sidoNm");
                hangjungNm = data.getStringExtra("hangjungNm");
                btnCategory.setText(result);
                jumpotest.setText("반경내" + RtotalCount + sidoNm + sido + hangjungNm + hangjung); //데이터 없으며 -로 뜸
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
        if (a == 100) {
            onAddCircle100(100);
        } else if (a == 200) {
            onAddCircle100(200);
        } else if (a == 500) {
            onAddCircle100(500);
        } else if (a == 1000) {
            onAddCircle100(1000);
        }
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(mLat, mLong);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 16));
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
                    addrnm = item.getString("sido_nm") + " " + item.getString("sgg_nm");

                }
                Population();
                getPercost();
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
                    double total = Integer.parseInt(tvtotal.getText().toString());
//                    Toast.makeText(getApplicationContext(), onehouse + "  " + total, Toast.LENGTH_LONG).show();
                    double one = (onehouse / total) * 100;
                    String percent = String.format("%.2f",one) + "%";
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
                    while (count < jsonArray.length()) {
                        JSONObject object = jsonArray.getJSONObject(count);
                        object.getString("address");
                        tvtotal.setText(object.getString("total"));
                        tvchild.setText(object.getString("child"));
                        tvteenage.setText(object.getString("teenage"));
                        tvtwenty.setText(object.getString("twenty"));
                        tvthirty.setText(object.getString("thirty"));
                        tvforty.setText(object.getString("forty"));
                        tvfifty.setText(object.getString("fifty"));
                        tvsixty.setText(object.getString("sixty"));
                        count++;
                    }

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
                        FH_retail.setText(object.getString("FH_retail"));
                        LH_retail.setText(object.getString("LH_retail"));
                        FH_life.setText(object.getString("FH_life"));
                        LH_life.setText(object.getString("LH_life"));
                        FH_tour.setText(object.getString("FH_tour"));
                        LH_tour.setText(object.getString("LH_tour"));
                        FH_stay.setText(object.getString("FH_stay"));
                        LH_stay.setText(object.getString("LH_stay"));
                        FH_sports.setText(object.getString("FH_sports"));
                        LH_sports.setText(object.getString("LH_sports"));
                        FH_food.setText(object.getString("FH_food"));
                        LH_food.setText(object.getString("LH_food"));
                        FH_edu.setText(object.getString("FH_edu"));
                        LH_edu.setText(object.getString("LH_edu"));
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