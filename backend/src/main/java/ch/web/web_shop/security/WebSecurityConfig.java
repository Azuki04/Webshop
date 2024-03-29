package ch.web.web_shop.security;

import ch.web.web_shop.security.jwt.AuthTokenFilter;
import ch.web.web_shop.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private AuthenticationEntryPoint unauthorizedHandler;

    // endpoints that are accessible without authentication
    private final static String[] WHITELIST = { "/api/auth/**","/category/**","/products/published/**","/newsletter", "/api/file/**"};
    // endpoints that are accessible with authentication
    private final static String[] SECURELIST_ADMIN = { "/products/admin/**",};
    private final static String[] SECURELIST_CUSTOMER = { "/cart/**",};
    private final static String[] SECURELIST_SELLER = { "/products/seller/**"};
    // roles that are allowed to access the secure endpoints
    private final static String[] ROLES_ADMIN = {"ADMIN"};
    private final static String[] ROLES_CUSTOMER = {"CUSTOMER"};
    private final static String[] ROLES_SELLER = {"SELLER"};


    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeHttpRequests((authz) ->
                        authz.requestMatchers(WHITELIST).permitAll()
                                .requestMatchers(SECURELIST_ADMIN).hasAnyRole(ROLES_ADMIN)
                                .requestMatchers(SECURELIST_CUSTOMER).hasAnyRole(ROLES_CUSTOMER)
                                .requestMatchers(SECURELIST_SELLER).hasAnyRole(ROLES_SELLER)
                                .anyRequest().authenticated()
                );
        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}