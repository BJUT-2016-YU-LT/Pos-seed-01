package com.thoughtworks.pos.domains;

import com.thoughtworks.pos.common.EmptyShoppingChartException;
import com.thoughtworks.pos.services.services.ReportDataGenerator;

/**
 * Created by Administrator on 2014/12/28.
 */

/**
 * Changed by 5Wenbin 2016.6.22
 */

public class Pos {
    public String getShoppingList(ShoppingChart shoppingChart) throws EmptyShoppingChartException {

        Report report = new ReportDataGenerator(shoppingChart).generate();

        StringBuilder shoppingListBuilder = new StringBuilder()
                .append("***商店购物清单***\n");

        StringBuilder vipStringBuilder = shoppingListBuilder;
        if(shoppingChart.getUser().getIsVIP()){
            vipStringBuilder
                    .append("会员编号：")
                    .append(shoppingChart.getUser().getUserCode().toString()).append("\t")
                    .append("会员积分：")
                    .append(shoppingChart.getUser().getScore()).append("分").append("\n")
                    .append("----------------------\n");
            shoppingListBuilder = vipStringBuilder;
        }

        //显示时间
/*
        SimpleDateFormat date = new SimpleDateFormat("yyyy年mm月dd日 HH:mm:ss");
        shoppingListBuilder
                .append("打印时间:")
                .append(date.format(new Date()).toString()).append("\n")
                .append("----------------------\n");
*/

        for (ItemGroup itemGroup : report.getItemGroupies()) {
            shoppingListBuilder.append(
                new StringBuilder()
                    .append("名称：").append(itemGroup.groupName()).append("，")
                    .append("数量：").append(itemGroup.groupSize()+itemGroup.groupGift()).append(itemGroup.groupUnit()).append("，")
                    .append("单价：").append(String.format("%.2f", itemGroup.groupPrice())).append("(元)").append("，")
                    .append("小计：").append(String.format("%.2f", itemGroup.subTotal())).append("(元)").append("\n")
                    .toString());
        }

        boolean flag = false;
        for (ItemGroup itemGroup : report.getItemGroupies()) {
            if(itemGroup.groupPromotion()&&itemGroup.groupSize()>1&&!flag) {
                shoppingListBuilder
                        .append("----------------------\n")
                        .append("挥泪赠送商品：\n");
                flag = true;
            }
        }
        for (ItemGroup itemGroup : report.getItemGroupies()) {
            if(itemGroup.groupPromotion()&&itemGroup.groupSize()>1) {
                shoppingListBuilder
                        .append("名称：").append(itemGroup.groupName()).append("，")
                        .append("数量：").append(itemGroup.groupGift()).append(itemGroup.groupUnit()).append("，")
                        .append("单价：").append(String.format("%.2f", itemGroup.groupPrice())).append("(元)").append("，")
                        .append("小计：").append(String.format("%.2f", itemGroup.groupGift()*itemGroup.groupPrice())).append("(元)").append("\n")
                        .toString();
            }
        }

        StringBuilder subStringBuilder = shoppingListBuilder
                .append("----------------------\n")
                .append("总计：").append(String.format("%.2f", report.getTotal())).append("(元)").append("\n");
        
        double saving = report.getSaving();
        if (saving == 0) {
            return subStringBuilder
                    .append("**********************\n")
                    .toString();
        }
        return subStringBuilder
                .append("节省：").append(String.format("%.2f", saving)).append("(元)").append("\n")
                .append("**********************\n")
                .toString();
    }
}
