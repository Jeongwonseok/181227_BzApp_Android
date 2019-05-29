package com.example.jws.bzapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TabHost;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Franchisedrink extends AppCompatActivity {

    ImageButton btnBack;
    //카드뷰에 표시될 정보 배열
    Button FranSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_franchisedrink);

        //카드뷰 추가 시킬 리사이클뷰 선언

        btnBack = (ImageButton)findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        FranSearch=(Button)findViewById(R.id.franSearch);
        FranSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Franchisedrink.this,FranchiseSearch.class);
                startActivity(intent);
            }
        });


        //탭호스트 선언, 탭추가
        TabHost tabHost = (TabHost)findViewById(R.id.Host);
        tabHost.setup();
        tabHost.addTab(tabHost.newTabSpec("전체").setContent(R.id.tab1).setIndicator("전체"));
        tabHost.addTab(tabHost.newTabSpec("커피").setContent(R.id.tab2).setIndicator("커피"));
        tabHost.addTab(tabHost.newTabSpec("제과제빵").setContent(R.id.tab3).setIndicator("제과제빵"));
        tabHost.addTab(tabHost.newTabSpec("주스/차").setContent(R.id.tab4).setIndicator("주스/차"));
        tabHost.addTab(tabHost.newTabSpec("아이스크림/빙수").setContent(R.id.tab5).setIndicator("아이스크림/빙수"));
        tabHost.setCurrentTab(0);
        for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++)
        {tabHost.getTabWidget().getChildAt(i).setPadding(0,0,0,0);}


        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
               if(tabId.equals("전체")){
                FranchiseList franchiseList = new FranchiseList("전체");
                franchiseList.execute();  }
                else if (tabId.equals("커피")) {
                   FranchiseList franchiseList = new FranchiseList("커피");
                   franchiseList.execute();}
               else if (tabId.equals("제과제빵")) {
                   FranchiseList franchiseList = new FranchiseList("제과제빵");
                   franchiseList.execute();
               }  else if (tabId.equals("주스/차")) {
                   FranchiseList franchiseList = new FranchiseList("주스/차");
                   franchiseList.execute();
               }  else if (tabId.equals("아이스크림/빙수")) {
                   FranchiseList franchiseList = new FranchiseList("아이스크림/빙수");
                   franchiseList.execute();
               }
            }
        });


        //FranchiseInfo형 배열 선언
        FranchiseList franchiseList = new FranchiseList("전체");
        franchiseList.execute();
    }

   class FranchiseList extends AsyncTask<Void, Void, String> {

        String kind;
        public FranchiseList(String Kind){
            this.kind=Kind;
        }
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                //서버에 있는 php 실행
                URL url = new URL("http://qwerr784.cafe24.com/Fdrink.php");
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

            ArrayList<FranchiseInfo> ALLarrayList = new ArrayList<>();
            ArrayList<FranchiseInfo> CoffeearrayList = new ArrayList<>();
            ArrayList<FranchiseInfo> BreadarrayList = new ArrayList<>();
            ArrayList<FranchiseInfo> JuicearrayList = new ArrayList<>();
            ArrayList<FranchiseInfo> AcreamarrayList = new ArrayList<>();

            RecyclerView recyclerView = (RecyclerView)findViewById(R.id.RV1);RecyclerView.LayoutManager manager = new LinearLayoutManager(Franchisedrink.this);
            recyclerView.setLayoutManager(manager);
            RecyclerView recyclerView2 = (RecyclerView)findViewById(R.id.RV2); RecyclerView.LayoutManager manager2 = new LinearLayoutManager(Franchisedrink.this);
            recyclerView2.setLayoutManager(manager2);
            RecyclerView recyclerView3 = (RecyclerView)findViewById(R.id.RV3); RecyclerView.LayoutManager manager3 = new LinearLayoutManager(Franchisedrink.this);
            recyclerView3.setLayoutManager(manager3);
            RecyclerView recyclerView4 = (RecyclerView)findViewById(R.id.RV4); RecyclerView.LayoutManager manager4 = new LinearLayoutManager(Franchisedrink.this);
            recyclerView4.setLayoutManager(manager4);
            RecyclerView recyclerView5 = (RecyclerView)findViewById(R.id.RV5); RecyclerView.LayoutManager manager5 = new LinearLayoutManager(Franchisedrink.this);
            recyclerView5.setLayoutManager(manager5);



//            RecyclerView recyclerView = (RecyclerView)findViewById(R.id.RV1);
//            RecyclerView recyclerView2 = (RecyclerView)findViewById(R.id.RV2);
//            RecyclerView recyclerView3 = (RecyclerView)findViewById(R.id.RV3);
//            RecyclerView recyclerView4 = (RecyclerView)findViewById(R.id.RV4);
//            RecyclerView recyclerView5 = (RecyclerView)findViewById(R.id.RV5);
//            if(urlnumber.equals("http://qwerr784.cafe24.com/Ffast.php")) {
//                RecyclerView.LayoutManager manager = new LinearLayoutManager(FranchiseActivity2.this);
//                recyclerView.setLayoutManager(manager);
//            }
//            else  if(urlnumber.equals("http://qwerr784.cafe24.com/FfastC.php")) {
//                RecyclerView.LayoutManager manager = new LinearLayoutManager(FranchiseActivity2.this);
//                recyclerView2.setLayoutManager(manager);
//            }


            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count = 0;
                String Name,Storesu, Ownermoney, Asales17,Interior,Category;
                while (count < jsonArray.length()) {
                    JSONObject object = jsonArray.getJSONObject(count);
                    Name = object.getString("Shopname");
                    Storesu = object.getString("StoreSu17");
                    Ownermoney = object.getString("Ownermoney");
                    Asales17 = object.getString("Asales17");
                    Interior = object.getString("Interior");
                    Category = object.getString("Category");
                    ///
                    //매출
                    if(Storesu.equals("정보없음")){
                        Storesu="정보없음";
                    }else  Storesu+="개";
                    //////////////////////////
                    FranchiseInfo ALLfranchiseInfo = new FranchiseInfo(Name,Storesu,Ownermoney,Asales17,Interior,Category);
                    ALLarrayList.add(ALLfranchiseInfo);
                    count++;

                    if (Category.equals("커피")){
                        FranchiseInfo franchiseInfo = new FranchiseInfo(Name,Storesu,Ownermoney,Asales17,Interior,Category);
                        CoffeearrayList.add(franchiseInfo);
                    }
                    else if(Category.equals("제과제빵")){
                        FranchiseInfo franchiseInfo = new FranchiseInfo(Name,Storesu,Ownermoney,Asales17,Interior,Category);
                        BreadarrayList.add(franchiseInfo);
                    }
                    else if(Category.equals("주스/차")){
                        FranchiseInfo franchiseInfo = new FranchiseInfo(Name,Storesu,Ownermoney,Asales17,Interior,Category);
                        JuicearrayList.add(franchiseInfo);
                    }
                    else if(Category.equals("아이스크림/빙수")){
                        FranchiseInfo franchiseInfo = new FranchiseInfo(Name,Storesu,Ownermoney,Asales17,Interior,Category);
                        AcreamarrayList.add(franchiseInfo);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            FranchiseAdapter AllfranchiseAdapter = new FranchiseAdapter(ALLarrayList);
            FranchiseAdapter CoffeeranchiseAdapter = new FranchiseAdapter(CoffeearrayList);
            FranchiseAdapter BreadfranchiseAdapter = new FranchiseAdapter(BreadarrayList);
            FranchiseAdapter JuicefranchiseAdapter = new FranchiseAdapter(JuicearrayList);
            FranchiseAdapter AcreamfranchiseAdapter = new FranchiseAdapter(AcreamarrayList);
            if(kind.equals("전체")){
            recyclerView.setAdapter(AllfranchiseAdapter);}
            else  if(kind.equals("커피")){
                recyclerView2.setAdapter(CoffeeranchiseAdapter);}

            else if(kind.equals("제과제빵")){
                recyclerView3.setAdapter(BreadfranchiseAdapter);}

            else if(kind.equals("주스/차")){
                recyclerView4.setAdapter(JuicefranchiseAdapter);}

            else if(kind.equals("아이스크림/빙수")){
                recyclerView5.setAdapter(AcreamfranchiseAdapter);}
        }

        @Override
        protected void onPostExecute(String s) {
            show(s);
        }


    }
}
