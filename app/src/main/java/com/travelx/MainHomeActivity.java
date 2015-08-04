package com.travelx;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.utils.adapter.NavAdapter;
import com.utils.model.NavDrawerItem;

import java.util.ArrayList;

public class MainHomeActivity extends AppCompatActivity {

    private Toolbar toolbar;
    ActionBarDrawerToggle mDrawerToggle;
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
    private ArrayList<NavDrawerItem> navDrawerItems;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    NavAdapter mAdapter;
    private int opened_fragment = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_home_layout);

        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowCustomEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
        navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);

        navDrawerItems = new ArrayList<NavDrawerItem>();
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));

        mAdapter = new NavAdapter(navDrawerItems, this);
        mDrawerList.setAdapter(mAdapter);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerOpened(View drawerView) {
                if(opened_fragment == 0){
                    mDrawerList.getChildAt(opened_fragment).setBackgroundColor(Color.parseColor("#b6b6b6"));
                }
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }

        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        Fragment fragment = new CategoryFragment();
        getSupportActionBar().setTitle("Home");
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_container, fragment)
                .commit();



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
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }


    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
            opened_fragment = position;
            for (int i = 0; i < mDrawerList.getChildCount(); i++) {
                mDrawerList.getChildAt(i).setBackgroundColor(Color.parseColor("#ffffff"));
            }
            if (mDrawerList != null) {
                if (mDrawerList.getChildCount() > opened_fragment) {
                    mDrawerList.getChildAt(opened_fragment).setBackgroundColor(Color.parseColor("#b6b6b6"));
                }
        }
    }

    /**
     * Swaps fragments in the main content view
     */
    public void selectItem(int position) {
        // Create a new fragment and specify the planet to show based on position
        Fragment fragment = null;
        if (position == 0) {
            fragment = new CategoryFragment();
            getSupportActionBar().setTitle("Home");
        }
        if (position == 1) {
            fragment = new ContactFragment();
            getSupportActionBar().setTitle("Contact Us");
        }
        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getFragmentManager();

        fragmentManager.beginTransaction()
                .replace(R.id.frame_container, fragment)
                .commit();

        // Highlight the selected item, update the title, and close the drawer
        mDrawerList.setItemChecked(position, true);
        mDrawerLayout.closeDrawer(mDrawerList);
        }


    }

}
