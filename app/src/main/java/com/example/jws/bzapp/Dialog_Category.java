package com.example.jws.bzapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

public class Dialog_Category extends Activity {
    Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.7f;
        getWindow().setAttributes(layoutParams);
        setContentView(R.layout.activity_dialog__category);


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

        ListView listview ;
        ListViewAdapter adapter;

        // Adapter 생성
        adapter = new ListViewAdapter() ;

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.listview1);
        listview.setAdapter(adapter);

        // 첫 번째 아이템 추가.
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.baseline_assignment_ind_black_18dp),
                "Box", "Account Box Black 36dp") ;
        // 두 번째 아이템 추가.
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.baseline_assignment_ind_black_18dp),
                "Circle", "Account Circle Black 36dp") ;
        // 세 번째 아이템 추가.
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.baseline_assignment_ind_black_18dp),
                "Ind", "Assignment Ind Black 36dp") ;

        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.baseline_assignment_ind_black_18dp),
                "Ind", "Assignment Ind Black 36dp") ;

        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.baseline_assignment_ind_black_18dp),
                "Ind", "Assignment Ind Black 36dp") ;

        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.baseline_assignment_ind_black_18dp),
                "Ind", "Assignment Ind Black 36dp") ;

        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.baseline_assignment_ind_black_18dp),
                "Ind", "Assignment Ind Black 36dp") ;

        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.baseline_assignment_ind_black_18dp),
                "Ind", "Assignment Ind Black 36dp") ;

        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.baseline_assignment_ind_black_18dp),
                "Ind", "Assignment Ind Black 36dp") ;

        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.baseline_assignment_ind_black_18dp),
                "Ind", "Assignment Ind Black 36dp") ;

        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.baseline_assignment_ind_black_18dp),
                "Ind", "Assignment Ind Black 36dp") ;

        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.baseline_assignment_ind_black_18dp),
                "Ind", "Assignment Ind Black 36dp") ;

        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.baseline_assignment_ind_black_18dp),
                "Ind", "Assignment Ind Black 36dp") ;

        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.baseline_assignment_ind_black_18dp),
                "Ind", "Assignment Ind Black 36dp") ;

        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.baseline_assignment_ind_black_18dp),
                "Ind", "Assignment Ind Black 36dp") ;


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // get item
                ListViewItem item = (ListViewItem) parent.getItemAtPosition(position) ;

                String titleStr = item.getTitle() ;
                String descStr = item.getDesc() ;
                Drawable iconDrawable = item.getIcon() ;

                // TODO : use item data.
            }
        }) ;
    }
}
