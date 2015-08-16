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
import com.utils.model.GalleryItem;
import com.utils.model.Place;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GalleryAdapter extends BaseAdapter {

    private ArrayList<GalleryItem> galleryList;
    Context context;
    private LayoutInflater mInflater;

    public GalleryAdapter(Context context,ArrayList<GalleryItem>  galleryList) {
        this.galleryList = galleryList;
        this.context = context;
        mInflater=LayoutInflater.from(context);
    }

    //How many items are in the data set represented by this Adapter.
    @Override
    public int getCount() {
        return galleryList.size();
    }
    //Get the data item associated with the specified position in the data set.
    @Override
    public Object getItem(int position) {
        return galleryList.get(position);
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
            convertView=mInflater.inflate(R.layout.gallery_single_item,null);
            holder = new ViewHolder();
            holder.title=(TextView) convertView.findViewById(R.id.image_title);
            holder.gallery_image=(ImageView) convertView.findViewById(R.id.gallery_image);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        AQuery aq = new AQuery(convertView);
        aq.id(holder.title).text(galleryList.get(position).getTitle());
        String url = Constant.SERVER_URL + galleryList.get(position).getImage();
        aq.id(holder.gallery_image).visibility(View.VISIBLE).image(url).backgroundColor(Color.parseColor("#FFFFFF"));
        return convertView;
    }



    private class ViewHolder{
        ImageView gallery_image;
        TextView title;
    }

}
