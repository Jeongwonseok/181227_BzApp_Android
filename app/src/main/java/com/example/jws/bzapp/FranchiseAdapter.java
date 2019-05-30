package com.example.jws.bzapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.jws.bzapp.FranchiseInfo;
import com.example.jws.bzapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class FranchiseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static String Category = "";
    private static String top = "";

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name, Storesu, Ownermoney, Asales17, Interior, Category;
        ImageView icon;

        MyViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            icon = view.findViewById(R.id.iv);
            name = view.findViewById(R.id.name);
            Storesu = view.findViewById(R.id.count);
            Ownermoney = view.findViewById(R.id.scost);
            Asales17 = view.findViewById(R.id.sales);
            Interior = view.findViewById(R.id.interior);
            Category = view.findViewById(R.id.Category);
        }

        @Override
        public void onClick(View view) {
            //카드뷰 클릭했을때 발생하는 이벤트
            Intent intent = new Intent(view.getContext(), Franchisedetail.class);
            String shopname = name.getText().toString();
            String category = Category.getText().toString();
            intent.putExtra("Category", category);
            intent.putExtra("Shopname", shopname);
            intent.putExtra("TOP", top);
            view.getContext().startActivity(intent);
        }
    }

    private ArrayList<FranchiseInfo> franchiseInfos;

    FranchiseAdapter(ArrayList<FranchiseInfo> franchiseInfos) {
        this.franchiseInfos = franchiseInfos;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        MyViewHolder myViewHolder = (MyViewHolder) viewHolder;

        //카드뷰 레이아웃 세팅
        if (franchiseInfos.get(i).Category.equals("커피")) {
            myViewHolder.icon.setImageResource(R.drawable.coffee);
            top = "3억 1천 740만원";
        } else if (franchiseInfos.get(i).Category.equals("제과제빵")) {
            top = "4억 5천 212만원";
            myViewHolder.icon.setImageResource(R.drawable.bread);
        } else if (franchiseInfos.get(i).Category.equals("주스/차")) {
            top = "1억 676만원";
            myViewHolder.icon.setImageResource(R.drawable.juice);
        } else if (franchiseInfos.get(i).Category.equals("아이스크림/빙수")) {
            top = "1억 1천 574만원";
            myViewHolder.icon.setImageResource(R.drawable.icecream);
        } else if (franchiseInfos.get(i).Category.equals("한식")) {
            top = "11억 4천 251만원";
            myViewHolder.icon.setImageResource(R.drawable.rice);
        } else if (franchiseInfos.get(i).Category.equals("퓨전/기타")) {
            top = "12억 7천 184만원";
            myViewHolder.icon.setImageResource(R.drawable.spoon);
        } else if (franchiseInfos.get(i).Category.equals("분식")) {
            top = "4억 218만원";
            myViewHolder.icon.setImageResource(R.drawable.gimbab);
        } else if (franchiseInfos.get(i).Category.equals("양식")) {
            top = "6억 848만원";
            myViewHolder.icon.setImageResource(R.drawable.steak);
        } else if (franchiseInfos.get(i).Category.equals("일식")) {
            top = "6억 8천 833만원";
            myViewHolder.icon.setImageResource(R.drawable.sushi);
        } else if (franchiseInfos.get(i).Category.equals("중식")) {
            top = "3억 3천 468만원";
            myViewHolder.icon.setImageResource(R.drawable.chinese);
        } else if (franchiseInfos.get(i).Category.equals("세계음식")) {
            top = "1억 7천 745만원";
            myViewHolder.icon.setImageResource(R.drawable.globalcook);
        } else if (franchiseInfos.get(i).Category.equals("치킨")) {
            top = "3억 7천 18만원";
            myViewHolder.icon.setImageResource(R.drawable.chicken);
        } else if (franchiseInfos.get(i).Category.equals("주점")) {
            top = "5억 3천 866만원";
            myViewHolder.icon.setImageResource(R.drawable.beer);
        } else if (franchiseInfos.get(i).Category.equals("피자")) {
            top = "3억 1천 910만원";
            myViewHolder.icon.setImageResource(R.drawable.pizza);
        } else if (franchiseInfos.get(i).Category.equals("패스트푸드")) {
            top = "2억 7천 510만원";
            myViewHolder.icon.setImageResource(R.drawable.junkfood);
        } else if (franchiseInfos.get(i).Category.equals("스포츠")) {
            top = "1억 2천 11만원";
            myViewHolder.icon.setImageResource(R.drawable.sports);
        } else if (franchiseInfos.get(i).Category.equals("숙박")) {
            top = "7억 2천 172만원";
            myViewHolder.icon.setImageResource(R.drawable.hotel);
        } else if (franchiseInfos.get(i).Category.equals("PC방")) {
            top = "1억 1천 291만원";
            myViewHolder.icon.setImageResource(R.drawable.pc);
        } else if (franchiseInfos.get(i).Category.equals("오락")) {
            top = "6천 241만원";
            myViewHolder.icon.setImageResource(R.drawable.orak);
        } else if (franchiseInfos.get(i).Category.equals("기타소매")) {
            top = "7억 7천 554만원";
            myViewHolder.icon.setImageResource(R.drawable.gitaretail);
        } else if (franchiseInfos.get(i).Category.equals("의류/패션")) {
            top = "1억 1천 253만원";
            myViewHolder.icon.setImageResource(R.drawable.fashion);
        } else if (franchiseInfos.get(i).Category.equals("화장품")) {
            top = "1억 9천 897만원";
            myViewHolder.icon.setImageResource(R.drawable.cosmetic);
        } else if (franchiseInfos.get(i).Category.equals("건강식품")) {
            top = "1억 7천 829만원";
            myViewHolder.icon.setImageResource(R.drawable.vegetable);
        } else if (franchiseInfos.get(i).Category.equals("편의점")) {
            top = "2억 7천 233만원";
            myViewHolder.icon.setImageResource(R.drawable.convenience);
        } else if (franchiseInfos.get(i).Category.equals("농수산물")) {
            top = "9천 774만원";
            myViewHolder.icon.setImageResource(R.drawable.farm);
        } else if (franchiseInfos.get(i).Category.equals("종합소매점")) {
            top = "10억 631만원";
            myViewHolder.icon.setImageResource(R.drawable.largeretail);
        } else if (franchiseInfos.get(i).Category.equals("기타서비스")) {
            top = "2억 5천 929만원";
            myViewHolder.icon.setImageResource(R.drawable.gitaservice);
        } else if (franchiseInfos.get(i).Category.equals("미용")) {
            top = "5억 1천 242만원";
            myViewHolder.icon.setImageResource(R.drawable.cut);
        } else if (franchiseInfos.get(i).Category.equals("기타교육")) {
            top = "3억 1천 906만원";
            myViewHolder.icon.setImageResource(R.drawable.gitaedu);
        } else if (franchiseInfos.get(i).Category.equals("외국어교육")) {
            top = "4억 1천 726만원";
            myViewHolder.icon.setImageResource(R.drawable.foreignedu);
        } else if (franchiseInfos.get(i).Category.equals("교과교육")) {
            top = "3억 277만원";
            myViewHolder.icon.setImageResource(R.drawable.gitaedu);
        } else if (franchiseInfos.get(i).Category.equals("유아")) {
            top = "1억 6천 315만원";
            myViewHolder.icon.setImageResource(R.drawable.child);
        } else if (franchiseInfos.get(i).Category.equals("자동차")) {
            top = "1억 3천 728만원";
            myViewHolder.icon.setImageResource(R.drawable.car);
        } else if (franchiseInfos.get(i).Category.equals("안경")) {
            top = "3억 4천 925만원";
            myViewHolder.icon.setImageResource(R.drawable.glasses);
        } else if (franchiseInfos.get(i).Category.equals("세탁")) {
            top = "3천 520만원";
            myViewHolder.icon.setImageResource(R.drawable.dry);
        } else if (franchiseInfos.get(i).Category.equals("반려동물")) {
            top = "9천 390만원";
            myViewHolder.icon.setImageResource(R.drawable.dogcat);
        } else if (franchiseInfos.get(i).Category.equals("운송")) {
            top = "3천 498만원";
            myViewHolder.icon.setImageResource(R.drawable.deliever);
        } else if (franchiseInfos.get(i).Category.equals("부동산/임대")) {
            top = "5천 347만원";
            myViewHolder.icon.setImageResource(R.drawable.realestate);
        }


        myViewHolder.name.setText(franchiseInfos.get(i).name);
        myViewHolder.Storesu.setText(franchiseInfos.get(i).Storesu);
        myViewHolder.Ownermoney.setText(franchiseInfos.get(i).Ownermoney);
        myViewHolder.Asales17.setText(franchiseInfos.get(i).Asales17);
        myViewHolder.Interior.setText(franchiseInfos.get(i).Interior);
        myViewHolder.Category.setText(franchiseInfos.get(i).Category);
    }

    @Override
    public int getItemCount() {
        return franchiseInfos.size();
    }
}
