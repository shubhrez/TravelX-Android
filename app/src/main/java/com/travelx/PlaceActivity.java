package com.travelx;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.utils.Constant;
import com.utils.adapter.PlaceAdapter;
import com.utils.model.Category;
import com.utils.model.Place;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PlaceActivity extends AppCompatActivity{

    private Toolbar toolbar;
    private ProgressBar pb;
    ListView list;
    ArrayList<Place> places_list = new ArrayList<Place>();
    PlaceAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.places_layout);

        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setTitle(Common.category_selected.getName());
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Back Pressed");
                onBackPressed();
            }
        });

        list = (ListView) findViewById(R.id.place_list);
        myAdapter = new PlaceAdapter(this,places_list);
        list.setAdapter(myAdapter);
        pb = (ProgressBar) findViewById(R.id.progressBar);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Common.place_selected = places_list.get(position);
                Intent intent = new Intent(getApplicationContext(),PlaceDetails.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        get_data();

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


    public void printdata_on_screen(){

        if(myAdapter!=null){
            try {
                myAdapter.notifyDataSetChanged();
            }catch(Exception e1){}
        }
    }
}
