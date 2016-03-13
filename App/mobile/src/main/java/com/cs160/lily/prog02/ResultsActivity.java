package com.cs160.lily.prog02;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lily.proj02_shared.District;

import org.w3c.dom.Text;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ResultsActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        TextView resultsText = (TextView)findViewById(R.id.results_title);
        LinearLayout repWrapper = (LinearLayout) findViewById(R.id.districts_wrapper);
        District district = (District)getIntent().getSerializableExtra("DISTRICTS");
        resultsText.setText("Results for " + this.getIntent().getStringExtra("city") + ", " +
            district.getRepresentatives()[0].getState().name());
        RepAdapter adapter = new RepAdapter(this, R.layout.representative, district.getRepresentatives());
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
