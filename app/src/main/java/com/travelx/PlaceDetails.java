package com.travelx;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TableLayout;

import com.astuetz.PagerSlidingTabStrip;
import com.utils.adapter.PlacePagerAdapter;

public class PlaceDetails extends AppCompatActivity{

    Toolbar toolbar;
    ViewPager viewPager;
    PlacePagerAdapter mAdapter;
    TabLayout tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_details_layout);

        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        viewPager = (ViewPager) findViewById(R.id.pager);
        tabs = (TabLayout) findViewById(R.id.tabs_layout);


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

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        finish();
    }

}
