package calaveirosdoxunxo.adielson.services;

import calaveirosdoxunxo.adielson.entities.Order;
import calaveirosdoxunxo.adielson.repositories.OrderItemRepository;
import calaveirosdoxunxo.adielson.repositories.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class OrderService {

    private final OrderRepository repository;
    private final OrderItemRepository itemRepository;

    public OrderService(OrderRepository repository, OrderItemRepository orderitemRepository) {
        this.repository = repository;
        this.itemRepository = orderitemRepository;
    }

    public List<Order> findAll() {
        return repository.findAll();
    }

    public Order find(long id) {
        Optional<Order> oOrder = repository.findById(id);
        if (oOrder.isEmpty()) {
            throw new IllegalArgumentException("ID not found!");
        }
        return oOrder.get();
    }

    public Order create(Order order) {
        order.setId(new Random().nextInt(1000));// TODO trocar por snowflake
        if (order.getUser() == null) {
            throw new IllegalArgumentException("User is null!");
        }
        return repository.save(order);
    }

    public void delete(long id) {
        Optional<Order> order = repository.findById(id);
        order.ifPresent(itemRepository::deleteAllByOrder);
        repository.deleteById(id);
    }
}
