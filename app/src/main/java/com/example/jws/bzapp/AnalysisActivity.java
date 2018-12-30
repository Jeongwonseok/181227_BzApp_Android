package com.example.jws.bzapp;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

public class AnalysisActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    double mLat = 0;
    double mLong = 0;
    LinearLayout pLayout;
    ImageButton pbtn;
    ImageButton btnBack;
    ImageButton btnHome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnHome = (ImageButton) findViewById(R.id.btnHome);

        pLayout = (LinearLayout) findViewById(R.id.pLayout);
        pbtn = (ImageButton) findViewById(R.id.pbtn);

        pbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pLayout.getVisibility() == View.VISIBLE) {
                    pLayout.setVisibility(View.GONE);
                } else {
                    pLayout.setVisibility(View.VISIBLE);
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
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(mLat, mLong);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 16));
//        onAddCircle(100);
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
