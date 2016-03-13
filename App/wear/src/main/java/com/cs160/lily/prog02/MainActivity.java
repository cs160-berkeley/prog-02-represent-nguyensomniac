package com.cs160.lily.prog02;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Message;
import android.support.wearable.view.DotsPageIndicator;
import android.support.wearable.view.GridViewPager;
import android.support.wearable.view.WatchViewStub;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.lily.proj02_shared.District;
import com.example.lily.proj02_shared.MessageContainer;
import com.example.lily.proj02_shared.Politician;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends Activity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        mAccel = 0.00f;
        mAccelCurrent = ((Global)getApplication()).getLastAcceleration();
        sendIntent = new Intent(getBaseContext(), WatchToPhoneService.class);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        MessageContainer messageContainer;
        if (extras == null) { return; }
        String data = extras.getString("district");
        if (data == null) { return; }
        ByteArrayInputStream biStream = new ByteArrayInputStream(
                Base64.decode(data.getBytes(), Base64.DEFAULT));
        try {
            ObjectInputStream oiStream = new ObjectInputStream(biStream);
            messageContainer = (MessageContainer) oiStream.readObject();
        } catch (Exception e)   {
            System.out.println(e.getMessage());
            return;
        }
        final GridViewPager pager = (GridViewPager)findViewById(R.id.pager);
        pager.setAdapter(new PoliticianGridPageAdapter(this, getFragmentManager(), messageContainer));
        pager.bringToFront();
    }
    private Intent sendIntent;

    /*From http://stackoverflow.com/questions/2317428/android-i-want-to-shake-it*/
    private SensorManager mSensorManager;
    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;

    private final SensorEventListener mSensorListener = new SensorEventListener() {

        public void onSensorChanged(SensorEvent se) {
            float x = se.values[0];
            float y = se.values[1];
            float z = se.values[2];
            mAccelLast = ((Global)getApplication()).getLastAcceleration();
            mAccelCurrent = (float) Math.sqrt((double) (x*x + y*y + z*z));
            ((Global)getApplication()).setLastAcceleration(mAccelCurrent);
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta;
            if (mAccel > 12 && delta > .5) {
                System.out.println(delta);
                System.out.println(mAccelLast);
                System.out.println(mAccelCurrent);
                sendIntent.putExtra("path", "/RANDOM");
                sendIntent.putExtra("data", "");
                startService(sendIntent);
            }

        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(mSensorListener);
        super.onPause();
    }
}
