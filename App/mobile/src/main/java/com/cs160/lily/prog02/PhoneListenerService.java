package com.cs160.lily.prog02;

import android.content.Context;
import android.content.Intent;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.example.lily.proj02_shared.County;
import com.example.lily.proj02_shared.District;
import com.example.lily.proj02_shared.MessageContainer;
import com.example.lily.proj02_shared.Politician;
import com.example.lily.proj02_shared.State;
import com.example.lily.proj02_shared.TestData;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;
import com.google.gson.JsonObject;
import com.koushikdutta.ion.Ion;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Future;

/**
 * Created by joleary and noon on 2/19/16 at very late in the night. (early in the morning?)
 */
public class PhoneListenerService extends WearableListenerService {

    //   WearableListenerServices don't need an iBinder or an onStartCommand: they just need an onMessageReceieved.
    private static final String RANDOM = "/RANDOM";
    private static final String REPRESENTATIVE = "/REPRESENTATIVE";

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.d("T", "in PhoneListenerService, got: " + messageEvent.getPath());
        if( messageEvent.getPath().equalsIgnoreCase(RANDOM) ) {
            Intent intent = new Intent(this, ResultsActivity.class);
            Intent sendRandomService = new Intent(this, PhoneToWatchService.class);
            District locationDistrict = getRandomDistrict(intent, getApplicationContext());
            MessageContainer m = new MessageContainer(locationDistrict, getApplicationContext());
            MessageContainer.sendMessage(m, this, sendRandomService);
            intent.putExtra("DISTRICTS", locationDistrict);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else if (messageEvent.getPath().equalsIgnoreCase(REPRESENTATIVE)) {
            byte[] value = messageEvent.getData();
            Intent intent = new Intent(this, DetailActivity.class);
            ByteArrayInputStream biStream = new ByteArrayInputStream(
                    Base64.decode(value, Base64.DEFAULT));
            Politician p;
            try {
                ObjectInputStream oiStream = new ObjectInputStream(biStream);
                p = (Politician) oiStream.readObject();
                intent.putExtra("politician", p);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } catch (Exception e)   {
                System.out.println(e.getMessage());
                return;
            }
        } else {
            super.onMessageReceived(messageEvent);
        }
    }

    private District getRandomDistrict(Intent i, Context c)    {
        String randomZip = generateRandomZip();
        while (!isValidZip(randomZip, c))  {
            randomZip = generateRandomZip();
        }
        return District.resultsFromZip(randomZip, i, c);
    }

    private boolean isValidZip(String zip, Context c)    {
        try {
            JsonObject loc = Ion.with(c)
                    .load("https://congress.api.sunlightfoundation.com/legislators/locate")
                    .addQuery("zip", zip)
                    .addQuery("apikey", "56ec116c6eeb4a09811323a2bf36a0c5")
                    .asJsonObject()
                    .get();
            if (loc.getAsJsonArray("results").size() == 0) {
                return false;
            }
            return true;
        } catch (Exception e)   {
            return false;
        }
    }

    private String generateRandomZip()  {
        String zip = "";
        for (int i = 0; i < 5; i++) {
            zip += (int) Math.floor(Math.random() * 10);
        }
        return zip;
    }
}
