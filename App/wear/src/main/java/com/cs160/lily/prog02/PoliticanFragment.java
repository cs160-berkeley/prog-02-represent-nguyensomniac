package com.cs160.lily.prog02;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lily.proj02_shared.Politician;
import com.example.lily.proj02_shared.ImageDownloader;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

/**
 * Created by lily on 3/3/16.
 */
public class PoliticanFragment extends Fragment {
    private  Politician p;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)   {
        Bundle bundle = getArguments();
        p = (Politician)bundle.getSerializable("politician");
        byte[] d = (byte[])bundle.getByteArray("image");
        View pView =  inflater.inflate(R.layout.politician_view, container, false);
        LinearLayout politicianImage = (LinearLayout)pView.findViewById(R.id.politician_image);
        politicianImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switchIntent = new Intent(getActivity().getBaseContext(), WatchToPhoneService.class);
                ObjectOutputStream oStream;
                try {
                    ByteArrayOutputStream boStream = new ByteArrayOutputStream();
                    oStream = new ObjectOutputStream(boStream);
                    oStream.writeObject(p);
                    oStream.flush();
                    switchIntent.putExtra("path", "/REPRESENTATIVE");
                    switchIntent.putExtra("data", Base64.encodeToString(boStream.toByteArray(), Base64.DEFAULT));
                    getActivity().getBaseContext().startService(switchIntent);
                } catch (Exception e)   {
                    System.out.println(e.getMessage());
                }
            }
        });
        int partyColor = getResources().getIdentifier(
                p.getParty().toString(), "color", getActivity().getApplicationContext().getPackageName());
        TextView politicianName = (TextView)pView.findViewById(R.id.politician_name);
        GradientDrawable partyIcon =
                (GradientDrawable)pView.findViewById(R.id.politician_party_symbol).getBackground();
        TextView politicianParty =
                (TextView)pView.findViewById(R.id.politician_party);
        politicianImage.setBackground(new BitmapDrawable(
                getActivity().getResources(), BitmapFactory.decodeByteArray(d, 0, d.length)));

        politicianName.setText(p.getName());
        politicianParty.setText(p.getAffiliation());
        politicianParty.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), partyColor));
        partyIcon.setColor(ContextCompat.getColor(getActivity().getApplicationContext(), partyColor));
        return pView;
    }

}
