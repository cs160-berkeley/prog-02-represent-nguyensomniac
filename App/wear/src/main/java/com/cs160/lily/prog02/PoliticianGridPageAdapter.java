package com.cs160.lily.prog02;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.wearable.view.CardFragment;
import android.support.wearable.view.FragmentGridPagerAdapter;

import com.example.lily.proj02_shared.County;
import com.example.lily.proj02_shared.District;
import com.example.lily.proj02_shared.MessageContainer;
import com.example.lily.proj02_shared.Politician;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * Created by lily on 3/3/16.
 */
public class PoliticianGridPageAdapter extends FragmentGridPagerAdapter {
    private final Context context;
    private List rows;
    private District d;
    private byte[][] img;
    public PoliticianGridPageAdapter(Context context, FragmentManager fm, MessageContainer m)   {
        super(fm);
        this.context = context;
        d = m.getDistricts();
        img = m.getImages();
    }

    @Override
    public int getRowCount()    {
        return 2;
    }

    @Override
    public int getColumnCount(int r) {
        if (r == 0) {
            return d.getRepresentatives().length;
        } else if (r == 1)  {
            return d.getCounties().length;
        }
        return 0;
    }

    @Override
    public Fragment getFragment(int r, int c)   {
        if (r == 0) {
            Politician currentPolitician = d.getRepresentatives()[c];
            byte[] currentImage = img[c];
            PoliticanFragment pf = new PoliticanFragment();
            Bundle politicianBundle = new Bundle();
            politicianBundle.putSerializable("politician", currentPolitician);
            politicianBundle.putByteArray("image", currentImage);
            pf.setArguments(politicianBundle);
            return pf;
        } else if (r == 1)  {
            County currentCounty = d.getCounties()[c];
            CountyFragment cf = new CountyFragment();
            Bundle countyBundle = new Bundle();
            countyBundle.putSerializable("county", currentCounty);
            cf.setArguments(countyBundle);
            return cf;
        }
        return null;
    }
}
