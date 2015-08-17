package com.travelx;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.utils.model.Category;
import com.utils.model.Place;

import java.util.ArrayList;


public class Common extends Application {

    public static Category category_selected;
    public static Place place_selected;
    public static ArrayList<Category> category_list = new ArrayList<Category>();
    public static SharedPreferences prefs;
    public static String regid="";
    public static SharedPreferences.Editor editor;

    @Override
    public void onCreate() {
        super.onCreate();
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();

    }
}
