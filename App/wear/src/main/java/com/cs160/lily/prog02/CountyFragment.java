package com.cs160.lily.prog02;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lily.proj02_shared.County;
import com.example.lily.proj02_shared.Politician;

/**
 * Created by lily on 3/3/16.
 */
public class CountyFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)   {
        Bundle bundle = getArguments();
        County currentCounty = (County)bundle.getSerializable("county");
        View view = inflater.inflate(R.layout.county_view, container, false);
        TextView countyName = (TextView) view.findViewById(R.id.county_name);
        TextView romneyVote = (TextView) view.findViewById(R.id.county_romney);
        TextView obamaVote = (TextView) view.findViewById(R.id.county_obama);
        countyName.setText(currentCounty.getName());
        romneyVote.setText(Integer.toString(currentCounty.getRomney()));
        obamaVote.setText(Integer.toString(currentCounty.getObama()));
        return view;
    }
}
