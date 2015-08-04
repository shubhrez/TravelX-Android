package com.travelx;

import android.app.Application;

import com.utils.model.Category;
import com.utils.model.Place;

import java.util.ArrayList;


public class Common extends Application {

    public static Category category_selected;
    public static Place place_selected;
    public static ArrayList<Category> category_list = new ArrayList<Category>();

    @Override
    public void onCreate() {
        super.onCreate();

    }
}
