<<<<<<< HEAD
package com.thoughtworks.pos.services.services;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.pos.domains.Item;
import com.thoughtworks.pos.domains.ShoppingChart;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Administrator on 2015/1/2.
 */
public class InputParser {
    private File indexFile;
    private File itemsFile;
    private final ObjectMapper objectMapper;

    public InputParser(File indexFile, File itemsFile) {
        this.indexFile = indexFile;
        this.itemsFile = itemsFile;
        objectMapper = new ObjectMapper(new JsonFactory());
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
    }

    public ShoppingChart parser() throws IOException {
        return BuildShoppingChart(getBoughtItemBarCodes(), getItemIndexes());
    }

    private ShoppingChart BuildShoppingChart(String[] barCodes, HashMap<String, Item> itemIndexes) {
        ShoppingChart shoppingChart = new ShoppingChart();
        for (String barcode : barCodes) {
            Item mappedItem = itemIndexes.get(barcode);
            Item item = new Item(mappedItem.getVipDiscount(), barcode, mappedItem.getName(), mappedItem.getUnit(), mappedItem.getPrice(), mappedItem.getDiscount(), mappedItem.getPromotion());
            shoppingChart.add(item);
        }
        return shoppingChart;
    }

    private String[] getBoughtItemBarCodes() throws IOException {
        String itemsStr = FileUtils.readFileToString(indexFile);
        return objectMapper.readValue(itemsStr, String[].class);
    }

    private HashMap<String, Item> getItemIndexes() throws IOException {
        String itemsIndexStr = FileUtils.readFileToString(itemsFile);
        TypeReference<HashMap<String,Item>> typeRef = new TypeReference<HashMap<String,Item>>() {};
        return objectMapper.readValue(itemsIndexStr, typeRef);
    }

}
=======
package com.thoughtworks.pos.services.services;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.pos.domains.Item;
import com.thoughtworks.pos.domains.ShoppingChart;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Administrator on 2015/1/2.
 */
public class InputParser {
    private File indexFile;
    private File itemsFile;
    private final ObjectMapper objectMapper;

    public InputParser(File indexFile, File itemsFile) {
        this.indexFile = indexFile;
        this.itemsFile = itemsFile;
        objectMapper = new ObjectMapper(new JsonFactory());
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
    }

    public ShoppingChart parser() throws IOException {
        return BuildShoppingChart(getBoughtItemBarCodes(), getItemIndexes());
    }

    private ShoppingChart BuildShoppingChart(String[] barCodes, HashMap<String, Item> itemIndexes) {
        ShoppingChart shoppingChart = new ShoppingChart();
        for (String barcode : barCodes) {
            Item mappedItem = itemIndexes.get(barcode);
            Item item = new Item(mappedItem.getVipDiscount(), barcode, mappedItem.getName(), mappedItem.getUnit(), mappedItem.getPrice(), mappedItem.getDiscount(), mappedItem.getPromotion());
            shoppingChart.add(item);
        }
        return shoppingChart;
    }

    private String[] getBoughtItemBarCodes() throws IOException {
        String itemsStr = FileUtils.readFileToString(indexFile);
        return objectMapper.readValue(itemsStr, String[].class);
    }

    private HashMap<String, Item> getItemIndexes() throws IOException {
        String itemsIndexStr = FileUtils.readFileToString(itemsFile);
        TypeReference<HashMap<String,Item>> typeRef = new TypeReference<HashMap<String,Item>>() {};
        return objectMapper.readValue(itemsIndexStr, typeRef);
    }

}
>>>>>>> 2b28a0ac09b1f19b7c4030b9295b86c537e8d9f6
