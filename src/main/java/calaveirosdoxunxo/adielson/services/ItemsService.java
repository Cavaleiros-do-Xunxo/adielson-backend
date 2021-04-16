package calaveirosdoxunxo.adielson.services;

import calaveirosdoxunxo.adielson.entities.ItemCategory;
import calaveirosdoxunxo.adielson.entities.MenuItem;
import calaveirosdoxunxo.adielson.models.MenuItemRequest;
import calaveirosdoxunxo.adielson.repositories.CategoryRepository;
import calaveirosdoxunxo.adielson.repositories.ItemRepository;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class ItemsService {

    private final ItemRepository repository;
    private final CategoryRepository categoryRepository;

    public ItemsService(ItemRepository repository, CategoryRepository categoryRepository) {
        this.repository = repository;
        this.categoryRepository = categoryRepository;
    }

    public List<MenuItem> findAll(Example<MenuItem> exemple) {
        return repository.findAll(exemple);
    }

    public MenuItem find(long id) {
        Optional<MenuItem> oItem = repository.findById(id);
        if (oItem.isEmpty()) {
            throw new IllegalArgumentException("ID not found!");
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
        item.setId((long) new Random().nextInt(1000));// TODO trocar por snowflake
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

    public MenuItem update(MenuItemRequest request, long id) {
        if (repository.findById(id).isEmpty()) {
            throw new IllegalArgumentException("Id doesn't exist!");
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
            throw new IllegalArgumentException("Id doesn't exist!");
        }
        MenuItem item = oItem.get();
        item.setInMenu(request.isInMenu());
        return repository.save(item);
    }
}
