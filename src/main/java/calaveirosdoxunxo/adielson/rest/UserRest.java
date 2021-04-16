package calaveirosdoxunxo.adielson.rest;

import calaveirosdoxunxo.adielson.entities.User;
import calaveirosdoxunxo.adielson.services.UserService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserRest {

    private final UserService service;

    public UserRest(UserService service) {
        this.service = service;
    }

    @GetMapping
    public List<User> findAll(User user) {
        ExampleMatcher matcher = ExampleMatcher.matchingAll()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
        return service.findAll(Example.of(user, matcher));
    }

    @GetMapping("{id}")
    public User find(@PathVariable("id") long id) {
        return service.find(id);
    }

    @PostMapping
    public User create(@RequestBody User item) {
        return service.create(item);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") long id) {
        service.delete(id);
    }

    @PutMapping("{id}")
    public User update(@PathVariable("id") long id, @RequestBody User item) {
        return service.update(item, id);
    }
}
