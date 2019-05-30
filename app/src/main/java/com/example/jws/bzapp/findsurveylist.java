package com.example.jws.bzapp;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class findsurveylist extends StringRequest {
    final static private String URL = "http://qwerr784.cafe24.com/findsurvey.php";
    private Map<String, String> parameters;

    public findsurveylist(String loginID, String gender, String age, String location, String lReason, String type, String tReason, String sales, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("ID", loginID);
        parameters.put("gender", gender);
        parameters.put("age", age);
        parameters.put("location", location);
        parameters.put("lReason", lReason);
        parameters.put("type", type);
        parameters.put("tReason", tReason);
        parameters.put("sales", sales);
    }

    public Map<String, String> getParams() {
        return parameters;
    }
}