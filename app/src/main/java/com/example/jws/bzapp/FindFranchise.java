package com.example.jws.bzapp;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class FindFranchise extends StringRequest {

    final static private String URL = "http://qwerr784.cafe24.com/Franchisedetail.php";
    private Map<String,String> parameters;

    public FindFranchise(String name,String table,Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("Name",name);
        parameters.put("Table",table);
    }

    public Map<String, String> getParams(){
        return parameters;
    }
}