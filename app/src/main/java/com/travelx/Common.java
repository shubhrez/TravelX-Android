package com.travelx;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Patterns;

import com.utils.model.Category;
import com.utils.model.Place;

import java.util.ArrayList;
import java.util.List;


public class Common extends Application {

    public static Category category_selected;
    public static Place place_selected;
    public static ArrayList<Category> category_list = new ArrayList<Category>();
    public static SharedPreferences prefs;
    public static String regid="";
    public static SharedPreferences.Editor editor;
    public static String[] email_arr;
    public static String location_show;
    public static double location_lat;
    public static double location_lon;

    @Override
    public void onCreate() {
        super.onCreate();
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();

        List<String> emailList = getEmailList();
        email_arr = emailList.toArray(new String[emailList.size()]);

    }

    private List<String> getEmailList() {
        List<String> lst = new ArrayList<String>();
        Account[] accounts = AccountManager.get(this).getAccounts();
        for (Account account : accounts) {
            if (Patterns.EMAIL_ADDRESS.matcher(account.name).matches()) {
                lst.add(account.name);
            }
        }
        return lst;
    }

    public static String getPreferredEmail() {
        return  email_arr.length==0 ? "" : email_arr[0];
    }
}
