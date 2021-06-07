package calaveirosdoxunxo.adielson.rest;

import calaveirosdoxunxo.adielson.models.LoginRequest;
import calaveirosdoxunxo.adielson.services.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("login")
public class LoginRest {

    private final UserService service;

    public LoginRest(UserService service) {
        this.service = service;
    }

    @PostMapping
    public String find(@RequestBody LoginRequest item) {
        return service.login(item);
    }
}
