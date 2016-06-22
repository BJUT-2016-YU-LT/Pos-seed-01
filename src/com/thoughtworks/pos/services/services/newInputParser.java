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
public class newInputParser {
    private File indexFile;
    private final ObjectMapper objectMapper;

    public newInputParser(File indexFile) {
        this.indexFile = indexFile;
        objectMapper = new ObjectMapper(new JsonFactory());
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
    }

    public ShoppingChart parser() throws IOException {
        return BuildShoppingChart(getItemIndexes());
    }

    private ShoppingChart BuildShoppingChart(HashMap<String, Item> itemIndexes) {
        ShoppingChart shoppingChart = new ShoppingChart();
        for (String barcode : barCodes) {
            Item mappedItem = itemIndexes.get(barcode);
            Item item = new Item(barcode, mappedItem.getName(), mappedItem.getUnit(), mappedItem.getPrice(), mappedItem.getDiscount());
            shoppingChart.add(item);
        }
        return shoppingChart;
    }

    private HashMap<String, Item> getItemIndexes() throws IOException {
        String itemsIndexStr = FileUtils.readFileToString(indexFile);
        TypeReference<HashMap<String,Item>> typeRef = new TypeReference<HashMap<String,Item>>() {};
        return objectMapper.readValue(itemsIndexStr, typeRef);
    }

}

