package com.example.jws.bzapp;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Addbookmark extends StringRequest {

    //php 파일 URL
    final static private String URL = "http://qwerr784.cafe24.com/Abookmark.php";
    private Map<String, String> parameters;

    public Addbookmark(String id, double Lat, double Lng, int area, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        //id값과 pw값을 전달
        parameters.put("id", id);
        parameters.put("Lat", String.valueOf(Lat));
        parameters.put("Lng", String.valueOf(Lng));
        parameters.put("area", String.valueOf(area));
    }

    public Map<String, String> getParams() {
        return parameters;
    }
}
