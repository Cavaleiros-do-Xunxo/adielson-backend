package calaveirosdoxunxo.adielson.rest;

import calaveirosdoxunxo.adielson.advice.security.SessionProvider;
import calaveirosdoxunxo.adielson.entities.User;
import calaveirosdoxunxo.adielson.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserRest {

    private final UserService service;
    private final SessionProvider session;

    public UserRest(UserService service, SessionProvider session) {
        this.service = service;
        this.session = session;
    }

    @GetMapping
    public List<User> findAll(User user) {
        return service.findAll(user, session.getUser());
    }

    @GetMapping("/{id}")
    public User find(@PathVariable("id") long id) {
        return service.find(id, session.getUser());
    }

    @PatchMapping("/{id}")
    public User patch(@PathVariable("id") long id, @RequestBody User item) {
        return service.patch(item, id, session.getUser());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) {
        service.delete(id);
    }

}
