package com.travelx;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.utils.adapter.GalleryAdapter;

import java.util.List;

public class GalleryPlaceFragment extends Fragment {

    ListView gallery_list;
    GalleryAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated
        // properly.
        View rootView = inflater.inflate(
                R.layout.gallery_place_fragment, container, false);
        gallery_list = (ListView) rootView.findViewById(R.id.gallery_list);
        mAdapter = new GalleryAdapter(getActivity(),Common.place_selected.getGalleryList());
        gallery_list.setAdapter(mAdapter);
        return rootView;
    }
}
