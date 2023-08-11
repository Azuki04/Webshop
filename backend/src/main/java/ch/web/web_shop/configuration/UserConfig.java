package ch.web.web_shop.configuration;

import ch.web.web_shop.model.ERole;
import ch.web.web_shop.model.Role;
import ch.web.web_shop.model.User;
import ch.web.web_shop.repository.RoleRepository;
import ch.web.web_shop.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * v1.0
 * @Author Sy Viet
 * UserConfig is used to:
 * - create the users
 * - save the users in the database
 */
@Configuration
public class UserConfig {

    @Bean
    public CommandLineRunner userCommandLineRunner(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        if(userRepository.count() > 0) {
            return null;
        }
        return args -> {
            // Erstelle einen Benutzer
            User user = new User("student", "student@wiss-edu.ch", passwordEncoder.encode("password"));

            // Erstelle eine Rolle für den Benutzer
            Role userRole = roleRepository.findByName(ERole.ROLE_CUSTOMER)
                    .orElseThrow(() -> new RuntimeException("Rolle ROLE_USER nicht gefunden."));

            // Weise die Rolle dem Benutzer zu
            Set<Role> roles = new HashSet<>();
            roles.add(userRole);
            user.setRoles(roles);

            // Speichere den Benutzer in der Datenbank
            userRepository.save(user);

            // Erstelle einen Administratorbenutzer
            User admin = new User("admin", "admin@wiss-edu.ch", passwordEncoder.encode("admin123"));

            // Erstelle eine Rolle für den Administrator
            Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Rolle ROLE_ADMIN nicht gefunden."));

            // Weise die Rolle dem Administrator zu
            Set<Role> adminRoles = new HashSet<>();
            adminRoles.add(adminRole);
            admin.setRoles(adminRoles);

            // Speichere den Administratorbenutzer in der Datenbank
            userRepository.save(admin);
        };
    }
}
