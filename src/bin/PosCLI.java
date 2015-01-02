package bin;

import common.EmptyShoppingCartException;
import domains.Item;
import domains.Pos;
import domains.ShoppingChart;
import services.InputParser;

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

    private static ShoppingChart ParseShoppingChartFromFile(String arg) throws IOException {
        File file = new File(arg);
        InputParser inputParser = new InputParser(file);
        return inputParser.parser();
    }
}
