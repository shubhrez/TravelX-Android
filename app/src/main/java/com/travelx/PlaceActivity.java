package com.travelx;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.os.StrictMode;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.androidquery.AQuery;
import com.utils.Constant;
import com.utils.adapter.PlaceAdapter;
import com.utils.adapter.RecyclerPlaceAdapter;
import com.utils.model.Category;
import com.utils.model.Place;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PlaceActivity extends AppCompatActivity{

    private Toolbar toolbar;
    private ProgressBar pb;
    RecyclerView list;
    public static ArrayList<Place> places_list = new ArrayList<Place>();
    RecyclerPlaceAdapter myAdapter;
    CollapsingToolbarLayout collapsingToolbarLayout;
    ImageView image;
    public static Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.places_layout);
        activity = this;

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(Common.category_selected.getName());

        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
//        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        image = (ImageView) findViewById(R.id.image);
        AQuery aq = new AQuery(this);
        String url = Constant.SERVER_URL + Common.category_selected.getImage();
        aq.id(image).visibility(View.VISIBLE).image(url).backgroundColor(Color.parseColor("#FFFFFF"));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Back Pressed");
                onBackPressed();
            }
        });

        list = (RecyclerView) findViewById(R.id.place_list);
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new RecyclerPlaceAdapter(places_list,this);
        list.setAdapter(myAdapter);

        pb = (ProgressBar) findViewById(R.id.progressBar);

//        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Common.place_selected = places_list.get(position);
//                Intent intent = new Intent(getApplicationContext(),PlaceDetails.class);
//                startActivity(intent);
//                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//            }
//        });

        get_data();

    }

    public static void open_place(int position){
        Common.place_selected = places_list.get(position);
        Intent intent = new Intent(activity,PlaceDetails.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        finish();
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
                String url = Constant.SERVER_URL+ "/android/get_places/?category_id=" + Common.category_selected.getId();
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

        for(int j=0; j <data.length();j++){
            JSONObject object = data.getJSONObject(j);
            int id = object.getInt("id");
            String image= object.getString("image");
            String name = object.getString("name");
            String location = object.getString("location");
            String short_description = object.getString("short_description");

            Place p = new Place(id,name,image,location,short_description);
            places_list.add(p);
        }
        printdata_on_screen();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_search) {
            Intent search_intent = new Intent(this,SearchActivity.class);
            startActivity(search_intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }

        return super.onOptionsItemSelected(item);
    }

    public void printdata_on_screen(){

        if(myAdapter!=null){
            try {
                myAdapter.notifyDataSetChanged();
            }catch(Exception e1){}
        }
    }
}
