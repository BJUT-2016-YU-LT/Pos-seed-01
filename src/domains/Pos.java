package domains;

import java.util.ArrayList;

/**
 * Created by Administrator on 2014/12/28.
 */
public class Pos {
    public String getShoppingList(ShoppingChart shoppingChart) {
        ArrayList<Item> items = shoppingChart.getItems();

        Item item = items.get(0);
        int amountOfItem = items.size();
        double priceOfItem = item.getPrice();
        String nameOfItem = item.getName();
        String unitOfItem = item.getUnit();
        double subTotal = priceOfItem * amountOfItem;
        double total = priceOfItem * amountOfItem;

        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder
                .append("***商店购物清单***\n")
                .append("名称：").append(nameOfItem).append("，")
                .append("数量：").append(amountOfItem).append(unitOfItem).append("，")
                .append("单价：").append(String.format("%.2f", priceOfItem)).append("(元)").append("，")
                .append("小计：").append(String.format("%.2f", subTotal)).append("(元)").append("\n")
                .append("----------------------\n")
                .append("总计：").append(String.format("%.2f", total)).append("(元)").append("\n")
                .append("**********************\n")
                .toString();
    }
}
