package com.utils.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.travelx.MainHomeActivity;
import com.travelx.R;
import com.utils.model.NavDrawerItem;

import java.io.File;
import java.util.ArrayList;


public class NavAdapter extends BaseAdapter {

    Context mContext;
    private ArrayList<NavDrawerItem> nav_items;
    private LayoutInflater mInflater;

    public NavAdapter(ArrayList<NavDrawerItem> nav_items,Context contxt){
        this.mContext=contxt;
        this.nav_items = nav_items;
        mInflater=LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return nav_items.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return nav_items.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            convertView=mInflater.inflate(R.layout.navigation_item,null);
            holder = new ViewHolder();
            holder.item_name = (TextView) convertView.findViewById(R.id.title);
            holder.item_image = (ImageView) convertView.findViewById(R.id.icon);
//			holder.mda_tv=(TextView)convertView.findViewById(R.id.mda_tv);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        NavDrawerItem object=nav_items.get(position);
        holder.item_name.setText(object.getTitle());
        holder.item_image.setImageResource(object.getIcon());
//        if(position == MainHomeActivity.opened_fragment) {
//            convertView.setBackgroundColor(Color.parseColor("#b6b6b6"));
//        }
        return convertView;
    }


    private class ViewHolder{
        TextView item_name;
        ImageView item_image;
    }

}