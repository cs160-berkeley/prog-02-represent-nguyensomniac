package com.cs160.lily.prog02;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by lily on 3/2/16.
 */
public class ContactFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.representative_contact, container, false);
        TextView politicianEmail = (TextView)v.findViewById(R.id.politician_email);
        TextView politicianWebsite = (TextView)v.findViewById(R.id.politician_website);
        Bundle data = this.getArguments();
        politicianEmail.setText(data.getString("email"));
        politicianWebsite.setText(data.getString("website"));
        return v;
    }
}
