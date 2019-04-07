package com.example.jws.bzapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FranchiseSearch  extends AppCompatActivity {
    // 데이터를 넣은 리스트변수
    private ListView listView;          // 검색을 보여줄 리스트변수
    private EditText Search;        // 검색어를 입력할 Input 창
    private SearchAdapter adapter;      // 리스트뷰에 연결할 아답터
    private List<ListVO> list;
    private ArrayList<ListVO> arraylist;
    ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_franchsearch);

        Search = (EditText) findViewById(R.id.fSearch);
        listView = (ListView) findViewById(R.id.listView);
        list = new ArrayList<ListVO>();
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        settingList();

        arraylist = new ArrayList<ListVO>();
        adapter = new SearchAdapter(list, this);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String top = "";
                Intent intent = new Intent(getApplicationContext(), Franchisedetail.class);
                intent.putExtra("Shopname", list.get(position).getTitle());
                intent.putExtra("Category", list.get(position).getCategory());
                startActivity(intent);
            }
        });


        Search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                String text = Search.getText().toString();
                search(text);
            }
        });


    }


    public void search(String charText) {
        list.clear();
        if (charText.length() == 0) {
            list.addAll(arraylist);
        } else {
            for (int i = 0; i < arraylist.size(); i++) {
                if (arraylist.get(i).getTitle().contains(charText)) {
                    ListVO listVO = new ListVO();
                    listVO.setCategory(arraylist.get(i).getCategory());
                    listVO.setTitle(arraylist.get(i).getTitle());
                    listVO.setContext(arraylist.get(i).getContext());
                    list.add(listVO);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }


    private void settingList() {
        Search SearchList = new Search();
        SearchList.execute();
    }

    class Search extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                //서버에 있는 php 실행
                URL url = new URL("http://qwerr784.cafe24.com/FranchTotalName.php");
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
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count = 0;
                String Name, Mutual, Category;
                while (count < jsonArray.length()) {
                    JSONObject object = jsonArray.getJSONObject(count);
                    Name = object.getString("Name");
                    Mutual = object.getString("Mutual");
                    Category = object.getString("Category");
                    ListVO listadd = new ListVO();
                    listadd.setTitle(Name);
                    listadd.setContext(Mutual);
                    listadd.setCategory(Category);
                    list.add(listadd);
                    arraylist.add(listadd);
                    count++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            show(s);
        }
    }
}