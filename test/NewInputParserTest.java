/**
 * Created by 5Wenbin on 2016/6/23.
 */

import com.thoughtworks.pos.domains.Item;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.thoughtworks.pos.services.services.newInputParser;
import com.thoughtworks.pos.common.BarCodeReuseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class NewInputParserTest {

    private File indexFile;

    @Before
    public void setUp() throws Exception {
        indexFile = new File("./sampleIndex.json");
    }

    private void WriteToFile(File file, String content) throws FileNotFoundException {
        PrintWriter printWriter = new PrintWriter(file);
        printWriter.write(content);
        printWriter.close();
    }

    @After
    public void tearDown() throws Exception {
        if(indexFile.exists()){
            indexFile.delete();
        }
    }

    @Test
    public void testSingleSimpleItems() throws Exception {
        String sampleIndex = new StringBuilder()
                .append("[\n")
                .append("{\n")
                .append("\"barcode\":\"ITEM000004\",\n")
                .append("\"name\": \"电池\",\n")
                .append("\"unit\": \"个\",\n")
                .append("\"price\": 2.00\n")
                .append("}\n")
                .append("]\n")
                .toString();
        WriteToFile(indexFile, sampleIndex);

        newInputParser inputParser = new newInputParser(indexFile);
        ArrayList<Item> items = inputParser.parser().getItems();

        assertThat(items.size(), is(1));
        Item item = items.get(0);
        assertThat(item.getBarcode(), is("ITEM000004"));
        assertThat(item.getName(), is("电池"));
        assertThat(item.getUnit(), is("个"));
        assertThat(item.getPrice(), is(2.00));
        assertThat(item.getDiscount(), is(1.0));
    }

    @Test
    public void testSingleItemHasDiscount() throws Exception{
        String sampleIndex = new StringBuilder()
                .append("[\n")
                .append("{\n")
                .append("\"barcode\":\"ITEM000004\",\n")
                .append("\"name\": \"电池\",\n")
                .append("\"unit\": \"个\",\n")
                .append("\"price\": 2.00,\n")
                .append("\"discount\": 0.8\n")
                .append("}\n")
                .append("]\n")
                .toString();
        WriteToFile(indexFile, sampleIndex);

        newInputParser inputParser = new newInputParser(indexFile);
        ArrayList<Item> items = inputParser.parser().getItems();

        assertThat(items.size(), is(1));
        Item item = items.get(0);
        assertThat(item.getBarcode(), is("ITEM000004"));
        assertThat(item.getName(), is("电池"));
        assertThat(item.getUnit(), is("个"));
        assertThat(item.getPrice(), is(2.00));
        assertThat(item.getDiscount(), is(0.8));
    }

    @Test
    public void testSameItemsHasDiscount() throws Exception{
        String sampleIndex = new StringBuilder()
                .append("[\n")
                .append("{\n")
                .append("\"barcode\":\"ITEM000004\",\n")
                .append("\"name\": \"电池\",\n")
                .append("\"unit\": \"个\",\n")
                .append("\"price\": 2.00,\n")
                .append("\"discount\":0.7\n")
                .append("},\n")
                .append("{\n")
                .append("\"barcode\":\"ITEM000004\",\n")
                .append("\"name\": \"电池\",\n")
                .append("\"unit\": \"个\",\n")
                .append("\"price\": 2.00,\n")
                .append("\"discount\":0.7\n")
                .append("}\n")
                .append("]\n")
                .toString();
        WriteToFile(indexFile, sampleIndex);

        newInputParser inputParser = new newInputParser(indexFile);
        ArrayList<Item> items = inputParser.parser().getItems();

        assertThat(items.size(), is(2));
        Item item = items.get(0);
        assertThat(item.getBarcode(), is("ITEM000004"));
        assertThat(item.getName(), is("电池"));
        assertThat(item.getUnit(), is("个"));
        assertThat(item.getPrice(), is(2.00));
        assertThat(item.getDiscount(), is(0.7));
        item = items.get(1);
        assertThat(item.getBarcode(), is("ITEM000004"));
        assertThat(item.getName(), is("电池"));
        assertThat(item.getUnit(), is("个"));
        assertThat(item.getPrice(), is(2.00));
        assertThat(item.getDiscount(), is(0.7));
    }

    @Test
    public void testDifferentItemsHaveDiscount() throws Exception{
        String sampleIndex = new StringBuilder()
                .append("[\n")
                .append("{\n")
                .append("\"barcode\":\"ITEM000004\",\n")
                .append("\"name\": \"电池\",\n")
                .append("\"unit\": \"个\",\n")
                .append("\"price\": 2.00,\n")
                .append("\"discount\": 0.8\n")
                .append("},\n")
                .append("{\n")
                .append("\"barcode\":\"ITEM000003\",\n")
                .append("\"name\": \"可乐\",\n")
                .append("\"unit\": \"罐\",\n")
                .append("\"price\": 4.00,\n")
                .append("\"discount\": 0.7\n")
                .append("}\n")
                .append("]\n")
                .toString();
        WriteToFile(indexFile, sampleIndex);

        newInputParser inputParser = new newInputParser(indexFile);
        ArrayList<Item> items = inputParser.parser().getItems();

        assertThat(items.size(), is(2));
        Item item = items.get(0);
        assertThat(item.getBarcode(), is("ITEM000004"));
        assertThat(item.getName(), is("电池"));
        assertThat(item.getUnit(), is("个"));
        assertThat(item.getPrice(), is(2.00));
        assertThat(item.getDiscount(), is(0.8));
        item = items.get(1);
        assertThat(item.getBarcode(), is("ITEM000003"));
        assertThat(item.getName(), is("可乐"));
        assertThat(item.getUnit(), is("罐"));
        assertThat(item.getPrice(), is(4.00));
        assertThat(item.getDiscount(), is(0.7));
    }

    @Test
    public void testMixedItems() throws Exception{
        String sampleIndex = new StringBuilder()
                .append("[\n")
                .append("{\n")
                .append("\"barcode\":\"ITEM000004\",\n")
                .append("\"name\": \"电池\",\n")
                .append("\"unit\": \"个\",\n")
                .append("\"price\": 2.00\n")
                .append("},\n")
                .append("{\n")
                .append("\"barcode\":\"ITEM000003\",\n")
                .append("\"name\": \"可乐\",\n")
                .append("\"unit\": \"罐\",\n")
                .append("\"price\": 4.00,\n")
                .append("\"discount\": 0.7\n")
                .append("}\n")
                .append("]\n")
                .toString();
        WriteToFile(indexFile, sampleIndex);

        newInputParser inputParser = new newInputParser(indexFile);
        ArrayList<Item> items = inputParser.parser().getItems();

        assertThat(items.size(), is(2));
        Item item = items.get(0);
        assertThat(item.getBarcode(), is("ITEM000004"));
        assertThat(item.getName(), is("电池"));
        assertThat(item.getUnit(), is("个"));
        assertThat(item.getPrice(), is(2.00));
        assertThat(item.getDiscount(), is(1.0));
        item = items.get(1);
        assertThat(item.getBarcode(), is("ITEM000003"));
        assertThat(item.getName(), is("可乐"));
        assertThat(item.getUnit(), is("罐"));
        assertThat(item.getPrice(), is(4.00));
        assertThat(item.getDiscount(), is(0.7));
    }

    @Test(expected = BarCodeReuseException.class)
    public void testDifferentItemsHaveSameBarCode() throws Exception{
        String sampleIndex = new StringBuilder()
                .append("[\n")
                .append("{\n")
                .append("\"barcode\":\"ITEM000004\",\n")
                .append("\"name\": \"电池\",\n")
                .append("\"unit\": \"个\",\n")
                .append("\"price\": 2.00\n")
                .append("},\n")
                .append("{\n")
                .append("\"barcode\":\"ITEM000004\",\n")
                .append("\"name\": \"可乐\",\n")
                .append("\"unit\": \"罐\",\n")
                .append("\"price\": 4.00,\n")
                .append("\"discount\": 0.7\n")
                .append("}\n")
                .append("]\n")
                .toString();
        WriteToFile(indexFile, sampleIndex);
        newInputParser inputParser = new newInputParser(indexFile);
        ArrayList<Item> items = inputParser.parser().getItems();
    }
}

