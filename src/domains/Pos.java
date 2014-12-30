package domains;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2014/12/28.
 */
public class Pos {
    public String getShoppingList(ShoppingChart shoppingChart) {
        ArrayList<Item> items = shoppingChart.getItems();
        Map<String, List<Item>> itemsWithSameType = groupByItemName(items);
        StringBuilder shoppingListBuilder = new StringBuilder()
                        .append("***商店购物清单***\n");

        for (List<Item> group : itemsWithSameType.values()) {
            shoppingListBuilder.append(getGroupOfItemsDescription(group));
        }

        double total = getTotalPrice(items);

        return shoppingListBuilder
                .append("----------------------\n")
                .append("总计：").append(String.format("%.2f", total)).append("(元)").append("\n")
                .append("**********************\n")
                .toString();
    }

    private double getTotalPrice(ArrayList<Item> items) {
        double result = 0;
        for (Item item : items)
            result += item.getPrice();
        return result;
    }

    private String getGroupOfItemsDescription(List<Item> items){
        Item item = items.get(0);
        int amountOfItem = items.size();

        double priceOfItem = item.getPrice();
        String nameOfItem = item.getName();
        String unitOfItem = item.getUnit();
        double subTotal = priceOfItem * amountOfItem;
        return new StringBuilder()
                .append("名称：").append(nameOfItem).append("，")
                .append("数量：").append(amountOfItem).append(unitOfItem).append("，")
                .append("单价：").append(String.format("%.2f", priceOfItem)).append("(元)").append("，")
                .append("小计：").append(String.format("%.2f", subTotal)).append("(元)").append("\n")
                .toString();
    };

    private Map<String, List<Item>> groupByItemName(ArrayList<Item> items) {
        Map<String, List<Item>> map = new HashMap<String, List<Item>>();
        for (Item item : items) {
            String itemName = item.getName();
            if (!map.containsKey(itemName)) {
                map.put(itemName, new ArrayList<Item>());
            }
            map.get(itemName).add(item);
        }
        return map;
    }
}
