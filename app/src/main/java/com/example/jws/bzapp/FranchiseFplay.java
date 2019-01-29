//package com.example.jws.bzapp;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.View;
//import android.widget.ImageButton;
//import android.widget.TabHost;
//
//public class FranchiseFplay extends AppCompatActivity {
//    ImageButton btnBack;
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_fplay);
//
//        btnBack = (ImageButton)findViewById(R.id.btnBack);
//        btnBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(FranchiseFplay.this, FranchiseActivity1.class);
//                startActivity(intent);
//            }
//        });
//
//
//        //탭호스트 선언, 탭추가
//        TabHost tabHost = (TabHost)findViewById(R.id.Host);
//        tabHost.setup();
//        tabHost.addTab(tabHost.newTabSpec("전체").setContent(R.id.tab1).setIndicator("전체"));
//        tabHost.addTab(tabHost.newTabSpec("한식").setContent(R.id.tab2).setIndicator("한식"));
//        tabHost.addTab(tabHost.newTabSpec("양식").setContent(R.id.tab3).setIndicator("양식"));
//        tabHost.addTab(tabHost.newTabSpec("일식").setContent(R.id.tab4).setIndicator("일식"));
//        tabHost.addTab(tabHost.newTabSpec("중식").setContent(R.id.tab5).setIndicator("중식"));
//
//        //카드뷰 추가 시킬 리사이클뷰 선언
//        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.RV1);
//        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(manager);
//
//        //FranchiseInfo형 배열 선언
//
//        FranchiseActivity2.FranchiseList franchiseList = new FranchiseActivity2.FranchiseList("http://qwerr784.cafe24.com/Ffast.php");
//        franchiseList.execute();
//    }
//}
