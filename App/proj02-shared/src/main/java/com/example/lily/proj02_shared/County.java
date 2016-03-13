package com.example.lily.proj02_shared;

import android.content.Context;
import android.content.Intent;
import android.util.JsonReader;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.util.concurrent.Future;

/**
 * Object for county names and 2012 election data.
 */
public class County implements Serializable {
    public County(String name, float obama, float romney)  {
        this.name = name;
        this.obama = obama;
        this.romney = romney;
    }
    public String getName() {
        return name;
    }
    public float getObama()    {
        return obama;
    }
    public float getRomney()   {
        /* bindas fulla women */
        return romney;
    }

    public static County getCountyFromLatLon(double lat, double lon, Intent intent, Context c) {
        County newCounty;
        String countyName = null;
        JsonParser parser = new JsonParser();
        JsonArray electionData = null;
        try {
            InputStream is = c.getResources().openRawResource(R.raw.election);
            BufferedReader r = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            electionData = parser.parse(r).getAsJsonArray();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        Future<JsonObject> locationFuture = Ion.with(c)
                .load("https://maps.googleapis.com/maps/api/geocode/json")
                .addQuery("latlng", Double.toString(lat) + "," + Double.toString(lon))
                .asJsonObject();
        try {
            JsonArray locComponents = locationFuture.get().getAsJsonArray("results").get(0)
                    .getAsJsonObject().getAsJsonArray("address_components");
            String state = null;
            for (int i = 0; i < locComponents.size(); i++) {
                String name = locComponents.get(i).getAsJsonObject().getAsJsonPrimitive("long_name").getAsString();
                String type = locComponents.get(i).getAsJsonObject()
                        .getAsJsonArray("types").get(0).getAsString();
                if (name != null && type != null && type.equals("administrative_area_level_1")) {
                    state = name;
                } else if (name != null && type != null && type.equals("administrative_area_level_2")) {
                    countyName = name;
                } else if (name != null && (type.equals("locality") || type.equals("sublocality"))) {
                    intent.putExtra("city", name);
                }
            }
            if (state != null && state.equals("Alaska")) {
                countyName = state;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return null;
        }
        if (countyName == null || electionData == null) {
            return null;
        } else {
            for (int i = 0; i < electionData.size(); i++) {
                String electionCounty = electionData.get(i).getAsJsonObject().get("county-name").getAsString();
                if (electionCounty.equals(countyName)) {
                    JsonObject countyObj = electionData.get(i).getAsJsonObject();
                    float romney = countyObj.get("romney-percentage").getAsFloat();
                    float obama = countyObj.get("obama-percentage").getAsFloat();
                    return new County(countyName, obama, romney);
                }
            }
        }
        return null;
    }


    /** Puts the city in the intent extras  */
    public static County getCountyFromZip(int zip, Intent intent, Context c) {
        County newCounty;
        String countyName = null;
        JsonParser parser = new JsonParser();
        JsonArray electionData = null;
        try {
            InputStream is = c.getResources().openRawResource(R.raw.election);
            BufferedReader r = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            electionData = parser.parse(r).getAsJsonArray();
        } catch (Exception e)   {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        Future<JsonObject> locationFuture = Ion.with(c)
            .load("https://maps.googleapis.com/maps/api/geocode/json")
            .addQuery("components", "country:US|postal_code:" + Integer.toString(zip))
            .asJsonObject();
        try {
            JsonObject loc = locationFuture.get();
            if (loc.getAsJsonArray("results").size() == 0)  {
                return null;
            }
            JsonArray locComponents = locationFuture.get().getAsJsonArray("results").get(0)
                    .getAsJsonObject().getAsJsonArray("address_components");
            String state = null;
            for (int i = 0; i < locComponents.size(); i++)  {
                String name = locComponents.get(i).getAsJsonObject().getAsJsonPrimitive("long_name").getAsString();
                String type = locComponents.get(i).getAsJsonObject()
                        .getAsJsonArray("types").get(0).getAsString();
                if (name != null && type != null && type.equals("administrative_area_level_1")) {
                    state = name;
                } else if (name != null && type != null && type.equals("administrative_area_level_2"))   {
                    countyName = name;
                } else if (name != null && (type.equals("locality") || type.equals("sublocality"))) {
                    intent.putExtra("city", name);
                }
            }
            if (state != null && state.equals("Alaska")) {
                countyName = state;
            }
        } catch (Exception e)   {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return null;
        }
        if (countyName == null || electionData == null) {
            return null;
        } else {
            for (int i = 0; i < electionData.size(); i++)   {
                String electionCounty = electionData.get(i).getAsJsonObject().get("county-name").getAsString();
                if (electionCounty.equals(countyName))  {
                    JsonObject countyObj = electionData.get(i).getAsJsonObject();
                    float romney = countyObj.get("romney-percentage").getAsFloat();
                    float obama = countyObj.get("obama-percentage").getAsFloat();
                    return new County(countyName, obama, romney);
                }
            }
        }
        return null;
    }
    private String name;
    /* The Romney and Obama numbers are stored as percentages */
    private float obama;
    private float romney;
}
