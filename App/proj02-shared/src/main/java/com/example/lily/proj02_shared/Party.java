package com.example.lily.proj02_shared;

import java.io.Serializable;

/**
 * A list of political parties in the US. Includes major parties
 * without federal representation because the two-party political system
 * will be our downfall (but this is a computer science class, not a political
 * science class, so I will refrain from commentary).
 * List from http://abcnews.go.com/Politics/party-abbreviations/story?id=10865978
 */
public enum Party implements Serializable {
    D("Democrat"),
    R("Republican"),
    I("Independent"),
    GR("Green"),
    LB("Libertarian"),
    CS("Constitution"),
    PF("Peace and Freedom"),
    MJ("Marijuana");
    private String longName;
    Party(String longName)  {
        this.longName = longName;
    }
    String getLongName()    {
        return longName;
    }
}
