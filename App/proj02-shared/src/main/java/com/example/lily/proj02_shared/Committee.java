package com.example.lily.proj02_shared;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.Serializable;

/**
 * Created by lily on 2/28/16.
 */
public class Committee implements Serializable {
    Committee(String name, boolean isCommittee)   {
        this.name = name;
        this.isCommittee = isCommittee;
    }
    public static Committee[] parseCommittees(JsonObject obj) {
        JsonArray committeeResults = obj.getAsJsonArray("results");
        Committee[] committees = new Committee[committeeResults.size()];
        for (int i = 0; i < committeeResults.size(); i++)   {
            JsonObject r = committeeResults.get(i).getAsJsonObject();
            String name = r.get("name").getAsString();
            boolean isCommittee = !r.get("subcommittee").getAsBoolean();
            Committee c = new Committee(name, isCommittee);
            committees[i] = c;
        }
        return committees;
    }
    public String getName() {
        return name;
    }
    public boolean getIfCommittee()   {
        return isCommittee;
    }
    private String name;
    private boolean isCommittee;
    public static final boolean COMMITTEE = true;
    public static final boolean SUBCOMMITTEE = false;
}
