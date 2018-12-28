package com.example.jws.bzapp;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

//문의사항 등록 클래스
public class QRegister extends StringRequest {

    final static private String URL = "http://qwerr784.cafe24.com/QRegister.php";
    private Map<String,String> parameters;

    //생성자
    public QRegister(String name, String email, String questiontype, String questioncontent, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("name",name);
        parameters.put("email",email);
        parameters.put("questiontype",questiontype);
        parameters.put("questioncontent",questioncontent);
    }

    public Map<String, String> getParams(){
        return parameters;
    }
}
