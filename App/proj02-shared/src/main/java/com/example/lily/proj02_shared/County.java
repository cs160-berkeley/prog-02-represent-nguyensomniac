package com.example.lily.proj02_shared;

import java.io.Serializable;

/**
 * Created by lily on 3/3/16.
 */
public class County implements Serializable {
    public County(String name, int obama, int romney)  {
        this.name = name;
        this.obama = obama;
        this.romney = romney;
    }
    public String getName() {
        return name;
    }
    public int getObama()    {
        return obama;
    }
    public int getRomney()   {
        /* bindas fulla women */
        return romney;
    }
    private String name;
    private int obama;
    private int romney;
}
