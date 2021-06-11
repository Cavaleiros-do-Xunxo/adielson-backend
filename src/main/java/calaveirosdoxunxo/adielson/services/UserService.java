package calaveirosdoxunxo.adielson.services;

import calaveirosdoxunxo.adielson.entities.User;
import calaveirosdoxunxo.adielson.enums.Role;
import calaveirosdoxunxo.adielson.repositories.UserRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;
    private final BCryptPasswordEncoder bcrypt;

    public UserService(UserRepository repository, BCryptPasswordEncoder bcrypt) {
        this.repository = repository;
        this.bcrypt = bcrypt;
    }

    public List<User> findAll(User user, User loggedIn) {
        if (loggedIn.getRole() == Role.CUSTOMER) {
            user = loggedIn;
        }
        ExampleMatcher matcher = ExampleMatcher.matchingAll()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
        return repository.findAll(Example.of(user, matcher));
    }

    public User find(long id, User user) {
        if (user.getRole() == Role.CUSTOMER) {
            return user;
        } else {
            return repository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
        }
    }

    public User patch(User request, long id, User loggedIn) {
        User user = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        if (request.getName() != null) {
            user.setName(request.getName());
        }
        if (request.getPassword() != null) {
            user.setPassword(this.bcrypt.encode(request.getPassword()));
        }
        if (request.getAddress() != null) {
            user.setAddress(request.getAddress());
        }
        if (request.getCpf() != null) {
            if (11 != request.getCpf().toString().length()) {
                throw new IllegalArgumentException("Cpf is invalid!");
            }
            user.setCpf(request.getCpf());
        }
        if (request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }
        if (request.getPhone() != null) {
            user.setPhone(request.getPhone());
        }
        if (request.getRole() != null && loggedIn.getRole() == Role.ADMIN) {
            user.setRole(request.getRole());
        }
        return repository.save(user);
    }

    public void delete(long id) {
        repository.deleteById(id);
    }

}
