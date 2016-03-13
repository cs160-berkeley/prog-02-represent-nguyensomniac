package com.cs160.lily.prog02;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
        if (currentCounty == null)  {
            return null;
        }
        View view = inflater.inflate(R.layout.county_view, container, false);
        TextView countyName = (TextView) view.findViewById(R.id.county_name);
        TextView romneyVote = (TextView) view.findViewById(R.id.county_romney);
        TextView obamaVote = (TextView) view.findViewById(R.id.county_obama);
        LinearLayout.LayoutParams romneyActiveBar = (LinearLayout.LayoutParams) view.findViewById(R.id.romney_active).getLayoutParams();
        LinearLayout.LayoutParams romneyInactiveBar = (LinearLayout.LayoutParams) view.findViewById(R.id.romney_inactive).getLayoutParams();
        LinearLayout.LayoutParams obamaActiveBar = (LinearLayout.LayoutParams) view.findViewById(R.id.obama_active).getLayoutParams();
        LinearLayout.LayoutParams obamaInactiveBar = (LinearLayout.LayoutParams) view.findViewById(R.id.obama_inactive).getLayoutParams();

        romneyActiveBar.weight = currentCounty.getRomney() / 2;
        romneyInactiveBar.weight = (float).5 - (currentCounty.getRomney() / 2);
        obamaActiveBar.weight = currentCounty.getObama() / 2;
        obamaInactiveBar.weight = (float).5 - (currentCounty.getObama() / 2);

        view.findViewById(R.id.romney_active).setLayoutParams(romneyActiveBar);
        view.findViewById(R.id.romney_inactive).setLayoutParams(romneyInactiveBar);
        view.findViewById(R.id.obama_active).setLayoutParams(obamaActiveBar);
        view.findViewById(R.id.obama_inactive).setLayoutParams(obamaInactiveBar);

        countyName.setText(currentCounty.getName());
        romneyVote.setText(Integer.toString((int)currentCounty.getRomney()) + "%");
        obamaVote.setText(Integer.toString((int)currentCounty.getObama()) + "%");
        return view;
    }
}
