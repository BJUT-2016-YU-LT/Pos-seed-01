/**
 * Created by 5Wenbin on 2016/6/29.
 */
import com.thoughtworks.pos.common.BarCodeNotExistException;
import com.thoughtworks.pos.common.EmptyShoppingChartException;
import com.thoughtworks.pos.domains.Item;
import com.thoughtworks.pos.domains.ShoppingChart;
import com.thoughtworks.pos.domains.User;
import com.thoughtworks.pos.services.services.ListInputParser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class ListInputParserTest {
    private File indexFile;
    private File itemsFile;
    private File usersFile;

    @Before
    public void setUp() throws Exception {
        indexFile = new File("./sampleItems.json");
        itemsFile = new File("./itemsFile.json");
        usersFile = new File("./usersFile.json");
    }

    private void WriteToFile(File file, String content) throws FileNotFoundException {
        PrintWriter printWriter = new PrintWriter(file);
        printWriter.write(content);
        printWriter.close();
    }

    @After
    public void tearDown() throws Exception {
        if (indexFile.exists()){
            indexFile.delete();
        }
        if (itemsFile.exists()){
            itemsFile.delete();
        }
        if(usersFile.exists()){
            usersFile.delete();
        }
    }

    @Test
    public void testExistUserBuyTwoSameItem() throws Exception {
        String sampleItem = new StringBuilder()
                .append("{\n")
                .append("\"ITEM000005\":{\n")
                .append("\"name\": \"电池\",\n")
                .append("\"unit\": \"个\",\n")
                .append("\"price\": 2.00,\n")
                .append("\"discount\": 0.6,\n")
                .append("\"vipDiscount\":0.8,\n")
                .append("\"promotion\": false\n")
                .append("}\n")
                .append("}\n")
                .toString();
        WriteToFile(itemsFile, sampleItem);

        String sampleUser = new StringBuilder()
                .append("{\n")
                .append("\"USER0001\":{\n")
                .append("\"userCode\":\"USER0001\",\n")
                .append("\"name\":\"武文斌\",\n")
                .append("\"isVIP\":true,\n")
                .append("\"score\":550\n")
                .append("}\n")
                .append("}\n")
                .toString();
        WriteToFile(usersFile, sampleUser);

        String sampleIndex = new StringBuilder()
                .append("{\n")
                .append("\"user\":\"USER0001\",\n")
                .append("\"items\":[\n")
                .append("\"ITEM000005\",\n")
                .append("\"ITEM000005\"\n")
                .append("]\n")
                .append("}\n")
                .toString();
        WriteToFile(indexFile, sampleIndex);

        ListInputParser inputParser = new ListInputParser(indexFile, itemsFile, usersFile);

        ShoppingChart shoppingChart = inputParser.parser();
        ArrayList<Item> items = shoppingChart.getItems();
        User user = shoppingChart.getUser();

        assertThat(items.size(), is(2));
        Item item = items.get(0);
        assertThat(item.getName(), is("电池"));
        assertThat(item.getBarcode(), is("ITEM000005"));
        assertThat(item.getUnit(), is("个"));
        assertThat(item.getPrice(), is(2.00));
        assertThat(item.getDiscount(), is(0.6));
        assertThat(item.getVipDiscount(), is(0.8));
        assertThat(item.getPromotion(), is(false));

        assertThat(user.getUserCode(), is("USER0001"));
        assertThat(user.getName(), is("武文斌"));
        assertThat(user.getIsVIP(), is(true));
        assertThat(user.getScore(), is(550));
    }

    @Test
    public void testNotExistUserBuyDifferentItems() throws Exception {
        String sampleItem = new StringBuilder()
                .append("{\n")
                .append("\"ITEM000005\":{\n")
                .append("\"name\": \"电池\",\n")
                .append("\"unit\": \"个\",\n")
                .append("\"price\": 2.00,\n")
                .append("\"discount\": 0.6,\n")
                .append("\"vipDiscount\":0.8,\n")
                .append("\"promotion\": false\n")
                .append("},\n")
                .append("\"ITEM000004\":{\n")
                .append("\"name\": \"牛奶\",\n")
                .append("\"unit\": \"盒\",\n")
                .append("\"price\": 5.00,\n")
                .append("\"vipDiscount\":0.8,\n")
                .append("\"promotion\": true\n")
                .append("}\n")
                .append("}\n")
                .toString();
        WriteToFile(itemsFile, sampleItem);

        String sampleUser = new StringBuilder()
                .append("{\n")
                .append("\"USER0001\":{\n")
                .append("\"userCode\":\"USER0001\",\n")
                .append("\"name\":\"武文斌\",\n")
                .append("\"isVIP\":true,\n")
                .append("\"score\":550\n")
                .append("}\n")
                .append("}\n")
                .toString();
        WriteToFile(usersFile, sampleUser);

        String sampleIndex = new StringBuilder()
                .append("{\n")
                .append("\"user\":\"USER0006\",\n")
                .append("\"items\":[\n")
                .append("\"ITEM000005\",\n")
                .append("\"ITEM000004\"\n")
                .append("]\n")
                .append("}\n")
                .toString();
        WriteToFile(indexFile, sampleIndex);

        ListInputParser inputParser = new ListInputParser(indexFile, itemsFile, usersFile);

        ShoppingChart shoppingChart = inputParser.parser();
        ArrayList<Item> items = shoppingChart.getItems();
        User user = shoppingChart.getUser();

        assertThat(items.size(), is(2));
        Item item = items.get(0);
        assertThat(item.getDiscount(), is(0.6));
        assertThat(item.getVipDiscount(), is(0.8));
        assertThat(item.getPromotion(), is(false));
        item = items.get(1);
        assertThat(item.getDiscount(), is(1.0));
        assertThat(item.getVipDiscount(), is(1.0));
        assertThat(item.getPromotion(), is(true));

        assertThat(user.getUserCode(), is("USER0006"));
        assertThat(user.getName(), is("新会员"));
        assertThat(user.getIsVIP(), is(true));
        assertThat(user.getScore(), is(0));
    }

    @Test
    public void testNullUserBuyDifferentItems() throws Exception {
        String sampleItem = new StringBuilder()
                .append("{\n")
                .append("\"ITEM000005\":{\n")
                .append("\"name\": \"电池\",\n")
                .append("\"unit\": \"个\",\n")
                .append("\"price\": 2.00,\n")
                .append("\"discount\": 0.6,\n")
                .append("\"vipDiscount\":0.8,\n")
                .append("\"promotion\": false\n")
                .append("},\n")
                .append("\"ITEM000004\":{\n")
                .append("\"name\": \"牛奶\",\n")
                .append("\"unit\": \"盒\",\n")
                .append("\"price\": 5.00,\n")
                .append("\"vipDiscount\":0.8,\n")
                .append("\"promotion\": true\n")
                .append("}\n")
                .append("}\n")
                .toString();
        WriteToFile(itemsFile, sampleItem);

        String sampleUser = new StringBuilder()
                .append("{\n")
                .append("\"USER0001\":{\n")
                .append("\"userCode\":\"USER0001\",\n")
                .append("\"name\":\"武文斌\",\n")
                .append("\"isVIP\":true,\n")
                .append("\"score\":550\n")
                .append("}\n")
                .append("}\n")
                .toString();
        WriteToFile(usersFile, sampleUser);

        String sampleIndex = new StringBuilder()
                .append("{\n")
                .append("\"user\":\"\",\n")
                .append("\"items\":[\n")
                .append("\"ITEM000005\",\n")
                .append("\"ITEM000004\",\n")
                .append("\"ITEM000005\"\n")
                .append("]\n")
                .append("}\n")
                .toString();
        WriteToFile(indexFile, sampleIndex);

        ListInputParser inputParser = new ListInputParser(indexFile, itemsFile, usersFile);

        ShoppingChart shoppingChart = inputParser.parser();
        ArrayList<Item> items = shoppingChart.getItems();
        User user = shoppingChart.getUser();

        assertThat(items.size(), is(3));
        Item item = items.get(0);
        assertThat(item.getDiscount(), is(0.6));
        assertThat(item.getVipDiscount(), is(0.8));
        assertThat(item.getPromotion(), is(false));
        item = items.get(1);
        assertThat(item.getDiscount(), is(1.0));
        assertThat(item.getVipDiscount(), is(1.0));
        assertThat(item.getPromotion(), is(true));

        assertThat(user.getUserCode(), is(nullValue()));
        assertThat(user.getName(), is(nullValue()));
        assertThat(user.getIsVIP(), is(false));
        assertThat(user.getScore(), is(0));
    }

    @Test(expected = BarCodeNotExistException.class)
    public void testExistUserBuyBarCodeNotExistItem() throws Exception {
        String sampleItem = new StringBuilder()
                .append("{\n")
                .append("\"ITEM000005\":{\n")
                .append("\"name\": \"电池\",\n")
                .append("\"unit\": \"个\",\n")
                .append("\"price\": 2.00,\n")
                .append("\"discount\": 0.6,\n")
                .append("\"vipDiscount\":0.8,\n")
                .append("\"promotion\": false\n")
                .append("}\n")
                .append("}\n")
                .toString();
        WriteToFile(itemsFile, sampleItem);

        String sampleUser = new StringBuilder()
                .append("{\n")
                .append("\"USER0001\":{\n")
                .append("\"userCode\":\"USER0001\",\n")
                .append("\"name\":\"武文斌\",\n")
                .append("\"isVIP\":true,\n")
                .append("\"score\":550\n")
                .append("}\n")
                .append("}\n")
                .toString();
        WriteToFile(usersFile, sampleUser);

        String sampleIndex = new StringBuilder()
                .append("{\n")
                .append("\"user\":\"USER0001\",\n")
                .append("\"items\":[\n")
                .append("\"ITEM000007\"\n")
                .append("]\n")
                .append("}\n")
                .toString();
        WriteToFile(indexFile, sampleIndex);

        ListInputParser inputParser = new ListInputParser(indexFile, itemsFile, usersFile);
        ShoppingChart shoppingChart = inputParser.parser();
    }

    @Test(expected = EmptyShoppingChartException.class)
    public void testExistUserEmptyShoppingChart() throws Exception {
        String sampleItem = new StringBuilder()
                .append("{\n")
                .append("\"ITEM000005\":{\n")
                .append("\"name\": \"电池\",\n")
                .append("\"unit\": \"个\",\n")
                .append("\"price\": 2.00,\n")
                .append("\"discount\": 0.6,\n")
                .append("\"vipDiscount\":0.8,\n")
                .append("\"promotion\": false\n")
                .append("}\n")
                .append("}\n")
                .toString();
        WriteToFile(itemsFile, sampleItem);

        String sampleUser = new StringBuilder()
                .append("{\n")
                .append("\"USER0001\":{\n")
                .append("\"userCode\":\"USER0001\",\n")
                .append("\"name\":\"武文斌\",\n")
                .append("\"isVIP\":true,\n")
                .append("\"score\":550\n")
                .append("}\n")
                .append("}\n")
                .toString();
        WriteToFile(usersFile, sampleUser);

        String sampleIndex = new StringBuilder()
                .append("{\n")
                .append("\"user\":\"USER0001\",\n")
                .append("\"items\":[\n")
                .append("]\n")
                .append("}\n")
                .toString();
        WriteToFile(indexFile, sampleIndex);

        ListInputParser inputParser = new ListInputParser(indexFile, itemsFile, usersFile);
        ShoppingChart shoppingChart = inputParser.parser();
    }
}
