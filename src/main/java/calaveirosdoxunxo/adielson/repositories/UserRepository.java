package calaveirosdoxunxo.adielson.repositories;

import calaveirosdoxunxo.adielson.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
