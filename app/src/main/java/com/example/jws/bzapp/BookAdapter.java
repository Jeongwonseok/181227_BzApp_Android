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

public class BookAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView TVaddress, TVtime;

        MyViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            TVaddress = view.findViewById(R.id.TVaddress);
            TVtime = view.findViewById(R.id.TVtime);

        }

        @Override
        public void onClick(View view) {

        }
    }

    private ArrayList<BookInfo> bookInfos;

    BookAdapter(ArrayList<BookInfo> bookInfos) {
        this.bookInfos = bookInfos;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.booklist, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
        //텍스트 삽입
        myViewHolder.TVaddress.setText(bookInfos.get(i).Address);
        myViewHolder.TVtime.setText(bookInfos.get(i).Time);
    }

    @Override
    public int getItemCount() {
        return bookInfos.size();
    }
}