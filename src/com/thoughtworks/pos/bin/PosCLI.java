package com.thoughtworks.pos.bin;

import com.thoughtworks.pos.common.BarCodeNotExistException;
import com.thoughtworks.pos.common.BarCodeReuseException;
import com.thoughtworks.pos.common.EmptyShoppingChartException;
import com.thoughtworks.pos.domains.Pos;
import com.thoughtworks.pos.domains.ShoppingChart;
import com.thoughtworks.pos.services.services.ListInputParser;

import java.io.File;
import java.io.IOException;

/**
 * Created by 5Wenbin 2016.6.27
 */

public class PosCLI {
    public static void main (String args[]) throws IOException, EmptyShoppingChartException, BarCodeReuseException, BarCodeNotExistException {
        File items = new File("C:\\Users\\5Wenbin\\Desktop\\target\\fixtures\\sampleItems.json");
        File users = new File("C:\\Users\\5Wenbin\\Desktop\\target\\fixtures\\sampleUsers.json");
        //File barCodes = new File("C:\\Users\\5Wenbin\\Desktop\\target\\fixtures\\sampleIndexes.json");
        File userShoppingList = new File("C:\\Users\\5Wenbin\\Desktop\\target\\fixtures\\newSampleIndexes.json");

        //InputParser inputParser = new InputParser(barCodes, items);
        ListInputParser inputParser = new ListInputParser(userShoppingList, items, users);

        ShoppingChart shoppingChart = inputParser.parser();

        Pos pos = new Pos();
        String shoppingList = pos.getShoppingList(shoppingChart);
        System.out.print(shoppingList);
    }
}
