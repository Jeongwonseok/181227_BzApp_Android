package com.example.jws.bzapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BookmarkActivity extends AppCompatActivity {

    ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        SharedPreferences test = getSharedPreferences("check", Activity.MODE_PRIVATE);
        final String loginID = test.getString("id", null);

        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookmarkActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //리사이클뷰에 공지사항 데이터 추가하는 클래스 선언후 실행
        BookList bookList = new BookList();
        bookList.execute(loginID);

    }

    class BookList extends AsyncTask<String, Void, String> {


        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {

            String ID = params[0];

            String serverURL = "http://qwerr784.cafe24.com/Findbookmark.php";
            String postParameters = "ID=" + ID;

            try {
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                int responseStatusCode = httpURLConnection.getResponseCode();

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                bufferedReader.close();
                return sb.toString().trim();
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
                String Lat = null, Lng = null, area = null, time = null, addr = null;
                while (count < jsonArray.length()) {

                    JSONObject object = jsonArray.getJSONObject(count);
                    Lat = object.getString("Lat");
                    Lng = object.getString("Lng");
                    area = object.getString("area");
                    time = object.getString("time");
                    addr = ChangeAddr(Double.valueOf(Lat), Double.valueOf(Lng));
                    BookInfo bookInfo = new BookInfo(Lat, Lng, area, addr, time);
                    arrayList.add(bookInfo);

                    count++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            BookAdapter bookAdapter = new BookAdapter(getApplicationContext(), arrayList);
            recyclerView.setAdapter(bookAdapter);
        }

        @Override
        protected void onPostExecute(String s) {
            show(s);
        }


    }

    public String ChangeAddr(double Lat, double Lng) {
        Geocoder geocoder = new Geocoder(this);
        // 위도,경도 입력 후 변환 버튼 클릭
        List<Address> list = null;
        String addr = null;
        try {

            list = geocoder.getFromLocation(
                    Lat, // 위도
                    Lng, // 경도
                    10); // 얻어올 값의 개수
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("test", "입출력 오류 - 서버에서 주소변환시 에러발생");
        }
        if (list != null) {
            if (list.size() == 0) {
                addr = "해당되는 주소 정보는 없습니다";
            } else {
                String[] splitStr = list.get(0).toString().split(",");
                addr = splitStr[0].substring(splitStr[0].indexOf("\"") + 6, splitStr[0].length() - 2);
            }
        }
        return addr;
    }
}