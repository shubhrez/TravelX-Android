package com.utils.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.travelx.Common;
import com.travelx.PlaceActivity;
import com.travelx.PlaceDetails;
import com.travelx.R;
import com.utils.Constant;
import com.utils.model.Place;

import java.util.ArrayList;

public class RecyclerPlaceAdapter extends RecyclerView.Adapter<RecyclerPlaceAdapter.PlaceViewHolder> {

    private ArrayList<Place> placeList;
    Context context;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class PlaceViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        CardView cv;
        TextView short_desc,place_name,place_address;
        ImageView place_image;

        public PlaceViewHolder(View v) {
            super(v);
            cv = (CardView)itemView.findViewById(R.id.card_view);
            short_desc = (TextView)itemView.findViewById(R.id.place_short_description);
            place_name = (TextView)itemView.findViewById(R.id.place_name);
            place_address = (TextView)itemView.findViewById(R.id.place_location);
            place_image = (ImageView)itemView.findViewById(R.id.place_image);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RecyclerPlaceAdapter(ArrayList<Place> place_list,Context context) {
        this.placeList = place_list;
        this.context = context;
    }



    // Create new views (invoked by the layout manager)
    @Override
    public PlaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_place_card_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        PlaceViewHolder vh = new PlaceViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(PlaceViewHolder holder, final int position) {
        holder.short_desc.setText(placeList.get(position).getShort_description());
        holder.place_name.setText(placeList.get(position).getName());
        holder.place_address.setText(placeList.get(position).getLocation());
        AQuery aq = new AQuery(context);
        String url = Constant.SERVER_URL + placeList.get(position).getImage();
        aq.id(holder.place_image).visibility(View.VISIBLE).image(url).backgroundColor(Color.parseColor("#FFFFFF"));
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlaceActivity.open_place(position);
            }
        });
    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return placeList.size();
    }
}
