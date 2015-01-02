/**
 * Created by Administrator on 2015/1/2.
 */

import com.thoughtworks.pos.domains.Item;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.thoughtworks.pos.services.services.InputParser;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class InputParserTest {

    private File file;

    @Before
    public void setUp() throws Exception {
        file = new File("./sampleInput.json");
    }

    @After
    public void tearDown() throws Exception {
        if(file.exists()){
            file.delete();
        }
    }

    @Test
    public void testParseJsonFileToItems() throws Exception {
        PrintWriter printWriter = new PrintWriter(file);
        String sampleInput = new StringBuilder()
                .append("[\n")
                .append("{\n")
                .append("\"barcode\": 'ITEM000004',\n")
                .append("\"name\": '电池',\n")
                .append("\"unit\": '个',\n")
                .append("\"price\": 2.00,\n")
                .append("\"discount\": 0.8\n")
                .append("}\n")
                .append("]")
                .toString();

        printWriter.write(sampleInput);
        printWriter.close();

        InputParser inputParser = new InputParser(file);
        ArrayList<Item> items = inputParser.parser().getItems();

        assertThat(items.size(), is(1));
        Item item = items.get(0);
        assertThat(item.getName(), is("电池"));
        assertThat(item.getBarcode(), is("ITEM000004"));
        assertThat(item.getUnit(), is("个"));
        assertThat(item.getPrice(), is(2.00));
        assertThat(item.getDiscount(), is(0.8));
    }

    @Test
    public void testParseJsonWhenHasNoDiscount() throws Exception {
        PrintWriter printWriter = new PrintWriter(file);
        String sampleInput = new StringBuilder()
                .append("[\n")
                .append("{\n")
                .append("\"barcode\": 'ITEM000004',\n")
                .append("\"name\": '电池',\n")
                .append("\"unit\": '个',\n")
                .append("\"price\": 2.00\n")
                .append("}\n")
                .append("]")
                .toString();

        printWriter.write(sampleInput);
        printWriter.close();

        InputParser inputParser = new InputParser(file);
        ArrayList<Item> items = inputParser.parser().getItems();

        assertThat(items.size(), is(1));
        Item item = items.get(0);
        assertThat(item.getDiscount(), is(1.00));
    }
}
