package com.cs160.lily.prog02;

import android.app.Application;
import android.hardware.SensorManager;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by lily on 3/4/16.
 */
public class Global extends Application {
    private float lastAcceleration = SensorManager.GRAVITY_EARTH;
    public float getLastAcceleration()    {
        return lastAcceleration;
    }
    public void setLastAcceleration(float i)  {
        lastAcceleration = i;
    }
    @Override
    public void onCreate()  {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
            .setDefaultFontPath("fonts/mark_simonson_-_proxima_nova_regular-webfont.ttf")
            .setFontAttrId(R.attr.fontPath)
            .build());
    }

}
