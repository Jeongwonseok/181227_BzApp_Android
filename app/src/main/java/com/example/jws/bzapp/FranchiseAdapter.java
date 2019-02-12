package com.example.jws.bzapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jws.bzapp.FranchiseInfo;
import com.example.jws.bzapp.R;

import java.util.ArrayList;

public class FranchiseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name, Storesu,Ownermoney, Asales17, Interior;

        MyViewHolder(View view){
            super(view);
            view.setOnClickListener(this);
            name = view.findViewById(R.id.name);
            Storesu = view.findViewById(R.id.count);
            Ownermoney = view.findViewById(R.id.scost);
            Asales17 = view.findViewById(R.id.sales);
            Interior = view.findViewById(R.id.interior);
        }

        @Override
        public void onClick(View view) {
            //카드뷰 클릭했을때 발생하는 이벤트
            Intent intent = new Intent(view.getContext(),Franchisedetail.class);
            String shopname=name.getText().toString();
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
        myViewHolder.name.setText(franchiseInfos.get(i).name);
        myViewHolder.Storesu.setText(franchiseInfos.get(i).Storesu);
        myViewHolder.Ownermoney.setText(franchiseInfos.get(i).Ownermoney);
        myViewHolder.Asales17.setText(franchiseInfos.get(i).Asales17);
        myViewHolder.Interior.setText(franchiseInfos.get(i).Interior);
    }

    @Override
    public int getItemCount() {
        return franchiseInfos.size();
    }
}
