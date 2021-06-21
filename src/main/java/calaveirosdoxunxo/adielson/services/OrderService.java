package calaveirosdoxunxo.adielson.services;

import calaveirosdoxunxo.adielson.advice.Snowflake;
import calaveirosdoxunxo.adielson.entities.MenuItem;
import calaveirosdoxunxo.adielson.entities.Order;
import calaveirosdoxunxo.adielson.entities.OrderItem;
import calaveirosdoxunxo.adielson.entities.User;
import calaveirosdoxunxo.adielson.enums.Role;
import calaveirosdoxunxo.adielson.enums.Status;
import calaveirosdoxunxo.adielson.models.OrderAddress;
import calaveirosdoxunxo.adielson.models.OrderItemRequest;
import calaveirosdoxunxo.adielson.models.OrderRequest;
import calaveirosdoxunxo.adielson.models.OrderResponse;
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
    private final OrderItemRepository orderItemsRepository;
    private final ItemRepository menuItems;
    private final Snowflake snowflake;

    public OrderService(
            OrderRepository repository, OrderItemRepository orderItemsRepository,
            ItemRepository menuItems, Snowflake snowflake
    ) {
        this.repository = repository;
        this.orderItemsRepository = orderItemsRepository;
        this.menuItems = menuItems;
        this.snowflake = snowflake;
    }

    public List<OrderResponse> findAll(Order order, User user) {
        if (user.getRole() == Role.CUSTOMER) {
            order.setUser(user);
        }

        List<Order> orders = repository.findAll(Example.of(order));
        List<OrderResponse> orderResponses = new ArrayList<>();

        for (Order _order : orders) {
            List<OrderItem> orderItems = orderItemsRepository.findAllByOrder(_order);

            OrderResponse orderResponse = new OrderResponse();
            orderResponse.build(_order, orderItems);
            orderResponses.add(orderResponse);
        }

        return orderResponses;
    }

    public OrderResponse find(long id, User user) {
        Optional<Order> oOrder;

        if (user.getRole() == Role.CUSTOMER) {
            oOrder = repository.findByIdAndUser(id, user);
        } else {
            oOrder = repository.findById(id);
        }

        if (oOrder.isPresent()) {
            Order order = oOrder.orElseThrow();
            List<OrderItem> orderItems = orderItemsRepository.findAllByOrder(order);
            OrderResponse orderResponse = new OrderResponse();
            orderResponse.build(order, orderItems);

            return orderResponse;
        }

        throw new IllegalArgumentException("Unknown order");
    }

    public Order create(OrderRequest request, User user) {
        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new IllegalArgumentException("Missing items");
        } else if (request.getOrderAddress().isEmpty() || request.getOrderAddress() == null) {
            throw new IllegalArgumentException("Missing address");
        }
        Order order = new Order();
        order.setId(this.snowflake.next());
        order.setUser(user);
        order.setDelivery(request.getDelivery());
        order.setStatus(Status.WAITING);
        order.setOrderTime(System.currentTimeMillis());
        order.setDeliveryTime(null);
        order.setOrderAddress(request.getOrderAddress());

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

        for (OrderAddress address : request.getOrderAddress()) {
            OrderAddress orderAdd = new OrderAddress();
            orderAdd.setAddress(address.getAddress());
            orderAdd.setAddress2(address.getAddress2());
            orderAdd.setZipCode(address.getZipCode());

        }

        order.setTotal(total);
        this.repository.save(order);
        this.orderItemsRepository.saveAll(items);
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
        this.orderItemsRepository.deleteAllByOrder(order);
        this.repository.deleteById(id);
    }

}
