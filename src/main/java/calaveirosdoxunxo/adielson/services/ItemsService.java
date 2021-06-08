package calaveirosdoxunxo.adielson.services;

import calaveirosdoxunxo.adielson.advice.Snowflake;
import calaveirosdoxunxo.adielson.entities.ItemCategory;
import calaveirosdoxunxo.adielson.entities.MenuItem;
import calaveirosdoxunxo.adielson.models.MenuItemRequest;
import calaveirosdoxunxo.adielson.repositories.CategoryRepository;
import calaveirosdoxunxo.adielson.repositories.ItemRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemsService {

    private final ItemRepository repository;
    private final CategoryRepository categoryRepository;
    private final Snowflake snowflake;

    public ItemsService(ItemRepository repository, CategoryRepository categoryRepository, Snowflake snowflake) {
        this.repository = repository;
        this.categoryRepository = categoryRepository;
        this.snowflake = snowflake;
    }

    public List<MenuItem> findAll(MenuItem example) {
        ExampleMatcher matcher = ExampleMatcher.matchingAll()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
        return repository.findAll(Example.of(example, matcher));
    }

    public MenuItem find(long id) {
        Optional<MenuItem> oItem = repository.findById(id);
        if (oItem.isEmpty()) {
            throw new IllegalArgumentException("Unknown item");
        }
        return oItem.get();
    }

    public MenuItem create(MenuItemRequest request) {
        if (request.getName() == null) {
            throw new IllegalArgumentException("Name is null!");
        }
        if (request.getPrice() < 1) {
            throw new IllegalArgumentException("Price is too low!");
        }
        Optional<ItemCategory> category = categoryRepository.findById(request.getCategory());
        if (category.isEmpty()) {
            throw new IllegalArgumentException("Category not found!");
        }
        MenuItem item = new MenuItem();
        item.setId(this.snowflake.next());
        item.setName(request.getName());
        item.setPrice(request.getPrice());
        item.setImage(request.getImage());
        item.setDescription(request.getDescription());
        item.setInMenu(request.isInMenu());
        item.setCategory(category.get());
        return repository.save(item);
    }

    public void delete(long id) {
        repository.deleteById(id);
    }

    public MenuItem put(MenuItemRequest request, long id) {
        if (repository.findById(id).isEmpty()) {
            throw new IllegalArgumentException("Unknown item");
        }
        if (request.getName() == null) {
            throw new IllegalArgumentException("Name is null!");
        }
        if (request.getPrice() < 1) {
            throw new IllegalArgumentException("Price is too low!");
        }
        Optional<ItemCategory> category = categoryRepository.findById(request.getCategory());
        if (category.isEmpty()) {
            throw new IllegalArgumentException("Category not found!");
        }
        MenuItem item = new MenuItem();
        item.setId(id);
        item.setName(request.getName());
        item.setPrice(request.getPrice());
        item.setImage(request.getImage());
        item.setDescription(request.getDescription());
        item.setInMenu(request.isInMenu());
        item.setCategory(category.get());
        return repository.save(item);
    }

    public MenuItem patch(MenuItemRequest request, long id) {
        Optional<MenuItem> oItem = repository.findById(id);
        if (oItem.isEmpty()) {
            throw new IllegalArgumentException("Unknown item");
        }
        MenuItem item = oItem.get();
        item.setInMenu(request.isInMenu());
        return repository.save(item);
    }

}
