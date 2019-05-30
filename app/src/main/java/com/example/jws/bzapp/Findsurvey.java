package com.example.jws.bzapp;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Findsurvey extends StringRequest {
    final static private String URL = "http://qwerr784.cafe24.com/Usersurveylist.php";
    private Map<String, String> parameters;

    public Findsurvey(String loginID, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("loginID", loginID);
    }

    public Map<String, String> getParams() {
        return parameters;
    }
}