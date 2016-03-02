package com.cs160.lily.prog02;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lily.proj02_shared.Politician;

import java.io.InputStream;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Class that converts a list of politicians to a view fragment.
 */
public class RepAdapter extends ArrayAdapter<Politician> {
    private final Context context;
    private Politician[] values;
    private int layoutId;
    public RepAdapter(Context c, int layoutResourceId, Politician[] politicians)   {
        super(c, layoutResourceId, politicians);
        context = c;
        layoutId = layoutResourceId;
        values = politicians;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)   {
        LayoutInflater inflater = LayoutInflater.from(context);
        View politicianView = inflater.inflate(layoutId, parent, false);
        if (politicianView != null) {
            Politician p = values[position];
            TextView politicianName = (TextView)politicianView.findViewById(R.id.politician_name);
            ImageView partyIcon = (ImageView)politicianView.findViewById(R.id.politician_party_symbol);
            CircleImageView politicianPhoto = (CircleImageView)politicianView.findViewById(R.id.politician_photo);
            int borderColor = context.getResources().getIdentifier(p.getParty().toString(), "color", context.getPackageName());
            politicianPhoto.setBorderColor(ContextCompat.getColor(context, borderColor));
            System.out.println(p.getParty().name());
            politicianName.setText(p.getName());
            try {
                DownloadImageTask downloader = new DownloadImageTask();
                Drawable d = downloader.execute(p.getImageUrl()).get();
                politicianPhoto.setImageDrawable(d);
            } catch (Exception e)   {
                System.out.println(e.toString());
            }
        }
        return politicianView;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Drawable>  {
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
