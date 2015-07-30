package com.travelx;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class JSONParser {
  static InputStream is = null;
  static JSONObject jObj = null;
  static String json = "";
  Boolean status_info=true;
  // constructor
  public JSONParser() {
  }
  public JSONObject getJSONFromUrl(String url) {
    // Making HTTP request
    try {
      // defaultHttpClient
	  HttpParams httpParameters = new BasicHttpParams();
      int timeoutConnection = 10000;
      HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
      int timeoutSocket = 12000;
      HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
      DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
      httpClient.setParams(httpParameters);
      HttpGet httpPost = new HttpGet(url);
      HttpResponse httpResponse = httpClient.execute(httpPost);
      int status =httpResponse.getStatusLine().getStatusCode();
      if(status>=400 && status<=510){
        status_info=false;
      }
      HttpEntity httpEntity = httpResponse.getEntity();
      is = httpEntity.getContent();
    } catch (UnsupportedEncodingException e) {
      status_info=false;
      e.printStackTrace();
    } catch (ClientProtocolException e) {
      status_info=false;
      e.printStackTrace();
    } catch (IOException e) {
      status_info=false;
      e.printStackTrace();
    }
    try {
      BufferedReader reader = new BufferedReader(new InputStreamReader(
          is, "iso-8859-1"), 8);
      StringBuilder sb = new StringBuilder();
      String line = null;
      while ((line = reader.readLine()) != null) {
//    	  //System.out.println(line);
    	  sb.append(line + "n");
      }
      is.close();
      json = sb.toString();
    } catch (Exception e) {
     // Log.e("Buffer Error", "Error converting result " + e.toString());
    }
    // try parse the string to a JSON object
    try {
//    	//System.out.println(json);
    	if(json.trim().toLowerCase().contains("NOT Available".toLowerCase())){
    		
    		return null;
    	}
      jObj = new JSONObject(json);
    } catch (JSONException e) {
    //  Log.e("JSON Parser", "Error parsing data " + e.toString());
    }
    // return JSON String
    return jObj;
  }

  public boolean get_status() {
    return status_info;
  }
}
