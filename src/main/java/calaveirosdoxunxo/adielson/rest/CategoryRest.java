package calaveirosdoxunxo.adielson.rest;

import calaveirosdoxunxo.adielson.entities.ItemCategory;
import calaveirosdoxunxo.adielson.services.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("categories")
public class CategoryRest {

    private final CategoryService service;

    public CategoryRest(CategoryService service) {
        this.service = service;
    }

    @GetMapping
    public List<ItemCategory> findAll(ItemCategory example) {
        return service.findAll(example);
    }

    @GetMapping("/{id}")
    public ItemCategory find(@PathVariable("id") long id) {
        return service.find(id);
    }

    @PostMapping
    public ItemCategory create(@RequestBody ItemCategory category) {
        return service.create(category);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) {
        service.delete(id);
    }

    @PutMapping("/{id}")
    public ItemCategory update(@PathVariable("id") long id, @RequestBody ItemCategory category) {
        return service.update(id, category);
    }

}
