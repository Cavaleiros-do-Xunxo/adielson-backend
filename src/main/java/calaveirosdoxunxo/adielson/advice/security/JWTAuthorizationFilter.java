package calaveirosdoxunxo.adielson.advice.security;

import calaveirosdoxunxo.adielson.advice.RestfulErrorAdviser;
import calaveirosdoxunxo.adielson.entities.User;
import calaveirosdoxunxo.adielson.repositories.UserRepository;
import calaveirosdoxunxo.adielson.spring.properties.SecurityProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private final UserRepository repository;
    private final SecurityProperties properties;

    public JWTAuthorizationFilter(AuthenticationManager authManager, UserRepository repository, SecurityProperties properties) {
        super(authManager);
        this.repository = repository;
        this.properties = properties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(properties.getHeader());
        if (header == null || !header.startsWith(properties.getPrefix())) {
            chain.doFilter(req, res);
            return;
        }
        try {
            UsernamePasswordAuthenticationToken authentication =
                    this.getAuthentication(header.substring(properties.getPrefix().length()));
            if (authentication == null) {
                chain.doFilter(req, res);
                return;
            }
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (MalformedJwtException | ExpiredJwtException | BadCredentialsException ex) {
            RestfulErrorAdviser.handleFilterException(res, ex, 401);
            return;
        }
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(properties.getToken().getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
        if (claims != null) {
            String subject = claims.getSubject();
            Optional<User> oUser = repository.findById(Long.parseLong(subject));
            if (oUser.isEmpty()) {
                throw new BadCredentialsException("Authentication failed");
            }
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(oUser.get().getRole().name()));
            return new UsernamePasswordAuthenticationToken(oUser.get(), null, authorities);
        } else {
            return null;
        }
    }

}
