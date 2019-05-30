package com.example.jws.bzapp;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Findrecommend extends StringRequest {
    final static private String URL = "http://qwerr784.cafe24.com/surveylist2.php";
    private Map<String, String> parameters;

    public Findrecommend(String location, String category, String Fsale, String Lsale, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("location", location);
        parameters.put("category", category);
        parameters.put("fsale", Fsale);
        parameters.put("lsale", Lsale);
    }

    public Map<String, String> getParams() {
        return parameters;
    }
}
