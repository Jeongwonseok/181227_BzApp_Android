package com.example.jws.bzapp;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Findpw extends StringRequest {

    final static private String URL = "http://qwerr784.cafe24.com/Findpw.php";
    private Map<String,String> parameters;

    public Findpw(String ID, String question, String answer, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("ID",ID);
        parameters.put("question",question);
        parameters.put("answer",answer);
    }

    public Map<String, String> getParams(){
        return parameters;
    }
}