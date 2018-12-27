package com.example.jws.bzapp;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Login extends StringRequest {

    //php 파일 URL
    final static private String URL = "http://qwerr784.cafe24.com/login.php";
    private Map<String,String> parameters;

    public Login(String id, String pw, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        //id값과 pw값을 전달
        parameters.put("id",id);
        parameters.put("pw",pw);
    }

    public Map<String, String> getParams(){
        return parameters;
    }
}
