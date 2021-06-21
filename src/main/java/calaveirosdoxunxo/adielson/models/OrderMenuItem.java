package calaveirosdoxunxo.adielson.models;

import calaveirosdoxunxo.adielson.entities.MenuItem;
import lombok.Data;

@Data
public class OrderMenuItem {
    private int count;
    private double price;
    private MenuItem menuItem;

    OrderMenuItem(int count, double price, MenuItem menuItem) {
        this.setCount(count);
        this.setPrice(price);
        this.setMenuItem(menuItem);
    }

    static OrderMenuItem build(int count, double price, MenuItem menuItem) {
        return new OrderMenuItem(count, price, menuItem);
    }
}
