package com.cs160.lily.prog02;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by lily on 3/2/16.
 */
public class RepresentApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //Initialize Calligraphy
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/mark_simonson_-_proxima_nova_regular-webfont.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
        getApplicationContext().setTheme(R.style.AppTheme);
    }
}
