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

public class Franchisefastfood extends AppCompatActivity {

    ImageButton btnBack;
    ImageButton btnFilter;

    //카드뷰에 표시될 정보 배열

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_franchisefastfood);

        //카드뷰 추가 시킬 리사이클뷰 선언

        btnBack = (ImageButton)findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Franchisefastfood.this, FranchiseActivity1.class);
                startActivity(intent);
            }
        });


        //탭호스트 선언, 탭추가
        TabHost tabHost = (TabHost)findViewById(R.id.Host);
        tabHost.setup();
        tabHost.addTab(tabHost.newTabSpec("전체").setContent(R.id.tab1).setIndicator("전체"));
        tabHost.addTab(tabHost.newTabSpec("치킨").setContent(R.id.tab2).setIndicator("치킨"));
        tabHost.addTab(tabHost.newTabSpec("주점").setContent(R.id.tab3).setIndicator("주점"));
        tabHost.addTab(tabHost.newTabSpec("피자").setContent(R.id.tab4).setIndicator("피자"));
        tabHost.addTab(tabHost.newTabSpec("패스트푸드").setContent(R.id.tab5).setIndicator("패스트푸드"));
        tabHost.setCurrentTab(0);
        for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++)
        {tabHost.getTabWidget().getChildAt(i).setPadding(0,0,0,0);}


        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
               if(tabId.equals("전체")){
                FranchiseList franchiseList = new FranchiseList("전체");
                franchiseList.execute();}
                else if (tabId.equals("치킨")) {
                   FranchiseList franchiseList = new FranchiseList("치킨");
                   franchiseList.execute();}
               else if (tabId.equals("주점")) {
                   FranchiseList franchiseList = new FranchiseList("주점");
                   franchiseList.execute();
               }  else if (tabId.equals("피자")) {
                   FranchiseList franchiseList = new FranchiseList("피자");
                   franchiseList.execute();
               }  else if (tabId.equals("패스트푸드")) {
                   FranchiseList franchiseList = new FranchiseList("패스트푸드");
                   franchiseList.execute();
               }
            }
        });


        //FranchiseInfo형 배열 선언
        FranchiseList franchiseList = new FranchiseList("전체");
        franchiseList.execute();
    }

   class FranchiseList extends AsyncTask<Void, Void, String> {

        String Kind;
        public FranchiseList(String Kind){
            this.Kind=Kind;
        }
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                //서버에 있는 php 실행
                URL url = new URL("http://qwerr784.cafe24.com/Ffast.php");
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
            ArrayList<FranchiseInfo> ChickensarrayList = new ArrayList<>();
            ArrayList<FranchiseInfo> BeararrayList = new ArrayList<>();
            ArrayList<FranchiseInfo> PizzaarrayList = new ArrayList<>();
            ArrayList<FranchiseInfo> fastfoodarrayList = new ArrayList<>();


            RecyclerView recyclerView = (RecyclerView)findViewById(R.id.RV1);RecyclerView.LayoutManager manager = new LinearLayoutManager(Franchisefastfood.this);
            recyclerView.setLayoutManager(manager);
            RecyclerView recyclerView2 = (RecyclerView)findViewById(R.id.RV2); RecyclerView.LayoutManager manager2 = new LinearLayoutManager(Franchisefastfood.this);
            recyclerView2.setLayoutManager(manager2);
            RecyclerView recyclerView3 = (RecyclerView)findViewById(R.id.RV3); RecyclerView.LayoutManager manager3 = new LinearLayoutManager(Franchisefastfood.this);
            recyclerView3.setLayoutManager(manager3);
            RecyclerView recyclerView4 = (RecyclerView)findViewById(R.id.RV4); RecyclerView.LayoutManager manager4 = new LinearLayoutManager(Franchisefastfood.this);
            recyclerView4.setLayoutManager(manager4);
            RecyclerView recyclerView5 = (RecyclerView)findViewById(R.id.RV5); RecyclerView.LayoutManager manager5 = new LinearLayoutManager(Franchisefastfood.this);
            recyclerView5.setLayoutManager(manager5);


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
                    if (Category.equals("치킨")){
                        FranchiseInfo franchiseInfo = new FranchiseInfo(Name,Storesu,Ownermoney,Asales17,Interior,Category);
                        ChickensarrayList.add(franchiseInfo);
                    }
                    else if(Category.equals("주점")){
                        FranchiseInfo franchiseInfo = new FranchiseInfo(Name,Storesu,Ownermoney,Asales17,Interior,Category);
                        BeararrayList.add(franchiseInfo);
                    }
                    else if(Category.equals("피자")){
                        FranchiseInfo franchiseInfo = new FranchiseInfo(Name,Storesu,Ownermoney,Asales17,Interior,Category);
                        PizzaarrayList.add(franchiseInfo);
                    }
                    else if(Category.equals("패스트푸드")){
                        FranchiseInfo franchiseInfo = new FranchiseInfo(Name,Storesu,Ownermoney,Asales17,Interior,Category);
                        fastfoodarrayList.add(franchiseInfo);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            FranchiseAdapter AllfranchiseAdapter = new FranchiseAdapter(ALLarrayList);
            FranchiseAdapter ChickenfranchiseAdapter = new FranchiseAdapter(ChickensarrayList);
            FranchiseAdapter BearfranchiseAdapter = new FranchiseAdapter(BeararrayList);
            FranchiseAdapter PizzafranchiseAdapter = new FranchiseAdapter(PizzaarrayList);
            FranchiseAdapter fastfoddfranchiseAdapter = new FranchiseAdapter(fastfoodarrayList);

            if(Kind.equals("전체")){
                recyclerView.setAdapter(AllfranchiseAdapter);}
            else  if(Kind.equals("치킨")){
                recyclerView2.setAdapter(ChickenfranchiseAdapter);}

            else if(Kind.equals("주점")){
                recyclerView3.setAdapter(BearfranchiseAdapter);}

            else if(Kind.equals("피자")){
                recyclerView4.setAdapter(PizzafranchiseAdapter);}

            else if(Kind.equals("패스트푸드")){
                recyclerView5.setAdapter(fastfoddfranchiseAdapter);}
        }

        @Override
        protected void onPostExecute(String s) {
            show(s);
        }


    }
}
