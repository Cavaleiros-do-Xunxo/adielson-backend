package calaveirosdoxunxo.adielson.repositories;

import calaveirosdoxunxo.adielson.entities.Address;
import calaveirosdoxunxo.adielson.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findAllByUser(User user);

}
