package calaveirosdoxunxo.adielson.advice.security;

import calaveirosdoxunxo.adielson.entities.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SessionProvider {

    public boolean hasSession() {
        return SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal() != null;
    }

    public User getUser() {
        return (User) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
    }

    public boolean hasRole(String name) {
        User user = getUser();
        if (user == null) {
            return false;
        }
        return user.getRole().name().equals(name.toUpperCase());
    }

}
