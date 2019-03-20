package com.example.jws.bzapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

public class FlipAdapter extends PagerAdapter{
    Context context;

    ArrayList<Integer>data;
    public FlipAdapter(Context context, ArrayList<Integer>data) {
        this.context = context;
        this.data=data;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        //뷰페이지 슬라이딩 할 레이아웃 인플레이션
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.slider,null);
        ImageView image_container = (ImageView) v.findViewById(R.id.image_container);
        position=position%data.size();
        image_container.setImageResource(data.get(position));
        container.addView(v);
        return v;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((View)object);

    }

    @Override
    public int getCount() {
        return data.size()*3;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

}
