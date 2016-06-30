package com.thoughtworks.pos.domains;

/**
 * Created by 5Wenbin on 2016/6/28.
 */
public class User {
    private String userCode;
    private String name;
    private boolean isVIP = false;
    private int score = 0;

    public User(){

    }

    public User(String userCode, String name){
        this.setUserCode(userCode);
        this.setName(name);
    }

    public User(String userCode, String name, boolean isVIP){
        this(userCode,name);
        if (isVIP) {
            this.setVIP();
        }
    }

    public User(String userCode, String name, int score){
        this(userCode,name);
        this.setVIP();
        this.score = score;
    }

    public User(String userCode, String name, boolean isVIP, int score){
        this(userCode,name,isVIP);
        this.addScore(score);
    }

    public String getUserCode() {
        return userCode;
    }

    public String getName() {
        return name;
    }

    public boolean getIsVIP() {
        return isVIP;
    }

    public int getScore() {
        return score;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(int score) { this.score = score; }

    public boolean setVIP() {
        if(this.isVIP) {
            return false;
        }
        this.isVIP = true;
        return true;
    }

    public boolean logoutVIP(){
        if(this.isVIP) {
            isVIP = false;
            this.score = 0;
            return true;
        }
        return false;
    }

    public boolean addScore(int score) {
        if(this.isVIP) {
            this.score += score;
            return true;
        }
        return false;
    }
}
