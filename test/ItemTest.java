import com.thoughtworks.pos.domains.Item;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by 5Wenbin on 2016/6/29.
 */

public class ItemTest {
    private Item item0 = new Item(0.9,"ITEM000001", "牛奶Milk", "瓶", 3.00, 0.8, false);
    private Item item1 = new Item(1.0,"ITEM000002", "可乐Cole", "罐", 2.00, 1.0, true);
    private Item item2 = new Item(0.9,"ITEM000003", "可乐Cole", "罐", 2.00, 0.8, true);

    @Test
    public void startPromotion(){
        item0.setPromotion(true);
        assertThat(item0.getPromotion(), is(true));
        assertThat(item0.getDiscount(), is(1.0));
        assertThat(item0.getVipDiscount(), is(1.0));
    }
    @Test
    public void startDiscount(){
        item1.setDiscount(0.6);
        assertThat(item1.getPromotion(), is(false));
        assertThat(item1.getDiscount(), is(0.6));

        item1.setVipDiscount(0.8);
        assertThat(item1.getPromotion(), is(false));
        assertThat(item1.getDiscount(), is(0.6));
        assertThat(item1.getVipDiscount(), is(0.8));
    }
    @Test
    public void whenBothDiscountAndPromotionPromotionFirst(){
        assertThat(item2.getPromotion(), is(true));
        assertThat(item2.getDiscount(), is(1.0));
        assertThat(item2.getVipDiscount(), is(1.0));
    }
}
