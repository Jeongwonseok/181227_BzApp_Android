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

    private static String Category ="";

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name, Storesu,Ownermoney, Asales17, Interior,Category;
        ImageView icon;
        MyViewHolder(View view){
            super(view);
            view.setOnClickListener(this);
            icon=view.findViewById(R.id.iv);
            name = view.findViewById(R.id.name);
            Storesu = view.findViewById(R.id.count);
            Ownermoney = view.findViewById(R.id.scost);
            Asales17 = view.findViewById(R.id.sales);
            Interior = view.findViewById(R.id.interior);
            Category=view.findViewById(R.id.Category);
        }

        @Override
        public void onClick(View view) {
            //카드뷰 클릭했을때 발생하는 이벤트
            Intent intent = new Intent(view.getContext(),Franchisedetail.class);
            String shopname=name.getText().toString();
            String category=Category.getText().toString();
            intent.putExtra("Category",category);
            intent.putExtra("Shopname",shopname);
            view.getContext().startActivity(intent);
        }
    }

    private ArrayList<FranchiseInfo> franchiseInfos;
    FranchiseAdapter(ArrayList<FranchiseInfo> franchiseInfos){
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

        MyViewHolder myViewHolder = (MyViewHolder)viewHolder;

        //카드뷰 레이아웃 세팅
        if(franchiseInfos.get(i).Category.equals("한식")){
        myViewHolder.icon.setImageResource(R.drawable.adver);}
        else if(franchiseInfos.get(i).Category.equals("분식"))
            myViewHolder.icon.setImageResource(R.drawable.btndinner);

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
