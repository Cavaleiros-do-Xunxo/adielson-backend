package calaveirosdoxunxo.adielson.repositories;

import calaveirosdoxunxo.adielson.entities.ItemCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<ItemCategory, Long> {

}
