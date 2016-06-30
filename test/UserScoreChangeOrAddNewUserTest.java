import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import com.thoughtworks.pos.common.BarCodeNotExistException;
import com.thoughtworks.pos.common.BarCodeReuseException;
import com.thoughtworks.pos.common.EmptyShoppingChartException;
import com.thoughtworks.pos.domains.Pos;
import com.thoughtworks.pos.domains.ShoppingChart;
import com.thoughtworks.pos.services.services.ListInputParser;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by 5Wenbin on 2016/6/30.
 */
public class UserScoreChangeOrAddNewUserTest {
    private File items = new File("C:\\Users\\5Wenbin\\Desktop\\target\\fixtures\\sampleItems.json");
    private File userShoppingList = new File("C:\\Users\\5Wenbin\\Desktop\\target\\fixtures\\newSampleIndexes.json");
    private File userShoppingList2 = new File("C:\\Users\\5Wenbin\\Desktop\\target\\fixtures\\newSampleIndexes2.json");
    private File users = new File("C:\\Users\\5Wenbin\\Desktop\\target\\fixtures\\sampleUsers.json");

    /*{
        "USER0001": {
        "userCode":"USER0001",
                "name": "武文斌",
                "isVIP": true,
                "score":550
    },
        "USER0002": {
        "userCode":"USER0002",
                "name": "赵佑溪",
                "isVIP": false
    },
        "USER0003": {
        "userCode":"USER0003",
                "name": "牛子儒",
                "isVIP": true
    }
    }*/
    @Test
    public void userScoreChange() throws IOException,BarCodeNotExistException,EmptyShoppingChartException{
        ListInputParser inputParser = new ListInputParser(userShoppingList, items, users);
        ShoppingChart shoppingChart = inputParser.parser();
        Pos pos = new Pos();
        pos.getShoppingList(shoppingChart);
        String actualUsersList = inputParser.saveFile(shoppingChart);
        String expectedUsersList =
                "{"
                        +"\"USER0002\":{"
                        +"\"userCode\":\"USER0002\","
                        +"\"name\":\"赵佑溪\","
                        +"\"isVIP\":false,"
                        +"\"score\":0},"

                        +"\"USER0001\":{"
                        + "\"userCode\":\"USER0001\","
                        +"\"name\":\"武文斌\","
                        +"\"isVIP\":true,"
                        +"\"score\":560},"

                        +"\"USER0003\":{"
                        +"\"userCode\":\"USER0003\","
                        +"\"name\":\"牛子儒\","
                        +"\"isVIP\":true,"
                        +"\"score\":0}"
                +"}";
        assertThat(actualUsersList, is(expectedUsersList));
    }

    @Test
    public void newUserAdd() throws IOException,BarCodeNotExistException,EmptyShoppingChartException{
        ListInputParser inputParser = new ListInputParser(userShoppingList2, items, users);
        ShoppingChart shoppingChart = inputParser.parser();
        Pos pos = new Pos();
        pos.getShoppingList(shoppingChart);
        String actualUsersList = inputParser.saveFile(shoppingChart);
        String expectedUsersList =
                "{"
                        +"\"USER0007\":{"
                        +"\"userCode\":\"USER0007\","
                        +"\"name\":\"新会员\","
                        +"\"isVIP\":true,"
                        +"\"score\":2},"

                        +"\"USER0002\":{"
                        +"\"userCode\":\"USER0002\","
                        +"\"name\":\"赵佑溪\","
                        +"\"isVIP\":false,"
                        +"\"score\":0},"

                        +"\"USER0001\":{"
                        + "\"userCode\":\"USER0001\","
                        +"\"name\":\"武文斌\","
                        +"\"isVIP\":true,"
                        +"\"score\":550},"

                        +"\"USER0003\":{"
                        +"\"userCode\":\"USER0003\","
                        +"\"name\":\"牛子儒\","
                        +"\"isVIP\":true,"
                        +"\"score\":0}"
                        +"}";
        assertThat(actualUsersList, is(expectedUsersList));
    }
}
