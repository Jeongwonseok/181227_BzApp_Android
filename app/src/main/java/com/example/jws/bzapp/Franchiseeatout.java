package com.example.jws.bzapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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

public class Franchiseeatout extends AppCompatActivity {

    ImageButton btnBack;
    ImageButton btnFilter;

    //카드뷰에 표시될 정보 배열

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_franchiseeatout);

        //카드뷰 추가 시킬 리사이클뷰 선언

        btnBack = (ImageButton)findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Franchiseeatout.this, FranchiseActivity1.class);
                startActivity(intent);
            }
        });


        //탭호스트 선언, 탭추가
        TabHost tabHost = (TabHost)findViewById(R.id.Host);
        tabHost.setup();
        tabHost.addTab(tabHost.newTabSpec("전체").setContent(R.id.tab1).setIndicator("전체"));
        tabHost.addTab(tabHost.newTabSpec("한식").setContent(R.id.tab2).setIndicator("한식"));
        tabHost.addTab(tabHost.newTabSpec("퓨전/기타").setContent(R.id.tab3).setIndicator("퓨전/기타"));
        tabHost.addTab(tabHost.newTabSpec("분식").setContent(R.id.tab4).setIndicator("분식"));
        tabHost.addTab(tabHost.newTabSpec("양식").setContent(R.id.tab5).setIndicator("양식"));
        tabHost.addTab(tabHost.newTabSpec("일식").setContent(R.id.tab6).setIndicator("일식"));
        tabHost.addTab(tabHost.newTabSpec("중식").setContent(R.id.tab7).setIndicator("중식"));
        tabHost.addTab(tabHost.newTabSpec("세계음식").setContent(R.id.tab8).setIndicator("세계음식"));
        tabHost.setCurrentTab(0);
        for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++)
        {tabHost.getTabWidget().getChildAt(i).setPadding(0,0,0,0);}


        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
               if(tabId.equals("전체")){
                FranchiseList franchiseList = new FranchiseList("전체");
                franchiseList.execute();  }
                else if (tabId.equals("한식")) {
                   FranchiseList franchiseList = new FranchiseList("한식");
                   franchiseList.execute();}
               else if (tabId.equals("퓨전/기타")) {
                   FranchiseList franchiseList = new FranchiseList("퓨전/기타");
                   franchiseList.execute();
               }  else if (tabId.equals("분식")) {
                   FranchiseList franchiseList = new FranchiseList("분식");
                   franchiseList.execute();
               }  else if (tabId.equals("양식")) {
                   FranchiseList franchiseList = new FranchiseList("양식");
                   franchiseList.execute();
               }
               else if (tabId.equals("일식")) {
                   FranchiseList franchiseList = new FranchiseList("일식");
                   franchiseList.execute();
               }
               else if (tabId.equals("중식")) {
                   FranchiseList franchiseList = new FranchiseList("중식");
                   franchiseList.execute();
               }
               else if (tabId.equals("세계음식")) {
                   FranchiseList franchiseList = new FranchiseList("세계음식");
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
                URL url = new URL("http://qwerr784.cafe24.com/Featout.php");
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
            ArrayList<FranchiseInfo> KoreaarrayList = new ArrayList<>();
            ArrayList<FranchiseInfo> FusionarrayList = new ArrayList<>();
            ArrayList<FranchiseInfo> SnackarrayList = new ArrayList<>();
            ArrayList<FranchiseInfo> WestarrayList = new ArrayList<>();
            ArrayList<FranchiseInfo> JapenarrayList = new ArrayList<>();
            ArrayList<FranchiseInfo> ChinaiarrayList = new ArrayList<>();
            ArrayList<FranchiseInfo> GlobalarrayList = new ArrayList<>();

            RecyclerView recyclerView = (RecyclerView)findViewById(R.id.RV1);RecyclerView.LayoutManager manager = new LinearLayoutManager(Franchiseeatout.this);
            recyclerView.setLayoutManager(manager);
            RecyclerView recyclerView2 = (RecyclerView)findViewById(R.id.RV2); RecyclerView.LayoutManager manager2 = new LinearLayoutManager(Franchiseeatout.this);
            recyclerView2.setLayoutManager(manager2);
            RecyclerView recyclerView3 = (RecyclerView)findViewById(R.id.RV3); RecyclerView.LayoutManager manager3 = new LinearLayoutManager(Franchiseeatout.this);
            recyclerView3.setLayoutManager(manager3);
            RecyclerView recyclerView4 = (RecyclerView)findViewById(R.id.RV4); RecyclerView.LayoutManager manager4 = new LinearLayoutManager(Franchiseeatout.this);
            recyclerView4.setLayoutManager(manager4);
            RecyclerView recyclerView5 = (RecyclerView)findViewById(R.id.RV5); RecyclerView.LayoutManager manager5 = new LinearLayoutManager(Franchiseeatout.this);
            recyclerView5.setLayoutManager(manager5);
            RecyclerView recyclerView6 = (RecyclerView)findViewById(R.id.RV6); RecyclerView.LayoutManager manager6 = new LinearLayoutManager(Franchiseeatout.this);
            recyclerView6.setLayoutManager(manager6);
            RecyclerView recyclerView7 = (RecyclerView)findViewById(R.id.RV7); RecyclerView.LayoutManager manager7 = new LinearLayoutManager(Franchiseeatout.this);
            recyclerView7.setLayoutManager(manager7);
            RecyclerView recyclerView8 = (RecyclerView)findViewById(R.id.RV8); RecyclerView.LayoutManager manager8 = new LinearLayoutManager(Franchiseeatout.this);
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
                    FranchiseInfo ALLfranchiseInfo = new FranchiseInfo(Name,Storesu,Ownermoney,Asales17,Interior);
                    ALLarrayList.add(ALLfranchiseInfo);
                    count++;




                    if (Category.equals("한식")){
                        FranchiseInfo franchiseInfo = new FranchiseInfo(Name,Storesu,Ownermoney,Asales17,Interior);
                        KoreaarrayList.add(franchiseInfo);
                    }
                    else if(Category.equals("퓨전/기타")){
                        FranchiseInfo franchiseInfo = new FranchiseInfo(Name,Storesu,Ownermoney,Asales17,Interior);
                        FusionarrayList.add(franchiseInfo);
                    }
                    else if(Category.equals("분식")){
                        FranchiseInfo franchiseInfo = new FranchiseInfo(Name,Storesu,Ownermoney,Asales17,Interior);
                        SnackarrayList.add(franchiseInfo);
                    }
                    else if(Category.equals("양식")){
                        FranchiseInfo franchiseInfo = new FranchiseInfo(Name,Storesu,Ownermoney,Asales17,Interior);
                        WestarrayList.add(franchiseInfo);
                    }
                    else if(Category.equals("일식")){
                        FranchiseInfo franchiseInfo = new FranchiseInfo(Name,Storesu,Ownermoney,Asales17,Interior);
                        JapenarrayList.add(franchiseInfo);
                    }
                    else if(Category.equals("중식")){
                        FranchiseInfo franchiseInfo = new FranchiseInfo(Name,Storesu,Ownermoney,Asales17,Interior);
                        ChinaiarrayList.add(franchiseInfo);
                    }
                    else if(Category.equals("세계음식")){
                        FranchiseInfo franchiseInfo = new FranchiseInfo(Name,Storesu,Ownermoney,Asales17,Interior);
                        GlobalarrayList.add(franchiseInfo);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            FranchiseAdapter AllfranchiseAdapter = new FranchiseAdapter(ALLarrayList);
            FranchiseAdapter KoreafranchiseAdapter = new FranchiseAdapter(KoreaarrayList);
            FranchiseAdapter FusionfranchiseAdapter = new FranchiseAdapter(FusionarrayList);
            FranchiseAdapter SnackfranchiseAdapter = new FranchiseAdapter(SnackarrayList);
            FranchiseAdapter WestfranchiseAdapter = new FranchiseAdapter(WestarrayList);
            FranchiseAdapter JapenfranchiseAdapter = new FranchiseAdapter(JapenarrayList);
            FranchiseAdapter ChinafranchiseAdapter = new FranchiseAdapter(ChinaiarrayList);
            FranchiseAdapter GlobalfranchiseAdapter = new FranchiseAdapter(GlobalarrayList);
            if(kind.equals("전체")){
            recyclerView.setAdapter(AllfranchiseAdapter);}
            else  if(kind.equals("한식")){
                recyclerView2.setAdapter(KoreafranchiseAdapter);}
            else if(kind.equals("퓨전/기타")){
                recyclerView3.setAdapter(FusionfranchiseAdapter);}
            else if(kind.equals("분식")){
                recyclerView4.setAdapter(SnackfranchiseAdapter);}
            else if(kind.equals("양식")){
                recyclerView5.setAdapter(WestfranchiseAdapter);}
            else if(kind.equals("일식")){
                recyclerView6.setAdapter(JapenfranchiseAdapter);}
            else if(kind.equals("중식")){
                recyclerView7.setAdapter(ChinafranchiseAdapter);}
            else if(kind.equals("세계음식")){
                recyclerView8.setAdapter(GlobalfranchiseAdapter);}
        }

        @Override
        protected void onPostExecute(String s) {
            show(s);
        }


    }
}
