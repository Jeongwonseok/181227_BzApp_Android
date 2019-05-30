package com.example.jws.bzapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class HelpActivity extends AppCompatActivity {

    ImageView btnif1, btnif2, btnif3, btnif4, btnif5;
    LinearLayout ifView1, ifView2, ifView3, ifView4, ifView5;
    ImageButton url1_1, url1_2, url1_3, url1_4, url2_1, url2_2, url3_1, url3_2, url3_3, url4_1, url5_1;
    ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnBack = (ImageButton) findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HelpActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        btnif1 = (ImageView) findViewById(R.id.btnif1);
        btnif2 = (ImageView) findViewById(R.id.btnif2);
        btnif3 = (ImageView) findViewById(R.id.btnif3);
        btnif4 = (ImageView) findViewById(R.id.btnif4);
        btnif5 = (ImageView) findViewById(R.id.btnif5);
        ifView1 = (LinearLayout) findViewById(R.id.ifLayout1);
        ifView2 = (LinearLayout) findViewById(R.id.ifLayout2);
        ifView3 = (LinearLayout) findViewById(R.id.ifLayout3);
        ifView4 = (LinearLayout) findViewById(R.id.ifLayout4);
        ifView5 = (LinearLayout) findViewById(R.id.ifLayout5);
        url1_1 = (ImageButton) findViewById(R.id.url1_1);
        url1_2 = (ImageButton) findViewById(R.id.url1_2);
        url1_3 = (ImageButton) findViewById(R.id.url1_3);
        url1_4 = (ImageButton) findViewById(R.id.url1_4);
        url2_1 = (ImageButton) findViewById(R.id.url2_1);
        url2_2 = (ImageButton) findViewById(R.id.url2_2);
        url3_1 = (ImageButton) findViewById(R.id.url3_1);
        url3_2 = (ImageButton) findViewById(R.id.url3_2);
        url3_3 = (ImageButton) findViewById(R.id.url3_3);
        url4_1 = (ImageButton) findViewById(R.id.url4_1);
        url5_1 = (ImageButton) findViewById(R.id.url5_1);


        //이미지 버튼 클릭시 content보여주기
        btnif1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ifView1.getVisibility() == View.VISIBLE) {
                    ifView1.setVisibility(View.GONE);
                    btnif1.setImageResource(R.drawable.under);
                } else {
                    ifView1.setVisibility(View.VISIBLE);
                    btnif1.setImageResource(R.drawable.over);
                }
            }
        });

        //이미지 버튼 클릭시 content보여주기
        btnif2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ifView2.getVisibility() == View.VISIBLE) {
                    ifView2.setVisibility(View.GONE);
                    btnif2.setImageResource(R.drawable.under);
                } else {
                    ifView2.setVisibility(View.VISIBLE);
                    btnif2.setImageResource(R.drawable.over);
                }
            }
        });

        //이미지 버튼 클릭시 content보여주기
        btnif3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ifView3.getVisibility() == View.VISIBLE) {
                    ifView3.setVisibility(View.GONE);
                    btnif3.setImageResource(R.drawable.under);
                } else {
                    ifView3.setVisibility(View.VISIBLE);
                    btnif3.setImageResource(R.drawable.over);
                }
            }
        });

        //이미지 버튼 클릭시 content보여주기
        btnif4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ifView4.getVisibility() == View.VISIBLE) {
                    ifView4.setVisibility(View.GONE);
                    btnif4.setImageResource(R.drawable.under);
                } else {
                    ifView4.setVisibility(View.VISIBLE);
                    btnif4.setImageResource(R.drawable.over);
                }
            }
        });

        //이미지 버튼 클릭시 content보여주기
        btnif5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ifView5.getVisibility() == View.VISIBLE) {
                    ifView5.setVisibility(View.GONE);
                    btnif5.setImageResource(R.drawable.under);
                } else {
                    ifView5.setVisibility(View.VISIBLE);
                    btnif5.setImageResource(R.drawable.over);
                }
            }
        });

        url1_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.data.go.kr/dataset/15012005/openapi.do"));
                startActivity(intent);
            }
        });

        url1_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://sg.sbiz.or.kr/index.sg?supDev=1#/statis/taxPayers/"));
                startActivity(intent);
            }
        });

        url1_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://sg.sbiz.or.kr/index.sg?supDev=1#/statis/storeOCPeriod/"));
                startActivity(intent);
            }
        });

        url1_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://sg.sbiz.or.kr/index.sg?supDev=1#/statis/sales/"));
                startActivity(intent);
            }
        });

        url2_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://sg.sbiz.or.kr/index.sg?supDev=1#/statis/sales/"));
                startActivity(intent);
            }
        });

        url2_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://sg.sbiz.or.kr/index.sg?supDev=1#/statis/sales/"));
                startActivity(intent);
            }
        });

        url3_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://sgis.kostat.go.kr/developer/html/openApi/api/data.html"));
                startActivity(intent);
            }
        });

        url3_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://sgis.kostat.go.kr/developer/html/openApi/api/data.html"));
                startActivity(intent);
            }
        });

        url3_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://sgis.kostat.go.kr/developer/html/openApi/api/data.html"));
                startActivity(intent);
            }
        });

        url4_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.data.go.kr/dataset/15012005/openapi.do"));
                startActivity(intent);
            }
        });

        url5_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.sbiz.or.kr/sup/main.do"));
                startActivity(intent);
            }
        });

    }
}