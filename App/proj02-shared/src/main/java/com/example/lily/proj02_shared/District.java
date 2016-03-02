package com.example.lily.proj02_shared;

import java.io.Serializable;

/**
 * Created by lily on 2/28/16.
 */

public class District implements Serializable {
    public District(State state, int districtNum, Politician[] representatives) {
        if (representatives.length != REPS_PER_DISTRICT)   {
            throw new IllegalArgumentException("Must have three representatives");
        }
        this.state = state;
        this.districtNum = districtNum;
        this.representatives = representatives;
    }
    public String getDistrictName()    {
        return state.name() + " district " + Integer.toString(districtNum);
    }
    public State getState() {
        return state;
    }
    public int getDistrictNum() {
        return districtNum;
    }
    public Politician[] getRepresentatives()   {
        return representatives;
    }
    private State state;
    private int districtNum;
    private Politician[] representatives;
    private static final int REPS_PER_DISTRICT = 3;
}
