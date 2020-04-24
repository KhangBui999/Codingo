package com.example.codingo.Entities;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class User {

    private String uid;
    private String name;
    private String profilePicUrl;
    private int xp;
    private int points;
    private ArrayList<String> badgesIdList;
    private int correct;
    private int attempts;

    public User(String uid, String name, String profilePicUrl, int xp, int points,
                ArrayList<String> badgesIdList, int correct, int attempts) {
        this.uid = uid;
        this.name = name;
        this.profilePicUrl = profilePicUrl;
        this.xp = xp;
        this.points = points;
        this.badgesIdList = badgesIdList;
        this.correct = correct;
        this.attempts = attempts;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public ArrayList<String> getBadgesIdList() {
        return badgesIdList;
    }

    public void setBadgesIdList(ArrayList<String> badgesIdList) {
        this.badgesIdList = badgesIdList;
    }

    public int getCorrect() {
        return correct;
    }

    public void setCorrect(int correct) {
        this.correct = correct;
    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public String getAccuracyRate() {
        if(this.attempts == 0) {
            return "0.00%";
        }
        else {
            DecimalFormat decimalFormat = new DecimalFormat("000.##");
            return decimalFormat.format(((double)this.correct/(double)this.attempts)*100)+"%";
        }
    }
}
