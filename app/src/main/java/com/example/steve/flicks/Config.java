/***************************************************************************************************
 Config.java
 Last updated: Steve Chou 7/19/2016

 Helper class to set various variables pulled from the config API listing

 **************************************************************************************************/


package com.example.steve.flicks;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Config {

    public String baseUrl;
    public String secureBaseUrl;
    public ArrayList<String> backdropSizes = new ArrayList<>();
    public ArrayList<String> logoSizes = new ArrayList<>();
    public ArrayList<String> posterSizes = new ArrayList<>();
    public ArrayList<String> profileSizes = new ArrayList<>();
    public ArrayList<String> stillSizes = new ArrayList<>();


    public void setAll(JSONObject images)
    {
        try {
            setBaseUrl(images.getString("base_url"));
            setSecureBaseUrl(images.getString("secure_base_url"));
            setBackdropSize(images.getJSONArray("backdrop_sizes"));
            setLogoSize(images.getJSONArray("logo_sizes"));
            setPosterSize(images.getJSONArray("poster_sizes"));
            setProfileSizes(images.getJSONArray("profile_sizes"));
            setStillSizes(images.getJSONArray("still_sizes"));
        }
        catch(JSONException e)
        {
            Log.e("ERROR", e.toString());
        }
    }

    public void setBaseUrl(String url)
    {
        this.baseUrl = url;
//        Log.d("setBaseUrl", url);
    }
    public void setSecureBaseUrl(String url)
    {
        this.secureBaseUrl = url;
//        Log.d("setSecureBaseUrl", url);
    }

    public void setBackdropSize(JSONArray jsonObjects){
        for (int i = 0; i < jsonObjects.length(); i++) {
            try {
                backdropSizes.add(jsonObjects.getString(i));
//                Log.d("setBackdropSize", jsonObjects.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void setLogoSize(JSONArray jsonObjects){
        for (int i = 0; i < jsonObjects.length(); i++) {
            try {
                logoSizes.add(jsonObjects.getString(i));
//                Log.d("setLogoSize", jsonObjects.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void setPosterSize(JSONArray jsonObjects){
        for (int i = 0; i < jsonObjects.length(); i++) {
            try {
                posterSizes.add(jsonObjects.getString(i));
//                Log.d("setPosterSize", jsonObjects.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void setProfileSizes(JSONArray jsonObjects){
        for (int i = 0; i < jsonObjects.length(); i++) {
            try {
                profileSizes.add(jsonObjects.getString(i));
//                Log.d("setProfileSizes", jsonObjects.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void setStillSizes(JSONArray jsonObjects){
        for (int i = 0; i < jsonObjects.length(); i++) {
            try {
                stillSizes.add(jsonObjects.getString(i));
//                Log.d("setStillSizes", jsonObjects.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
