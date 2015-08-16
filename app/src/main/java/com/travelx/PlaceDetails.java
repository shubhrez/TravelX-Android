package com.travelx;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.os.PersistableBundle;
import android.os.StrictMode;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableLayout;

import com.astuetz.PagerSlidingTabStrip;
import com.utils.Constant;
import com.utils.adapter.PlacePagerAdapter;
import com.utils.model.Description;
import com.utils.model.GalleryItem;
import com.utils.model.Place;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PlaceDetails extends AppCompatActivity{

    Toolbar toolbar;
    ViewPager viewPager;
    PlacePagerAdapter mAdapter;
    TabLayout tabs;
    ProgressBar pb;
    LinearLayout content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_details_layout);

        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        viewPager = (ViewPager) findViewById(R.id.pager);
        tabs = (TabLayout) findViewById(R.id.tabs_layout);
        pb = (ProgressBar) findViewById(R.id.progressBar);
        content = (LinearLayout) findViewById(R.id.content);


        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setTitle(Common.place_selected.getName());
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);


        mAdapter = new PlacePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mAdapter);


        tabs.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        get_data();

    }

    public void get_data(){

        class SendPostReqAsyncTask extends AsyncTask<String, Void, JSONObject> {

            @Override
            protected void onPreExecute() {
                pb.setVisibility(View.VISIBLE);
            }

            @Override
            protected JSONObject doInBackground(String... params) {
                if(Looper.myLooper()==null){
                    Looper.prepare();
                }
                String url = Constant.SERVER_URL+ "/android/get_place_details/?place_id=" + Common.place_selected.getId();
                JSONParser jParser = new JSONParser();

                // Getting JSON from URL
                JSONObject json = jParser.getJSONFromUrl(url);
                return json;
            }

            @Override
            protected void onPostExecute(JSONObject result) {
                super.onPostExecute(result);
                if(result!=null){
                    try {
                        update_data(result);
                        pb.setVisibility(View.GONE);
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute();
    }

    public void update_data(JSONObject json) throws JSONException{

        JSONArray data = json.getJSONArray("objects");


        JSONObject object = data.getJSONObject(0);
        String duration = object.getString("duration");
        int budget = object.getInt("budget");
        JSONArray description = object.getJSONArray("description");
        JSONArray gallery = object.getJSONArray("gallery");

        Common.place_selected.setBudget(budget);
        Common.place_selected.setDuration(duration);

        ArrayList<Description> desc_list = new ArrayList<Description>();
        for(int i=0;i<description.length();i++){
            JSONObject desc_object = description.getJSONObject(i);
            String title = desc_object.getString("title");
            String text = desc_object.getString("text");
            Description desc = new Description(title,text);
            desc_list.add(desc);
        }

        ArrayList<GalleryItem> gallery_list = new ArrayList<GalleryItem>();
        for(int j=0;j<gallery.length();j++){
            JSONObject gallery_object = gallery.getJSONObject(j);
            String short_description = gallery_object.getString("short_description");
            String image = gallery_object.getString("image");
            GalleryItem item = new GalleryItem(short_description,image);
            gallery_list.add(item);
        }

        Common.place_selected.setDescriptionList(desc_list);
        Common.place_selected.setGalleryList(gallery_list);

        printdata_on_screen();

    }


    public void printdata_on_screen(){

        if(mAdapter!=null){
            try {
                mAdapter.notifyDataSetChanged();
                content.setVisibility(View.VISIBLE);
            }catch(Exception e1){}
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        finish();
    }

}
