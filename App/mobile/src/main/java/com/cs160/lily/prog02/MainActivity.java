package com.cs160.lily.prog02;

import android.app.Activity;
import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.lily.proj02_shared.District;
import com.example.lily.proj02_shared.Politician;
import com.example.lily.proj02_shared.State;
import com.example.lily.proj02_shared.TestData;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class MainActivity extends Activity {
    //there's not much interesting happening. when the buttons are pressed, they start
    //the PhoneToWatchService with the cat name passed in.

//    private Button mFredButton;
//    private Button mLexyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

//        mFredButton = (Button) findViewById(R.id.fred_btn);
//        mLexyButton = (Button) findViewById(R.id.lexy_btn);
//
//        mFredButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent sendIntent = new Intent(getBaseContext(), PhoneToWatchService.class);
//                sendIntent.putExtra("CAT_NAME", "Fred");
//                startService(sendIntent);
//            }
//        });
//
//        mLexyButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent sendIntent = new Intent(getBaseContext(), PhoneToWatchService.class);
//                sendIntent.putExtra("CAT_NAME", "Lexy");
//                startService(sendIntent);
//            }
//        });

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button_secondary, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void findLocation(View view) {
        Intent intent = new Intent(this, ResultsActivity.class);
        District locationDistrict;
        Politician representative = TestData.createTestPolitician(1);
        Politician sen1 = TestData.createTestPolitician(3);
        Politician sen2 = TestData.createTestPolitician(2);
        Politician[] representatives = {sen1, sen2, representative};
        locationDistrict = new District(State.CA, 20, representatives);
        District[] district = {locationDistrict};
        intent.putExtra("DISTRICTS", district);
        startActivity(intent);
    }

    public void searchZip(View view)    {
        Intent intent = new Intent(this, ResultsActivity.class);
        District locationDistrict;
        Politician representative = TestData.createTestPolitician(1);
        Politician sen1 = TestData.createTestPolitician(3);
        Politician sen2 = TestData.createTestPolitician(4);
        Politician[] representatives = {sen1, sen2, representative};
        locationDistrict = new District(State.CA, 3, representatives);
        District[] district = {locationDistrict};
        intent.putExtra("DISTRICTS", district);
        startActivity(intent);
    }
}
