package com.thoughtworks.pos.domains;

import java.util.List;

/**
 * Created by 5Wenbin 2016.6.22
 */
public class ItemGroup {
    private Item item;
    private int quanitty;

    public ItemGroup(Item item) {
        this.item = item;
        this.quanitty = 1;
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

    public void addOne(){ quanitty++; }

    public double subTotal() { return item.getPrice()*quanitty*item.getDiscount(); }

    public double saving() { return item.getPrice()*quanitty-this.subTotal(); }
}
