package com.example.jws.bzapp;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Findavgcareer extends StringRequest {
    final static private String URL = "http://qwerr784.cafe24.com/Findavgcareer.php";
    private Map<String,String> parameters;

    public Findavgcareer(String location, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("location",location);
    }

    public Map<String, String> getParams(){
        return parameters;
    }
}
