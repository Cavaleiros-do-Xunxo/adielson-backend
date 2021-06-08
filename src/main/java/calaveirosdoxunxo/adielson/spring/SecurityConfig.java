package calaveirosdoxunxo.adielson.spring;

import calaveirosdoxunxo.adielson.advice.security.JWTAuthProvider;
import calaveirosdoxunxo.adielson.advice.security.JWTAuthenticationFilter;
import calaveirosdoxunxo.adielson.advice.security.JWTAuthorizationFilter;
import calaveirosdoxunxo.adielson.repositories.UserRepository;
import calaveirosdoxunxo.adielson.services.LoginService;
import calaveirosdoxunxo.adielson.spring.properties.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JWTAuthProvider authProvider;
    private final UserRepository userRepository;
    private final LoginService service;
    private final SecurityProperties properties;

    public SecurityConfig(
            JWTAuthProvider authProvider,
            UserRepository userRepository,
            LoginService service,
            SecurityProperties properties
    ) {
        this.authProvider = authProvider;
        this.userRepository = userRepository;
        this.service = service;
        this.properties = properties;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers("/actuator/health").permitAll()
                .antMatchers("/actuator/info").permitAll()
                .antMatchers(HttpMethod.GET, "/categories/**").permitAll()
                .antMatchers("/categories/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/items/**").permitAll()
                .antMatchers("/items/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PATCH, "/orders/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/orders/**").hasAuthority("ADMIN")
                .antMatchers("/register").permitAll()
                .antMatchers(HttpMethod.DELETE, "/users/**").hasAuthority("ADMIN")
                .anyRequest().authenticated()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager(), this.service))
                .addFilter(new JWTAuthorizationFilter(authenticationManager(), this.userRepository, this.properties))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder builder) {
        builder.authenticationProvider(authProvider);
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    protected CorsConfigurationSource corsConfigurationSource(CorsConfiguration configuration) {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    protected CorsConfiguration corsConfiguration() {
        CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
        corsConfiguration.addAllowedMethod(HttpMethod.PUT);
        corsConfiguration.addAllowedMethod(HttpMethod.PATCH);
        corsConfiguration.addAllowedMethod(HttpMethod.OPTIONS);
        corsConfiguration.addAllowedMethod(HttpMethod.DELETE);
        return corsConfiguration;
    }

}
