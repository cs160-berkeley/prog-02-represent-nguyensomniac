package com.cs160.lily.prog02;

import android.app.Application;
import android.hardware.SensorManager;

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
}
