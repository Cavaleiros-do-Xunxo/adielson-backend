package calaveirosdoxunxo.adielson.rest;

import calaveirosdoxunxo.adielson.advice.security.SessionProvider;
import calaveirosdoxunxo.adielson.entities.Address;
import calaveirosdoxunxo.adielson.repositories.AddressRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("address")
public class AddressRest {

    private final SessionProvider session;
    private final AddressRepository repository;

    public AddressRest(SessionProvider session, AddressRepository repository) {
        this.session = session;
        this.repository = repository;
    }

    @GetMapping
    public List<Address> findAll() {
        return this.repository.findAllByUser(this.session.getUser());
    }

}
