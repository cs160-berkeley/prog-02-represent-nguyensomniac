package com.cs160.lily.prog02;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lily.proj02_shared.Committee;
import com.example.lily.proj02_shared.Politician;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

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
        TextView committees = (TextView)findViewById(R.id.detail_committees);
        LinearLayout billContainer = (LinearLayout)findViewById(R.id.detail_bills_container);
        BillAdapter billAdapter = new BillAdapter(this, R.layout.bill_list, p.getBills());
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
            detailPhoto.setImageDrawable(d);
        } catch (Exception e)   {
            System.out.println(e.toString());
        }
        TextView startDate = (TextView)findViewById(R.id.detail_start_date);
        TextView endDate = (TextView)findViewById(R.id.detail_end_date);
        startDate.setText(Integer.toString(p.getStartDate()));
        endDate.setText(Integer.toString(p.getEndDate()));
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        float percentComplete = ((float)currentYear - p.getStartDate()) / ((float)p.getEndDate() - p.getStartDate());
        System.out.println(percentComplete);
        LinearLayout.LayoutParams timeStarted = (LinearLayout.LayoutParams)findViewById(R.id.detail_start_path).getLayoutParams();
        timeStarted.weight = percentComplete;
        findViewById(R.id.detail_start_path).setLayoutParams(timeStarted);
        LinearLayout.LayoutParams timeEnd = (LinearLayout.LayoutParams)findViewById(R.id.detail_end_path).getLayoutParams();
        timeEnd.weight = 1 - percentComplete;
        findViewById(R.id.detail_end_path).setLayoutParams(timeEnd);

        if (p.getCommittees().length == 0)  {
            committees.setText("None");
        } else {
            ArrayList<String> committeeList = new ArrayList<String>();
            ArrayList<String> subCommitteeList = new ArrayList<String>();
            Committee[] c = p.getCommittees();
            for (int i = 0; i < c.length; i++)  {
                if (c[i].getIfCommittee())  {
                    committeeList.add(c[i].getName());
                } else  {
                    subCommitteeList.add(c[i].getName());
                }
            }
            String finalCommittees = "";
            if (committeeList.size() > 0)   {
                finalCommittees += committeeList.get(0);
                for (int i = 1; i < committeeList.size(); i++)  {
                    finalCommittees += ", " + committeeList.get(i);
                }
            }
            committees.setText(finalCommittees);
        }
        billContainer.removeAllViews();
        for (int i = 0; i < billAdapter.getCount(); i++)    {
            billContainer.addView(billAdapter.getView(i, null, billContainer));
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

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
