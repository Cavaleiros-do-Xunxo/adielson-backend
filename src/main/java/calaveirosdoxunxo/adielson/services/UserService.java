package calaveirosdoxunxo.adielson.services;

import calaveirosdoxunxo.adielson.entities.User;
import calaveirosdoxunxo.adielson.models.LoginRequest;
import calaveirosdoxunxo.adielson.repositories.UserRepository;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> findAll(Example<User> user) {
        return repository.findAll(user);
    }

    public User find(long id) {
        Optional<User> oUser = repository.findById(id);
        if (oUser.isEmpty()) {
            throw new IllegalArgumentException("ID not found!");
        }
        return oUser.get();
    }

    public User create(User user) {
        if (user.getName() == null) {
            throw new IllegalArgumentException("Name is null!");
        }
        if (user.getPassword() == null) {
            throw new IllegalArgumentException("Password is null!");
        }
        if (user.getCpf() < 100_000_000_00D) {
            throw new IllegalArgumentException("Cpf is invalid!");
        }
        user.setId((long) new Random().nextInt(1000));// TODO trocar por snowflake
        return repository.save(user);
    }

    public User update(User request, long id) {
        if (repository.findById(id).isEmpty()) {
            throw new IllegalArgumentException("Id doesn't exist!");
        }
        if (request.getPassword() == null) {
            throw new IllegalArgumentException("Password is null!");
        }
        if (request.getCpf() < 100_000_000_00D) {
            throw new IllegalArgumentException("Cpf is invalid!");
        }
        User user = new User();
        user.setName(request.getName());
        user.setPassword(request.getPassword());
        user.setAddress(request.getAddress());
        user.setCpf(request.getCpf());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        return repository.save(user);
    }

    public void delete(long id) {
        repository.deleteById(id);
    }

    public String login(LoginRequest login){
        if(login.getName() == null || login.getPassword() == null){
            throw new IllegalArgumentException("User is null!");
        }
        Optional<User> l = repository.findUserByNameAndPassword(login.getName(),login.getPassword());
        if(l.isEmpty()){
            throw new IllegalArgumentException("Invalid dont exist!");
        }
        return token(l.get());
    }

    public String register(User user){
        if(user.getName() == null || user.getPassword() == null || user.getCpf() < 100_000_000_00D){
            throw new IllegalArgumentException("Invalid User!");
        }
        if (repository.findUserByName(user.getName()).isPresent()) {
            throw new IllegalArgumentException("Username is taken");/////////////////////////////////////
        }
        if (repository.findUserByCpf(user.getCpf()).isPresent()) {
            throw new IllegalArgumentException("CPF is used");/////////////////////////////////////////
        }
        user.setId((long) new Random().nextInt(1000));// TODO trocar por snowflake
        user.setPassword(Password(user.getPassword()));
        repository.save(user);
        return token(user);
    }

    public String token(User user){
        return user.getName()+user.getId(); // TODO trocar para token
    }

    public String Password(String password){
        return password; // TODO trocar para criptografia
    }
}
