package domains;

/**
 * Created by Administrator on 2014/12/28.
 */
public class ShoppingChart {
    private Item item;

    public void add(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }
}
