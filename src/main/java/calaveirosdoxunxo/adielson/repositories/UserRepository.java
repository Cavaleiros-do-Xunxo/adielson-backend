package calaveirosdoxunxo.adielson.repositories;

import calaveirosdoxunxo.adielson.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByCpf(long cpf);

    Optional<User> findByEmail(String email);

}
