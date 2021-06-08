package calaveirosdoxunxo.adielson.services;

import calaveirosdoxunxo.adielson.advice.Snowflake;
import calaveirosdoxunxo.adielson.entities.ItemCategory;
import calaveirosdoxunxo.adielson.repositories.CategoryRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository repository;
    private final Snowflake snowflake;

    public CategoryService(CategoryRepository repository, Snowflake snowflake) {
        this.repository = repository;
        this.snowflake = snowflake;
    }

    public List<ItemCategory> findAll(ItemCategory category) {
        ExampleMatcher matcher = ExampleMatcher.matchingAll()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
        return repository.findAll(Example.of(category, matcher));
    }

    public ItemCategory find(long id) {
        Optional<ItemCategory> oItem = repository.findById(id);
        if (oItem.isEmpty()) {
            throw new IllegalArgumentException("Unknown category");
        }
        return oItem.get();
    }

    public ItemCategory create(ItemCategory item) {
        item.setId(this.snowflake.next());
        if (item.getName() == null) {
            throw new IllegalArgumentException("Name is null!");
        }
        return repository.save(item);
    }

    public void delete(long id) {
        repository.deleteById(id);
    }

    public ItemCategory update(long id, ItemCategory item) {
        if (repository.findById(id).isEmpty()) {
            throw new IllegalArgumentException("Unknown category");
        }
        item.setId(id);
        if (item.getName() == null) {
            throw new IllegalArgumentException("Name is null!");
        }
        return repository.save(item);
    }

}
