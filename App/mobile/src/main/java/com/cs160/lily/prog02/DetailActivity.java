package com.cs160.lily.prog02;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lily.proj02_shared.Politician;

import java.io.InputStream;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Politician p = (Politician)this.getIntent().getSerializableExtra("politician");
        CircleImageView detailPhoto = (CircleImageView)findViewById(R.id.detail_photo);
        TextView detailName = (TextView)findViewById(R.id.detail_name);
        TextView partyName = (TextView)findViewById(R.id.detail_party);
        GradientDrawable partyIcon = (GradientDrawable)findViewById(R.id.detail_party_symbol).getBackground();
        int partyColor = ContextCompat.getColor(
                this, getResources().getIdentifier(p.getParty().toString(), "color", getPackageName()));
        detailPhoto.setBorderColor(partyColor);
        detailName.setText(p.getTitle() + " " + p.getName());
        partyName.setText(p.getAffiliation());
        partyName.setTextColor(partyColor);
        partyIcon.setColor(partyColor);
        try {
            DownloadImageTask downloader = new DownloadImageTask();
            Drawable d = downloader.execute(p.getImageUrl()).get();
            System.out.println(detailPhoto);
            detailPhoto.setImageDrawable(d);
        } catch (Exception e)   {
            System.out.println(e.toString());
        }
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
