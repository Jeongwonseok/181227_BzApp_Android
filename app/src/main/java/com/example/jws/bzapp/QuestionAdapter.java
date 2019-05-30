package com.example.jws.bzapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class QuestionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgbtnUnder1;
        LinearLayout updateView1;
        TextView TVtitle, TVdatetime, TVContent;

        MyViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            imgbtnUnder1 = view.findViewById(R.id.imgbtnUnder1);
            updateView1 = view.findViewById(R.id.updateView1);
            TVtitle = view.findViewById(R.id.TVtitle);
            TVdatetime = view.findViewById(R.id.TVdatetime);
            TVContent = view.findViewById(R.id.TVContent);
            //이미지 버튼 클릭시 content보여주기
            imgbtnUnder1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (updateView1.getVisibility() == View.VISIBLE) {
                        updateView1.setVisibility(View.GONE);
                        imgbtnUnder1.setImageResource(R.drawable.under);
                    } else {
                        updateView1.setVisibility(View.VISIBLE);
                        imgbtnUnder1.setImageResource(R.drawable.over);
                    }
                }
            });
        }

        @Override
        public void onClick(View view) {

        }
    }

    private ArrayList<QuestionInfo> questionInfos;

    QuestionAdapter(ArrayList<QuestionInfo> questionInfos) {
        this.questionInfos = questionInfos;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.questionlist, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
        //텍스트 삽입
        myViewHolder.TVtitle.setText(questionInfos.get(i).Title);
        myViewHolder.TVdatetime.setText(questionInfos.get(i).Date);
        myViewHolder.TVContent.setText(questionInfos.get(i).Content);
    }

    @Override
    public int getItemCount() {
        return questionInfos.size();
    }
}