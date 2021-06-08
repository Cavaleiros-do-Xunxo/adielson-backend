package calaveirosdoxunxo.adielson.advice.security;

import calaveirosdoxunxo.adielson.entities.User;
import calaveirosdoxunxo.adielson.models.AuthRequest;
import calaveirosdoxunxo.adielson.services.LoginService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final LoginService service;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, LoginService service) {
        this.authenticationManager = authenticationManager;
        this.service = service;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException {
        try {
            AuthRequest credentials = new ObjectMapper().readValue(req.getInputStream(), AuthRequest.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    credentials.getEmail(), credentials.getPassword(), new ArrayList<>()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
                                            Authentication auth) {
        User user = (User) auth.getPrincipal();
        String token = this.service.generateToken(user.getId());
        try {
            JSONObject object = new JSONObject();
            object.put("token", token);
            res.getWriter().write(object.toString());
            res.addHeader("Content-Type", "application/json");
        } catch (IOException ex) {
            logger.error("Failed to generate JWT token", ex);
        }
    }

}
