package com.example.jws.bzapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017-08-07.
 */

public class SearchAdapter extends BaseAdapter {

    private ArrayList<ListVO>listVO = new ArrayList<ListVO>();
    private List<ListVO> list;
    private LayoutInflater inflate;
    private Context context;
    private  ViewHolder viewHolder;
    public SearchAdapter(List<ListVO> list,Context context){
        this.list=list;
        this.context=context;
        this.inflate=LayoutInflater.from(context);

    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        final Context context = viewGroup.getContext();

        if(convertView == null){
            convertView = inflate.inflate(R.layout.activity_franchisesearchlist,null);

            viewHolder = new ViewHolder();
            viewHolder.Name = (TextView) convertView.findViewById(R.id.Name);
            viewHolder.Mutual=(TextView)convertView.findViewById(R.id.Mutual);
            viewHolder.image=(ImageView)convertView.findViewById(R.id.img);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        // 리스트에 있는 데이터를 리스트뷰 셀에 뿌린다.
        viewHolder.Name.setText(list.get(position).getTitle());
        viewHolder.Mutual.setText(list.get(position).getContext());
        if(list.get(position).getCategory().equals("커피")){
            viewHolder.image.setImageResource(R.drawable.coffee);
        }else if(list.get(position).getCategory().equals("제과제빵")){
            viewHolder.image.setImageResource(R.drawable.bread);
        }  else if(list.get(position).getCategory().equals("주스/차")){
            viewHolder.image.setImageResource(R.drawable.juice);}
        else if(list.get(position).getCategory().equals("아이스크림/빙수")){
            viewHolder.image.setImageResource(R.drawable.icecream);}
        else if(list.get(position).getCategory().equals("한식")){
            viewHolder.image.setImageResource(R.drawable.rice);}
        else if(list.get(position).getCategory().equals("퓨전/기타")){
            viewHolder.image.setImageResource(R.drawable.spoon);}
        else if(list.get(position).getCategory().equals("분식")){
            viewHolder.image.setImageResource(R.drawable.gimbab);}
        else if(list.get(position).getCategory().equals("양식")){
            viewHolder.image.setImageResource(R.drawable.steak);}
        else if(list.get(position).getCategory().equals("일식")){
            viewHolder.image.setImageResource(R.drawable.sushi);}
        else if(list.get(position).getCategory().equals("중식")){
            viewHolder.image.setImageResource(R.drawable.chinese);}
        else if(list.get(position).getCategory().equals("세계음식")){
            viewHolder.image.setImageResource(R.drawable.globalcook);}
        else if(list.get(position).getCategory().equals("치킨")){
            viewHolder.image.setImageResource(R.drawable.chicken);}
        else if(list.get(position).getCategory().equals("주점")){
            viewHolder.image.setImageResource(R.drawable.beer);}
        else if(list.get(position).getCategory().equals("피자")){
            viewHolder.image.setImageResource(R.drawable.pizza);}
        else if(list.get(position).getCategory().equals("패스트푸드")){
            viewHolder.image.setImageResource(R.drawable.junkfood);}
        else if(list.get(position).getCategory().equals("스포츠")){
            viewHolder.image.setImageResource(R.drawable.sports);}
        else if(list.get(position).getCategory().equals("숙박")){
            viewHolder.image.setImageResource(R.drawable.hotel);}
        else if(list.get(position).getCategory().equals("PC방")){
            viewHolder.image.setImageResource(R.drawable.pc);}
        else if(list.get(position).getCategory().equals("오락")){
            viewHolder.image.setImageResource(R.drawable.orak);}
        else if(list.get(position).getCategory().equals("기타소매")){
            viewHolder.image.setImageResource(R.drawable.gitaretail);}
        else if(list.get(position).getCategory().equals("의류/패션")){
            viewHolder.image.setImageResource(R.drawable.fashion);}
        else if(list.get(position).getCategory().equals("화장품")){
            viewHolder.image.setImageResource(R.drawable.cosmetic);}
        else if(list.get(position).getCategory().equals("건강식품")){
            viewHolder.image.setImageResource(R.drawable.vegetable);}
        else if(list.get(position).getCategory().equals("편의점")){
            viewHolder.image.setImageResource(R.drawable.convenience);}
        else if(list.get(position).getCategory().equals("농수산물")){
            viewHolder.image.setImageResource(R.drawable.farm);}
        else if(list.get(position).getCategory().equals("종합소매점")){
            viewHolder.image.setImageResource(R.drawable.largeretail);}
        else if(list.get(position).getCategory().equals("기타서비스")){
            viewHolder.image.setImageResource(R.drawable.gitaservice);}
        else if(list.get(position).getCategory().equals("미용")){
            viewHolder.image.setImageResource(R.drawable.cut);}
        else if(list.get(position).getCategory().equals("기타교육")){
            viewHolder.image.setImageResource(R.drawable.gitaedu);}
        else if(list.get(position).getCategory().equals("외국어교육")){
            viewHolder.image.setImageResource(R.drawable.foreignedu);}
        else if(list.get(position).getCategory().equals("교과교육")){
            viewHolder.image.setImageResource(R.drawable.gitaedu);}
        else if(list.get(position).getCategory().equals("유아")){
            viewHolder.image.setImageResource(R.drawable.child);}
        else if(list.get(position).getCategory().equals("자동차")){
            viewHolder.image.setImageResource(R.drawable.car);}
        else if(list.get(position).getCategory().equals("안경")){
            viewHolder.image.setImageResource(R.drawable.glasses);}
        else if(list.get(position).getCategory().equals("세탁")){
            viewHolder.image.setImageResource(R.drawable.dry);}
        else if(list.get(position).getCategory().equals("반려동물")){
            viewHolder.image.setImageResource(R.drawable.dogcat);}
        else if(list.get(position).getCategory().equals("운송")){
            viewHolder.image.setImageResource(R.drawable.deliever);}
        else if(list.get(position).getCategory().equals("부동산/임대")){
            viewHolder.image.setImageResource(R.drawable.realestate);}
        return convertView;
    }

    class ViewHolder{
        public TextView Name;
        public TextView Mutual;
        public ImageView image;
    }
}