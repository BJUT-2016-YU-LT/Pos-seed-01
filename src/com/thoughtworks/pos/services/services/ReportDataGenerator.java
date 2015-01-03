package com.thoughtworks.pos.services.services;

import com.thoughtworks.pos.common.EmptyShoppingCartException;
import com.thoughtworks.pos.domains.Item;
import com.thoughtworks.pos.domains.ItemGroup;
import com.thoughtworks.pos.domains.Report;
import com.thoughtworks.pos.domains.ShoppingChart;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2014/12/31.
 */
public class ReportDataGenerator {
    private ShoppingChart shoppingChart;

    public ReportDataGenerator(ShoppingChart shoppingChart) {
        this.shoppingChart = shoppingChart;
    }

    public Report generate() throws EmptyShoppingCartException {
        ArrayList<Item> items = shoppingChart.getItems();
        if (items.size() <= 0) {
            throw new EmptyShoppingCartException();
        }

        List<ItemGroup> itemGroups = GetItemGroups(items);
        return new Report(itemGroups);
    }

    private List<ItemGroup> GetItemGroups(ArrayList<Item> items) {
        List<ItemGroup> itemGroupies = new LinkedList<ItemGroup>();
        for (List<Item> group : groupByItemBarCode(items).values())
            itemGroupies.add(new ItemGroup(group));
        return itemGroupies;
    }

    private static LinkedHashMap<String, List<Item>> groupByItemBarCode(ArrayList<Item> items) {
        LinkedHashMap<String, List<Item>> map = new LinkedHashMap<String, List<Item>>();
        for (Item item : items) {
            String itemBarCode = item.getBarcode();
            if (!map.containsKey(itemBarCode)) {
                map.put(itemBarCode, new ArrayList<Item>());
            }
            map.get(itemBarCode).add(item);
        }
        return map;
    }
}