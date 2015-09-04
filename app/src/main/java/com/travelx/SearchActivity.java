package com.travelx;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.HeaderViewListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.utils.Constant;
import com.utils.PlayServiceUtils;
import com.utils.adapter.SearchAdapter;
import com.utils.model.Place;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;

public class SearchActivity extends Activity {

    TextView no_result_text;
    EditText search_text;
    ListView result_list;
    TextWatcher search_text_watcher;
    ArrayList<Place> place_list = new ArrayList<Place>();
    ProgressBar pag_pb;
    SearchAdapter mAdapter;
    RelativeLayout no_internet_layout;
    SendPostReqAsyncTask sendPostReqAsyncTask;
    String url="";
    Activity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);
        activity = this;

        no_result_text = (TextView) findViewById(R.id.no_result_text);
        search_text = (EditText) findViewById(R.id.search_text);
        result_list = (ListView) findViewById(R.id.result_list);
        pag_pb = (ProgressBar) findViewById(R.id.pb);
        no_internet_layout = (RelativeLayout) findViewById(R.id.no_internet_layout);
        sendPostReqAsyncTask = new SendPostReqAsyncTask();
        mAdapter = new SearchAdapter(this,place_list);
        result_list.setAdapter(mAdapter);

        result_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Common.place_selected = mAdapter.getItem(position);
                Intent intent = new Intent(getApplicationContext(),PlaceDetails.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        search_text_watcher = new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {

                result_list.setVisibility(View.GONE);
                no_result_text.setVisibility(View.GONE);
                pag_pb.setVisibility(View.GONE);
                place_list.removeAll(place_list);

                PlayServiceUtils playService = new PlayServiceUtils(getApplicationContext(),activity);
                if(playService.IsInternetActive()){
                    sendPostReqAsyncTask.cancel(true);
                    if(s.length()>1){
                        no_internet_layout.setVisibility(View.GONE);
                        pag_pb.setVisibility(View.VISIBLE);
                        result_list.setVisibility(View.GONE);
                        get_search_data(s.toString().trim());
                    }else{
                        pag_pb.setVisibility(View.GONE);
                        result_list.setVisibility(View.GONE);
                    }
                }else{
                    pag_pb.setVisibility(View.GONE);
                    no_internet_layout.setVisibility(View.VISIBLE);
                    result_list.setVisibility(View.GONE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        search_text.addTextChangedListener(search_text_watcher);
    }

    class SendPostReqAsyncTask extends AsyncTask<String, Void, JSONObject> {
        String keyword="";
        @Override
        protected void onPreExecute(){
            if(mAdapter.getCount()==0){
                pag_pb.setVisibility(View.VISIBLE);
            }else{
                pag_pb.setVisibility(View.GONE);
            }
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            if(Looper.myLooper()==null){
                Looper.prepare();
            }
            String search_url = Constant.SERVER_URL + url;
            JSONParser jParser = new JSONParser();
            // Getting JSON from URL
            JSONObject json = jParser.getJSONFromUrl(search_url);
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);
            if(result!=null){
                try {
                    update_data(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void update_data(JSONObject json) throws JSONException {
        JSONArray results = json.getJSONArray("objects");
        for(int i=0;i<results.length();i++){
            JSONObject object = results.getJSONObject(i);
            String name = object.getString("name");
            int id = object.getInt("id");
            String location = object.getString("location");
            Place place = new Place(id,name,"",location,"");
            place_list.add(place);
        }
        print_data_on_screen();
    }

    public void print_data_on_screen(){
        mAdapter.notifyDataSetChanged();
        no_internet_layout.setVisibility(View.GONE);
        pag_pb.setVisibility(View.GONE);
        result_list.setVisibility(View.VISIBLE);
    }

    public void get_search_data(String key){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        url="/android/get_search_results/?keyword="+ URLEncoder.encode(key);
        sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
