package com.example.jws.bzapp;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;

public class LoginCheck {
    Activity activity;

    public LoginCheck(Activity activity) {
        this.activity = activity;
    }

    public Boolean isLogin() {
        SharedPreferences test = activity.getSharedPreferences("check", Activity.MODE_PRIVATE);
        Boolean logincheck = test.getBoolean("check", false);
//        Log.d("check2", String.valueOf(test.getBoolean("check", false)));
        return logincheck;
    }

    public void Logout() {
        SharedPreferences test = activity.getSharedPreferences("check", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = test.edit();
        //editor.clear()는 auto에 들어있는 모든 정보를 기기에서 지웁니다.
        editor.clear();
        editor.commit();
//        Log.d("check2", String.valueOf(test.getBoolean("check", false)));
    }

}
