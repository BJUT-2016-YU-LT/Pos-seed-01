import com.thoughtworks.pos.domains.Item;
import com.thoughtworks.pos.domains.Pos;
import com.thoughtworks.pos.domains.ShoppingChart;
import com.thoughtworks.pos.domains.User;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by 5Wenbin on 2016/6/29.
 */
public class UserPosTest {
    @Test
    public void testExistVipUserBuyItemsHaveDifferentDiscountType() throws  Exception{
        // given
        ShoppingChart shoppingChart = new ShoppingChart();
        shoppingChart.add(new Item(0.6, "ITEM000000", "雪碧", "瓶", 4.00));
        shoppingChart.add(new Item("ITEM000001", "可乐", "罐", 2.00, 0.8));
        shoppingChart.add(new Item(0.8, "ITEM000002", "雪碧", "听", 3.00, 0.9));

        shoppingChart.setUser(new User("USER0001","武文斌", true, 0));

        // when
        Pos pos = new Pos();
        String actualShoppingList = pos.getShoppingList(shoppingChart);

        // then
        String expectedShoppingList =
                  "***商店购物清单***\n"
                + "会员编号：USER0001\t会员积分：1分\n"
                + "----------------------\n"
                + "名称：雪碧，数量：1瓶，单价：4.00(元)，小计：2.40(元)\n"
                + "名称：可乐，数量：1罐，单价：2.00(元)，小计：1.60(元)\n"
                + "名称：雪碧，数量：1听，单价：3.00(元)，小计：2.16(元)\n"
                + "----------------------\n"
                + "总计：6.16(元)\n"
                + "节省：2.84(元)\n"
                + "**********************\n";
        assertThat(actualShoppingList, is(expectedShoppingList));
    }

    @Test
    public void testExistVVipUserBuyItemsHaveDifferentPromotion() throws  Exception{
        // given
        ShoppingChart shoppingChart = new ShoppingChart();
        shoppingChart.add(new Item("ITEM000000", "雪碧", "瓶", 4.00, true));
        shoppingChart.add(new Item("ITEM000000", "雪碧", "瓶", 4.00, true));
        shoppingChart.add(new Item("ITEM000001", "可乐", "罐", 2.00, true));
        shoppingChart.add(new Item("ITEM000002", "雪碧", "听", 3.00, false));
        shoppingChart.add(new Item("ITEM000002", "雪碧", "听", 3.00, false));

        shoppingChart.setUser(new User("USER0001","武文斌", true, 201));

        // when
        Pos pos = new Pos();
        String actualShoppingList = pos.getShoppingList(shoppingChart);

        // then
        String expectedShoppingList =
                "***商店购物清单***\n"
                        + "会员编号：USER0001\t会员积分：210分\n"
                        + "----------------------\n"
                        + "名称：雪碧，数量：3瓶，单价：4.00(元)，小计：8.00(元)\n"
                        + "名称：可乐，数量：1罐，单价：2.00(元)，小计：2.00(元)\n"
                        + "名称：雪碧，数量：2听，单价：3.00(元)，小计：6.00(元)\n"
                        + "----------------------\n"
                        + "挥泪赠送商品：\n"
                        + "名称：雪碧，数量：1瓶，单价：4.00(元)，小计：4.00(元)\n"
                        + "----------------------\n"
                        + "总计：16.00(元)\n"
                        + "节省：4.00(元)\n"
                        + "**********************\n";
        assertThat(actualShoppingList, is(expectedShoppingList));
    }

    @Test
    public void testExistVVVipUserBuyItemsHaveDifferentPromotion() throws  Exception{
        // given
        ShoppingChart shoppingChart = new ShoppingChart();
        shoppingChart.add(new Item("ITEM000000", "雪碧", "瓶", 4.00, true));
        shoppingChart.add(new Item("ITEM000000", "雪碧", "瓶", 4.00, true));
        shoppingChart.add(new Item("ITEM000001", "可乐", "罐", 2.00, true));
        shoppingChart.add(new Item("ITEM000002", "雪碧", "听", 3.00, false));
        shoppingChart.add(new Item("ITEM000002", "雪碧", "听", 3.00, false));

        shoppingChart.setUser(new User("USER0001","武文斌", true, 501));

        // when
        Pos pos = new Pos();
        String actualShoppingList = pos.getShoppingList(shoppingChart);

        // then
        String expectedShoppingList =
                "***商店购物清单***\n"
                        + "会员编号：USER0001\t会员积分：516分\n"
                        + "----------------------\n"
                        + "名称：雪碧，数量：3瓶，单价：4.00(元)，小计：8.00(元)\n"
                        + "名称：可乐，数量：1罐，单价：2.00(元)，小计：2.00(元)\n"
                        + "名称：雪碧，数量：2听，单价：3.00(元)，小计：6.00(元)\n"
                        + "----------------------\n"
                        + "挥泪赠送商品：\n"
                        + "名称：雪碧，数量：1瓶，单价：4.00(元)，小计：4.00(元)\n"
                        + "----------------------\n"
                        + "总计：16.00(元)\n"
                        + "节省：4.00(元)\n"
                        + "**********************\n";
        assertThat(actualShoppingList, is(expectedShoppingList));
    }

    @Test
    public void testExistNormalUserBuyMixedItems() throws  Exception{
        // given
        ShoppingChart shoppingChart = new ShoppingChart();
        shoppingChart.add(new Item("ITEM000000", "雪碧", "瓶", 4.00, true));
        shoppingChart.add(new Item("ITEM000000", "雪碧", "瓶", 4.00, true));
        shoppingChart.add(new Item("ITEM000001", "可乐", "罐", 2.00, 0.5));
        shoppingChart.add(new Item(0.8, "ITEM000002", "雪碧", "听", 3.00, false));
        shoppingChart.add(new Item(0.8, "ITEM000003", "牛奶", "盒", 3.00, 0.8));

        shoppingChart.setUser(new User("USER0001","武文斌", false));

        // when
        Pos pos = new Pos();
        String actualShoppingList = pos.getShoppingList(shoppingChart);

        // then
        String expectedShoppingList =
                "***商店购物清单***\n"
                        + "名称：雪碧，数量：3瓶，单价：4.00(元)，小计：8.00(元)\n"
                        + "名称：可乐，数量：1罐，单价：2.00(元)，小计：1.00(元)\n"
                        + "名称：雪碧，数量：1听，单价：3.00(元)，小计：3.00(元)\n"
                        + "名称：牛奶，数量：1盒，单价：3.00(元)，小计：2.40(元)\n"
                        + "----------------------\n"
                        + "挥泪赠送商品：\n"
                        + "名称：雪碧，数量：1瓶，单价：4.00(元)，小计：4.00(元)\n"
                        + "----------------------\n"
                        + "总计：14.40(元)\n"
                        + "节省：5.60(元)\n"
                        + "**********************\n";
        assertThat(actualShoppingList, is(expectedShoppingList));
    }

    @Test
    public void testNotExistVIPUserBuyMixedItems() throws  Exception{
        // given
        ShoppingChart shoppingChart = new ShoppingChart();
        shoppingChart.add(new Item("ITEM000000", "雪碧", "瓶", 4.00, true));
        shoppingChart.add(new Item("ITEM000000", "雪碧", "瓶", 4.00, true));
        shoppingChart.add(new Item("ITEM000001", "可乐", "罐", 2.00, 0.5));
        shoppingChart.add(new Item(0.8, "ITEM000002", "雪碧", "听", 3.00, false));
        shoppingChart.add(new Item(0.8, "ITEM000003", "牛奶", "盒", 3.00, 0.8));

        shoppingChart.setUser(new User("USER0009","武文斌", true, 501));

        // when
        Pos pos = new Pos();
        String actualShoppingList = pos.getShoppingList(shoppingChart);

        // then
        String expectedShoppingList =
                "***商店购物清单***\n"
                        + "会员编号：USER0009\t会员积分：511分\n"
                        + "----------------------\n"
                        + "名称：雪碧，数量：3瓶，单价：4.00(元)，小计：8.00(元)\n"
                        + "名称：可乐，数量：1罐，单价：2.00(元)，小计：1.00(元)\n"
                        + "名称：雪碧，数量：1听，单价：3.00(元)，小计：2.40(元)\n"
                        + "名称：牛奶，数量：1盒，单价：3.00(元)，小计：1.92(元)\n"
                        + "----------------------\n"
                        + "挥泪赠送商品：\n"
                        + "名称：雪碧，数量：1瓶，单价：4.00(元)，小计：4.00(元)\n"
                        + "----------------------\n"
                        + "总计：13.32(元)\n"
                        + "节省：6.68(元)\n"
                        + "**********************\n";
        assertThat(actualShoppingList, is(expectedShoppingList));
    }
}
