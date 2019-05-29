package com.example.jws.bzapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class surveylist extends AppCompatActivity {

    String location;
    String sales;
    String type;
    String loginID;
    TextView test;
    ArrayList<String>list = new ArrayList<String>();
    surveylistAdapter adapter;
    ListView listView;
    ImageButton btnHome;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surveylist);
        Intent intent = getIntent();
        loginID = intent.getStringExtra("ID");
        test=(TextView)findViewById(R.id.test1);
        adapter = new surveylistAdapter();
        listView = (ListView)findViewById(R.id.surveylist) ;
        btnHome=(ImageButton)findViewById(R.id.btnHome);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(surveylist.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        getClosure();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                surveylist_item surveyData = (surveylist_item)parent.getItemAtPosition(position);
               Intent intent = new Intent(surveylist.this,RecommendActivity.class);
               String location=surveyData.getLocation();
               String type=surveyData.getType();
               String sales=surveyData.getSales();
                if(type.equals("소매")){
                    type="retail";
                }else   if(type.equals("생활서비스")){
                    type="life";
                }
                else   if(type.equals("관광/여가/오락")){
                    type="tour";
                }
                else   if(type.equals("숙박")){
                    type="stay";
                }
                else   if(type.equals("스포츠")){
                    type="sports";
                }
                else   if(type.equals("음식")){
                    type="food";
                }
                else   if(type.equals("학문/교육")){
                    type="edu";
                }
                if(sales.equals("2000미만")){
                    sales="0";
                }else   if(sales.equals("2000 ~ 2500")){
                    sales="1";
                }
                else   if(sales.equals("2500 ~ 3000")){
                    sales="2";
                }
                else   if(sales.equals("3000 ~ 3500")){
                    sales="3";
                }
                else   if(sales.equals("3500 ~ 4000")){
                    sales="4";
                }
                else   if(sales.equals("4000이상")){
                    sales="5";
                }
                intent.putExtra("location",location);
                intent.putExtra("type",type);
                intent.putExtra("sales",sales);

               startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(final AdapterView<?> parent, View view,
                                           final int position, long id) {

                AlertDialog diaBox = new AlertDialog.Builder(surveylist.this)
                        .setTitle("삭제")
                        .setMessage("정말 삭제하십니까?")
                        .setPositiveButton("네",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int whichButton) {
                                        surveylist_item surveyData = (surveylist_item)parent.getItemAtPosition(position);
                                        String location=surveyData.getLocation();
                                        String type=surveyData.getType();
                                        String sales=surveyData.getSales();
                                        getDelete(location,type,sales);

                                    }
                                }).setNegativeButton("아니요", null).create();
                diaBox.show();
                return true;
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
                    String location,type,sales;
                    String sale2="";
                    String category="";

                    while (count < jsonArray.length()) {
                        JSONObject object = jsonArray.getJSONObject(count);
                        location = object.getString("location");
                        type=object.getString("type");
                        sales=object.getString("sales");


                        //타입형식 바꿔서 list에 넣기
                        if(type.equals("retail")){
                            category="소매";
                        }else   if(type.equals("life")){
                            category="생활서비스";
                        }
                        else   if(type.equals("tour")){
                            category="관광/여가/오락";
                        }
                        else   if(type.equals("stay")){
                            category="숙박";
                        }
                        else   if(type.equals("sports")){
                            category="스포츠";
                        }
                        else   if(type.equals("food")){
                            category="음식";
                        }
                        else   if(type.equals("edu")){
                            category="학문/교육";
                        }

                        //가격 형식 바꾸기
                        if(sales.equals("0")){
                            sale2="2000미만";
                        }else   if(sales.equals("1")){
                            sale2="2000 ~ 2500";
                        }
                        else   if(sales.equals("2")){
                            sale2="2500 ~ 3000";
                        }
                        else   if(sales.equals("3")){
                            sale2="3000 ~ 3500";
                        }
                        else   if(sales.equals("4")){
                            sale2="3500 ~ 4000";
                        }
                        else   if(sales.equals("5")){
                            sale2="4000이상";
                        }

                        adapter.addItem(location,category,sale2);
                        listView.setAdapter(adapter);
                        count++;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        Findsurvey findsurvey = new Findsurvey(loginID, responseListener);
        RequestQueue queue = Volley.newRequestQueue(surveylist.this);
        queue.add(findsurvey);
    }



    public void getDelete(final String location2, final String type2, final String sales2) {
        String type=type2;
        String location=location2;
        String sales=sales2;
        if(type.equals("소매")){
            type="retail";
        }else   if(type.equals("생활서비스")){
            type="life";
        }
        else   if(type.equals("관광/여가/오락")){
            type="tour";
        }
        else   if(type.equals("숙박")){
            type="stay";
        }
        else   if(type.equals("스포츠")){
            type="sports";
        }
        else   if(type.equals("음식")){
            type="food";
        }
        else   if(type.equals("학문/교육")){
            type="edu";
        }
        if(sales.equals("2000미만")){
            sales="0";
        }else   if(sales.equals("2000 ~ 2500")){
            sales="1";
        }
        else   if(sales.equals("2500 ~ 3000")){
            sales="2";
        }
        else   if(sales.equals("3000 ~ 3500")){
            sales="3";
        }
        else   if(sales.equals("3500 ~ 4000")){
            sales="4";
        }
        else   if(sales.equals("4000이상")){
            sales="5";
        }
//        Toast.makeText(getApplicationContext(), addrnm, Toast.LENGTH_LONG).show();
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArray = jsonObject.getJSONArray("response");
                    int count = 0;

                    while (count < jsonArray.length()) {
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        DeleteSurvey deletesurvey = new DeleteSurvey(loginID,location2,type,sales,responseListener);
        RequestQueue queue = Volley.newRequestQueue(surveylist.this);
        queue.add(deletesurvey);
    }



}
