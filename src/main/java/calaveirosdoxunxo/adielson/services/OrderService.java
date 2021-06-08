package calaveirosdoxunxo.adielson.services;

import calaveirosdoxunxo.adielson.advice.Snowflake;
import calaveirosdoxunxo.adielson.entities.MenuItem;
import calaveirosdoxunxo.adielson.entities.Order;
import calaveirosdoxunxo.adielson.entities.OrderItem;
import calaveirosdoxunxo.adielson.entities.User;
import calaveirosdoxunxo.adielson.enums.Role;
import calaveirosdoxunxo.adielson.enums.Status;
import calaveirosdoxunxo.adielson.models.OrderItemRequest;
import calaveirosdoxunxo.adielson.models.OrderRequest;
import calaveirosdoxunxo.adielson.repositories.ItemRepository;
import calaveirosdoxunxo.adielson.repositories.OrderItemRepository;
import calaveirosdoxunxo.adielson.repositories.OrderRepository;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository repository;
    private final OrderItemRepository orderItems;
    private final ItemRepository menuItems;
    private final Snowflake snowflake;

    public OrderService(
            OrderRepository repository, OrderItemRepository orderItems,
            ItemRepository menuItems, Snowflake snowflake
    ) {
        this.repository = repository;
        this.orderItems = orderItems;
        this.menuItems = menuItems;
        this.snowflake = snowflake;
    }

    public List<Order> findAll(Order example, User user) {
        if (user.getRole() == Role.CUSTOMER) {
            example.setUser(user);
        }
        return repository.findAll(Example.of(example));
    }

    public Order find(long id, User user) {
        Optional<Order> oOrder;
        if (user.getRole() == Role.CUSTOMER) {
            oOrder = repository.findByIdAndUser(id, user);
        } else {
            oOrder = repository.findById(id);
        }
        return oOrder.orElseThrow(() -> new IllegalArgumentException("Unknown order"));
    }

    public Order create(OrderRequest request, User user) {
        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new IllegalArgumentException("Missing items");
        }
        Order order = new Order();
        order.setId(this.snowflake.next());
        order.setUser(user);
        order.setDelivery(request.getDelivery());
        order.setStatus(Status.WAITING);
        order.setOrderTime(System.currentTimeMillis());
        order.setDeliveryTime(null);

        double total = 0;
        List<OrderItem> items = new ArrayList<>();
        for (OrderItemRequest model : request.getItems()) {
            MenuItem item = this.menuItems.findById(model.getItem())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid item"));
            OrderItem orderItem = new OrderItem();
            orderItem.setId(this.snowflake.next());
            orderItem.setOrder(order);
            orderItem.setItem(item);
            orderItem.setCount(model.getCount());
            orderItem.setPrice(model.getCount() * item.getPrice());
            items.add(orderItem);
            total += orderItem.getPrice();
        }
        order.setTotal(total);
        this.repository.save(order);
        this.orderItems.saveAll(items);
        return order;
    }

    public Order patch(long id, OrderRequest request) {
        Order order = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Unknown order"));
        order.setStatus(request.getStatus());
        if (request.getStatus() == Status.FINISHED) {
            order.setDeliveryTime(System.currentTimeMillis());
        }
        return this.repository.save(order);
    }

    public void delete(long id) {
        Order order = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Unknown order"));
        this.orderItems.deleteAllByOrder(order);
        this.repository.deleteById(id);
    }

}
