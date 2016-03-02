package com.example.lily.proj02_shared;

import java.util.Date;

/**
 * Created by lily on 3/1/16.
 */
public class TestData {
    public static Politician createTestPolitician(int i)    {
        String name;
        String imageUrl;
        Title t;
        Party p;
        State s;
        String website;
        String email;
        String twitter;
        String lastTweet;
        int startDate;
        int endDate;
        Committee[] committees = new Committee[2];
        Bill[] bills = new Bill[3];

        switch (i)  {
            case 4:
                name = "Left Shark";
                imageUrl = "https://pbs.twimg.com/media/B9XsY43IAAA3cax.jpg";
                t = Title.SEN;
                p = Party.D;
                s = State.CA;
                website = "https://myspace.com";
                email = "leftshark@gmail.com";
                twitter = "leftshark";
                lastTweet = "It's about time they realize Leo was as good as me #Oscars #LeonardoDiCaprio";
                startDate = 2012;
                endDate = 2018;
                break;
            case 3:
                name = "Beyonce Knowles";
                imageUrl = "http://pixel.nymag.com/imgs/fashion/daily/2016/02/07/07-beyonce-formation-dylanlex.w529.h352.jpg";
                t = Title.SEN;
                p = Party.D;
                s = State.CA;
                website = "https://youtu.be/LrCHz1gwzTo";
                email = "queen@beyonce.com";
                twitter = "beyonce";
                lastTweet = "RT @jleicole For #WHD2013, I ran 5.312 @CharityMiles to " +
                        "help @GirlUp educate girls in the developing world. #EveryMileMatters " +
                        "#BeyGood";
                startDate = 2008;
                endDate = 2020;
                break;
            case 2:
                name = "Donald Drumpf";
                imageUrl = "http://i2.cdn.turner.com/money/dam/assets/151008102414-trump-hat-2-780x439.jpg";
                t = Title.SEN;
                p = Party.R;
                s = State.CA;
                website = "http://donaldjdrumpf.com/";
                email = "money@money.money";
                twitter = "realdonaldtrump";
                lastTweet = "MAKE AMERICA GREAT AGAIN!";
                startDate = 2012;
                endDate = 2018;
                break;
            case 1:
            default:
                name = "Frank Ocean";
                imageUrl = "http://static.independent.co.uk/s3fs-public/thumbnails/image/2015/07/29/15/frankocean2.png";
                t = Title.REP;
                p = Party.I;
                s = State.CA;
                website = "http://boysdontcry.co/";
                email = "july2015@frankocean.com";
                twitter = "frankocean";
                lastTweet = "LOVE TO PARIS AND ANY INDIVIDUAL OR FAMILY WHO’S LOST LOVED ONES" +
                    "TO EVIL ANYWHERE IN THIS WORLD. CHILL W/ THE ANGLES.";
                startDate = 2006;
                endDate = 2016;
                break;
        }
        committees[0] = new Committee("Ethics", "Chair");
        committees[1] = new Committee("Agriculture", "Member");
        bills[0] = new Bill("PATRIOT Act", new Date());
        bills[1] = new Bill("Donald J. Drumpf Act to Make America Great Again", new Date());
        bills[2] = new Bill("Veterans' Bill of 2016", new Date());
        return new Politician(name, imageUrl, t, p, s, website, email, twitter,
                lastTweet, startDate, endDate, committees, bills);
    }
}
