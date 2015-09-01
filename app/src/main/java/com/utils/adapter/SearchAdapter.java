package com.utils.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.travelx.R;
import com.utils.Constant;
import com.utils.model.Place;

import java.util.ArrayList;

public class SearchAdapter extends BaseAdapter{

    ArrayList<Place> place_list = new ArrayList<Place>();
    Context context;
    private LayoutInflater mInflater;

    public SearchAdapter(Context context,ArrayList<Place> place_list){
        this.context = context;
        this.place_list = place_list;
        mInflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return place_list.size();
    }

    @Override
    public Place getItem(int position) {
        return place_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return place_list.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null) {
            convertView=mInflater.inflate(R.layout.search_single_item,null);
            holder = new ViewHolder();
            holder.Name=(TextView) convertView.findViewById(R.id.place_name);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        AQuery aq = new AQuery(convertView);
        aq.id(holder.Name).text(place_list.get(position).getName());
        return convertView;
    }

    private class ViewHolder{
        TextView Name;
    }
}
