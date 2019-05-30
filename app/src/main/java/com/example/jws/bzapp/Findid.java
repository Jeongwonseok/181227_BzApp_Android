package com.example.jws.bzapp;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Findid extends StringRequest {

    final static private String URL = "http://qwerr784.cafe24.com/Findid.php";
    private Map<String, String> parameters;

    public Findid(String name, String email, String phone, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("name", name);
        parameters.put("email", email);
        parameters.put("phone", phone);
    }

    public Map<String, String> getParams() {
        return parameters;
    }
}