package calaveirosdoxunxo.adielson.advice.security;

import calaveirosdoxunxo.adielson.entities.User;
import calaveirosdoxunxo.adielson.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class JWTAuthProvider implements AuthenticationProvider {

    private final UserRepository repository;
    private final BCryptPasswordEncoder bCrypt;

    public JWTAuthProvider(UserRepository repository, BCryptPasswordEncoder bCrypt) {
        this.repository = repository;
        this.bCrypt = bCrypt;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = String.valueOf(authentication.getCredentials());
        // --
        Optional<User> oUser = repository.findByEmail(email);
        if (oUser.isEmpty()) {
            throw new BadCredentialsException("Authentication failed for " + email);
        }
        User user = oUser.get();
        if (!bCrypt.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Authentication failed for " + email);
        } else if (bCrypt.upgradeEncoding(user.getPassword())) {
            user.setPassword(bCrypt.encode(password));
            repository.save(user);
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(oUser.get().getRole().name()));
        return new UsernamePasswordAuthenticationToken(user, password, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
