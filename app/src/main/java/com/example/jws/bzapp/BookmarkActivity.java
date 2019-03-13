package com.example.jws.bzapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class BookmarkActivity extends AppCompatActivity {

    ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookmarkActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //리사이클뷰에 공지사항 데이터 추가하는 클래스 선언후 실행
        BookList bookList = new BookList();
        bookList.execute();

    }
    class BookList extends AsyncTask<Void, Void, String> {


        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                //서버에 있는 php 실행
                URL url = new URL("http://qwerr784.cafe24.com/NList.php");
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



            ArrayList<BookInfo> arrayList = new ArrayList<>();

            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.BRV);
            RecyclerView.LayoutManager manager = new LinearLayoutManager(BookmarkActivity.this);
            recyclerView.setLayoutManager(manager);

            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count = 0;
                String address, time;
                while (count < jsonArray.length()) {

                    JSONObject object = jsonArray.getJSONObject(count);
                    address = object.getString("address");
                    time = object.getString("time");

                    BookInfo bookInfo = new BookInfo(address, time);
                    arrayList.add(bookInfo);

                    count++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            BookAdapter bookAdapter = new BookAdapter(arrayList);
            recyclerView.setAdapter(bookAdapter);
        }

        @Override
        protected void onPostExecute(String s) {
            show(s);
        }


    }
}
