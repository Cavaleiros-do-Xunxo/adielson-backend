package calaveirosdoxunxo.adielson.services;

import calaveirosdoxunxo.adielson.advice.Snowflake;
import calaveirosdoxunxo.adielson.entities.*;
import calaveirosdoxunxo.adielson.enums.Role;
import calaveirosdoxunxo.adielson.enums.Status;
import calaveirosdoxunxo.adielson.models.OrderItemRequest;
import calaveirosdoxunxo.adielson.models.OrderRequest;
import calaveirosdoxunxo.adielson.repositories.AddressRepository;
import calaveirosdoxunxo.adielson.repositories.ItemRepository;
import calaveirosdoxunxo.adielson.repositories.OrderItemRepository;
import calaveirosdoxunxo.adielson.repositories.OrderRepository;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository repository;
    private final OrderItemRepository orderItemsRepository;
    private final ItemRepository menuItems;
    private final Snowflake snowflake;
    private final AddressRepository addresses;

    public OrderService(
            OrderRepository repository, OrderItemRepository orderItemsRepository,
            ItemRepository menuItems, Snowflake snowflake,
            AddressRepository addresses) {
        this.repository = repository;
        this.orderItemsRepository = orderItemsRepository;
        this.menuItems = menuItems;
        this.snowflake = snowflake;
        this.addresses = addresses;
    }

    public List<Order> findAll(Order order, User user) {
        if (user.getRole() == Role.CUSTOMER) {
            order.setUser(user);
        }
        return repository.findAll(Example.of(order));
    }

    public Order find(long id, User user) {
        Order order;
        if (user.getRole() == Role.CUSTOMER) {
            order = this.repository.findByIdAndUser(id, user)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid order"));
        } else {
            order = this.repository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid order"));
        }
        return order;
    }

    public List<OrderItem> items(long id, User user) {
        return this.orderItemsRepository.findAllByOrder(this.find(id, user));
    }

    public Order create(OrderRequest request, User user) {
        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new IllegalArgumentException("Missing items");
        } else if (request.getAddress() == null) {
            throw new IllegalArgumentException("Missing address");
        }
        Order order = new Order();
        order.setId(this.snowflake.next());
        order.setUser(user);
        order.setDelivery(request.getDelivery());
        order.setStatus(Status.WAITING);
        order.setOrderTime(System.currentTimeMillis());
        order.setDeliveryTime(null);
        order.setPayMethod(request.getPaymentMethod());

        Address address = new Address();
        address.setId(this.snowflake.next());
        address.setUser(user);
        address.setAddress(request.getAddress().getAddress());
        address.setComplement(request.getAddress().getComplement());
        this.addresses.save(address);
        order.setAddress(address);

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
