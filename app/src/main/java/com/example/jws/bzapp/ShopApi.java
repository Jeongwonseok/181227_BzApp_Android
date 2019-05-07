package com.example.jws.bzapp;

import android.app.Activity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class ShopApi extends Activity {


    static String ServiceKey = "MxfED6C3Sd6Ja7QuU2BNU8xqBX5Yiy26t4sWS0PWUm%2B6WFjChgI3KoNQRMdO9LM5xvKfXOtMIh40XqadzCbTfw%3D%3D";


    String RadiuAll(String radius, String mLong, String mLat){
        StringBuffer buffer = new StringBuffer();
        String total = "";
        String queryUrl = "http://apis.data.go.kr/B553077/api/open/sdsc/storeListInRadius?" +
                "radius="+radius+
                "&cx="+mLong+
                "&cy="+mLat+
                "&ServiceKey=MxfED6C3Sd6Ja7QuU2BNU8xqBX5Yiy26t4sWS0PWUm%2B6WFjChgI3KoNQRMdO9LM5xvKfXOtMIh40XqadzCbTfw%3D%3D";

        try {
            URL url = new URL(queryUrl);//문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is = url.openStream(); //url위치로 입력스트림 연결

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new InputStreamReader(is, "UTF-8")); //inputstream 으로부터 xml 입력받기

            String tag;

            xpp.next();
            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        buffer.append("파싱 시작...\n\n");
                        break;

                    case XmlPullParser.START_TAG:
                        tag = xpp.getName();//태그 이름 얻어오기

                        if (tag.equals("body")) ;// 첫번째 검색결과
                        else if (tag.equals("totalCount")) {
                            xpp.next();
                            buffer.append(xpp.getText());
                        }
                        break;
                    case XmlPullParser.TEXT:
                        break;
                    case XmlPullParser.END_TAG:
                        tag = xpp.getName(); //태그 이름 얻어오기
                        break;
                }
                eventType = xpp.next();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch blocke.printStackTrace();
        }
        if(buffer.length()==0)
            buffer.append("-");

        return buffer.toString();
    }

    String radiusData(String radius,String mLong, String mLat,String Lcls,String Mcls,String Scls){
        StringBuffer buffer=new StringBuffer();
        String queryUrl="http://apis.data.go.kr/B553077/api/open/sdsc/storeListInRadius?" +
                "radius="+radius+"&cx="+mLong+"&cy="+mLat+
                "&indsLclsCd=" + Lcls +
                "&indsMclsCd=" + Mcls +
                "&indsSclsCd=" + Scls +
                "&ServiceKey="+ServiceKey;
        String total ="-";
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
                        tag= xpp.getName();//테그 이름 얻어오기

                        if(tag.equals("item")) ;// 첫번째 검색결과
                        else if(tag.equals("totalCount")){
                            xpp.next();
                            total=xpp.getText();
                        }
                        break;
                    case XmlPullParser.TEXT:
                        break;
                    case XmlPullParser.END_TAG:
                        tag= xpp.getName(); //테그 이름 얻어오기
                        if(tag.equals("item")) buffer.append("\n");// 첫번째 검색결과종료..줄바꿈
                        break;
                }

                eventType= xpp.next();
            }

        } catch (Exception e) {
            // TODO Auto-generated catch blocke.printStackTrace();
        }

        buffer.append("파싱 끝\n");
        return total;//StringBuffer 문자열 객체 반환

    }//getXmlData method....
    String radiusData(String radius,String mLong, String mLat,String Lcls,String Mcls){
        StringBuffer buffer=new StringBuffer();
        String queryUrl="http://apis.data.go.kr/B553077/api/open/sdsc/storeListInRadius?" +
                "radius="+radius+"&cx="+mLong+"&cy="+mLat+
                "&indsLclsCd=" + Lcls +
                "&indsMclsCd=" + Mcls +
                "&ServiceKey="+ServiceKey;
        String total ="-";
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
                        tag= xpp.getName();//테그 이름 얻어오기

                        if(tag.equals("item")) ;// 첫번째 검색결과
                        else if(tag.equals("totalCount")){
                            xpp.next();
                            total=xpp.getText();
                        }
                        break;
                    case XmlPullParser.TEXT:
                        break;
                    case XmlPullParser.END_TAG:
                        tag= xpp.getName(); //테그 이름 얻어오기
                        if(tag.equals("item")) buffer.append("\n");// 첫번째 검색결과종료..줄바꿈
                        break;
                }

                eventType= xpp.next();
            }

        } catch (Exception e) {
            // TODO Auto-generated catch blocke.printStackTrace();
        }

        buffer.append("파싱 끝\n");
        return total;//StringBuffer 문자열 객체 반환

    }//getXmlData method....

    String[] location(String radius,String mLong, String mLat){
        StringBuffer buffer=new StringBuffer();
        String queryUrl="http://apis.data.go.kr/B553077/api/open/sdsc/storeListInRadius?" +
                "radius="+radius+"&cx="+mLong+"&cy="+mLat+
                "&ServiceKey="+ServiceKey;
        String location[]= new String[4];
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
                        tag= xpp.getName();//테그 이름 얻어오기

                        if(tag.equals("item")) ;// 첫번째 검색결과
                        else if(tag.equals("ctprvnCd")){
                            xpp.next();
                            location[0]=xpp.getText();
                        }
                        else if(tag.equals("signguCd")){
                            xpp.next();
                            location[1]=xpp.getText();
                        }
                        else if(tag.equals("ctprvnNm"))
                        {
                            xpp.next();
                            location[2]=xpp.getText();
                        }
                        else if(tag.equals("signguNm"))
                        { xpp.next();
                            location[3]=xpp.getText();
                        }
                        break;
                    case XmlPullParser.TEXT:
                        break;
                    case XmlPullParser.END_TAG:
                        tag= xpp.getName(); //테그 이름 얻어오기
                        if(tag.equals("item")) buffer.append("\n");// 첫번째 검색결과종료..줄바꿈
                        break;
                }

                eventType= xpp.next();
            }

        } catch (Exception e) {
            // TODO Auto-generated catch blocke.printStackTrace();
        }

        buffer.append("파싱 끝\n");
        return location;//StringBuffer 문자열 객체 반환

    }//getXmlData method....

//


    String[] sidoData(String divId, String key, String Lcls, String Mcls, String Scls) {
        StringBuffer buffer = new StringBuffer();
        String queryUrl = "http://apis.data.go.kr/B553077/api/open/sdsc/storeListInDong?" +
                "divId=" + divId +
                "&key=" + key +
                "&indsLclsCd=" + Lcls +
                "&indsMclsCd=" + Mcls +
                "&indsSclsCd=" + Scls +
                "&ServiceKey=" + ServiceKey;
        String sido[]= new String[2];
        try {
            URL url = new URL(queryUrl);//문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is = url.openStream(); //url위치로 입력스트림 연결

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new InputStreamReader(is, "UTF-8")); //inputstream 으로부터 xml 입력받기
            String tag;
            xpp.next();
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        buffer.append("파싱 시작...\n\n");
                        break;

                    case XmlPullParser.START_TAG:
                        tag = xpp.getName();//태그 이름 얻어오기

                        if (tag.equals("item")) ;// 첫번째 검색결과
                        else  if(tag.equals("ctprvnNm")){
                            xpp.next();
                            sido[0]=xpp.getText();
                        }
                        else if (tag.equals("totalCount")) {
                            xpp.next();
                            sido[1]=xpp.getText();//addr 요소의 TEXT 읽어와서 문자열버퍼에 추가
                        }

                        break;
                    case XmlPullParser.TEXT:
                        break;
                    case XmlPullParser.END_TAG:
                        tag = xpp.getName(); //태그 이름 얻어오기
                        break;
                }
                eventType = xpp.next();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch blocke.printStackTrace();
        }
        return sido;//StringBuffer 문자열 객체 반환

    }
    String[] sidoData(String divId, String key, String Lcls, String Mcls) {
        StringBuffer buffer = new StringBuffer();
        String queryUrl = "http://apis.data.go.kr/B553077/api/open/sdsc/storeListInDong?" +
                "divId=" + divId +
                "&key=" + key +
                "&indsLclsCd=" + Lcls +
                "&indsMclsCd=" + Mcls +
                "&ServiceKey=" + ServiceKey;
        String sido[]= new String[2];
        try {
            URL url = new URL(queryUrl);//문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is = url.openStream(); //url위치로 입력스트림 연결

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new InputStreamReader(is, "UTF-8")); //inputstream 으로부터 xml 입력받기
            String tag;
            xpp.next();
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        buffer.append("파싱 시작...\n\n");
                        break;

                    case XmlPullParser.START_TAG:
                        tag = xpp.getName();//태그 이름 얻어오기

                        if (tag.equals("item")) ;// 첫번째 검색결과
                        else  if(tag.equals("ctprvnNm")){
                            xpp.next();
                            sido[0]=xpp.getText();
                        }
                        else if (tag.equals("totalCount")) {
                            xpp.next();
                            sido[1]=xpp.getText();//addr 요소의 TEXT 읽어와서 문자열버퍼에 추가
                        }

                        break;
                    case XmlPullParser.TEXT:
                        break;
                    case XmlPullParser.END_TAG:
                        tag = xpp.getName(); //태그 이름 얻어오기
                        break;
                }
                eventType = xpp.next();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch blocke.printStackTrace();
        }
        return sido;//StringBuffer 문자열 객체 반환

    }



    String[] hangjungData(String divId, String key, String Lcls, String Mcls, String Scls) {
        StringBuffer buffer = new StringBuffer();
        String queryUrl = "http://apis.data.go.kr/B553077/api/open/sdsc/storeListInDong?" +
                "divId=" + divId +
                "&key=" + key +
                "&indsLclsCd=" + Lcls +
                "&indsMclsCd=" + Mcls +
                "&indsSclsCd=" + Scls +
                "&ServiceKey=" + ServiceKey;
        String hangjung[]=new String[2];
        try {
            URL url = new URL(queryUrl);//문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is = url.openStream(); //url위치로 입력스트림 연결

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new InputStreamReader(is, "UTF-8")); //inputstream 으로부터 xml 입력받기

            String tag;

            xpp.next();
            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        buffer.append("파싱 시작...\n\n");
                        break;
                    case XmlPullParser.START_TAG:
                        tag = xpp.getName();//태그 이름 얻어오기

                        if (tag.equals("item")) ;// 첫번째 검색결과
                        else if(tag.equals("signguNm")){
                            xpp.next();
                            hangjung[0]=xpp.getText();
                        }
                        else if (tag.equals("totalCount")) {
                            xpp.next();
                            hangjung[1]=xpp.getText();
                        }

                        break;
                    case XmlPullParser.TEXT:
                        break;
                    case XmlPullParser.END_TAG:
                        tag = xpp.getName(); //태그 이름 얻어오기
                        break;
                }
                eventType = xpp.next();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch blocke.printStackTrace();
        }
        return hangjung;//StringBuffer 문자열 객체 반환

    }

    static String[] hangjungData(String divId, String key, String Lcls, String Mcls) {
        StringBuffer buffer = new StringBuffer();
        String queryUrl = "http://apis.data.go.kr/B553077/api/open/sdsc/storeListInDong?" +
                "divId=" + divId +
                "&key=" + key +
                "&indsLclsCd=" + Lcls +
                "&indsMclsCd=" + Mcls +
                "&ServiceKey=" + ServiceKey;
        String hangjung[]=new String[2];
        try {
            URL url = new URL(queryUrl);//문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is = url.openStream(); //url위치로 입력스트림 연결

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new InputStreamReader(is, "UTF-8")); //inputstream 으로부터 xml 입력받기

            String tag;

            xpp.next();
            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        buffer.append("파싱 시작...\n\n");
                        break;
                    case XmlPullParser.START_TAG:
                        tag = xpp.getName();//태그 이름 얻어오기

                        if (tag.equals("item")) ;// 첫번째 검색결과
                        else if(tag.equals("signguNm")){
                            xpp.next();
                            hangjung[0]=xpp.getText();
                        }
                        else if (tag.equals("totalCount")) {
                            xpp.next();
                            hangjung[1]=xpp.getText();
                        }

                        break;
                    case XmlPullParser.TEXT:
                        break;
                    case XmlPullParser.END_TAG:
                        tag = xpp.getName(); //태그 이름 얻어오기
                        break;
                }
                eventType = xpp.next();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch blocke.printStackTrace();
        }
        return hangjung;//StringBuffer 문자열 객체 반환

    }
    static String[] hangjungData(String divId, String key) {
        StringBuffer buffer = new StringBuffer();
        String queryUrl = "http://apis.data.go.kr/B553077/api/open/sdsc/storeListInDong?" +
                "divId=" + divId +
                "&key=" + key +
                "&ServiceKey=" + ServiceKey;
        String hangjung[]=new String[2];
        try {
            URL url = new URL(queryUrl);//문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is = url.openStream(); //url위치로 입력스트림 연결

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new InputStreamReader(is, "UTF-8")); //inputstream 으로부터 xml 입력받기

            String tag;

            xpp.next();
            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        buffer.append("파싱 시작...\n\n");
                        break;
                    case XmlPullParser.START_TAG:
                        tag = xpp.getName();//태그 이름 얻어오기

                        if (tag.equals("item")) ;// 첫번째 검색결과
                        else if(tag.equals("signguNm")){
                            xpp.next();
                            hangjung[0]=xpp.getText();
                        }
                        else if (tag.equals("totalCount")) {
                            xpp.next();
                            hangjung[1]=xpp.getText();
                        }

                        break;
                    case XmlPullParser.TEXT:
                        break;
                    case XmlPullParser.END_TAG:
                        tag = xpp.getName(); //태그 이름 얻어오기
                        break;
                }
                eventType = xpp.next();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch blocke.printStackTrace();
        }
        return hangjung;//StringBuffer 문자열 객체 반환

    }

}
