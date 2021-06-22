package calaveirosdoxunxo.adielson.rest;

import calaveirosdoxunxo.adielson.advice.security.SessionProvider;
import calaveirosdoxunxo.adielson.entities.Order;
import calaveirosdoxunxo.adielson.entities.OrderItem;
import calaveirosdoxunxo.adielson.models.OrderRequest;
import calaveirosdoxunxo.adielson.services.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("orders")
public class OrderRest {

    private final OrderService service;
    private final SessionProvider session;

    public OrderRest(OrderService service, SessionProvider session) {
        this.service = service;
        this.session = session;
    }

    @GetMapping
    public List<Order> findAll(Order order) {
        return service.findAll(order, session.getUser());
    }

    @GetMapping("/{id}")
    public Order find(@PathVariable("id") long id) {
        return service.find(id, session.getUser());
    }

    @PostMapping
    public Order create(@RequestBody OrderRequest order) {
        return service.create(order, this.session.getUser());
    }

    @PatchMapping("/{id}")
    public Order patch(@PathVariable("id") long id, @RequestBody OrderRequest request) {
        return service.patch(id, request);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") long id) {
        service.delete(id);
    }

    @GetMapping("/{id}/items")
    public List<OrderItem> items(@PathVariable("id") long id) {
        return this.service.items(id, this.session.getUser());
    }

}
