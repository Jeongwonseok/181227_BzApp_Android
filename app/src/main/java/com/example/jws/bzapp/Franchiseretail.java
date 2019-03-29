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

public class Franchiseretail extends AppCompatActivity {

    ImageButton btnBack;
    ImageButton btnFilter;

    //카드뷰에 표시될 정보 배열

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_franchisereatail);
        Button FranSearch;
        //카드뷰 추가 시킬 리사이클뷰 선언

        btnBack = (ImageButton)findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Franchiseretail.this, FranchiseActivity1.class);
                startActivity(intent);
            }
        });
        FranSearch=(Button)findViewById(R.id.franSearch);

        FranSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Franchiseretail.this,FranchiseSearch.class);
                startActivity(intent);
            }
        });

        //탭호스트 선언, 탭추가
        TabHost tabHost = (TabHost)findViewById(R.id.Host);
        tabHost.setup();
        tabHost.addTab(tabHost.newTabSpec("전체").setContent(R.id.tab1).setIndicator("전체"));
        tabHost.addTab(tabHost.newTabSpec("기타소매").setContent(R.id.tab2).setIndicator("기타소매"));
        tabHost.addTab(tabHost.newTabSpec("의류/패션").setContent(R.id.tab3).setIndicator("의류/패션"));
        tabHost.addTab(tabHost.newTabSpec("화장품").setContent(R.id.tab4).setIndicator("화장품"));
        tabHost.addTab(tabHost.newTabSpec("건강식품").setContent(R.id.tab5).setIndicator("건강식품"));
        tabHost.addTab(tabHost.newTabSpec("편의점").setContent(R.id.tab6).setIndicator("편의점"));
        tabHost.addTab(tabHost.newTabSpec("농수산물").setContent(R.id.tab7).setIndicator("농수산물"));
        tabHost.addTab(tabHost.newTabSpec("종합소매점").setContent(R.id.tab8).setIndicator("종합소매점"));
        tabHost.setCurrentTab(0);
        for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++)
        {tabHost.getTabWidget().getChildAt(i).setPadding(0,0,0,0);}


        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
               if(tabId.equals("전체")){
                FranchiseList franchiseList = new FranchiseList("전체");
                franchiseList.execute();  }
                else if (tabId.equals("기타소매")) {
                   FranchiseList franchiseList = new FranchiseList("기타소매");
                   franchiseList.execute();}
               else if (tabId.equals("의류/패션")) {
                   FranchiseList franchiseList = new FranchiseList("의류/패션");
                   franchiseList.execute();
               }  else if (tabId.equals("화장품")) {
                   FranchiseList franchiseList = new FranchiseList("화장품");
                   franchiseList.execute();
               }  else if (tabId.equals("건강식품")) {
                   FranchiseList franchiseList = new FranchiseList("건강식품");
                   franchiseList.execute();
               }
               else if (tabId.equals("편의점")) {
                   FranchiseList franchiseList = new FranchiseList("편의점");
                   franchiseList.execute();
               }
               else if (tabId.equals("농수산물")) {
                   FranchiseList franchiseList = new FranchiseList("농수산물");
                   franchiseList.execute();
               }
               else if (tabId.equals("종합소매점")) {
                   FranchiseList franchiseList = new FranchiseList("종합소매점");
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
                URL url = new URL("http://qwerr784.cafe24.com/Fretail.php");
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
            ArrayList<FranchiseInfo> OtherarrayList = new ArrayList<>();
            ArrayList<FranchiseInfo> ClothesarrayList = new ArrayList<>();
            ArrayList<FranchiseInfo> MakeuparrayList = new ArrayList<>();
            ArrayList<FranchiseInfo> HealtharrayList = new ArrayList<>();
            ArrayList<FranchiseInfo> ConveniencearrayList = new ArrayList<>();
            ArrayList<FranchiseInfo> AgriarrayList = new ArrayList<>();
            ArrayList<FranchiseInfo> SynarrayList = new ArrayList<>();

            RecyclerView recyclerView = (RecyclerView)findViewById(R.id.RV1);RecyclerView.LayoutManager manager = new LinearLayoutManager(Franchiseretail.this);
            recyclerView.setLayoutManager(manager);
            RecyclerView recyclerView2 = (RecyclerView)findViewById(R.id.RV2); RecyclerView.LayoutManager manager2 = new LinearLayoutManager(Franchiseretail.this);
            recyclerView2.setLayoutManager(manager2);
            RecyclerView recyclerView3 = (RecyclerView)findViewById(R.id.RV3); RecyclerView.LayoutManager manager3 = new LinearLayoutManager(Franchiseretail.this);
            recyclerView3.setLayoutManager(manager3);
            RecyclerView recyclerView4 = (RecyclerView)findViewById(R.id.RV4); RecyclerView.LayoutManager manager4 = new LinearLayoutManager(Franchiseretail.this);
            recyclerView4.setLayoutManager(manager4);
            RecyclerView recyclerView5 = (RecyclerView)findViewById(R.id.RV5); RecyclerView.LayoutManager manager5 = new LinearLayoutManager(Franchiseretail.this);
            recyclerView5.setLayoutManager(manager5);
            RecyclerView recyclerView6 = (RecyclerView)findViewById(R.id.RV6); RecyclerView.LayoutManager manager6 = new LinearLayoutManager(Franchiseretail.this);
            recyclerView6.setLayoutManager(manager6);
            RecyclerView recyclerView7 = (RecyclerView)findViewById(R.id.RV7); RecyclerView.LayoutManager manager7 = new LinearLayoutManager(Franchiseretail.this);
            recyclerView7.setLayoutManager(manager7);
            RecyclerView recyclerView8 = (RecyclerView)findViewById(R.id.RV8); RecyclerView.LayoutManager manager8 = new LinearLayoutManager(Franchiseretail.this);
            recyclerView8.setLayoutManager(manager8);






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


                    if (Category.equals("기타소매")){
                        FranchiseInfo franchiseInfo = new FranchiseInfo(Name,Storesu,Ownermoney,Asales17,Interior,Category);
                        OtherarrayList.add(franchiseInfo);
                    }
                    else if(Category.equals("의류/패션")){
                        FranchiseInfo franchiseInfo = new FranchiseInfo(Name,Storesu,Ownermoney,Asales17,Interior,Category);
                        ClothesarrayList.add(franchiseInfo);
                    }
                    else if(Category.equals("화장품")){
                        FranchiseInfo franchiseInfo = new FranchiseInfo(Name,Storesu,Ownermoney,Asales17,Interior,Category);
                        MakeuparrayList.add(franchiseInfo);
                    }
                    else if(Category.equals("건강식품")){
                        FranchiseInfo franchiseInfo = new FranchiseInfo(Name,Storesu,Ownermoney,Asales17,Interior,Category);
                        HealtharrayList.add(franchiseInfo);
                    }
                    else if(Category.equals("편의점")){
                        FranchiseInfo franchiseInfo = new FranchiseInfo(Name,Storesu,Ownermoney,Asales17,Interior,Category);
                        ConveniencearrayList.add(franchiseInfo);
                    }
                    else if(Category.equals("농수산물")){
                        FranchiseInfo franchiseInfo = new FranchiseInfo(Name,Storesu,Ownermoney,Asales17,Interior,Category);
                        AgriarrayList.add(franchiseInfo);
                    }
                    else if(Category.equals("종합소매점")){
                        FranchiseInfo franchiseInfo = new FranchiseInfo(Name,Storesu,Ownermoney,Asales17,Interior,Category);
                        SynarrayList.add(franchiseInfo);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            FranchiseAdapter AllfranchiseAdapter = new FranchiseAdapter(ALLarrayList);
            FranchiseAdapter OtherffranchiseAdapter = new FranchiseAdapter(OtherarrayList);
            FranchiseAdapter ClothesfranchiseAdapter = new FranchiseAdapter(ClothesarrayList);
            FranchiseAdapter MakeupfranchiseAdapter = new FranchiseAdapter(MakeuparrayList);
            FranchiseAdapter HealthfranchiseAdapter = new FranchiseAdapter(HealtharrayList);
            FranchiseAdapter ConveniencefranchiseAdapter = new FranchiseAdapter(ConveniencearrayList);
            FranchiseAdapter AgrifranchiseAdapter = new FranchiseAdapter(AgriarrayList);
            FranchiseAdapter SynfranchiseAdapter = new FranchiseAdapter(SynarrayList);
            if(kind.equals("전체")){
            recyclerView.setAdapter(AllfranchiseAdapter);}
            else  if(kind.equals("기타소매")){
                recyclerView2.setAdapter(OtherffranchiseAdapter);}
            else if(kind.equals("의류/패션")){
                recyclerView3.setAdapter(ClothesfranchiseAdapter);}
            else if(kind.equals("화장품")){
                recyclerView4.setAdapter(MakeupfranchiseAdapter);}
            else if(kind.equals("건강식품")){
                recyclerView5.setAdapter(HealthfranchiseAdapter);}
            else if(kind.equals("편의점")){
                recyclerView6.setAdapter(ConveniencefranchiseAdapter);}
            else if(kind.equals("농수산물")){
                recyclerView7.setAdapter(AgrifranchiseAdapter);}
            else if(kind.equals("종합소매점")){
                recyclerView8.setAdapter(SynfranchiseAdapter);}
        }

        @Override
        protected void onPostExecute(String s) {
            show(s);
        }


    }
}
