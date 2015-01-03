package com.thoughtworks.pos.bin;

import com.thoughtworks.pos.common.EmptyShoppingCartException;
import com.thoughtworks.pos.domains.Pos;
import com.thoughtworks.pos.domains.ShoppingChart;
import com.thoughtworks.pos.services.services.InputParser;

import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 2015/1/2.
 */
public class PosCLI {
    public static void main (String args[]) throws IOException, EmptyShoppingCartException {
        ShoppingChart shoppingChart = ParseShoppingChartFromFile(args[0]);

        Pos pos = new Pos();
        String shoppingList = pos.getShoppingList(shoppingChart);
        System.out.print(shoppingList);
    }

    private static ShoppingChart ParseShoppingChartFromFile(String pathname) throws IOException {
        InputParser inputParser = new InputParser(new File(pathname));
        return inputParser.parser();
    }
}
