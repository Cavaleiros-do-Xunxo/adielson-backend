package calaveirosdoxunxo.adielson.services;

import calaveirosdoxunxo.adielson.entities.Order;
import calaveirosdoxunxo.adielson.entities.OrderItem;
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

    public OrderItemService(OrderItemRepository repository, OrderRepository orderRepository) {
        this.repository = repository;
        this.orderRepository = orderRepository;
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

    public OrderItem create(OrderItem order) {
        order.setId(new Random().nextInt(1000));// TODO trocar por snowflake
        if (order.getOrder() == null) {
            throw new IllegalArgumentException("Order is null!");
        }
        return repository.save(order);
    }

    public void delete(long id) {
        repository.deleteById(id);
    }
}
