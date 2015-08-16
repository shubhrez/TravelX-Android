package com.travelx;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.utils.adapter.DescriptionAdapter;

import java.util.HashMap;
import java.util.Set;

public class AboutPlaceFragment extends Fragment {

    public static final String ARG_OBJECT = "object";
    ListView description_list;
    private LayoutInflater mInflater;
    DescriptionAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated
        // properly.
        View rootView = inflater.inflate(
                R.layout.about_place_fragment, container, false);
        description_list = (ListView) rootView.findViewById(R.id.desc_list);
//        mInflater=LayoutInflater.from(getActivity());
//        Set<String> map_keys = Common.place_selected.getDescriptionList().keySet();
//        description_list.removeAllViews();
//        for(String key:map_keys){
//            System.out.println(key);
//            description_list.addView(buildView(key, Common.place_selected.getDescriptionList().get(key)));
//        }

        mAdapter = new DescriptionAdapter(getActivity(),Common.place_selected.getDescriptionList());
        description_list.setAdapter(mAdapter);
        return rootView;
    }

//    public View buildView(String heading,String text){
//        View item_view = mInflater.inflate(R.layout.description_item,null);
//
//        AQuery aq = new AQuery(item_view);
//        aq.id(R.id.heading).text(heading);
//        aq.id(R.id.text).text(text);
//        return item_view;
//    }

}
