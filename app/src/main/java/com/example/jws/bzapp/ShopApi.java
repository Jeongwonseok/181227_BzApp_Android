package com.example.jws.bzapp;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

public class ShopApi extends Activity {

    String key="FilEKayTSk42P7Hla4uqqj5ZoDS1wwnZSNBs5";

    String data;



    String getXmlData(){

        StringBuffer buffer=new StringBuffer();
        String radius = "500";
        String mLat="33.449826";
        String mLong="126.573301";
        String Lcls="Q";
        String Mcls="Q04";
        String Scls="Q04A01";


        // radius=500&cx=126.573301&cy=33.449826&indsLclsCd=Q
        // &indsMclsCd=Q04&indsSclsCd=Q04A01&
        // ServiceKey=MxfED6C3Sd6Ja7QuU2BNU8xqBX5Yiy26t4sWS0PWUm%2B6WFjChgI3KoNQRMdO9LM5xvKfXOtMIh40XqadzCbTfw%3D%3D

        String queryUrl="http://apis.data.go.kr/B553077/api/open/sdsc/storeListInRadius?"//요청 URL
                +"radius="+radius+"&cx="+mLong+"&cy="+mLat
                +"&indsLclsCd="+Lcls+"&indsMclsCd="+Mcls+"&indsSclsCd="+Scls+"&ServiceKey="+key;

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
                        else if(tag.equals("indsLclsNm")){
                            buffer.append("대분류 : ");
                            xpp.next();
                            buffer.append(xpp.getText());//addr 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n"); //줄바꿈 문자 추가
                        }
                        else if(tag.equals("indsMclsNm")){
                            buffer.append("중분류 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }
                        else if(tag.equals("indsSclsNm")){
                            buffer.append("소분류 :");
                            xpp.next();
                            buffer.append(xpp.getText());//cpId
                            buffer.append("\n");
                        }
                        else if(tag.equals("lat")){
                            buffer.append("위도 :");
                            xpp.next();
                            buffer.append(xpp.getText());//cpNm
                            buffer.append("\n");
                        }
                        else if(tag.equals("lon")){
                            buffer.append("경도 :");
                            xpp.next();
                            buffer.append(xpp.getText());//
                            buffer.append("\n");
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

        buffer.append("파싱 끝\n");

        return buffer.toString();//StringBuffer 문자열 객체 반환

    }

}
