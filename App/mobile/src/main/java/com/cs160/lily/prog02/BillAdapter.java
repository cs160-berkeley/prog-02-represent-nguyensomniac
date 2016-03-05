package com.cs160.lily.prog02;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lily.proj02_shared.Bill;

import java.text.SimpleDateFormat;

/**
 * Created by lily on 3/4/16.
 */
public class BillAdapter extends ArrayAdapter<Bill> {
    private Context context;
    private int layoutId;
    private Bill[] values;
    public BillAdapter(Context c, int layoutResourceId, Bill[] bills)   {
        super(c, layoutResourceId, bills);
        context = c;
        layoutId = layoutResourceId;
        values = bills;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)   {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View billView = inflater.inflate(layoutId, parent, false);
        if (billView != null) {
            Bill b = values[position];
            TextView billName = (TextView)billView.findViewById(R.id.bill_name);
            TextView billDate = (TextView)billView.findViewById(R.id.bill_date);
            SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            billName.setText(b.getTitle());
            billDate.setText(df.format(b.getDate()));
        }
        return billView;
    }
}
