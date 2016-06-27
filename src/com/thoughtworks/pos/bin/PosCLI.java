package com.thoughtworks.pos.bin;

import com.thoughtworks.pos.common.BarCodeReuseException;
import com.thoughtworks.pos.common.EmptyShoppingCartException;
import com.thoughtworks.pos.domains.Pos;
import com.thoughtworks.pos.domains.ShoppingChart;
import com.thoughtworks.pos.services.services.InputParser;
import com.thoughtworks.pos.services.services.newInputParser;

import java.io.File;
import java.io.IOException;

/**
 * Created by 5Wenbin 2016.6.27
 */

public class PosCLI {
    public static void main (String args[]) throws IOException, EmptyShoppingCartException, BarCodeReuseException {
        File items = new File("C:\\Users\\5Wenbin\\Desktop\\target\\fixtures\\sampleItems.json");
        File barCodes = new File("C:\\Users\\5Wenbin\\Desktop\\target\\fixtures\\sampleIndexes.json");

        InputParser inputParser = new InputParser(barCodes, items);

        ShoppingChart shoppingChart = inputParser.parser();

        Pos pos = new Pos();
        String shoppingList = pos.getShoppingList(shoppingChart);
        System.out.print(shoppingList);
    }
}
