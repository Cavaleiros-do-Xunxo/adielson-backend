package calaveirosdoxunxo.adielson.rest;

import calaveirosdoxunxo.adielson.entities.User;
import calaveirosdoxunxo.adielson.services.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("register")
public class RegisterRest {

    private final UserService service;

    public RegisterRest(UserService service) {
        this.service = service;
    }

    @PostMapping
    public String create(@RequestBody User item) {
        return service.register(item);
    }
}
