package com.thoughtworks.pos.domains;

/**
 * Created by 5Wenbin 2016.6.22
 */
public class Item {
    private String barcode;
    private String name;
    private String unit;
    private double price;
    private double discount = 1.0;
    private double vipDiscount = 1.0;
    private boolean promotion = false;

    public Item(){
    }

    public Item(String barcode, String name, String unit, double price) {
        this.setBarcode(barcode);
        this.setName(name);
        this.setUnit(unit);
        this.setPrice(price);
    }


    public Item(String barcode, String name, String unit, double price, double discount) {
        this(barcode, name, unit, price);
        this.setDiscount(discount);
    }

    public Item(String barcode, String name, String unit, double price, boolean promotion) {
        this(barcode, name, unit, price);
        this.setPromotion(promotion);
    }

    public Item(String barcode, String name, String unit, double price, double discount, boolean promotion) {
        this(barcode, name, unit, price, discount);
        if(this.discount==1) {
            this.setPromotion(promotion);
        }
    }

    public Item(double vipDiscount, String barcode, String name, String unit, double price) {
        this.setBarcode(barcode);
        this.setName(name);
        this.setUnit(unit);
        this.setPrice(price);
        this.setVipDiscount(vipDiscount);
    }


    public Item(double vipDiscount, String barcode, String name, String unit, double price, double discount) {
        this(barcode, name, unit, price);
        this.setDiscount(discount);
        this.setVipDiscount(vipDiscount);
    }

    public Item(double vipDiscount, String barcode, String name, String unit, double price, boolean promotion) {
        this(vipDiscount, barcode, name, unit, price);
        if(this.vipDiscount==1) {
            this.setPromotion(promotion);
        }
    }

    public Item(double vipDiscount, String barcode, String name, String unit, double price, double discount, boolean promotion) {
        this(vipDiscount, barcode, name, unit, price, discount);
        this.setPromotion(promotion);
    }

    public String getName() {
        return name;
    }

    public String getUnit() {
        return unit;
    }

    public double getPrice() {
        return price;
    }

    public String getBarcode() { return barcode; }

    public double getDiscount() { return discount; }

    public double getVipDiscount() { return vipDiscount; }

    public boolean getPromotion(){ return promotion; }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDiscount(double discount) {
        if(discount>=0&&discount<1) {
            this.discount = discount;
            this.promotion = false;
        }
    }

    public void setVipDiscount(double vipDiscount) {
        if(vipDiscount>=0&&vipDiscount<1) {
            this.vipDiscount = vipDiscount;
            this.promotion = false;
        }
    }

    public void setPromotion(boolean promotion){
        if(promotion){
            this.discount = 1;
            this.vipDiscount = 1;
        }
        this.promotion = promotion;
    }

    public boolean equals(Item item){
        return this.name.equals(item.name)
                &&this.price==item.price
                &&this.discount==item.discount
                &&this.unit.equals(item.unit)
                &&this.barcode.equals(item.barcode);
    }
}
