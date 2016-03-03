package com.cs160.lily.prog02;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by lily on 3/2/16.
 */
public class TweetFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.representative_tweets, container, false);
        TextView lastTweet = (TextView)v.findViewById(R.id.politician_tweet);
        TextView twitter = (TextView)v.findViewById(R.id.politician_twitter);
        Bundle data = this.getArguments();
        lastTweet.setText("“" + data.getString("lastTweet") + "”");
        twitter.setText("— @" + data.getString("twitter"));
        return v;
    }
}
