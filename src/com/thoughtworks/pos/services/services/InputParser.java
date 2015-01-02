package com.thoughtworks.pos.services.services;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.pos.domains.Item;
import com.thoughtworks.pos.domains.ShoppingChart;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 2015/1/2.
 */
public class InputParser {
    private File file;
    private final ObjectMapper objectMapper;

    public InputParser(File file) {
        this.file = file;
        objectMapper = new ObjectMapper(new JsonFactory());
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
    }

    public ShoppingChart parser() throws IOException {
        String textInput = FileUtils.readFileToString(file);
        Item[] items = objectMapper.readValue(textInput, Item[].class);
        return BuildShoppingChart(items);
    }

    private ShoppingChart BuildShoppingChart(Item[] items) {
        ShoppingChart shoppingChart = new ShoppingChart();
        for (Item item : items){
            shoppingChart.add(item);
        }
        return shoppingChart;
    }
}
