package com.utils.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.travelx.AboutPlaceFragment;
import com.travelx.BudgetTimePlaceFragment;
import com.travelx.GalleryPlaceFragment;


public class PlacePagerAdapter extends FragmentPagerAdapter {

    public PlacePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {

        switch (i) {
            case 0:
                return new AboutPlaceFragment();
            case 1:
                return new GalleryPlaceFragment();
            case 2:
                return new BudgetTimePlaceFragment();
            default:
                return new AboutPlaceFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        switch (position) {
            case 0:
                return "About";
            case 1:
                return "Gallery";
            case 2:
                return "Budget & Time";
            default:
                return "About";
        }
//        return "OBJECT " + (position + 1);
    }


}

