package calaveirosdoxunxo.adielson.models;

import calaveirosdoxunxo.adielson.entities.MenuItem;
import calaveirosdoxunxo.adielson.entities.Order;
import calaveirosdoxunxo.adielson.entities.OrderItem;
import calaveirosdoxunxo.adielson.entities.User;
import calaveirosdoxunxo.adielson.enums.DeliveryType;
import calaveirosdoxunxo.adielson.enums.Status;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrderResponse {
    private Long id;
    private User user;
    private List<OrderAddress> orderAddress;
    private List<MenuItem> orderItems;
    private DeliveryType delivery;
    private Status status;
    private Double total;
    private Long orderTime;
    private Long deliveryTime;

    public OrderResponse build(Order order, List<OrderItem> orderItems) {
        this.setId(order.getId());
        this.setUser(order.getUser());
        this.setOrderAddress(order.getOrderAddress());
        this.setDelivery(order.getDelivery());
        this.setStatus(order.getStatus());
        this.setTotal(order.getTotal());
        this.setOrderTime(order.getOrderTime());
        this.setDeliveryTime(order.getDeliveryTime());

        List<MenuItem> menuItems = new ArrayList<>();

        for (OrderItem orderItem : orderItems) {
            menuItems.add(orderItem.getItem());
        }

        this.setOrderItems(menuItems);

        return this;
    }
}
