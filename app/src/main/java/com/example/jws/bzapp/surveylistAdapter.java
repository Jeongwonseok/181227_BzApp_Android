package com.example.jws.bzapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class surveylistAdapter extends BaseAdapter {
    private ArrayList<surveylist_item> listViewItemList = new ArrayList<surveylist_item>();

    public surveylistAdapter() {

    }

    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.surveylsit_item, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView location = (TextView) convertView.findViewById(R.id.location);
        TextView type = (TextView) convertView.findViewById(R.id.type);
        TextView sales = (TextView) convertView.findViewById(R.id.sales);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        surveylist_item listViewItem = listViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        location.setText(listViewItem.getLocation());
        type.setText(listViewItem.getType());
        sales.setText(listViewItem.getSales());

        return convertView;
    }

    public void addItem(String location, String type, String sales) {
        surveylist_item item = new surveylist_item();

        item.setLocation(location);
        item.setType(type);
        item.setSales(sales);

        listViewItemList.add(0,item);
    }
    public void clearitem(){
        listViewItemList.clear();
    }

}
