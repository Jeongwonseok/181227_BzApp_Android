package com.example.jws.bzapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HotAdapter extends RecyclerView.Adapter<HotAdapter.ViewHolder> implements OnListItemClickListener {

    private ArrayList<HotInfo> hotInfos;
    Context context;
    OnListItemClickListener mListener;

    HotAdapter(Context context, ArrayList<HotInfo> hotInfos) {
        this.context = context;
        this.hotInfos = hotInfos;
    }

    @Override
    public HotAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.hotlist, viewGroup, false);
        HotAdapter.ViewHolder holder = new HotAdapter.ViewHolder(view);
        holder.setOnListItemClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(HotAdapter.ViewHolder viewHolder, int i) {

        HotAdapter.ViewHolder myViewHolder = (HotAdapter.ViewHolder) viewHolder;
        //텍스트 삽입
        Picasso.get()
                .load(hotInfos.get(i).Image)
                .into(myViewHolder.HOTimage);
        myViewHolder.HOTtitle.setText(hotInfos.get(i).Title);
        myViewHolder.HOTtime.setText(hotInfos.get(i).Date);
    }

    @Override
    public int getItemCount() {
        return hotInfos.size();
    }

    @Override
    public void onListItemClick(int position) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(hotInfos.get(position).Url));
        context.startActivity(intent);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView HOTtitle, HOTtime;
        ImageView HOTimage;

        ViewHolder(View view) {
            super(view);
            HOTimage = view.findViewById(R.id.HOTimage);
            HOTtitle = view.findViewById(R.id.HOTtitle);
            HOTtime = view.findViewById(R.id.HOTtime);

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
