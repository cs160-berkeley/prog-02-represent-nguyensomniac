package com.example.lily.proj02_shared;

/**
 * Created by lily on 2/28/16.
 */
public enum Title {
    SEN("Senator"),
    REP("Representative");
    private String longName;
    Title(String longName)  {
        this.longName = longName;
    }
    public String getLongName() {
        return longName;
    }
    @Override
    public String toString()    {
        return name().substring(0, 1).toUpperCase() + name().substring(1).toLowerCase() + ".";
    }
}
