package com.programa;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.Arrays;

public class User {

    private String name;
    private String uid;
    private String email;
    private String points;
    private int tarefa;
    private ArrayList<String> badges;

    public User(){}
    public User(String name, String uid, String email, String points, String badges) {
        this.name = name;
        this.uid = uid;
        this.email = email;
        this.points = points;
        this.badges = new ArrayList<>(Arrays.asList(badges.split(";")));
        tarefa = 0;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getBadges(){
        String msg = "";
        for (String x: badges
                ) {
            msg+=x+";";
        }
        return msg;
    }

    @Exclude
    public ArrayList<String> getBadgesList() {
        return badges;
    }

    public void setBadges(String badge) {
        badges = new ArrayList<>(Arrays.asList(badge.split(";")));
    }

    @Exclude
    public String getBadgesToString(){
        String msg = "";
        for (String x: badges
             ) {
            msg+=x+";";
        }
        return msg;
    }

    public boolean hasBadge(String id){
        for (String x : badges
             ) {
            if(x.equals(id)) return true;
        }
        return false;
    }
    public int getTarefa(){ return tarefa;}
    public void setTarefa(){
        tarefa++;
    }
}
