package com.cs160.lily.prog02;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lily.proj02_shared.District;

/**
 * Created by lily on 3/1/16.
 */
public class DistrictAdapter extends ArrayAdapter<District> {
    private final Context context;
    private District[] values;
    private int layoutId;
    public DistrictAdapter(Context c, int layoutResourceId, District[] districts)   {
        super(c, layoutResourceId, districts);
        context = c;
        layoutId = layoutResourceId;
        values = districts;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)   {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View districtView = inflater.inflate(layoutId, parent, false);
        if (districtView != null) {
            District d = values[position];
            TextView districtName = (TextView)districtView.findViewById(R.id.district_name);
            LinearLayout wrapper = (LinearLayout)districtView.findViewById(R.id.representatives_wrapper);
            districtName.setText(d.getDistrictName());
            wrapper.removeAllViews();
            RepAdapter r = new RepAdapter(context, R.layout.representative, d.getRepresentatives());
            for (int i = 0; i < r.getCount(); i++)  {
                wrapper.addView(r.getView(i, convertView, wrapper));
            }
        }
        return districtView;
    }
}
