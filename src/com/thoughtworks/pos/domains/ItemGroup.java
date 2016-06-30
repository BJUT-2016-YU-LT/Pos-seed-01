package com.thoughtworks.pos.domains;

import java.util.List;

/**
 * Created by 5Wenbin 2016.6.22
 */
public class ItemGroup {
    private Item item;
    private int quanitty;
    private int gift;
    private User user = new User();

    public void setUser(User user){
        this.user = user;
    }

    public User getUser(){
        return user;
    }

    public ItemGroup(Item item) {
        this.item = item;
        this.quanitty = 0;
        this.gift = 0;
    }
    
    public String groupName() {
        return item.getName();
    }

    public int groupSize() {
        return quanitty;
    }

    public String groupUnit() {
        return item.getUnit();
    }

    public double groupPrice() { return item.getPrice(); }

    public boolean groupPromotion(){ return item.getPromotion(); }

    public int groupGift(){ return gift; }

    public void addOne(){
        quanitty++;
        if(item.getPromotion()&&quanitty>1){
            gift = 1;
        }
    }

    public double subTotal() {
        if (item.getPromotion() && quanitty > 1) {
            return item.getPrice() * quanitty;
        }
        double result;
        result = item.getPrice() * quanitty * item.getDiscount();
        if(user.getIsVIP()){
            result *= item.getVipDiscount();
        }
        return result;
    }

    public double saving() {
        if(item.getPromotion()&&quanitty>1) {
            return item.getPrice();
        }
        double result;
        result = item.getPrice() * quanitty * (1 - item.getDiscount());
        if(user.getIsVIP()){
            result += item.getPrice() * quanitty * item.getDiscount() * (1-item.getVipDiscount());
        }
        return result;
    }
}
