package calaveirosdoxunxo.adielson.models;

import calaveirosdoxunxo.adielson.advice.serializer.LongToStringSerializer;
import calaveirosdoxunxo.adielson.entities.Order;
import calaveirosdoxunxo.adielson.entities.OrderItem;
import calaveirosdoxunxo.adielson.entities.User;
import calaveirosdoxunxo.adielson.enums.DeliveryType;
import calaveirosdoxunxo.adielson.enums.Status;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrderResponse {
    @JsonSerialize(using = LongToStringSerializer.class)
    private Long id;
    private User user;
    private List<OrderAddress> orderAddress;
    private List<OrderMenuItem> orderItems;
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

        List<OrderMenuItem> orderMenuItems = new ArrayList<>();

        for (OrderItem orderItem : orderItems) {
            OrderMenuItem orderMenuItem = OrderMenuItem.build(orderItem.getCount(), orderItem.getPrice(), orderItem.getItem());
            orderMenuItems.add(orderMenuItem);
        }

        this.setOrderItems(orderMenuItems);

        return this;
    }
}
