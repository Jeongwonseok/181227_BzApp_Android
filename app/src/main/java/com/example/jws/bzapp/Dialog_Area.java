package com.example.jws.bzapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Dialog_Area extends Activity {
    Button btnCancel;
    Button btnOk;
    String tv;
    int area;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog__area);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.7f;
        getWindow().setAttributes(layoutParams);
        Intent intent = getIntent();
        area = intent.getIntExtra("area",0);
        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.areaGroup);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (area==100) {
            radioGroup.check(R.id.rb100);
        } else if (area==200) {
            radioGroup.check(R.id.rb200);
        } else if(area==500) {
            radioGroup.check(R.id.rb500);
        } else if(area==1000) {
            radioGroup.check(R.id.rb1000);
        }


        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

        int width = (int) (display.getWidth() * 0.8); //Display 사이즈의 70%

        int height = (int) (display.getHeight() * 0.5);  //Display 사이즈의 30%

        getWindow().getAttributes().width = width;

        getWindow().getAttributes().height = height;

        getWindow().setGravity(Gravity.TOP);


        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    //확인 버튼 클릭시 라디오 그룹의 라디오 버튼 아이디값 전달
    public void mOnOk(View v){
        final RadioGroup radioGroup = (RadioGroup)findViewById(R.id.areaGroup);
        int id = radioGroup.getCheckedRadioButtonId();
        //getCheckedRadioButtonId() 의 리턴값은 선택된 RadioButton 의 id 값.
        RadioButton rb = (RadioButton) findViewById(id);

        tv  = rb.getText().toString();
        //데이터 전달하기
        Intent intent = new Intent();
        intent.putExtra("result", tv);
        setResult(RESULT_OK, intent);

        //액티비티(팝업) 닫기
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }

}

