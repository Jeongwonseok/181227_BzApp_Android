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

public class Franchiseservice extends AppCompatActivity {

    ImageButton btnBack;
    ImageButton btnFilter;

    //카드뷰에 표시될 정보 배열

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_franchiseservice);

        //카드뷰 추가 시킬 리사이클뷰 선언

        btnBack = (ImageButton)findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Franchiseservice.this, FranchiseActivity1.class);
                startActivity(intent);
            }
        });


        //탭호스트 선언, 탭추가
        TabHost tabHost = (TabHost)findViewById(R.id.Host);
        tabHost.setup();
        tabHost.addTab(tabHost.newTabSpec("전체").setContent(R.id.tab1).setIndicator("전체"));
        tabHost.addTab(tabHost.newTabSpec("기타서비스").setContent(R.id.tab2).setIndicator("기타서비스"));
        tabHost.addTab(tabHost.newTabSpec("미용").setContent(R.id.tab3).setIndicator("미용"));
        tabHost.addTab(tabHost.newTabSpec("기타교육").setContent(R.id.tab4).setIndicator("기타교육"));
        tabHost.addTab(tabHost.newTabSpec("외국어교육").setContent(R.id.tab5).setIndicator("외국어교육"));
        tabHost.addTab(tabHost.newTabSpec("교과교육").setContent(R.id.tab6).setIndicator("교과교육"));
        tabHost.addTab(tabHost.newTabSpec("유아").setContent(R.id.tab7).setIndicator("유아"));
        tabHost.addTab(tabHost.newTabSpec("자동차").setContent(R.id.tab8).setIndicator("자동차"));
        tabHost.addTab(tabHost.newTabSpec("안경").setContent(R.id.tab9).setIndicator("안경"));
        tabHost.addTab(tabHost.newTabSpec("세탁").setContent(R.id.tab10).setIndicator("세탁"));
        tabHost.addTab(tabHost.newTabSpec("반려동물").setContent(R.id.tab11).setIndicator("반려동물"));
        tabHost.addTab(tabHost.newTabSpec("운송").setContent(R.id.tab12).setIndicator("운송"));
        tabHost.addTab(tabHost.newTabSpec("부동산/임대").setContent(R.id.tab13).setIndicator("부동산/임대"));
        tabHost.setCurrentTab(0);
        for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++)
        {tabHost.getTabWidget().getChildAt(i).setPadding(0,0,0,0);}


        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
               if(tabId.equals("전체")){
                FranchiseList franchiseList = new FranchiseList("전체");
                franchiseList.execute();  }
                else if (tabId.equals("기타서비스")) {
                   FranchiseList franchiseList = new FranchiseList("기타서비스");
                   franchiseList.execute();}
               else if (tabId.equals("미용")) {
                   FranchiseList franchiseList = new FranchiseList("미용");
                   franchiseList.execute();
               }  else if (tabId.equals("기타교육")) {
                   FranchiseList franchiseList = new FranchiseList("기타교육");
                   franchiseList.execute();
               }  else if (tabId.equals("외국어교육")) {
                   FranchiseList franchiseList = new FranchiseList("외국어교육");
                   franchiseList.execute();
               }
               else if (tabId.equals("교과교육")) {
                   FranchiseList franchiseList = new FranchiseList("교과교육");
                   franchiseList.execute();
               }
               else if (tabId.equals("유아")) {
                   FranchiseList franchiseList = new FranchiseList("유아");
                   franchiseList.execute();
               }
               else if (tabId.equals("자동차")) {
                   FranchiseList franchiseList = new FranchiseList("자동차");
                   franchiseList.execute();
               }
               else if (tabId.equals("안경")) {
                   FranchiseList franchiseList = new FranchiseList("안경");
                   franchiseList.execute();
               }
               else if (tabId.equals("세탁")) {
                   FranchiseList franchiseList = new FranchiseList("세탁");
                   franchiseList.execute();
               }
               else if (tabId.equals("반려동물")) {
                   FranchiseList franchiseList = new FranchiseList("반려동물");
                   franchiseList.execute();
               }  else if (tabId.equals("운송")) {
                   FranchiseList franchiseList = new FranchiseList("운송");
                   franchiseList.execute();
               }
               else if (tabId.equals("부동산/임대")) {
                   FranchiseList franchiseList = new FranchiseList("부동산/임대");
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
                URL url = new URL("http://qwerr784.cafe24.com/Fservice.php");
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
            ArrayList<FranchiseInfo> ServicearrayList = new ArrayList<>();
            ArrayList<FranchiseInfo> HairarrayList = new ArrayList<>();
            ArrayList<FranchiseInfo> EduparrayList = new ArrayList<>();
            ArrayList<FranchiseInfo> FeduarrayList = new ArrayList<>();
            ArrayList<FranchiseInfo> SeduarrayList = new ArrayList<>();
            ArrayList<FranchiseInfo> ChildarrayList = new ArrayList<>();
            ArrayList<FranchiseInfo> CararrayList = new ArrayList<>();
            ArrayList<FranchiseInfo> GlassarrayList = new ArrayList<>();
            ArrayList<FranchiseInfo> WasharrayList = new ArrayList<>();
            ArrayList<FranchiseInfo> PetarrayList = new ArrayList<>();
            ArrayList<FranchiseInfo> TransarrayList = new ArrayList<>();
            ArrayList<FranchiseInfo> HomearrayList = new ArrayList<>();


            RecyclerView recyclerView = (RecyclerView)findViewById(R.id.RV1);RecyclerView.LayoutManager manager = new LinearLayoutManager(Franchiseservice.this);
            recyclerView.setLayoutManager(manager);
            RecyclerView recyclerView2 = (RecyclerView)findViewById(R.id.RV2); RecyclerView.LayoutManager manager2 = new LinearLayoutManager(Franchiseservice.this);
            recyclerView2.setLayoutManager(manager2);
            RecyclerView recyclerView3 = (RecyclerView)findViewById(R.id.RV3); RecyclerView.LayoutManager manager3 = new LinearLayoutManager(Franchiseservice.this);
            recyclerView3.setLayoutManager(manager3);
            RecyclerView recyclerView4 = (RecyclerView)findViewById(R.id.RV4); RecyclerView.LayoutManager manager4 = new LinearLayoutManager(Franchiseservice.this);
            recyclerView4.setLayoutManager(manager4);
            RecyclerView recyclerView5 = (RecyclerView)findViewById(R.id.RV5); RecyclerView.LayoutManager manager5 = new LinearLayoutManager(Franchiseservice.this);
            recyclerView5.setLayoutManager(manager5);
            RecyclerView recyclerView6 = (RecyclerView)findViewById(R.id.RV6); RecyclerView.LayoutManager manager6 = new LinearLayoutManager(Franchiseservice.this);
            recyclerView6.setLayoutManager(manager6);
            RecyclerView recyclerView7 = (RecyclerView)findViewById(R.id.RV7); RecyclerView.LayoutManager manager7 = new LinearLayoutManager(Franchiseservice.this);
            recyclerView7.setLayoutManager(manager7);
            RecyclerView recyclerView8 = (RecyclerView)findViewById(R.id.RV8); RecyclerView.LayoutManager manager8 = new LinearLayoutManager(Franchiseservice.this);
            recyclerView8.setLayoutManager(manager8);
            RecyclerView recyclerView9 = (RecyclerView)findViewById(R.id.RV9); RecyclerView.LayoutManager manager9 = new LinearLayoutManager(Franchiseservice.this);
            recyclerView9.setLayoutManager(manager9);
            RecyclerView recyclerView10 = (RecyclerView)findViewById(R.id.RV10); RecyclerView.LayoutManager manager10 = new LinearLayoutManager(Franchiseservice.this);
            recyclerView10.setLayoutManager(manager10);
            RecyclerView recyclerView11 = (RecyclerView)findViewById(R.id.RV11); RecyclerView.LayoutManager manager11 = new LinearLayoutManager(Franchiseservice.this);
            recyclerView11.setLayoutManager(manager11);
            RecyclerView recyclerView12 = (RecyclerView)findViewById(R.id.RV12); RecyclerView.LayoutManager manager12= new LinearLayoutManager(Franchiseservice.this);
            recyclerView12.setLayoutManager(manager12);
            RecyclerView recyclerView13 = (RecyclerView)findViewById(R.id.RV13); RecyclerView.LayoutManager manager13= new LinearLayoutManager(Franchiseservice.this);
            recyclerView13.setLayoutManager(manager13);







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


                    if (Category.equals("기타서비스")){
                        FranchiseInfo franchiseInfo = new FranchiseInfo(Name,Storesu,Ownermoney,Asales17,Interior);
                        ServicearrayList .add(franchiseInfo);
                    }
                    else if(Category.equals("미용")){
                        FranchiseInfo franchiseInfo = new FranchiseInfo(Name,Storesu,Ownermoney,Asales17,Interior);
                        HairarrayList .add(franchiseInfo);
                    }
                    else if(Category.equals("기타교육")){
                        FranchiseInfo franchiseInfo = new FranchiseInfo(Name,Storesu,Ownermoney,Asales17,Interior);
                        EduparrayList .add(franchiseInfo);
                    }
                    else if(Category.equals("외국어교육")){
                        FranchiseInfo franchiseInfo = new FranchiseInfo(Name,Storesu,Ownermoney,Asales17,Interior);
                        FeduarrayList .add(franchiseInfo);
                    }
                    else if(Category.equals("교과교육")){
                        FranchiseInfo franchiseInfo = new FranchiseInfo(Name,Storesu,Ownermoney,Asales17,Interior);
                        SeduarrayList .add(franchiseInfo);
                    }
                    else if(Category.equals("유아")){
                        FranchiseInfo franchiseInfo = new FranchiseInfo(Name,Storesu,Ownermoney,Asales17,Interior);
                        ChildarrayList .add(franchiseInfo);
                    }
                    else if(Category.equals("자동차")){
                        FranchiseInfo franchiseInfo = new FranchiseInfo(Name,Storesu,Ownermoney,Asales17,Interior);
                        CararrayList .add(franchiseInfo);
                    }
                    else if(Category.equals("안경")){
                        FranchiseInfo franchiseInfo = new FranchiseInfo(Name,Storesu,Ownermoney,Asales17,Interior);
                        GlassarrayList .add(franchiseInfo);
                    }
                    else if(Category.equals("세탁")){
                        FranchiseInfo franchiseInfo = new FranchiseInfo(Name,Storesu,Ownermoney,Asales17,Interior);
                        WasharrayList .add(franchiseInfo);
                    }
                    else if(Category.equals("반려동물")){
                        FranchiseInfo franchiseInfo = new FranchiseInfo(Name,Storesu,Ownermoney,Asales17,Interior);
                        PetarrayList .add(franchiseInfo);
                    }
                    else if(Category.equals("운송")){
                        FranchiseInfo franchiseInfo = new FranchiseInfo(Name,Storesu,Ownermoney,Asales17,Interior);
                        TransarrayList .add(franchiseInfo);
                    }
                    else if(Category.equals("부동산/임대")){
                        FranchiseInfo franchiseInfo = new FranchiseInfo(Name,Storesu,Ownermoney,Asales17,Interior);
                        HomearrayList .add(franchiseInfo);
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            FranchiseAdapter AllfranchiseAdapter = new FranchiseAdapter(ALLarrayList);
            FranchiseAdapter ServicefranchiseAdapter = new FranchiseAdapter(ServicearrayList );
            FranchiseAdapter HairfranchiseAdapter = new FranchiseAdapter(HairarrayList );
            FranchiseAdapter EdufranchiseAdapter = new FranchiseAdapter(EduparrayList );
            FranchiseAdapter FedufranchiseAdapter = new FranchiseAdapter(FeduarrayList );
            FranchiseAdapter SedufranchiseAdapter = new FranchiseAdapter(SeduarrayList );
            FranchiseAdapter ChildfranchiseAdapter = new FranchiseAdapter(ChildarrayList );
            FranchiseAdapter CarfranchiseAdapter = new FranchiseAdapter(CararrayList );
            FranchiseAdapter GlassfranchiseAdapter = new FranchiseAdapter(GlassarrayList  );
            FranchiseAdapter WashfranchiseAdapter = new FranchiseAdapter(WasharrayList  );
            FranchiseAdapter PeetfranchiseAdapter = new FranchiseAdapter(PetarrayList  );
            FranchiseAdapter TransfranchiseAdapter = new FranchiseAdapter(TransarrayList  );
            FranchiseAdapter HomefranchiseAdapter = new FranchiseAdapter(HomearrayList  );

            if(kind.equals("전체")){
            recyclerView.setAdapter(AllfranchiseAdapter);}
            else  if(kind.equals("기타서비스")){
                recyclerView2.setAdapter(ServicefranchiseAdapter);}
            else if(kind.equals("미용")){
                recyclerView3.setAdapter(HairfranchiseAdapter);}
            else if(kind.equals("기타교육")){
                recyclerView4.setAdapter(EdufranchiseAdapter);}
            else if(kind.equals("외국어교육")){
                recyclerView5.setAdapter(FedufranchiseAdapter);}
            else if(kind.equals("교과교육")){
                recyclerView6.setAdapter(SedufranchiseAdapter);}
            else if(kind.equals("유아")){
                recyclerView7.setAdapter(ChildfranchiseAdapter);}
            else if(kind.equals("자동차")){
                recyclerView8.setAdapter(CarfranchiseAdapter);}
            else if(kind.equals("안경")){
                recyclerView9.setAdapter(GlassfranchiseAdapter);}
            else if(kind.equals("세탁")){
                recyclerView10.setAdapter(WashfranchiseAdapter);}
            else if(kind.equals("반려동물")){
                recyclerView11.setAdapter(PeetfranchiseAdapter);}
            else if(kind.equals("운송")){
                recyclerView12.setAdapter(TransfranchiseAdapter);}
            else if(kind.equals("부동산/임대")){
                recyclerView13.setAdapter(HomefranchiseAdapter);}
        }

        @Override
        protected void onPostExecute(String s) {
            show(s);
        }


    }
}
