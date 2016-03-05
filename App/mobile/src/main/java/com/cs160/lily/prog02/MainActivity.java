package com.cs160.lily.prog02;

import android.app.Activity;
import android.content.Intent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.lily.proj02_shared.County;
import com.example.lily.proj02_shared.District;
import com.example.lily.proj02_shared.ImageDownloader;
import com.example.lily.proj02_shared.MessageContainer;
import com.example.lily.proj02_shared.Politician;
import com.example.lily.proj02_shared.State;
import com.example.lily.proj02_shared.TestData;

import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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
        Politician sen1 = TestData.createTestPolitician(2);
        Politician sen2 = TestData.createTestPolitician(3);
        Politician[] representatives = {sen1, sen2, representative};
        County[] counties = {new County("Fresno County", 53, 47), new County("Alameda County", 80, 20)};
        locationDistrict = new District(State.CA, 20, representatives, counties);
        District[] district = {locationDistrict};
        intent.putExtra("DISTRICTS", district);
        startActivity(intent);
        MessageContainer messageContainer = new MessageContainer(district, this);
        sendToWatch(messageContainer);
    }

    public void searchZip(View view) {
        Intent intent = new Intent(this, ResultsActivity.class);
        District locationDistrict;
        Politician representative = TestData.createTestPolitician(1);
        Politician sen1 = TestData.createTestPolitician(3);
        Politician sen2 = TestData.createTestPolitician(4);
        Politician[] representatives = {sen1, sen2, representative};
        County[] counties = {new County("Fresno County", 53, 47), new County("Alameda County", 80, 20)};
        locationDistrict = new District(State.CA, 3, representatives, counties);
        District[] district = {locationDistrict};
        intent.putExtra("DISTRICTS", district);
        startActivity(intent);
        MessageContainer messageContainer = new MessageContainer(district, this);
        sendToWatch(messageContainer);
    }

    /* Sends a message container (a district array and an array of drawable photos) to the watch. */
    private void sendToWatch(MessageContainer m)    {
        Intent sendIntent = new Intent(getBaseContext(), PhoneToWatchService.class);
        MessageContainer.sendMessage(m, this, sendIntent);
    }
}
