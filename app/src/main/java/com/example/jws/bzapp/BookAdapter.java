package com.example.jws.bzapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> implements OnListItemClickListener {

    private ArrayList<BookInfo> bookInfos;
    Context context;
    OnListItemClickListener mListener;

    BookAdapter(Context context, ArrayList<BookInfo> bookInfos) {
        this.context = context;
        this.bookInfos = bookInfos;
    }

    @Override
    public BookAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.booklist, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        holder.setOnListItemClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(BookAdapter.ViewHolder viewHolder, int i) {

        ViewHolder myViewHolder = (ViewHolder) viewHolder;
        //텍스트 삽입
        myViewHolder.TVaddress.setText(bookInfos.get(i).addr);
        myViewHolder.TVtime.setText(bookInfos.get(i).time);
    }

    @Override
    public int getItemCount() {
        return bookInfos.size();
    }

    @Override
    public void onListItemClick(int position) {
        double mLat = Double.valueOf(bookInfos.get(position).getLat());
        double mLong = Double.valueOf(bookInfos.get(position).getLng());
        int a = Integer.valueOf(bookInfos.get(position).getArea());
        Intent intent = new Intent(context, AnalysisActivity.class);
        intent.putExtra("mLat", mLat);
        intent.putExtra("mLong", mLong);
        intent.putExtra("a", a);
        context.startActivity(intent);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView TVaddress, TVtime;

        ViewHolder(View view) {
            super(view);
            TVaddress = view.findViewById(R.id.TVaddress);
            TVtime = view.findViewById(R.id.TVtime);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onListItemClick(getAdapterPosition());
                }
            });
        }

        public void setOnListItemClickListener(OnListItemClickListener onListItemClickListener) {
            mListener = onListItemClickListener;
        }
    }
}

interface OnListItemClickListener {
    public void onListItemClick(int position);
}