package com.example.jws.bzapp;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

//회원등록 클래스
public class Register extends StringRequest {

    final static private String URL = "http://qwerr784.cafe24.com/Register.php";
    private Map<String,String> parameters;

    public Register(String id, String pw, String name, String phone, String email,String question, String answer, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("id",id);
        parameters.put("pw",pw);
        parameters.put("name",name);
        parameters.put("phone",phone);
        parameters.put("email",email);
        parameters.put("question",question);
        parameters.put("answer",answer);
    }

    public Map<String, String> getParams(){
        return parameters;
    }
}