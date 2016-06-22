package com.thoughtworks.pos.domains;

/**
 * Created by 5Wenbin 2016.6.22
 */
public class Item {
    private String barcode;
    private String name;
    private String unit;
    private double price;
    private double discount;

    public Item() {
    }

    public Item(String barcode, String name, String unit, double price) {
        this.setBarcode(barcode);
        this.setName(name);
        this.setUnit(unit);
        this.setPrice(price);
        discount = 1;
    }

    public Item(String barcode, String name, String unit, double price, double discount) {
        this(barcode, name, unit, price);
        this.setDiscount(discount);
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
        this.discount = discount;
    }

   /* public Item binding(Item gift, double newPrice){
        return Item(this.barcode, this.name, this. unit, newPrice)
    }
}
