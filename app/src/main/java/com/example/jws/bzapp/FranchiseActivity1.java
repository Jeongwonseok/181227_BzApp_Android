package com.example.jws.bzapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class FranchiseActivity1 extends AppCompatActivity {
    ImageButton btnBack,btnFast,btnleisure,btnDrink,btnReatil,btnService,btnEatout;
    EditText FranSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_franchise1);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FranSearch=(EditText)findViewById(R.id.franSearch);

        FranSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(FranchiseActivity1.this,FranchiseSearch.class);
                startActivity(intent);
            }
        });


        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FranchiseActivity1.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btnFast = (ImageButton) findViewById(R.id.btnfast);
        btnFast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FranchiseActivity1.this, Franchisefastfood.class);
                startActivity(intent);
            }
        });
        btnleisure=(ImageButton)findViewById(R.id.btnleisure);
        btnleisure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FranchiseActivity1.this,FranchiseFplay.class);
                startActivity(intent);
            }
        });
        btnDrink=(ImageButton)findViewById(R.id.btnbever);
        btnDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FranchiseActivity1.this,Franchisedrink.class);
                startActivity(intent);
            }
        });
        btnReatil=(ImageButton)findViewById(R.id.btnsale);
        btnReatil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FranchiseActivity1.this,Franchiseretail.class);
                startActivity(intent);
            }
        });
        btnService=(ImageButton)findViewById(R.id.btnservice);
        btnService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FranchiseActivity1.this,Franchiseservice.class);
                startActivity(intent);
            }
        });

        btnEatout=(ImageButton)findViewById(R.id.btndinner);
        btnEatout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FranchiseActivity1.this,Franchiseeatout.class);
                startActivity(intent);
            }
        });
    }
}
