package calaveirosdoxunxo.adielson.rest;

import calaveirosdoxunxo.adielson.entities.Order;
import calaveirosdoxunxo.adielson.models.OrderRequest;
import calaveirosdoxunxo.adielson.services.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("order")
public class OrderRest {

    private final OrderService service;

    public OrderRest(OrderService service) {
        this.service = service;
    }

    @GetMapping
    public List<Order> findAll() {
        return service.findAll();
    }

    @GetMapping("{id}")
    public Order find(@PathVariable("id") long id) {
        return service.find(id);
    }

    @PostMapping
    public Order create(@RequestBody OrderRequest order) {
        return service.create(order);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") long id) {
        service.delete(id);
    }
}
