package calaveirosdoxunxo.adielson.repositories;

import calaveirosdoxunxo.adielson.entities.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<MenuItem, Long> {

}
