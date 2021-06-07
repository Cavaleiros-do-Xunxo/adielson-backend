package calaveirosdoxunxo.adielson.repositories;

import calaveirosdoxunxo.adielson.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByName(String name);

    Optional<User> findUserByCpf(double cpf);

    Optional<User> findUserByNameAndPassword(String name, String password);
}
