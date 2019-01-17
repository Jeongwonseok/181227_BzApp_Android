package com.example.jws.bzapp;

import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
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

import java.io.IOException;
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
    //api 테스트
    TextView test1;

    ShopApi shopapi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);
        geocoder = new Geocoder(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // api테스트
        shopapi = new ShopApi();

        test1 = (TextView) findViewById(R.id.txttest);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnHome = (ImageButton) findViewById(R.id.btnHome);
        population = (TextView)findViewById(R.id.population);
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
                    String test = shopapi.getXmlData();
                    test1.setText(test);
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
        Intent intent = getIntent();
        mLat = intent.getDoubleExtra("mLat", 0);
        mLong = intent.getDoubleExtra("mLong", 0);
        a = intent.getIntExtra("a", 0);
        address(mLat, mLong);
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

    public void address(double mLat,double mLong){
        List<Address> list = null;
        try{
            double lat= mLat;
            double lng=mLong;
            list=geocoder.getFromLocation(lat,lng,1);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("test","입출력 오류");
        }
        if(list!=null){
            if(list.size()==0){
                Toast.makeText(getApplicationContext(),"해당되는 주소 정보는 없습니다.",Toast.LENGTH_SHORT).show();
            }else {
                String address=list.get(0).getAddressLine(0).toString();
                String address1=address.substring(5);
                int su=address1.lastIndexOf("동");
                address1=address1.substring(0,su+1);
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
                                if(count >= 1) {
                                    fid = fid + ", ";
                                }
                                JSONObject object = jsonArray.getJSONObject(count);
                                fid = fid + object.getString("population");
                                count++;
                            }
                            if (jsonArray.length() > 0) {
                                Toast.makeText(getApplicationContext(),fid,Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(),"실패",Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                population.setText(address1);
                FindRpopulation findpopulation = new FindRpopulation(address1,responseListener);
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
    }
}

//    public void onAddCircle(int a){
//        LatLng position = new LatLng(mLat,mLong);
//
//        //나의 위치 마커
////        MarkerOptions mymarker = new MarkerOptions().position(position);
//
//        //사이클 반경 설정
//        CircleOptions circle = new CircleOptions().center(position).radius(a)//반경
//                .strokeColor(0)//선 넓이
//                .fillColor(Color.parseColor("#880000ff"));
////        this.mMap.addMarker(mymarker);
//        this.mMap.addCircle(circle);
//    }
//}
