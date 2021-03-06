package com.example.lily.proj02_shared;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Message;
import android.util.Base64;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;

/**
 * Created by lily on 3/3/16.
 */
public class MessageContainer implements Serializable {
    private District d;
    private byte[][] img;
    public MessageContainer(District d, Context c)   {
        this.d = d;
        byte[][] img = new byte[d.getRepresentatives().length][];
        for (int i = 0; i < d.getRepresentatives().length; i++)    {
            ImageDownloader id = new ImageDownloader();
            ByteArrayOutputStream imageStream = new ByteArrayOutputStream();
            Bitmap b = ((BitmapDrawable)
                    id.getImageFromUrl(d.getRepresentatives()[i].getImageUrl())).getBitmap();

            float densityMultiplier = c.getResources().getDisplayMetrics().density;

            int h = (int) (320 * densityMultiplier);
            int w = (int)((h * b.getWidth()) / ((double) b.getHeight()));

            b = Bitmap.createScaledBitmap(b,w, h, true);
            b.compress(Bitmap.CompressFormat.JPEG, 30, imageStream);
            img[i] = imageStream.toByteArray();
        }
        this.img = img;
    }

    public MessageContainer(District d, byte[][] img)    {
        this.d = d;
        this.img = img;
    }

    public District getDistricts()    {
        return d;
    }
    public byte[][] getImages()  {
        return img;
    }
    public static void sendMessage(MessageContainer m, Context c, Intent i)   {
        ObjectOutputStream oStream;
        try {
            ByteArrayOutputStream boStream = new ByteArrayOutputStream();
            oStream = new ObjectOutputStream(boStream);
            oStream.writeObject(m);
            oStream.flush();
            i.putExtra("path", "District");
            i.putExtra("data", Base64.encodeToString(boStream.toByteArray(), Base64.DEFAULT));
            System.out.println("Sending");
            c.startService(i);
        } catch (Exception e)   {
            System.out.println(e.getMessage());
        }
    }
}
