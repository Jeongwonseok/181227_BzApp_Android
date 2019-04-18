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

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Dialog_Category extends Activity {
    Button btnCancel;
    Button btnNext;
    TextView txtCat;
    String SelectLarge;
    String SelectMiddle;
    String SelectShort;
    String Long,Lat;
    ShopApi shopapi;
    int i;
    int count;
    String SelectLargeCode;
    String SelectMiddleCode;
    String SelectShortCode;
    String jumpoRadius;
    String LargeCode[]=new String[21];
    String LargeName[]=new String[21];
    List<String> MCode=new ArrayList<>();
    List<String>Mname=new ArrayList<>();
    List<String>Scode=new ArrayList<>();
    List<String>Sname=new ArrayList<>();
    ListViewAdapter Middleadapter;
    ListView Middlelistview;
    ListViewAdapter Shortadapter;
    ListView Shortlistview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.7f;
        getWindow().setAttributes(layoutParams);
        setContentView(R.layout.activity_dialog__category);
        shopapi=new ShopApi();


        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

        int width = (int) (display.getWidth() * 0.8); //Display 사이즈의 70%

        int height = (int) (display.getHeight() * 0.5);  //Display 사이즈의 30%

        getWindow().getAttributes().width = width;

        getWindow().getAttributes().height = height;

        getWindow().setGravity(Gravity.TOP);
        Intent intent = getIntent();
        jumpoRadius=intent.getStringExtra("JumpoRadius");
        LargeCode=intent.getStringArrayExtra("LargeCode");
        LargeName=intent.getStringArrayExtra("LargeName");
        Long=intent.getStringExtra("mLong");
        Lat=intent.getStringExtra("mLat");
        if(jumpoRadius.equals("100m"))
            jumpoRadius="200";
        else if(jumpoRadius.equals("200m"))
            jumpoRadius="200";
        else if(jumpoRadius.equals("500m"))
            jumpoRadius="500";
        else if(jumpoRadius.equals(" 1km  "))
            jumpoRadius="1000";


        /////////////////////////////////////////////////////////////////////////////////////////
        //대분류 어뎁터
        final ListViewAdapter Largeadapter;
        final ListView Largelistview;
        // Adapter 생성
        Largeadapter = new ListViewAdapter();
        // 리스트뷰 참조 및 Adapter달기
        Largelistview = (ListView) findViewById(R.id.listview1);
        Largelistview.setAdapter(Largeadapter);
        for (int i=0;i<LargeName.length;i++){
            Largeadapter.addItem(LargeName[i]);
        }
        // 첫 번째 아이템 추가.
        txtCat = (TextView) findViewById(R.id.txtCat);
        btnNext = (Button) findViewById(R.id.btnNext);
        btnNext.setEnabled(false);
        btnNext.setBackgroundResource(R.drawable.round_button2);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Largelistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                i = Largelistview.getCheckedItemPosition();
                ListViewItem listViewItem = Largeadapter.getItem(i);
                SelectLarge = listViewItem.getText(); //이게 누른 값? 받아오는 건듯
                btnNext.setEnabled(true);
                btnNext.setBackgroundResource(R.drawable.round_button1);
                btnNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ///////중분류 리스트뷰
                        txtCat.setText("중분류");
                        Middleadapter = new ListViewAdapter();
                        Middlelistview = (ListView) findViewById(R.id.listview1);
                        // Adapter 생성
                        MCode.clear();
                        Mname.clear();
                        for (int i = 0; i < 21; i++) {
                            if (SelectLarge.equals(LargeName[i]))
                                SelectLargeCode=LargeCode[i];//대분류 알파벳
                        }
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                MgetData(SelectLargeCode);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        // 리스트뷰 참조 및 Adapter달기
                                        for (int i = 0; i <MCode.size(); i++) {
                                            Middlelistview.setAdapter(Middleadapter);
                                            Middleadapter.addItem(Mname.get(i));
                                        }
                                    }
                                });
                            }
                        }).start();

                        // 첫 번째 아이템 추가.
                        btnNext.setEnabled(false);
                        btnNext.setBackgroundResource(R.drawable.round_button2);
                        btnCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finish();
                            }
                        });

                        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                        Middlelistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                i = Middlelistview.getCheckedItemPosition();
                                ListViewItem listViewItem = Middleadapter.getItem(i);
                                SelectMiddle = listViewItem.getText();
                                btnNext.setEnabled(true);
                                btnNext.setBackgroundResource(R.drawable.round_button1);
                                btnNext.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        final TextView txtCat = (TextView) findViewById(R.id.txtCat);
                                        txtCat.setText("소분류");

                                        Shortadapter = new ListViewAdapter();
                                        Shortlistview = (ListView) findViewById(R.id.listview1);
                                        Shortlistview.setAdapter(Shortadapter);
                                        Scode.clear();
                                        Sname.clear();
                                        for (int i = 0; i <Mname.size(); i++) {
                                            if (SelectMiddle.equals(Mname.get(i)))
                                                SelectMiddleCode=MCode.get(i);//대분류 알파벳
                                        }
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                SgetData(SelectLargeCode,SelectMiddleCode);
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        // 리스트뷰 참조 및 Adapter달기
                                                        for (int i = 0; i <Sname.size(); i++) {
                                                            Shortlistview.setAdapter(Shortadapter);
                                                            Shortadapter.addItem(Sname.get(i));
                                                        }
                                                    }
                                                });
                                            }
                                        }).start();
                                        // 첫 번째 아이템 추가.
                                        Shortadapter.addItem("전체");
                                        // 두 번째 아이템 추가.
                                        btnNext.setText("확인");



                                        btnNext.setEnabled(false);
                                        btnNext.setBackgroundResource(R.drawable.round_button2);
                                        btnCancel.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                finish();
                                            }
                                        });

                                        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                                        Shortlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                i = Shortlistview.getCheckedItemPosition();
                                                ListViewItem listViewItem = Shortadapter.getItem(i);
                                                SelectShort = listViewItem.getText();
                                                btnNext.setEnabled(true);
                                                btnNext.setBackgroundResource(R.drawable.round_button1);
                                                btnNext.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        for(int i=0;i<Sname.size();i++){
                                                            if(SelectShort.equals(Sname.get(i)))
                                                                SelectShortCode=Scode.get(i);
                                                        }
                                                        new Thread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                final String RtotalCount[]=new String[1];
                                                                final String location[]=shopapi.location(jumpoRadius, Long,Lat);//시도랑 행정동 코드 가져오기
                                                                final String sido[];
                                                                final String hangjung[];
                                                                if(SelectShort.equals("전체")){
                                                                    sido=shopapi.sidoData("ctprvnCd", location[0], SelectLargeCode,SelectMiddleCode);//시도
                                                                    hangjung=shopapi.hangjungData("signguCd", location[1],SelectLargeCode,SelectMiddleCode);
                                                                    RtotalCount[0]=shopapi.radiusData(jumpoRadius, Long,Lat,SelectLargeCode,SelectMiddleCode);//반경내토탈
                                                                }
                                                               else {
                                                                   sido= shopapi.sidoData("ctprvnCd", location[0], SelectLargeCode,SelectMiddleCode,SelectShortCode);//시도}
                                                                    hangjung=shopapi.hangjungData("signguCd", location[1],SelectLargeCode,SelectMiddleCode,SelectShortCode);//행정동
                                                                    RtotalCount[0]=shopapi.radiusData(jumpoRadius, Long,Lat,SelectLargeCode,SelectMiddleCode,SelectShortCode);//반경내토탈
                                                                }
                                                                runOnUiThread(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        Intent intent = new Intent();
                                                                        if(sido[0]==null){//시도에 없을때
                                                                            sido[0]="-";sido[1]="-";hangjung[0]="-";hangjung[1]="-";
                                                                        }
                                                                        else if(hangjung[0]==null){ //시도에는 있고 구에는 없을때
                                                                            hangjung[0]="-";hangjung[1]="-";
                                                                        }
                                                                        intent.putExtra("RtotalCount",RtotalCount[0]);
                                                                        intent.putExtra("sidoNm",location[2]);
                                                                        intent.putExtra("sidosu", sido[1]);
                                                                        intent.putExtra("hangjungNm", location[3]);
                                                                        intent.putExtra("hangjungsu", hangjung[1]);
                                                                        intent.putExtra("category",SelectLarge+" > "+SelectMiddle+" > "+SelectShort);

                                                                        setResult(RESULT_OK, intent);

                                                                        finish();
                                                                    }
                                                                });
                                                            }
                                                        }).start();
//
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
            }
        });




        //count = adapter.getCount();
        //tv = adapter.getItem(count).toString();
    }

    public void MgetData(String Lcls){
        StringBuffer buffer=new StringBuffer();
        String queryUrl="http://apis.data.go.kr/B553077/api/open/sdsc/middleUpjongList?"+
                "indsLclsCd="+Lcls+"&ServiceKey=MxfED6C3Sd6Ja7QuU2BNU8xqBX5Yiy26t4sWS0PWUm%2B6WFjChgI3KoNQRMdO9LM5xvKfXOtMIh40XqadzCbTfw%3D%3D";
        int su=0;
        try {
            URL url= new URL(queryUrl);//문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is= url.openStream(); //url위치로 입력스트림 연결

            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
            XmlPullParser xpp= factory.newPullParser();
            xpp.setInput( new InputStreamReader(is, "UTF-8") ); //inputstream 으로부터 xml 입력받기
            String tag;
            xpp.next();
            int eventType= xpp.getEventType();
            while( eventType != XmlPullParser.END_DOCUMENT ){
                switch( eventType ){
                    case XmlPullParser.START_DOCUMENT:
                        buffer.append("파싱 시작...\n\n");
                        break;

                    case XmlPullParser.START_TAG:
                        tag= xpp.getName();//태그 이름 얻어오기

                        if(tag.equals("item")) ;// 첫번째 검색결과
                        else if(tag.equals("indsMclsCd")){
                            xpp.next();
                            MCode.add(xpp.getText());
                        }
                        else if(tag.equals("indsMclsNm")){
                            xpp.next();
                            Mname.add(xpp.getText());
                            su++;
                        }
                        break;
                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag= xpp.getName(); //태그 이름 얻어오기
                        if(tag.equals("item")) buffer.append("\n");// 첫번째 검색결과종료..줄바꿈
                        break;
                }
                eventType= xpp.next();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch blocke.printStackTrace();
        }
    }
    public void SgetData(String Lcls,String Mcls){
        StringBuffer buffer=new StringBuffer();
        String queryUrl="http://apis.data.go.kr/B553077/api/open/sdsc/smallUpjongList?"+
                "indsLclsCd="+Lcls+"&indsMclsCd="+Mcls+"&ServiceKey=MxfED6C3Sd6Ja7QuU2BNU8xqBX5Yiy26t4sWS0PWUm%2B6WFjChgI3KoNQRMdO9LM5xvKfXOtMIh40XqadzCbTfw%3D%3D";
        int su=0;
        try {
            URL url= new URL(queryUrl);//문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is= url.openStream(); //url위치로 입력스트림 연결

            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
            XmlPullParser xpp= factory.newPullParser();
            xpp.setInput( new InputStreamReader(is, "UTF-8") ); //inputstream 으로부터 xml 입력받기
            String tag;
            xpp.next();
            int eventType= xpp.getEventType();
            while( eventType != XmlPullParser.END_DOCUMENT ){
                switch( eventType ){
                    case XmlPullParser.START_DOCUMENT:
                        buffer.append("파싱 시작...\n\n");
                        break;

                    case XmlPullParser.START_TAG:
                        tag= xpp.getName();//태그 이름 얻어오기

                        if(tag.equals("item")) ;// 첫번째 검색결과
                        else if(tag.equals("indsSclsCd")){
                            xpp.next();
                            Scode.add(xpp.getText());
                        }
                        else if(tag.equals("indsSclsNm")){
                            xpp.next();
                            Sname.add(xpp.getText());
                            su++;
                        }
                        break;
                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag= xpp.getName(); //태그 이름 얻어오기
                        if(tag.equals("item")) buffer.append("\n");// 첫번째 검색결과종료..줄바꿈
                        buffer.append("파싱끝");
                        break;
                }
                eventType= xpp.next();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch blocke.printStackTrace();
        }
    }

}
