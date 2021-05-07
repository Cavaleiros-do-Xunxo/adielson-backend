package calaveirosdoxunxo.adielson.services;

import calaveirosdoxunxo.adielson.entities.Order;
import calaveirosdoxunxo.adielson.entities.User;
import calaveirosdoxunxo.adielson.models.OrderRequest;
import calaveirosdoxunxo.adielson.repositories.OrderItemRepository;
import calaveirosdoxunxo.adielson.repositories.OrderRepository;
import calaveirosdoxunxo.adielson.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class OrderService {

    private final OrderRepository repository;
    private final OrderItemRepository itemRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository repository, OrderItemRepository orderitemRepository, UserRepository userRepository) {
        this.repository = repository;
        this.itemRepository = orderitemRepository;
        this.userRepository = userRepository;
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

    public Order create(OrderRequest request) {
        if (request.getUser() < 1) {
            throw new IllegalArgumentException("User is null!");
        }
        Order order = new Order();

        Optional<User> user = userRepository.findById(request.getUser());
        if (user.isPresent()) {
            order.setUser(user.get());
        } else {
            throw new IllegalArgumentException("User not found!");
        }

        order.setId(new Random().nextInt(1000));// TODO trocar por snowflake
        order.setDelivery(request.getDelivery());
        order.setStatus(request.getStatus());
        order.setTotal(request.getTotal());
        order.setOrderTime(request.getOrderTime());
        order.setDeliveryTime(request.getDeliveryTime());
        return repository.save(order);
    }

    public void delete(long id) {
        Optional<Order> order = repository.findById(id);
        order.ifPresent(itemRepository::deleteAllByOrder);
        repository.deleteById(id);
    }
}
