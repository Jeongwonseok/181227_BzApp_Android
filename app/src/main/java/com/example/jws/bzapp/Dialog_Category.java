package com.example.jws.bzapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class Dialog_Category extends Activity {
    Button btnCancel;
    Button btnNext;
    String tv;
    int i;

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

        final ListView listview;
        ListViewAdapter adapter;

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

        /*
        i = listview.getCheckedItemPosition();
        tv = adapter.getItem(i).toString();
        */

    }

    //anal.xml의 android:onClick 이용해서 메서드 정의
    public void mOnPopupClick3(View v) {
        //데이터 담아서 팝업(액티비티) 호출
        finish();
    }

    //다이얼로그 실행후 결과값 받는 메서드
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                //데이터 받기
                String result = data.getStringExtra("category");
            }
        } else if (requestCode == 3) {
            if (resultCode == RESULT_OK) {
                //데이터 받기
                String result = data.getStringExtra("category");
            }
        }
    }

    public void mOnPopupClick4(View v) {

        Toast.makeText(getApplicationContext(), tv, Toast.LENGTH_SHORT).show();

        final ListView listview;
        ListViewAdapter adapter;

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
        final Button btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setText("이전");
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //이전 리스트뷰로 돌아가게 해야함
            }
        });

        final Button btnNext = (Button) findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Button btnCancel = (Button) findViewById(R.id.btnCancel);
                btnCancel.setText("이전");
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //이전 리스트뷰로 돌아가게 해야함
                    }
                });
                final ListView listview;
                ListViewAdapter adapter;

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

                btnNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent();
                        intent.putExtra("category", "안녕");
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });

            }
        });

    }
}
