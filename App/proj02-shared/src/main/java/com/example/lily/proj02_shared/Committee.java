package com.example.lily.proj02_shared;

import java.io.Serializable;

/**
 * Created by lily on 2/28/16.
 */
public class Committee implements Serializable {
    Committee(String name, String position)   {
        this.name = name;
        this.position = position;
    }
    public String getName() {
        return name;
    }
    public String getPosition() {
        return position;
    }
    private String position;
    private String name;
}
