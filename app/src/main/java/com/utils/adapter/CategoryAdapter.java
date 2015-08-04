package com.utils.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.travelx.R;
import com.utils.Constant;
import com.utils.model.Category;

import java.util.List;

public class CategoryAdapter extends BaseAdapter {

    private List<Category> categoryList;
    Context context;
    private LayoutInflater mInflater;

    public CategoryAdapter(Context context, List<Category> categoryList) {
        this.categoryList = categoryList;
        this.context = context;
        mInflater=LayoutInflater.from(context);
    }

    //How many items are in the data set represented by this Adapter.
    @Override
    public int getCount() {
        return categoryList.size();
    }
    //Get the data item associated with the specified position in the data set.
    @Override
    public Object getItem(int position) {
        return categoryList.get(position);
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
            convertView=mInflater.inflate(R.layout.single_category_card_layout,null);
            holder = new ViewHolder();
            holder.vName=(TextView) convertView.findViewById(R.id.txtName);
            holder.category_image=(ImageView) convertView.findViewById(R.id.category_image);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        AQuery aq = new AQuery(convertView);
        aq.id(holder.vName).text(categoryList.get(position).getName());
        String url = Constant.SERVER_URL + categoryList.get(position).getImage();
        aq.id(holder.category_image).visibility(View.VISIBLE).image(url).backgroundColor(Color.parseColor("#FFFFFF"));
        return convertView;
    }


    //Get a View that displays the data at the specified position in the data set.

//    @Override
//    public int getItemCount() {
//
//        return contactList.size();
//    }

//    @Override
//    public void onBindViewHolder(CategoryHolder contactViewHolder, int i) {
//        AQuery aq = new AQuery(context);
//        Category ci = contactList.get(i);
//        contactViewHolder.vName.setText(ci.getName());
//        String url = Constant.SERVER_URL + ci.getImage();
//        System.out.println(url);
//        aq.id(contactViewHolder.category_image).visibility(View.VISIBLE).image(url).backgroundColor(Color.parseColor("#FFFFFF"));
//
//    }
//
//    @Override
//    public CategoryHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
//        View itemView = LayoutInflater.
//                from(viewGroup.getContext()).
//                inflate(R.layout.single_category_card_layout, viewGroup, false);
//
//        return new CategoryHolder(itemView);
//    }

    private class ViewHolder{
        TextView vName;
        ImageView category_image;
    }

}
