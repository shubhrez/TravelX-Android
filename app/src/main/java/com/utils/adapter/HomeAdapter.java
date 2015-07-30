package com.utils.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.travelx.R;
import com.utils.Constant;
import com.utils.model.Category;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.CategoryHolder> {

    private List<Category> contactList;
    Context context;
    public HomeAdapter(Context context,List<Category> contactList) {
        this.contactList = contactList;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    @Override
    public void onBindViewHolder(CategoryHolder contactViewHolder, int i) {
        AQuery aq = new AQuery(context);
        Category ci = contactList.get(i);
        contactViewHolder.vName.setText(ci.getName());
        String url = Constant.SERVER_URL + ci.getImage();
        System.out.println(url);
        aq.id(contactViewHolder.category_image).visibility(View.VISIBLE).image(url).backgroundColor(Color.parseColor("#FFFFFF"));

    }

    @Override
    public CategoryHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.home_card_layout, viewGroup, false);

        return new CategoryHolder(itemView);
    }

    public static class CategoryHolder extends RecyclerView.ViewHolder {
        protected TextView vName;
        protected ImageView category_image;

        public CategoryHolder(View v) {
            super(v);
            vName =  (TextView) v.findViewById(R.id.txtName);
            category_image = (ImageView)  v.findViewById(R.id.category_image);

        }
    }
}
