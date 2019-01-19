package com.example.jws.bzapp;

import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class AnalysisActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    double mLat = 0;
    double mLong = 0;
    LinearLayout pLayout1, pLayout2, pLayout3, pLayout4;
    ImageView pbtn1, pbtn2, pbtn3, pbtn4;
    ImageButton btnBack;
    ImageButton btnHome;
    Geocoder geocoder;
    int a;
    TextView population;
    Button btnArea;
    Button btnCategory;
    //인구분석
    TextView tvtotal, tvchild, tvteenage, tvtwenty, tvthirty, tvforty, tvfifty, tvsixty;
    String UTM_KX, UTM_KY, addr;


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
        address(mLat, mLong);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        //업종 텍스트뷰 클릭 시 다이얼로그 생성
        btnCategory = (Button) findViewById(R.id.btnCategory);
        btnArea = (Button) findViewById(R.id.btnArea);


        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnHome = (ImageButton) findViewById(R.id.btnHome);
        population = (TextView) findViewById(R.id.population);
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

        Change change = new Change(mLong, mLat);
        change.execute();

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
        startActivityForResult(intent, 1);
    }

    //다이얼로그 실행후 결과값 받는 메서드
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                //데이터 받기
                String result = data.getStringExtra("result");
                btnArea.setText(result);
            }
        }
    }

    public void mOnPopupClick2(View v) {
        //데이터 담아서 팝업(액티비티) 호출
        Intent intent = new Intent(this, Dialog_Category.class);
        startActivityForResult(intent, 1);
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

    public void address(double mLat, double mLong) {
        List<Address> list = null;
        try {
            double lat = mLat;
            double lng = mLong;
            list = geocoder.getFromLocation(lat, lng, 1);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("test", "입출력 오류");
        }
        if (list != null) {
            if (list.size() == 0) {
                Toast.makeText(getApplicationContext(), "해당되는 주소 정보는 없습니다.", Toast.LENGTH_SHORT).show();
            } else {
                String address = list.get(0).getAddressLine(0).toString();
                String address1 = address.substring(5);
                int su = address1.lastIndexOf("동");
                address1 = address1.substring(0, su + 1);
//
//                boolean Seoul=address1.contains("서울특별시");
//                boolean Gyeonggi=address1.contains("경기도");
//                boolean Busan=address1.contains("부산광역시");
//                boolean Daegu=address1.contains("대구광역시");
//                boolean inchen=address1.contains("인쳔광역시");
//                boolean Gangju=address1.contains("광주광역시");
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray jsonArray = jsonObject.getJSONArray("response");
                            int count = 0;
                            String fid = "몇명 : ";
                            while (count < jsonArray.length()) {
                                if (count >= 1) {
                                    fid = fid + ", ";
                                }
                                JSONObject object = jsonArray.getJSONObject(count);
                                fid = fid + object.getString("population");
                                count++;
                            }
                            if (jsonArray.length() > 0) {
                                Toast.makeText(getApplicationContext(), fid, Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "실패", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                population.setText(address1);
                FindRpopulation findpopulation = new FindRpopulation(address1, responseListener);
                RequestQueue queue = Volley.newRequestQueue(AnalysisActivity.this);
                queue.add(findpopulation);

            }
        }

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


    public class Change extends AsyncTask<String, Void, String> {
        double lat, lng;


    
    public class Change extends AsyncTask<String, Void, String> {
        double lat, lng;


        public Change(double lat, double lng) {
            this.lat = lat;
            this.lng = lng;
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... voids) {
            try {
                //서버에 있는 php 실행
                URL url = new URL("https://sgisapi.kostat.go.kr/OpenAPI3/transformation/transcoord.json?accessToken=d18f5a3a-be61-425a-92f4-574ab3b72409&src=4326&dst=5179&posX=" + lat + "&posY=" + lng);
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

        //결과값 출력 메소드
        public void show(String s) {

            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONObject object = jsonObject.getJSONObject("result");


                UTM_KX = object.getString("posX");
                UTM_KY = object.getString("posY");
//                Toast.makeText(getApplicationContext(), UTM_KX + "  " +UTM_KY, Toast.LENGTH_LONG).show();
                Achange achange = new Achange(UTM_KX, UTM_KY);
                achange.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        protected void onPostExecute(String s) {
            show(s);
        }


    }

    public class Achange extends AsyncTask<String, Void, String> {
        String UTM_KX, UTM_KY;

        public Achange(String UTM_KX, String UTM_KY) {
            this.UTM_KX = UTM_KX;
            this.UTM_KY = UTM_KY;
        }


        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... voids) {
            try {
                //서버에 있는 php 실행
                URL url = new URL("https://sgisapi.kostat.go.kr/OpenAPI3/addr/rgeocode.json?accessToken=d18f5a3a-be61-425a-92f4-574ab3b72409&x_coor=" + UTM_KX + "&y_coor=" + UTM_KY + "1816238.1323095055&addr_type=20");
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

        //결과값 출력 메소드
        public void show(String s) {

            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("result");

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject item = jsonArray.getJSONObject(i);

                    addr = item.getString("full_addr");
                }
                Population();
//                Toast.makeText(getApplicationContext(), addr, Toast.LENGTH_LONG).show();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        protected void onPostExecute(String s) {
            show(s);
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
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        FindApopulation findApopulation = new FindApopulation(addr, responseListener);
        RequestQueue queue = Volley.newRequestQueue(AnalysisActivity.this);
        queue.add(findApopulation);
    }
}