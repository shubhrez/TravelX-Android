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
import com.utils.model.Category;
import com.utils.model.Place;

import java.util.List;

public class PlaceAdapter extends BaseAdapter {

    private List<Place> placeList;
    Context context;
    private LayoutInflater mInflater;

    public PlaceAdapter(Context context,List<Place> placeList) {
        this.placeList = placeList;
        this.context = context;
        mInflater=LayoutInflater.from(context);
    }

    //How many items are in the data set represented by this Adapter.
    @Override
    public int getCount() {
        return placeList.size();
    }
    //Get the data item associated with the specified position in the data set.
    @Override
    public Object getItem(int position) {
        return placeList.get(position);
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
            convertView=mInflater.inflate(R.layout.single_place_card_layout,null);
            holder = new ViewHolder();
            holder.name=(TextView) convertView.findViewById(R.id.place_name);
            holder.location=(TextView) convertView.findViewById(R.id.place_location);
            holder.short_description=(TextView) convertView.findViewById(R.id.place_short_description);
            holder.place_image=(ImageView) convertView.findViewById(R.id.place_image);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        AQuery aq = new AQuery(convertView);
        aq.id(holder.name).text(placeList.get(position).getName());
        aq.id(holder.location).text(placeList.get(position).getLocation());
        aq.id(holder.short_description).text(placeList.get(position).getShort_description());
//        aq.id(holder.short_description).text("jhgcjhvGDCJ,AGD,JHCQ CD,JHC Q,jdcq,jgd ,qgfdCFGQdgfqdghafd hgf dhq    fdm");
        String url = Constant.SERVER_URL + placeList.get(position).getImage();
        aq.id(holder.place_image).visibility(View.VISIBLE).image(url).backgroundColor(Color.parseColor("#FFFFFF"));
        return convertView;
    }



    private class ViewHolder{
        TextView name;
        ImageView place_image;
        TextView location;
        TextView short_description;
    }

}
