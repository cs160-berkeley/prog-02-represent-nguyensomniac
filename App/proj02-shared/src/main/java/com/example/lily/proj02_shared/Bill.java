package com.example.lily.proj02_shared;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by lily on 2/28/16.
 */
public class Bill implements Serializable {
    Bill(String title, Date date)   {
        this.title = title;
        this.date = date;
    }
    public String getTitle() {
        return title;
    }
    public Date getDate() {
        return date;
    }
    private Date date;
    private String title;
}

