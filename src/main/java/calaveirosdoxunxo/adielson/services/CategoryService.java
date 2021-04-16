package calaveirosdoxunxo.adielson.services;

import calaveirosdoxunxo.adielson.entities.ItemCategory;
import calaveirosdoxunxo.adielson.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class CategoryService {

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public List<ItemCategory> findAll() {
        return repository.findAll();
    }

    public ItemCategory find(long id) {
        Optional<ItemCategory> oItem = repository.findById(id);
        if (oItem.isEmpty()) {
            throw new IllegalArgumentException("ID not found!");
        }
        return oItem.get();
    }

    public ItemCategory create(ItemCategory item) {
        item.setId(new Random().nextInt(1000));// TODO trocar por snowflake
        if (item.getName() == null) {
            throw new IllegalArgumentException("Name is null!");
        }
        return repository.save(item);
    }

    public void delete(long id) {
        repository.deleteById(id);
    }

    public ItemCategory update(ItemCategory item) {
        if (repository.findById(item.getId()).isEmpty()) {
            throw new IllegalArgumentException("Id doesn't exist!");
        }
        if (item.getName() == null) {
            throw new IllegalArgumentException("Name is null!");
        }
        return repository.save(item);
    }
}
