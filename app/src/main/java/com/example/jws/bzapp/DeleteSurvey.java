package com.example.jws.bzapp;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class DeleteSurvey extends StringRequest {
    final static private String URL = "http://qwerr784.cafe24.com/surveyDelete.php";
    private Map<String, String> parameters;

    public DeleteSurvey(String loginID, String location, String type, String sales, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("ID", loginID);
        parameters.put("location", location);
        parameters.put("type", type);
        parameters.put("sales", sales);
    }

    public Map<String, String> getParams() {
        return parameters;
    }
}