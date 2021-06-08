package calaveirosdoxunxo.adielson.repositories;

import calaveirosdoxunxo.adielson.entities.Order;
import calaveirosdoxunxo.adielson.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByIdAndUser(long id, User user);

}
