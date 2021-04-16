package calaveirosdoxunxo.adielson.rest;

import calaveirosdoxunxo.adielson.entities.MenuItem;
import calaveirosdoxunxo.adielson.models.MenuItemRequest;
import calaveirosdoxunxo.adielson.services.ItemsService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
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
        ExampleMatcher matcher = ExampleMatcher.matchingAll()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
        return service.findAll(Example.of(item, matcher));
    }

    @GetMapping("{id}")
    public MenuItem find(@PathVariable("id") long id) {
        return service.find(id);
    }

    @PostMapping
    public MenuItem create(@RequestBody MenuItemRequest item) {
        return service.create(item);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") long id) {
        service.delete(id);
    }

    @PutMapping("{id}")
    public MenuItem update(@PathVariable("id") long id, @RequestBody MenuItemRequest item) {
        return service.update(item, id);
    }

    @PatchMapping("{id}")
    public MenuItem patch(@PathVariable("id") long id, @RequestBody MenuItemRequest item) {
        return service.patch(item, id);
    }
}
