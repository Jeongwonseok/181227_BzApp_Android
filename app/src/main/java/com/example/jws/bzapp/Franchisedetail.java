package com.example.jws.bzapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class Franchisedetail extends AppCompatActivity {
    ImageButton btnBack;
    ImageButton btnHome;
    TextView Shopname, Shopname2, Shopname3, Shopname4, txtMutual, txtFRegister, txtStoreSu17, txtStoreSu16, txtStoreSu15, txtAsales17, txtOwnermoney, txtInterior, txtFcontract, txtRcontract, txtAdvertisement, txtPromotion, txtTop30;

    int color1, color2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_franchisedetail);

        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnHome = (ImageButton) findViewById(R.id.btnHome);
        Shopname = (TextView) findViewById(R.id.shopname);
        Shopname2 = (TextView) findViewById(R.id.shopname2);
        Shopname3 = (TextView) findViewById(R.id.shopname3);
        Shopname4 = (TextView) findViewById(R.id.shopname4);
        txtMutual = (TextView) findViewById(R.id.Mutual);
        txtFRegister = (TextView) findViewById(R.id.FRegister);
        txtStoreSu17 = (TextView) findViewById(R.id.StoreSu17);
        txtStoreSu16 = (TextView) findViewById(R.id.StoreSu16);
        txtStoreSu15 = (TextView) findViewById(R.id.StoreSu15);
        txtAsales17 = (TextView) findViewById(R.id.Asales17);
        txtOwnermoney = (TextView) findViewById(R.id.Ownermoney);
        txtInterior = (TextView) findViewById(R.id.Interior);
        txtFcontract = (TextView) findViewById(R.id.Fcontract);
        txtRcontract = (TextView) findViewById(R.id.Rcontract);
        txtAdvertisement = (TextView) findViewById(R.id.Advertisement);
        txtPromotion = (TextView) findViewById(R.id.Promotion);
        txtTop30 = (TextView) findViewById(R.id.top30);
        Intent intent = getIntent();
        final String shopname = intent.getStringExtra("Shopname");
        Shopname.setText(shopname);
        Shopname2.setText(shopname);
        Shopname3.setText(shopname);
        Shopname4.setText(shopname);


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Franchisedetail.this, Franchisedetail.class);
                startActivity(intent);
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Franchisedetail.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //막대그래프(연평균매출액) 시작
        BarChart barChart2 = (BarChart) findViewById(R.id.barChartYear);

        YAxis leftAxis2 = barChart2.getAxisLeft();
        YAxis rightAxis2 = barChart2.getAxisRight();
        XAxis xAxis2 = barChart2.getXAxis();

        // 텍스트 위치 지정
        xAxis2.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis2.setTextSize(12f);

        //그래프 맨 아래 가로선 유무, 그래프 내부 세로선 유무,
        xAxis2.setDrawAxisLine(true);
        xAxis2.setDrawGridLines(true);
        leftAxis2.setTextSize(10f);

        //y축 텍스트 표시 (값), 그래프 맨 왼쪽 세로선 유무, 그래프 내부 가로선  유무
        leftAxis2.setDrawLabels(true);
        leftAxis2.setDrawAxisLine(true);
        leftAxis2.setDrawGridLines(false);

        //나머지 전부다 false 해야함
        rightAxis2.setDrawAxisLine(false);
        rightAxis2.setDrawGridLines(false);
        rightAxis2.setDrawLabels(false);

        // 그룹에 추가
        ArrayList<BarEntry> bargroup3 = new ArrayList<>();
        bargroup3.add(new BarEntry(33, 0));
        bargroup3.add(new BarEntry(25, 1));


        BarDataSet barDataSet3 = new BarDataSet(bargroup3, "");

        //바 색상

        color1 = Color.rgb(237, 48, 80);
        color2 = Color.rgb(255, 88, 116);

        barDataSet3.setColors(new int[]{color1, color2});
        // barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);


        ArrayList<String> labels2 = new ArrayList<String>();
        labels2.add("달봉이치킨");
        labels2.add("상위 30개사 평균");


        ArrayList<BarDataSet> dataSets2 = new ArrayList<>();  // mbined all dataset into an arraylistco
        dataSets2.add(barDataSet3);

        //그래프 아래 카테고리 표시 지우기
        Legend i2 = barChart2.getLegend();
        i2.setEnabled(false);
        //i2.setPosition(Legend.LegendPosition.ABOVE_CHART_RIGHT);
        //i2.setTextSize(13f);

        // 막대그래프 디자인
        BarData data2 = new BarData(labels2, dataSets2);
        data2.setValueTextColor(Color.WHITE);
        barChart2.setData(data2);
        barChart2.setTouchEnabled(false);
        barChart2.setDescription("");
        barChart2.invalidate(); // refresh
        barChart2.setScaleEnabled(false);
        barChart2.setGridBackgroundColor(Color.rgb(255, 255, 255));
        barChart2.animateXY(2000, 2000);
        barChart2.setDrawBorders(false);
        barChart2.setDrawValueAboveBar(false);
        //막대그래프(연평균매출액) 끝 */



    }
}
//    class FranchiseList extends AsyncTask<Void, Void, String> {
//
//        String shopname;
//        public FranchiseList(String shopname){
//            this.shopname=shopname;
//        }
//        @Override
//        protected void onPreExecute() {
//        }
//
//        @Override
//        protected String doInBackground(Void... voids) {
//            try {
//                //서버에 있는 php 실행
//                URL url = new URL("http://qwerr784.cafe24.com/Franchisedetail.php");
//                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//                InputStream inputStream = httpURLConnection.getInputStream();
//                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//                String temp;
//                StringBuilder stringBuilder = new StringBuilder();
//                while ((temp = bufferedReader.readLine()) != null) {
//                    stringBuilder.append(temp + "\n");
//                }
//                bufferedReader.close();
//                inputStream.close();
//                httpURLConnection.disconnect();
//                //결과 값을 리턴
//                return stringBuilder.toString().trim();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onProgressUpdate(Void... values) {
//            super.onProgressUpdate(values);
//        }
//
//        //결과값 출력 메소드
//        public void show(String s) {
//
//            ArrayList<FranchiseInfo> ALLarrayList = new ArrayList<>();
//
//
//            try {
//                JSONObject jsonObject = new JSONObject(s);
//                JSONArray jsonArray = jsonObject.getJSONArray("response");
//                int count = 0;
//                String Mutual,FRegister,StoreSu17,StoreSu16,StoreSu15,Asales17,Ownermoney,Interior,Fcontract,Rcontract,Advertisement,Promotion,Top30;
//                while (count < jsonArray.length()) {
//                    JSONObject object = jsonArray.getJSONObject(count);
//                    Mutual=object.getString("Mutual");
//                    FRegister = object.getString("FRegister");
//                    StoreSu17 = object.getString("StoreSu17");
//                    StoreSu16 = object.getString("StoreSu16");
//                    StoreSu15 = object.getString("StoreSu15");
//                    Asales17 = object.getString("Asales17");
//                    Ownermoney = object.getString("Ownermoney");
//                    Interior = object.getString("Interior");
//                    Fcontract = object.getString("Fcontract");
//                    Rcontract = object.getString("Rcontract");
//                    Advertisement = object.getString("Advertisement");
//                    Promotion = object.getString("Promotion");
//                    Top30=object.getString("Top30");
//                    ///
//                    txtMutual.setText(Mutual);
//
//
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            show(s);
//        }
//
//
//    }
