package calaveirosdoxunxo.adielson.rest;

import calaveirosdoxunxo.adielson.entities.OrderItem;
import calaveirosdoxunxo.adielson.models.OrderItemRequest;
import calaveirosdoxunxo.adielson.services.OrderItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("orderItem")
public class OrderItemRest {

    private final OrderItemService service;

    public OrderItemRest(OrderItemService service) {
        this.service = service;
    }

    @GetMapping
    public List<OrderItem> findAll() {
        return service.findAll();
    }

    @GetMapping("{order}")
    public List<OrderItem> find(@PathVariable("order") Long orderID) {
        return service.find(orderID);
    }

    @PostMapping
    public OrderItem create(@RequestBody OrderItemRequest order) {
        return service.create(order);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") long id) {
        service.delete(id);
    }
}
