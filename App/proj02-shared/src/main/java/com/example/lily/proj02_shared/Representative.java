package com.example.lily.proj02_shared;

/**
 * Created by lily on 3/11/16.
 */
public class Representative extends Politician {
    public Representative(String name, String imageUrl, Title title, Party party, State state, String website, String email, String twitter,
                   String lastTweet, int startDate, int endDate, Committee[] committees, Bill[] bills, int district)    {
        super(name, imageUrl, title, party, state, website, email, twitter, lastTweet, startDate, endDate, committees, bills);
        districtNum = district;
    }
    public int getDistrictNum()  {return districtNum;}
    private int districtNum;
}
