package com.example.jws.bzapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class Dialog_Category extends Activity {
    Button btnCancel;
    Button btnNext;
    TextView txtCat;
    String tv1;
    String tv2;
    String tv3;
    int i;
    int count;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.7f;
        getWindow().setAttributes(layoutParams);
        setContentView(R.layout.activity_dialog__category);


        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

        int width = (int) (display.getWidth() * 0.8); //Display 사이즈의 70%

        int height = (int) (display.getHeight() * 0.5);  //Display 사이즈의 30%

        getWindow().getAttributes().width = width;

        getWindow().getAttributes().height = height;

        getWindow().setGravity(Gravity.TOP);


        // 갱신할때 참고
        // adapter.notifyDataSetChanged();


        /*
        btnNext = (Button) findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListView listview ;
                ListViewAdapter adapter;
                adapter = new ListViewAdapter() ;

                // 리스트뷰 참조 및 Adapter달기
                listview = (ListView) findViewById(R.id.listview1);
                listview.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                adapter.addItem("음식") ;
                // 두 번째 아이템 추가.
                adapter.addItem("소매") ;
                Intent intent = new Intent(Dialog_Category.this, Dialog_Category2.class);
                startActivity(intent);
            }
        });
        */

        final ListViewAdapter adapter;
        final ListView listview;

        // Adapter 생성
        adapter = new ListViewAdapter();

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.listview1);
        listview.setAdapter(adapter);

        // 첫 번째 아이템 추가.
        adapter.addItem("음식");
        // 두 번째 아이템 추가.
        adapter.addItem("소매");
        // 세 번째 아이템 추가.
        adapter.addItem("관광/여가/오락");
        adapter.addItem("생활서비스");
        adapter.addItem("학문/교육");
        adapter.addItem("부동산");
        adapter.addItem("숙박");
        adapter.addItem("도매/유통/무역");
        adapter.addItem("스포츠");
        adapter.addItem("의료");
        adapter.addItem("제조");
        adapter.addItem("문화/예술/종교");
        adapter.addItem("1차산업");
        adapter.addItem("교통/운송");
        adapter.addItem("언론/미디어");
        adapter.addItem("기술/건축/환경");
        adapter.addItem("국가기관/단체");
        adapter.addItem("금융");
        adapter.addItem("전기/가스/수도");
        adapter.addItem("전자/정보통신");
        adapter.addItem("복지");
        adapter.addItem("업종분류불능");

        txtCat = (TextView)findViewById(R.id.txtCat);
        btnNext = (Button)findViewById(R.id.btnNext);
        btnNext.setEnabled(false);
        btnCancel = (Button)findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                i = listview.getCheckedItemPosition();
                ListViewItem listViewItem = adapter.getItem(i);
                tv1 = listViewItem.getText();
                btnNext.setEnabled(true);
                btnNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        txtCat.setText("중분류");
                        final ListViewAdapter adapter;
                        final ListView listview;
                        // Adapter 생성
                        adapter = new ListViewAdapter();

                        // 리스트뷰 참조 및 Adapter달기
                        listview = (ListView) findViewById(R.id.listview1);
                        listview.setAdapter(adapter);


                        // 첫 번째 아이템 추가.
                        adapter.addItem("한식");
                        // 두 번째 아이템 추가.
                        adapter.addItem("양식");
                        // 세 번째 아이템 추가.
                        adapter.addItem("중식");
                        adapter.addItem("별식/퓨전요리");
                        adapter.addItem("일식/수산물");
                        adapter.addItem("커피점/카페");
                        adapter.addItem("닭/오리요리");
                        adapter.addItem("유흥주점");
                        adapter.addItem("분식");
                        adapter.addItem("부페");
                        adapter.addItem("패스트푸드");
                        adapter.addItem("제과제빵떡케익");
                        adapter.addItem("음식배달서비스");
                        adapter.addItem("기타음식업");
                        btnNext.setEnabled(false);
                        btnCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finish();
                            }
                        });

                        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                i = listview.getCheckedItemPosition();
                                ListViewItem listViewItem = adapter.getItem(i);
                                tv2 = listViewItem.getText();
                                btnNext.setEnabled(true);
                                btnNext.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        final TextView txtCat = (TextView)findViewById(R.id.txtCat);
                                        txtCat.setText("소분류");
                                        btnCancel.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                finish();
                                            }
                                        });

                                        final ListViewAdapter adapter;
                                        final ListView listview;
                                        // Adapter 생성
                                        adapter = new ListViewAdapter();

                                        // 리스트뷰 참조 및 Adapter달기
                                        listview = (ListView) findViewById(R.id.listview1);
                                        listview.setAdapter(adapter);

                                        // 첫 번째 아이템 추가.
                                        adapter.addItem("전체");
                                        // 두 번째 아이템 추가.
                                        adapter.addItem("갈비/삼겹살");
                                        // 세 번째 아이템 추가.
                                        adapter.addItem("곱창/양구이전문");
                                        adapter.addItem("기사식당");
                                        adapter.addItem("기타고기요리");
                                        adapter.addItem("냉면집");
                                        btnNext.setText("확인");

                                        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                i = listview.getCheckedItemPosition();
                                                ListViewItem listViewItem = adapter.getItem(i);
                                                tv3 = listViewItem.getText();
                                            }
                                        });

                                        btnNext.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                Intent intent = new Intent();
                                                intent.putExtra("category", tv1+" > "+tv2+" > "+tv3);
                                                setResult(RESULT_OK, intent);
                                                finish();
                                            }
                                        });

                                    }
                                });

                            }
                        });
                    }
                });
            }
        });

        //count = adapter.getCount();
        //tv = adapter.getItem(count).toString();
    }

}
