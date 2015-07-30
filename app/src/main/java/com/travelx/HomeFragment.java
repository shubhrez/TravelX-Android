package com.travelx;


import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.os.StrictMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HeaderViewListAdapter;
import android.widget.ProgressBar;

import com.utils.Constant;
import com.utils.adapter.HomeAdapter;
import com.utils.model.Category;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    ProgressBar pb;
    HomeAdapter myAdapter;
    ArrayList<Category> category_list=new ArrayList<Category>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.home_fragment, container, false);

        RecyclerView recList = (RecyclerView)rootView.findViewById(R.id.cardList);
        pb = (ProgressBar)rootView.findViewById(R.id.progressBar);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity().getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

//        final GestureDetector mGestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
//            @Override public boolean onSingleTapUp(MotionEvent e) {
//                return true;
//            }
//        });
//
//        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
//            @Override
//            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
//                View child = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
//                if (child != null && mGestureDetector.onTouchEvent(e)) {
//                    mDrawerLayout.closeDrawers();
//                    displayView(mRecyclerView.getChildPosition(child));
//
//                    return true;
//                }
//                return false;
//            }
//
//            @Override
//            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
//
//            }
//        });


        myAdapter = new HomeAdapter(getActivity().getApplicationContext(),category_list);
        recList.setAdapter(myAdapter);
        get_data();
        return rootView;
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
	        	String url = Constant.SERVER_URL+ "/android/get_categories/";
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

            Category c = new Category(name,image,id);
            category_list.add(c);
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
