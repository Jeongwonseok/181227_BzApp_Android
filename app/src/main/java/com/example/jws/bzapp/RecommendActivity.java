package com.example.jws.bzapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RecommendActivity extends AppCompatActivity {

    TextView text1;
    Button btn;
    ArrayList<String> list = new ArrayList<String>();;
    ArrayAdapter<String>Adapter;
    ListView listView;
    String mJsonString;
    String Location;
    String sales;
    String Fsale;
    String Lsale;
    String Category;

    private static final String TAG = "Login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);
     text1=(TextView)findViewById(R.id.test1);


        Adapter = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,list);
        listView=(ListView)findViewById(R.id.Recomlistview);
        Intent intent = getIntent();
        Location=intent.getStringExtra("location");
        sales=intent.getStringExtra("sales");
        Category=intent.getStringExtra("type");

        if(sales.equals("1")){
            Fsale="0";
            Lsale="2000";
        }else if(sales.equals("2")){
            Fsale="2000";
            Lsale="2500";
        }
        else if(sales.equals("3")){
            Fsale="2500";
            Lsale="3000";
        }
        else if(sales.equals("4")){
            Fsale="3000";
            Lsale="3500";
        }
        else if(sales.equals("5")){
            Fsale="3500";
            Lsale="4000";
        }
        else if(sales.equals("6")){
            Fsale="4000";
            Lsale="10000";
        }


        getClosure();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(RecommendActivity.this,Recommenddetail.class);
                intent.putExtra("Location",list.get(position));
                startActivity(intent);
            }
        });


    }



    public void getClosure() {

//        Toast.makeText(getApplicationContext(), addrnm, Toast.LENGTH_LONG).show();
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArray = jsonObject.getJSONArray("response");
                    int count = 0;
                    String location;
                    while (count < jsonArray.length()) {
                        JSONObject object = jsonArray.getJSONObject(count);
                        location = object.getString("location");
                        list.add(location);
                        listView.setAdapter(Adapter);
                        count++;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        Findrecommend findrecommend = new Findrecommend(Location,Category,Fsale,Lsale, responseListener);
        RequestQueue queue = Volley.newRequestQueue(RecommendActivity.this);
        queue.add(findrecommend);
    }
}
