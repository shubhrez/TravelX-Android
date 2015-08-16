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
import com.utils.model.Description;

import java.util.ArrayList;
import java.util.HashMap;

public class DescriptionAdapter extends BaseAdapter {

    private ArrayList<Description> descList;
    Context context;
    private LayoutInflater mInflater;

    public DescriptionAdapter (Context context,ArrayList<Description> descList) {
        this.descList = descList;
        this.context = context;
        mInflater=LayoutInflater.from(context);
    }

    //How many items are in the data set represented by this Adapter.
    @Override
    public int getCount() {
        return descList.size();
    }
    //Get the data item associated with the specified position in the data set.
    @Override
    public Object getItem(int position) {
        return descList.get(position);
    }
    //Get the row id associated with the specified position in the list.
    @Override
    public long getItemId(int position) {

        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null) {
            convertView=mInflater.inflate(R.layout.description_single_item,null);
            holder = new ViewHolder();
            holder.title=(TextView) convertView.findViewById(R.id.title);
            holder.text=(TextView) convertView.findViewById(R.id.text);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        AQuery aq = new AQuery(convertView);
        aq.id(holder.title).text(descList.get(position).getTitle());
        aq.id(holder.text).text(descList.get(position).getText());
        return convertView;
    }



    private class ViewHolder{
        TextView title;
        TextView text;
    }

}
