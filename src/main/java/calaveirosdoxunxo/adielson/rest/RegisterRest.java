package calaveirosdoxunxo.adielson.rest;

import calaveirosdoxunxo.adielson.entities.User;
import calaveirosdoxunxo.adielson.models.TokenResponse;
import calaveirosdoxunxo.adielson.services.LoginService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("register")
public class RegisterRest {

    private final LoginService service;

    public RegisterRest(LoginService service) {
        this.service = service;
    }

    @PostMapping
    public TokenResponse register(@RequestBody User user) {
        return service.register(user);
    }

}
