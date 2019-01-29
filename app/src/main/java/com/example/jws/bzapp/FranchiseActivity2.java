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

public class FranchiseActivity2 extends AppCompatActivity {

    ImageButton btnBack;
    ImageButton btnFilter;

    //카드뷰에 표시될 정보 배열

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_franchise2);


        btnBack = (ImageButton)findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FranchiseActivity2.this, FranchiseActivity1.class);
                startActivity(intent);
            }
        });


        //탭호스트 선언, 탭추가
        TabHost tabHost = (TabHost)findViewById(R.id.Host);
        tabHost.setup();
        tabHost.addTab(tabHost.newTabSpec("전체").setContent(R.id.tab1).setIndicator("전체"));
        tabHost.addTab(tabHost.newTabSpec("한식").setContent(R.id.tab2).setIndicator("한식"));
        tabHost.addTab(tabHost.newTabSpec("양식").setContent(R.id.tab3).setIndicator("양식"));
        tabHost.addTab(tabHost.newTabSpec("일식").setContent(R.id.tab4).setIndicator("일식"));
        tabHost.addTab(tabHost.newTabSpec("중식").setContent(R.id.tab5).setIndicator("중식"));



        //카드뷰 추가 시킬 리사이클뷰 선언
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.RV1);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        //FranchiseInfo형 배열 선언

        FranchiseList franchiseList = new FranchiseList("http://qwerr784.cafe24.com/Ffast.php");
        franchiseList.execute();
    }













   class FranchiseList extends AsyncTask<Void, Void, String> {

        String urlnumber;
        public FranchiseList(String url){
            this.urlnumber=url;
        }
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                //서버에 있는 php 실행
                URL url = new URL(urlnumber);
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



            ArrayList<FranchiseInfo> arrayList = new ArrayList<>();

            RecyclerView recyclerView = (RecyclerView)findViewById(R.id.RV1);
            if(urlnumber.equals("http://qwerr784.cafe24.com/Ffast.php")) {
                RecyclerView.LayoutManager manager = new LinearLayoutManager(FranchiseActivity2.this);
                recyclerView.setLayoutManager(manager);
            }


            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count = 0;
                String Name,Storesu, Ownermoney, Asales17,Interior;
                while (count < jsonArray.length()) {
                    JSONObject object = jsonArray.getJSONObject(count);
                    Name = object.getString("Shopname");
                    Storesu = object.getString("StoreSu17");
                    Ownermoney = object.getString("Ownermoney");
                    Asales17 = object.getString("Asales17");
                    Interior = object.getString("Interior");
                    ///
                    //매출
                    if(Storesu.equals("정보없음")){
                        Storesu="정보없음";
                    }else  Storesu+="개";
                    //////////////////////////
                    FranchiseInfo franchiseInfo = new FranchiseInfo(Name,Storesu,Ownermoney,Asales17,Interior);
                    arrayList.add(franchiseInfo);
                    count++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            FranchiseAdapter franchiseAdapter = new FranchiseAdapter(arrayList);
            recyclerView.setAdapter(franchiseAdapter);
        }

        @Override
        protected void onPostExecute(String s) {
            show(s);
        }


    }
}
