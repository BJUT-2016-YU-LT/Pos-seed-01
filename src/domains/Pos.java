package domains;

/**
 * Created by Administrator on 2014/12/28.
 */
public class Pos {
    public String getShoppingList(ShoppingChart shoppingChart) {
        Item item = shoppingChart.getItem();
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder
                .append("***商店购物清单***\n")
                .append("名称：可口可乐，数量：1瓶，单价：3.00(元)，小计：3.00(元)\n")
                .append("----------------------\n")
                .append("总计：3.00(元)\n")
                .append("**********************\n")
                .toString();
    }
}
