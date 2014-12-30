package domains;

import common.EmptyShoppingCartException;

import java.util.*;

/**
 * Created by Administrator on 2014/12/28.
 */
public class Pos {
    public String getShoppingList(ShoppingChart shoppingChart) throws EmptyShoppingCartException {
        ArrayList<Item> items = shoppingChart.getItems();
        if (items.size() <= 0) {
            throw new EmptyShoppingCartException();
        }

        LinkedHashMap<String, List<Item>> itemsWithSameType = groupByItemBarCode(items);
        StringBuilder shoppingListBuilder = new StringBuilder()
                        .append("***商店购物清单***\n");

        for (List<Item> group : itemsWithSameType.values())
            shoppingListBuilder.append(getGroupOfItemsDescription(group));

        double total = getTotalPrice(items);
        double savingMoney = getSavingMoney(items);

        StringBuilder subStringBuilder = shoppingListBuilder
                .append("----------------------\n")
                .append("总计：").append(String.format("%.2f", total)).append("(元)").append("\n");

        if (savingMoney == 0) {
            return subStringBuilder
                    .append("**********************\n")
                    .toString();
        }
        return subStringBuilder
                .append("节省：").append(String.format("%.2f", savingMoney)).append("(元)").append("\n")
                .append("**********************\n")
                .toString();
    }

    private double getSavingMoney(ArrayList<Item> items) {
        double result = 0;
        for (Item item : items)
            result += item.getPrice() * (1 - item.getDiscount());
        return result;
    }

    private double getTotalPrice(ArrayList<Item> items) {
        double result = 0;
        for (Item item : items)
            result += item.getPrice() * item.getDiscount();
        return result;
    }

    private String getGroupOfItemsDescription(List<Item> items){
        Item item = items.get(0);
        int amountOfItem = items.size();

        double priceOfItem = item.getPrice();
        double discount = item.getDiscount();
        String nameOfItem = item.getName();
        String unitOfItem = item.getUnit();
        double subTotal = priceOfItem * amountOfItem * discount;
        return new StringBuilder()
                .append("名称：").append(nameOfItem).append("，")
                .append("数量：").append(amountOfItem).append(unitOfItem).append("，")
                .append("单价：").append(String.format("%.2f", priceOfItem)).append("(元)").append("，")
                .append("小计：").append(String.format("%.2f", subTotal)).append("(元)").append("\n")
                .toString();
    };

    private LinkedHashMap<String, List<Item>> groupByItemBarCode(ArrayList<Item> items) {
        LinkedHashMap<String, List<Item>> map = new LinkedHashMap<String, List<Item>>();
        for (Item item : items) {
            String itemBarCode = item.getBarCode();
            if (!map.containsKey(itemBarCode)) {
                map.put(itemBarCode, new ArrayList<Item>());
            }
            map.get(itemBarCode).add(item);
        }
        return map;
    }
}
