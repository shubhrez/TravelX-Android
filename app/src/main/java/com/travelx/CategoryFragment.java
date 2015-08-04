package com.travelx;


import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.utils.Constant;
import com.utils.adapter.CategoryAdapter;
import com.utils.model.Category;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CategoryFragment extends Fragment {

    ProgressBar pb;
    CategoryAdapter myAdapter;
    ArrayList<Category> category_list=new ArrayList<Category>();
    GridView recList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.home_fragment, container, false);

        recList = (GridView)rootView.findViewById(R.id.cardList);
        pb = (ProgressBar)rootView.findViewById(R.id.progressBar);
//        recList.setHasFixedSize(true);
//        LinearLayoutManager llm = new LinearLayoutManager(getActivity().getApplicationContext());
//        llm.setOrientation(LinearLayoutManager.VERTICAL);
//        recList.setLayoutManager(llm);

//        final GestureDetector mGestureDetector = new GestureDetector(getActivity().getApplicationContext(), new GestureDetector.SimpleOnGestureListener() {
//            @Override
//            public boolean onSingleTapUp(MotionEvent e) {
//                return true;
//            }
//        });
//
//        recList.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
//            @Override
//            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
//                View child = recList.findChildViewUnder(e.getX(), e.getY());
//
//                if (child != null && mGestureDetector.onTouchEvent(e)) {
//                    Intent intent = new Intent(getActivity().getApplicationContext(),PlaceActivity.class);
//                    startActivity(intent);
//                    getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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


        myAdapter = new CategoryAdapter(getActivity().getApplicationContext(),category_list);
        recList.setAdapter(myAdapter);
        get_data();

        recList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Common.category_selected = category_list.get(position);
                Intent intent = new Intent(getActivity().getApplicationContext(),PlaceActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
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
        Common.category_list = category_list;
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
