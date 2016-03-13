package com.cs160.lily.prog02;

import android.app.Activity;
import android.content.Intent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.os.Message;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.lily.proj02_shared.County;
import com.example.lily.proj02_shared.District;
import com.example.lily.proj02_shared.MessageContainer;
import com.example.lily.proj02_shared.Politician;
import com.example.lily.proj02_shared.State;
import com.example.lily.proj02_shared.TestData;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;

import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class MainActivity extends Activity implements GoogleApiClient.ConnectionCallbacks, OnConnectionFailedListener {
    private GoogleApiClient mGoogleApiClient;
    private Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }
    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }
    /*  */
    @Override
    public void onConnected(Bundle connectionHint) {
        System.out.println(mGoogleApiClient);
        location = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        System.out.println(location);
    }
    @Override
    public void onConnectionSuspended(int cause) {}
    @Override
    public void onConnectionFailed(ConnectionResult result) {}

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
        double lat = location.getLatitude();
        double lon = location.getLongitude();
        District d = District.resultsFromLatLon(lat, lon, intent, this);
        if (d == null)  {return;}
        intent.putExtra("DISTRICTS", d);
        startActivity(intent);
        MessageContainer messageContainer = new MessageContainer(d, this);
        sendToWatch(messageContainer);
    }

    public void searchZip(View view) {
        EditText zipInput = (EditText)findViewById(R.id.main_zip);
        String zip = zipInput.getText().toString();
        if (zip.length() != 5)   {
            return;
        } else  {
            Intent intent = new Intent(this, ResultsActivity.class);
            District d = District.resultsFromZip(zip, intent, this);
            if (d == null)  {return;}
            intent.putExtra("DISTRICTS", d);
            startActivity(intent);
            MessageContainer messageContainer = new MessageContainer(d, this);
            sendToWatch(messageContainer);
        }
    }

    /* Sends a message container (a district array and an array of drawable photos) to the watch. */
    private void sendToWatch(MessageContainer m)    {
        Intent sendIntent = new Intent(getBaseContext(), PhoneToWatchService.class);
        MessageContainer.sendMessage(m, this, sendIntent);
    }
}
