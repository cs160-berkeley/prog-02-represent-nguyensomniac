package com.example.lily.proj02_shared;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by lily on 2/27/16.
 */
public class Politician implements Serializable {
    Politician(String name, String imageUrl, Title title, Party party, State state, String website, String email, String twitter,
               String lastTweet, int startDate, int endDate, Committee[] committees, Bill[] bills)   {
        this.name = name;
        this.imageUrl = imageUrl;
        this.title = title;
        this.party = party;
        this.state = state;
        this.website = website;
        this.email = email;
        this.twitter = twitter;
        this.lastTweet = lastTweet;
        this.startDate = startDate;
        this.endDate = endDate;
        this.committees = committees;
        this.bills = bills;
    }
    public String getName() {
        return name;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public String getTitle()    {
        return title.toString();
    }
    public Party getParty(){ return party; }
    public String getWebsite () {
        return website;
    }
    public String getEmail()    {
        return email;
    }
    public String getTwitter()  {
        return twitter;
    }
    public String getLastTweet()    {
        return lastTweet;
    }
    public String getAffiliation() {
        return party.name() + "-" + state.name();
    }
    public int getStartDate()  {
        return startDate;
    }
    public int getEndDate()    {
        return endDate;
    }
    public Committee[] getCommittees()  {
        return committees;
    }
    public Bill[] getBills()    {
        return bills;
    }
    private String name;
    private String imageUrl;
    private Party party;
    private State state;
    private Title title;
    private String website;
    private String email;
    private String twitter;
    private String lastTweet;
    private int startDate;
    private int endDate;
    private Committee[] committees;
    private Bill[] bills;
}
