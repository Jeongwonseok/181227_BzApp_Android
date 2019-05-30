package com.example.jws.bzapp;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

//회원등록 클래스
public class Setmypage extends StringRequest {

    final static private String URL = "http://qwerr784.cafe24.com/Setmypage.php";
    private Map<String, String> parameters;

    public Setmypage(String id, String pw, String name, String phone, String email, String question, String answer, String Fid, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("id", id);
        parameters.put("pw", pw);
        parameters.put("name", name);
        parameters.put("tel", phone);
        parameters.put("email", email);
        parameters.put("question", question);
        parameters.put("answer", answer);
        parameters.put("Fid", Fid);
    }

    public Map<String, String> getParams() {
        return parameters;
    }
}