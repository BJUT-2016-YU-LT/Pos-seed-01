/**
 * Created by 5Wenbin on 2016/6/23.
 */

import com.thoughtworks.pos.domains.Item;
import com.thoughtworks.pos.domains.ShoppingChart;
import org.junit.Test;
import com.thoughtworks.pos.domains.Pos;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ShoppingListTest {
    @Test
    public void outPutTest() throws Exception{
        ShoppingChart shoppingChart = new ShoppingChart();
        shoppingChart.add(new Item("ITEM000004", "电池", "节", 2.00, 0.8));
        shoppingChart.add(new Item("ITEM000003", "可乐", "罐", 3.00, 1));
        shoppingChart.add(new Item("ITEM000002", "手机", "支", 1799.00, 0.6));
        shoppingChart.add(new Item("ITEM000001", "电脑", "台", 3000.00, 1));
        shoppingChart.add(new Item("ITEM000004", "电池", "节", 2.00, 0.8));
        Pos posSample = new Pos();
        String actualShoppingList = posSample.getShoppingList(shoppingChart);
        String expectedShoppingList =
                "***商店购物清单***\n"
                        + "名称：电池，数量：2节，单价：2.00(元)，小计：3.20(元)\n"
                        + "名称：可乐，数量：1罐，单价：3.00(元)，小计：3.00(元)\n"
                        + "名称：手机，数量：1支，单价：1799.00(元)，小计：1079.40(元)\n"
                        + "名称：电脑，数量：1台，单价：3000.00(元)，小计：3000.00(元)\n"
                        + "----------------------\n"
                        + "总计：4085.60(元)\n"
                        + "节省：720.40(元)\n"
                        + "**********************\n";
        assertThat(actualShoppingList, is(expectedShoppingList));
    }
}

