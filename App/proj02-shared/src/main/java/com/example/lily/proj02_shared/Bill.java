package com.example.lily.proj02_shared;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lily on 2/28/16.
 */
public class Bill implements Serializable {
    Bill(String title, Date date)   {
        this.title = title;
        this.date = date;
    }
    public static Bill[] parseBills(JsonObject obj) {
        JsonArray billResults = obj.getAsJsonArray("results");
        int max = (billResults.size() > MAX_BILLS) ? MAX_BILLS : billResults.size();
        Bill[] bills = new Bill[max];
        for (int i = 0; i < max; i++)   {
            JsonObject r = billResults.get(i).getAsJsonObject();
            String name;
            try {
                name = r.getAsJsonPrimitive("short_title").getAsString();
            } catch (ClassCastException e)  {
                name = r.getAsJsonPrimitive("official_title").getAsString();
            }
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date introducedDate;
            try {
                introducedDate = df.parse(r.getAsJsonPrimitive("introduced_on").getAsString());
            } catch (ParseException e) {
                System.out.println(e.getMessage());
                return null;
            }
            Bill b = new Bill(name, introducedDate);
            bills[i] = b;
        }
        return bills;
    }
    public String getTitle() {
        return title;
    }
    public Date getDate() {
        return date;
    }
    private Date date;
    private String title;
    private static final int MAX_BILLS = 10;
}

