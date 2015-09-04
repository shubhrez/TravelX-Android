package com.travelx;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.utils.PlayServiceUtils;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationActivity extends Activity implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener{

    Activity activity;
    RelativeLayout no_internet,fetching_location;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Intent intent;
    static List<Address> listOfAddress;
    Geocoder geocoder;
    static double curr_latitude ;
    static double curr_longitude;
    protected LocationManager locationManager;
    boolean isGPSEnabled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_layout);
        activity = this;

        no_internet = (RelativeLayout) findViewById(R.id.no_internet_layout);
        fetching_location =(RelativeLayout) findViewById(R.id.fetching_location_layout);

        intent = new Intent(this,MainHomeActivity.class);
        geocoder = new Geocoder(this, Locale.getDefault());

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }


    @Override
    public void onConnected(Bundle bundle) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            curr_latitude = mLastLocation.getLatitude();
            curr_longitude = mLastLocation.getLongitude();
            if(Geocoder.isPresent()){
                try {
                    listOfAddress = geocoder.getFromLocation(curr_latitude, curr_longitude, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(listOfAddress != null && !listOfAddress.isEmpty()){
                    Address address = listOfAddress.get(0);
                    int index= address.getMaxAddressLineIndex();
                    String area=address.getAddressLine(0);
                    for(int i=1;i<=index;i++){
                        area = area+", "+ address.getAddressLine(i);
                    }
                    String str =area;
                    if(!str.equals("")){
                        String[] location_parts =  str.split(",");
                        String location_to_save=location_parts[1].trim();
                        for(int i=2;i<location_parts.length && i<4;i++){
                            location_to_save = location_to_save+", "+location_parts[i].trim();
                        }
                        Common.location_show=location_to_save;
                        Common.location_lat= curr_latitude;
                        Common.location_lon=curr_longitude;
                    }
                }
//                System.out.println(Common.location_show);
                this.startActivity(intent);
                this.finish();
                this.overridePendingTransition(R.anim.animation, R.anim.animation_out);
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    protected void onStart() {
        super.onStart();
//        mGoogleApiClient.connect();
    }

    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // Setting Icon to Dialog
        //alertDialog.setIcon(R.drawable.delete);

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        PlayServiceUtils playServiceUtils = new PlayServiceUtils(this,activity);
        if(playServiceUtils.IsInternetActive()){
            if(no_internet.getVisibility() == View.VISIBLE){
                no_internet.setVisibility(View.GONE);
            }
            fetching_location.setVisibility(View.VISIBLE);
            locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
            // getting GPS status
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);
            if(isGPSEnabled){
                mGoogleApiClient.disconnect();
                mGoogleApiClient.connect();
            }else{
                showSettingsAlert();
            }
        }else{
            no_internet.setVisibility(View.VISIBLE);
        }

    }
}
