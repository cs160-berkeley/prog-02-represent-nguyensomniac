package com.example.lily.proj02_shared;

import android.content.Context;
import android.content.Intent;
import android.util.Base64;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;

/**
 * Created by lily on 2/28/16.
 */

public class District implements Serializable {
    public District(State state, int[] districtNums, Politician[] representatives, County[] counties) {
        this.state = state;
        this.districtNums = districtNums;
        this.representatives = representatives;
        this.counties = counties;
    }
    public String getDistrictName()    {
        String districtList = "";
        for (int i = 0; i < districtNums.length; i++)   {
            districtList += Integer.toString(districtNums[i]);
            if (i + 1 != districtNums.length)   {
                districtList += ", ";
            }
        }
        return state.name() + " district " + districtList;
    }
    public State getState() {
        return state;
    }
    public int[] getDistrictNums() {
        return districtNums;
    }
    public Politician[] getRepresentatives()   {
        return representatives;
    }
    public County[] getCounties() {
        return counties;
    }

    public static District resultsFromLatLon(double lat, double lon, Intent i, Context c) {
        County countyFromLatLon = County.getCountyFromLatLon(lat, lon, i, c);
        if (countyFromLatLon == null && i.getStringExtra("city") == null)   {
            return null;
        }
        try {
            JsonObject congresspeople = Ion.with(c)
                .load("https://congress.api.sunlightfoundation.com/legislators/locate")
                .addQuery("latitude", Double.toString(lat))
                .addQuery("longitude", Double.toString(lon))
                .addQuery("apikey", "56ec116c6eeb4a09811323a2bf36a0c5")
                .asJsonObject()
                .get();
            JsonArray congressList = congresspeople.getAsJsonArray("results");
            String twitterAccess = getTwitterAccessToken(c);
            return fillCongressInfo(congressList, countyFromLatLon, twitterAccess, c);
        } catch (Exception e)    {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public static District resultsFromZip(String zipCode, Intent i, Context c)  {
        System.out.println(zipCode);
        County countyFromZip = County.getCountyFromZip(Integer.parseInt(zipCode), i, c);
        try {
            JsonObject congresspeople = Ion.with(c)
                    .load("https://congress.api.sunlightfoundation.com/legislators/locate")
                    .addQuery("zip", zipCode)
                    .addQuery("apikey", "56ec116c6eeb4a09811323a2bf36a0c5")
                    .asJsonObject()
                    .get();
            JsonArray congressList = congresspeople.getAsJsonArray("results");
            String twitterAccess = getTwitterAccessToken(c);
            return fillCongressInfo(congressList, countyFromZip, twitterAccess, c);
        } catch (Exception e)   {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public static District fillCongressInfo(JsonArray reps, County county, String twitterAccess, Context c)   {
        Politician[] politicians = new Politician[reps.size()];
        State s;
        ArrayList<Integer> districtNums = new ArrayList<Integer>();
        for (int i = 0; i < reps.size(); i++) {
            JsonObject p = reps.get(i).getAsJsonObject();
            final CountDownLatch apiNum = new CountDownLatch(3);
            Future<JsonObject> tweets = null;
            Future<JsonObject> committees;
            Future<JsonObject> bills;
            String repId = p.getAsJsonPrimitive("bioguide_id").getAsString();
            String twitterName = null;
            try {
               twitterName = p.getAsJsonPrimitive("twitter_id").getAsString();
            } catch (ClassCastException _)  {}
            if (twitterName != null)   {
                tweets = Ion.with(c)
                        .load("https://api.twitter.com/1.1/users/show.json")
                        .setHeader("Authorization", "Bearer " + twitterAccess)
                        .addQuery("screen_name", twitterName)
                        .asJsonObject()
                        .setCallback(new FutureCallback<JsonObject>() {
                            @Override
                            public void onCompleted(Exception e, JsonObject result) {
                                System.out.println(result.toString());
                                apiNum.countDown();
                            }
                        });
            }
            committees = Ion.with(c)
                    .load("https://congress.api.sunlightfoundation.com/committees")
                    .addQuery("member_ids", repId)
                    .addQuery("apikey", "56ec116c6eeb4a09811323a2bf36a0c5")
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            System.out.println(result.toString());
                            apiNum.countDown();
                        }
                    });
            bills = Ion.with(c)
                    .load("https://congress.api.sunlightfoundation.com/bills")
                    .addQuery("sponsor_id", repId)
                    .addQuery("apikey", "56ec116c6eeb4a09811323a2bf36a0c5")
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            System.out.println(result.toString());
                            apiNum.countDown();
                        }
                    });
            apiNum.countDown();
            try {
                JsonObject tweetObj = (tweets != null) ? tweets.get() : null;
                JsonObject committeeObj = committees.get();
                JsonObject billObj = bills.get();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                String name = p.getAsJsonPrimitive("first_name").getAsString()
                        + " " + p.getAsJsonPrimitive("last_name").getAsString();
                String imageUrl = (tweetObj != null) ?
                        tweetObj.get("profile_image_url").getAsString() : "http://www.billboard.com/files/media/frank-ocean-650px.jpg";
                imageUrl = imageUrl.replace("_normal", "");
                Title title = Title.valueOf(p.getAsJsonPrimitive("title").getAsString().toUpperCase());
                Party party = Party.valueOf(p.getAsJsonPrimitive("party").getAsString().toUpperCase());
                State state = State.valueOf(p.getAsJsonPrimitive("state").getAsString().toUpperCase());
                s = state;
                String website = p.getAsJsonPrimitive("website").getAsString();
                String email = p.getAsJsonPrimitive("oc_email").getAsString();
                String twitter = p.getAsJsonPrimitive("twitter_id").getAsString();
                String lastTweet = (tweetObj != null) ?
                        tweetObj.get("status").getAsJsonObject().getAsJsonPrimitive("text").getAsString() : "";
                Calendar start = Calendar.getInstance();
                start.setTime(df.parse(p.getAsJsonPrimitive("term_start").getAsString()));
                int startYear = start.get(Calendar.YEAR);
                Calendar end = Calendar.getInstance();
                end.setTime(df.parse(p.getAsJsonPrimitive("term_end").getAsString()));
                int endYear = end.get(Calendar.YEAR);
                Committee[] committeeList = Committee.parseCommittees(committeeObj);
                Bill[] billList = Bill.parseBills(billObj);
                try {
                    int districtNum = p.getAsJsonPrimitive("district").getAsInt();
                    districtNums.add(districtNum);
                    Representative rObj = new Representative(name, imageUrl, title, party, state, website, email,
                            twitter, lastTweet, startYear, endYear, committeeList, billList, districtNum);
                    politicians[i] = rObj;
                } catch (ClassCastException _)   {
                    Politician pObj = new Politician(name, imageUrl, title, party, state, website, email, twitter,
                            lastTweet, startYear, endYear, committeeList, billList);
                    politicians[i] = pObj;
                }
            } catch (Exception e)   {
                System.out.print(e.getMessage());
                e.printStackTrace();
            }
        }
        if (politicians.length == 0)    {
            return null;
        } else  {
            s = politicians[0].getState();
            County[] counties = {county};
            int[] districtArray = new int[districtNums.size()];
            for (int i = 0; i < districtNums.size(); i++)  {
                districtArray[i] = districtNums.get(i);
            }
            return new District(s, districtArray, politicians, counties);
        }
    }

    private static String getTwitterAccessToken(Context c)   {
        String keys = "ONfD60i3n5uq1RXmHN0rsRMGJ:Z4iMsLCqIz7Us2CTy4zDQerwzSmdCrb3eFVqF19VCFyMuCjq2X";
        String encoded  = Base64.encodeToString(keys.getBytes(), Base64.NO_WRAP);
        try {
            JsonObject access = Ion.with(c)
                    .load("https://api.twitter.com/oauth2/token")
                    .setHeader("Authorization", "Basic " + encoded)
                    .setHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8")
                    .setBodyParameter("grant_type", "client_credentials")
                    .asJsonObject()
                    .get();
            return access.getAsJsonPrimitive("access_token").getAsString();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private State state;
    private int[] districtNums;
    private Politician[] representatives;
    private County[] counties;

}
