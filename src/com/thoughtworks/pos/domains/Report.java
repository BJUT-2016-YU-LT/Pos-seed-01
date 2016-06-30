package com.thoughtworks.pos.domains;

import java.util.List;

/**
 * Created by Administrator on 2014/12/31.
 */

/**
 * Changed by 5Wenbin on 2016/6/28.
 */
public class Report{
    private List<ItemGroup> itemGroupies;
    private int scoreType = 0;
    private int score = 0;

    public Report(List<ItemGroup> itemGroupies){
        this.itemGroupies = itemGroupies;
    }

    public List<ItemGroup> getItemGroupies() {
        return this.itemGroupies;
    }

    public double getTotal(){
        double result = 0.00;
        for (ItemGroup itemGroup : itemGroupies)
            result += itemGroup.subTotal();
        return result;
    }

    public double getSaving(){
        double result = 0.00;
        for (ItemGroup itemGroup : itemGroupies)
            result += itemGroup.saving();
        return result;
    }

    public int getScoreType() {
        return scoreType;
    }

    public void setScoreType(int scoreType) {
        this.scoreType = scoreType;
    }

    public int getScore(){
        return (int)getTotal()/5*scoreType;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
