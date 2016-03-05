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

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.nio.charset.StandardCharsets;

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
            District locationDistrict;
            Politician representative = TestData.createTestPolitician(2);
            Politician sen1 = TestData.createTestPolitician(1);
            Politician sen2 = TestData.createTestPolitician(4);
            Politician[] representatives = {sen1, sen2, representative};
            County[] counties = {new County("Fresno County", 53, 47), new County("Alameda County", 80, 20)};
            locationDistrict = new District(State.AZ, 12, representatives, counties);
            District[] district = {locationDistrict};
            MessageContainer m = new MessageContainer(district, this);
            MessageContainer.sendMessage(m, this, sendRandomService);
            intent.putExtra("DISTRICTS", district);
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
}
