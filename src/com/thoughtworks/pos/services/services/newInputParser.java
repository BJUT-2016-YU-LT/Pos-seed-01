package com.thoughtworks.pos.services.services;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.pos.domains.Item;
import com.thoughtworks.pos.domains.ShoppingChart;
import org.apache.commons.io.FileUtils;
import com.thoughtworks.pos.common.BarCodeReuseException;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;


/**
 * Created by 5Wenbin 2016.6.22
 */
public class newInputParser {
    private File itemsFile;
    private final ObjectMapper objectMapper;

    public newInputParser(File itemsFile) {
        this.itemsFile = itemsFile;
        objectMapper = new ObjectMapper(new JsonFactory());
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
    }

    public ShoppingChart parser() throws IOException,BarCodeReuseException {
        return BuildShoppingChart(getItemIndexes());
    }

    private ShoppingChart BuildShoppingChart(Item[] itemIndexes) throws BarCodeReuseException {
        ShoppingChart shoppingChart = new ShoppingChart();
        HashMap<String, Item> table = new HashMap<String, Item>();
        for (Item item:itemIndexes) {
            Item i = table.get(item.getBarcode());
            if(i == null){
                table.put(item.getBarcode(), item);
            }
            else{
                if(!i.equals(item))
                    throw new BarCodeReuseException();
            }
            shoppingChart.add(item);
        }
        return shoppingChart;
    }

    private Item[] getItemIndexes() throws IOException {
        String itemsIndexStr = FileUtils.readFileToString(itemsFile);
        TypeReference<Item[]> typeRef = new TypeReference<Item[]>() {};
        return objectMapper.readValue(itemsIndexStr, typeRef);
    }
}

