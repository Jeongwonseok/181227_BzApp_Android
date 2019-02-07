package com.example.jws.bzapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

public class Franchisedetail extends AppCompatActivity {
    ImageButton btnBack;
    ImageButton btnHome;

    int color1, color2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_franchisedetail);

        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnHome = (ImageButton) findViewById(R.id.btnHome);

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
        BarData data2 = new BarData(labels2,dataSets2);
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
