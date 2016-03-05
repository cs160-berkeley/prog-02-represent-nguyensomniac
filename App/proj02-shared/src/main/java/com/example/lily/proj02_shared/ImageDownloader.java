package com.example.lily.proj02_shared;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by lily on 3/3/16.
 */
public class ImageDownloader {
    public Drawable getImageFromUrl(String imageUrl)    {
        DownloadImageTask task = new DownloadImageTask();
        try {
            return task.execute(imageUrl).get();
        } catch (Exception e)   {
            System.out.println(e.toString());
            return null;
        }
    }
    private class DownloadImageTask extends AsyncTask<String, Void, Drawable> {
        protected Drawable doInBackground(String... imageUrl) {
            try {
                URL img = new URL(imageUrl[0]);
                return Drawable.createFromStream((InputStream) img.getContent(), "src name");
            } catch (Exception e)   {
                System.out.println(e.toString());
                return null;
            }
        }
    }
}
