package com.cs160.lily.prog02;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.example.lily.proj02_shared.District;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        LayoutInflater inflater = getLayoutInflater();
        LinearLayout repWrapper = (LinearLayout) findViewById(R.id.districts_wrapper);
        District[] districts = (District[])getIntent().getSerializableExtra("DISTRICTS");
        DistrictAdapter adapter = new DistrictAdapter(getBaseContext(), R.layout.district_list, districts);
        repWrapper.removeAllViews();
        for (int i = 0; i < adapter.getCount(); i++)    {
            repWrapper.addView(adapter.getView(i, null, repWrapper));
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
