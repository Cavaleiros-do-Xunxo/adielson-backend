package calaveirosdoxunxo.adielson.services;

import calaveirosdoxunxo.adielson.entities.MenuItem;
import calaveirosdoxunxo.adielson.entities.Order;
import calaveirosdoxunxo.adielson.entities.OrderItem;
import calaveirosdoxunxo.adielson.models.OrderItemRequest;
import calaveirosdoxunxo.adielson.repositories.ItemRepository;
import calaveirosdoxunxo.adielson.repositories.OrderItemRepository;
import calaveirosdoxunxo.adielson.repositories.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class OrderItemService {

    private final OrderItemRepository repository;
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;

    public OrderItemService(OrderItemRepository repository, OrderRepository orderRepository, ItemRepository itemRepository) {
        this.repository = repository;
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
    }

    public List<OrderItem> findAll() {
        return repository.findAll();
    }

    public List<OrderItem> find(Long orderID) {
        if (orderID > 0) {
            throw new IllegalArgumentException("Order ID invalid!");
        }
        Optional<Order> order = orderRepository.findById(orderID);
        if (order.isPresent()) {
            return repository.findAllByOrder(order.get());
        }
        throw new IllegalArgumentException("OrderItem not found!");
    }

    public OrderItem create(OrderItemRequest request) {
        if (request.getOrder() < 1) {
            throw new IllegalArgumentException("Order is null!");
        }
        OrderItem order = new OrderItem();
        Optional<Order> ord = orderRepository.findById(request.getOrder());
        if (ord.isPresent()) {
            order.setOrder(ord.get());
        } else {
            throw new IllegalArgumentException("Order not found!");
        }
        Optional<MenuItem> item = itemRepository.findById(request.getMenuItem());
        if (item.isPresent()) {
            order.setItem(item.get());
        } else {
            throw new IllegalArgumentException("MenuItem not found!");
        }

        order.setId(new Random().nextInt(1000));// TODO trocar por snowflake
        order.setPrice(request.getPrice());
        return repository.save(order);
    }

    public void delete(long id) {
        repository.deleteById(id);
    }
}
