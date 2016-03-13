package com.cs160.lily.prog02;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lily.proj02_shared.Politician;
import com.example.lily.proj02_shared.Representative;

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
            final Politician p = values[position];
            TextView politicianName = (TextView)politicianView.findViewById(R.id.politician_name);
            TextView politicianDistrict = (TextView)politicianView.findViewById(R.id.politician_district);
            GradientDrawable partyIcon = (GradientDrawable)politicianView.findViewById(R.id.politician_party_symbol).getBackground();
            CircleImageView politicianPhoto = (CircleImageView)politicianView.findViewById(R.id.politician_photo);
            TextView politicianParty = (TextView)politicianView.findViewById(R.id.politician_party);
            int partyColor = context.getResources().getIdentifier(p.getParty().toString(), "color", context.getPackageName());
            politicianPhoto.setBorderColor(ContextCompat.getColor(context, partyColor));
            politicianName.setText(p.getTitle() + " " + p.getName());
            politicianParty.setText(p.getAffiliation());
            politicianParty.setTextColor(ContextCompat.getColor(context, partyColor));
            try {
                politicianDistrict.setText("District " + ((Representative) p).getDistrictNum());
            } catch (ClassCastException e)  {
                politicianDistrict.setMaxHeight(0);
            }
            partyIcon.setColor(ContextCompat.getColor(context, partyColor));
            FragmentTabHost politicianTabHost = (FragmentTabHost) politicianView.findViewById(R.id.politician_tab);
            Bundle contactData = new Bundle();
            Bundle tweetData = new Bundle();
            contactData.putString("website", p.getWebsite());
            contactData.putString("email", p.getEmail());
            tweetData.putString("twitter", p.getTwitter());
            tweetData.putString("lastTweet", p.getLastTweet());
            int tabId = View.generateViewId();
            politicianView.findViewById(R.id.politician_tab_content).setId(tabId);
            politicianTabHost.setup(
                    context, ((FragmentActivity)context).getSupportFragmentManager(), tabId);
            politicianTabHost.addTab(politicianTabHost.newTabSpec("tab1" + Integer.toString(position)).setIndicator("Contact", null),
                    ContactFragment.class, contactData);
            politicianTabHost.addTab(politicianTabHost.newTabSpec("tab2" + Integer.toString(position)).setIndicator("Tweets", null),
                    TweetFragment.class, tweetData);
            for (int i = 0; i < politicianTabHost.getTabWidget().getChildCount(); i++) {
                politicianTabHost.getTabWidget().getChildAt(i).getLayoutParams().height /= 2.7;

            }
            try {
                DownloadImageTask downloader = new DownloadImageTask();
                Drawable d = downloader.execute(p.getImageUrl()).get();
                politicianPhoto.setImageDrawable(d);
            } catch (Exception e)   {
                System.out.println(e.toString());
            }
            Button btn = (Button)politicianView.findViewById(R.id.politician_detail_btn);
            btn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("politician", p);
                    context.startActivity(intent);
                }
            });
        }
        return politicianView;
    }

    /*Returns the image at a given URL.*/
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
