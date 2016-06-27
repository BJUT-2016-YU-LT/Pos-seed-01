/**
 * Created by Administrator on 2015/1/2.
 */

import com.thoughtworks.pos.domains.Item;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.thoughtworks.pos.services.services.InputParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class InputParserTest {

    private File indexFile;
    private File itemsFile;

    @Before
    public void setUp() throws Exception {
        indexFile = new File("./sampleIndex.json");
        itemsFile = new File("./itemsFile.json");
    }

    private void WriteToFile(File file, String content) throws FileNotFoundException {
        PrintWriter printWriter = new PrintWriter(file);
        printWriter.write(content);
        printWriter.close();
    }

    @After
    public void tearDown() throws Exception {
        if (indexFile.exists()) {
            indexFile.delete();
        }
        if (itemsFile.exists()) {
            itemsFile.delete();
        }
    }

    @Test
    public void testSimpleItem() throws Exception {
        String sampleIndex = new StringBuilder()
                .append("{\n")
                .append("'ITEM000004':{\n")
                .append("\"name\": '电池',\n")
                .append("\"unit\": '个',\n")
                .append("\"price\": 2.00\n")
                .append("}\n")
                .append("}\n")
                .toString();
        WriteToFile(indexFile, sampleIndex);

        String sampleItems = new StringBuilder()
                .append("[\n")
                .append("\"ITEM000004\"")
                .append("]")
                .toString();
        WriteToFile(itemsFile, sampleItems);

        InputParser inputParser = new InputParser(indexFile, itemsFile);
        ArrayList<Item> items = inputParser.parser().getItems();

        assertThat(items.size(), is(1));
        Item item = items.get(0);
        assertThat(item.getName(), is("电池"));
        assertThat(item.getBarcode(), is("ITEM000004"));
        assertThat(item.getUnit(), is("个"));
        assertThat(item.getPrice(), is(2.00));
        assertThat(item.getDiscount(), is(1.0));
        assertThat(item.getPromotion(), is(false));
    }

    @Test
    public void testSingleItemHasDiscount() throws Exception {
        String sampleIndex = new StringBuilder()
                .append("{\n")
                .append("'ITEM000004':{\n")
                .append("\"name\": '电池',\n")
                .append("\"unit\": '个',\n")
                .append("\"price\": 2.00,\n")
                .append("\"discount\": 0.7\n")
                .append("}\n")
                .append("}\n")
                .toString();
        WriteToFile(indexFile, sampleIndex);

        String sampleItems = new StringBuilder()
                .append("[\n")
                .append("\"ITEM000004\"")
                .append("]")
                .toString();
        WriteToFile(itemsFile, sampleItems);

        InputParser inputParser = new InputParser(indexFile, itemsFile);
        ArrayList<Item> items = inputParser.parser().getItems();
        Item item = items.get(0);
        assertThat(item.getDiscount(), is(0.7));
        assertThat(item.getPromotion(), is(false));
    }

    @Test
    public void testSingleItemHasPromotionItem() throws Exception {
        String sampleIndex = new StringBuilder()
                .append("{\n")
                .append("'ITEM000003':{\n")
                .append("\"name\": '可乐',\n")
                .append("\"unit\": '罐',\n")
                .append("\"price\": 4.00,\n")
                .append("\"promotion\": true\n")
                .append("}\n")
                .append("}\n")
                .toString();
        WriteToFile(indexFile, sampleIndex);

        String sampleItems = new StringBuilder()
                .append("[\n")
                .append("\"ITEM000003\"")
                .append("]")
                .toString();
        WriteToFile(itemsFile, sampleItems);

        InputParser inputParser = new InputParser(indexFile, itemsFile);
        ArrayList<Item> items = inputParser.parser().getItems();

        Item item = items.get(0);
        assertThat(item.getDiscount(), is(1.0));
        assertThat(item.getPromotion(), is(true));
    }

    @Test
    public void testSameItemsHavePromotionItem() throws Exception {
        String sampleIndex = new StringBuilder()
                .append("{\n")
                .append("'ITEM000003':{\n")
                .append("\"name\": '可乐',\n")
                .append("\"unit\": '罐',\n")
                .append("\"price\": 4.00,\n")
                .append("\"promotion\": true\n")
                .append("},\n")
                .append("'ITEM000003':{\n")
                .append("\"name\": '可乐',\n")
                .append("\"unit\": '罐',\n")
                .append("\"price\": 4.00,\n")
                .append("\"promotion\": true\n")
                .append("}\n")
                .append("}\n")
                .toString();
        WriteToFile(indexFile, sampleIndex);

        String sampleItems = new StringBuilder()
                .append("[\n")
                .append("\"ITEM000003\",")
                .append("\"ITEM000003\"")
                .append("]")
                .toString();
        WriteToFile(itemsFile, sampleItems);

        InputParser inputParser = new InputParser(indexFile, itemsFile);
        ArrayList<Item> items = inputParser.parser().getItems();

        assertThat(items.size(), is(2));
        Item item = items.get(0);
        assertThat(item.getDiscount(), is(1.0));
        assertThat(item.getPromotion(), is(true));
        item = items.get(1);
        assertThat(item.getDiscount(), is(1.0));
        assertThat(item.getPromotion(), is(true));
    }

    @Test
    public void testDifferentItemsHavePromotionItemAndDiscount() throws Exception {
        String sampleIndex = new StringBuilder()
                .append("{\n")
                .append("'ITEM000003':{\n")
                .append("\"name\": '可乐',\n")
                .append("\"unit\": '罐',\n")
                .append("\"price\": 4.00,\n")
                .append("\"promotion\": true\n")
                .append("},\n")
                .append("'ITEM000003':{\n")
                .append("\"name\": '可乐',\n")
                .append("\"unit\": '罐',\n")
                .append("\"price\": 4.00,\n")
                .append("\"promotion\": true\n")
                .append("},\n")
                .append("'ITEM000004':{\n")
                .append("\"name\": '电池',\n")
                .append("\"unit\": '节',\n")
                .append("\"price\": 2.00,\n")
                .append("\"discount\": 0.7\n")
                .append("}\n")
                .append("}\n")
                .toString();
        WriteToFile(indexFile, sampleIndex);

        String sampleItems = new StringBuilder()
                .append("[\n")
                .append("\"ITEM000003\",")
                .append("\"ITEM000003\",")
                .append("\"ITEM000004\"")
                .append("]")
                .toString();
        WriteToFile(itemsFile, sampleItems);

        InputParser inputParser = new InputParser(indexFile, itemsFile);
        ArrayList<Item> items = inputParser.parser().getItems();

        assertThat(items.size(), is(3));
        Item item = items.get(0);
        assertThat(item.getDiscount(), is(1.0));
        assertThat(item.getPromotion(), is(true));
        item = items.get(2);
        assertThat(item.getDiscount(), is(0.7));
        assertThat(item.getPromotion(), is(false));
    }

    @Test
    public void testSingleItemHasDiscountAndPromotion() throws Exception {//折扣优先，若无折扣，则采用活动
        String sampleIndex = new StringBuilder()
                .append("{\n")
                .append("'ITEM000004':{\n")
                .append("\"name\": '电池',\n")
                .append("\"unit\": '个',\n")
                .append("\"price\": 2.00,\n")
                .append("\"discount\": 0.7,\n")
                .append("\"promotion\": true\n")
                .append("}\n")
                .append("}\n")
                .toString();
        WriteToFile(indexFile, sampleIndex);

        String sampleItems = new StringBuilder()
                .append("[\n")
                .append("\"ITEM000004\"")
                .append("]")
                .toString();
        WriteToFile(itemsFile, sampleItems);

        InputParser inputParser = new InputParser(indexFile, itemsFile);
        ArrayList<Item> items = inputParser.parser().getItems();
        Item item = items.get(0);
        assertThat(item.getDiscount(), is(1.0));
        assertThat(item.getPromotion(), is(true));
    }
}