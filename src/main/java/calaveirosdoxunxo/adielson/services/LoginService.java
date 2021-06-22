package calaveirosdoxunxo.adielson.services;

import calaveirosdoxunxo.adielson.advice.Snowflake;
import calaveirosdoxunxo.adielson.entities.User;
import calaveirosdoxunxo.adielson.enums.Role;
import calaveirosdoxunxo.adielson.models.TokenResponse;
import calaveirosdoxunxo.adielson.repositories.UserRepository;
import calaveirosdoxunxo.adielson.spring.properties.SecurityProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class LoginService {

    private final SecurityProperties properties;
    private final UserRepository repository;
    private final BCryptPasswordEncoder encoder;
    private final Snowflake snowflake;

    public LoginService(
            SecurityProperties properties, UserRepository repository,
            BCryptPasswordEncoder encoder, Snowflake snowflake
    ) {
        this.properties = properties;
        this.repository = repository;
        this.encoder = encoder;
        this.snowflake = snowflake;
    }

    public TokenResponse register(User user) {
        if (user.getEmail() == null || user.getName() == null
                || user.getPassword() == null || 11 != user.getCpf().toString().length()) {
            throw new IllegalArgumentException("Missing required fields");
        } else if (repository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email is taken");
        } else if (repository.findByCpf(user.getCpf()).isPresent()) {
            throw new IllegalArgumentException("CPF is taken");
        }
        user.setId(this.snowflake.next());
        if (repository.count() == 0) {
            user.setRole(Role.ADMIN);
        } else {
            user.setRole(Role.CUSTOMER);
        }
        user.setPassword(this.encoder.encode(user.getPassword()));
        repository.save(user);
        return new TokenResponse(this.generateToken(user.getId()));
    }

    public String generateToken(long userId) {
        Date exp = new Date(System.currentTimeMillis() + this.properties.getExpiration());
        Key key = Keys.hmacShaKeyFor(this.properties.getToken().getBytes());
        Claims claims = Jwts.claims()
                .setSubject(String.valueOf(userId));
        return Jwts.builder()
                .setClaims(claims)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(exp)
                .compact();
    }

}
