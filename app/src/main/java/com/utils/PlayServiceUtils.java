package com.utils;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.travelx.Common;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class PlayServiceUtils {

    public static Context context;
    public static String SENDER_ID = "248256756871";
    static final String TAG = "GCMDemo";
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    public static Activity activity;
    //    GoogleCloudMessaging gcm;

    public PlayServiceUtils(Context context,Activity activity){
        this.context=context;
        this.activity=activity;
//        this.gcm = GoogleCloudMessaging.getInstance(context);
    }

    public boolean IsInternetActive(){
        ConnectionDetector cd = new ConnectionDetector(context);
        return  cd.isConnectingToInternet();
    }

    public static boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, activity,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(TAG, "This device is not supported.");
            }
            return false;
        }
        return true;
    }

    public void register_app_id() {
        if (checkPlayServices()) {
            Common.regid = getRegistrationId(context);
            if (Common.regid.isEmpty()) {
                registerInBackground();
            }
        }
    }

    public static void storeRegistrationId(Context context, String regId) {
        int appVersion = getAppVersion(context);
//        Log.i(TAG, "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = Common.prefs.edit();
        editor.putString(PROPERTY_REG_ID, regId);
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }
    private String getRegistrationId(Context context) {
        String registrationId = Common.prefs.getString(PROPERTY_REG_ID, "");
        if (registrationId.isEmpty()) {
            Log.i(TAG, "Registration not found.");
            return "";
        }
        // Check if app was updated; if so, it must clear the registration ID
        // since the existing regID is not guaranteed to work with the new
        // app version.
        int registeredVersion = Common.prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i(TAG, "App version changed.");
            return "";
        }
        return registrationId;
    }

    public static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    public static void registerInBackground() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    InstanceID instanceID = InstanceID.getInstance(context);
                    String token = instanceID.getToken(SENDER_ID, GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
                    Common.regid = token;
                    // [END get_token]

                    sendRegistrationIdToBackend();
                    storeRegistrationId(context, token);

                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                    // If there is an error, don't just keep trying to register.
                    // Require the user to click a button again, or perform
                    // exponential back-off.
                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
            }
        }.execute(null, null, null);
    }

    public static void sendRegistrationIdToBackend() {
        // Your implementation here.
        try {
            String url = Constant.SERVER_URL + "/android/register_app_id/";
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(url);
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("app_id", Common.regid));
            nameValuePairs.add(new BasicNameValuePair("email", Common.getPreferredEmail()));
//            nameValuePairs.add(new BasicNameValuePair("device_id", Common.get_device_id()));
            nameValuePairs.add(new BasicNameValuePair("version_code",Integer.toString(getAppVersion(context))));
            post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            @SuppressWarnings("unused")
            HttpResponse response = client.execute(post);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
