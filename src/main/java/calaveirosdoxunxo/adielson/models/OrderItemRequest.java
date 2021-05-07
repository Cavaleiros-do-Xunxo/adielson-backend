package calaveirosdoxunxo.adielson.models;

import lombok.Data;

@Data
public class OrderItemRequest {
    private long order;
    private long menuItem;
    private double price;
}
