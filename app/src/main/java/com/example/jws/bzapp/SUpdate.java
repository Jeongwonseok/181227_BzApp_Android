package com.example.jws.bzapp;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

//회원등록 클래스
public class SUpdate extends StringRequest {

    final static private String URL = "http://qwerr784.cafe24.com/SUpdate.php";
    private Map<String,String> parameters;

    public SUpdate(String ID, String gender, String age, String location, String lReason, String type, String tReason, String sales, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("ID",ID);
        parameters.put("gender",gender);
        parameters.put("age",age);
        parameters.put("location",location);
        parameters.put("lReason",lReason);
        parameters.put("type",type);
        parameters.put("tReason",tReason);
        parameters.put("sales", sales);
    }

    public Map<String, String> getParams(){
        return parameters;
    }
}