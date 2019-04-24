package com.example.jws.bzapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RecommendActivity extends AppCompatActivity {

    TextView text1,text2,text3;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);
        text1 =(TextView)findViewById(R.id.test1);
        text2 =(TextView)findViewById(R.id.test2);
        text3 =(TextView)findViewById(R.id.test3);

        Intent intent = getIntent();
        String a,b,c;
        a=intent.getStringExtra("location");
        b=intent.getStringExtra("sales");
        c=intent.getStringExtra("type");
        text1.setText(a);
        text2.setText(b);
        text3.setText(c);

        btn = (Button)findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecommendActivity.this, Recommenddetail.class);
                startActivity(intent);
            }
        });

    }
}
