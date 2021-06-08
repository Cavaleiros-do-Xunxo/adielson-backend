package calaveirosdoxunxo.adielson.rest;

import calaveirosdoxunxo.adielson.entities.MenuItem;
import calaveirosdoxunxo.adielson.models.MenuItemRequest;
import calaveirosdoxunxo.adielson.services.ItemsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("items")
public class ItemsRest {

    private final ItemsService service;

    public ItemsRest(ItemsService service) {
        this.service = service;
    }

    @GetMapping
    public List<MenuItem> findAll(MenuItem item) {
        return service.findAll(item);
    }

    @GetMapping("/{id}")
    public MenuItem find(@PathVariable("id") long id) {
        return service.find(id);
    }

    @PostMapping
    public MenuItem create(@RequestBody MenuItemRequest item) {
        return service.create(item);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) {
        service.delete(id);
    }

    @PutMapping("/{id}")
    public MenuItem put(@PathVariable("id") long id, @RequestBody MenuItemRequest item) {
        return service.put(item, id);
    }

    @PatchMapping("/{id}")
    public MenuItem patch(@PathVariable("id") long id, @RequestBody MenuItemRequest item) {
        return service.patch(item, id);
    }

}
