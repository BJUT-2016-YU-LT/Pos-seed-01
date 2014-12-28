package domains;

/**
 * Created by Administrator on 2014/12/28.
 */
public class Item {
    private String barCode;
    private String name;
    private String unit;
    private double price;

    public Item(String barCode, String name, String unit, double price) {

        this.barCode = barCode;
        this.name = name;
        this.unit = unit;
        this.price = price;
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
}
