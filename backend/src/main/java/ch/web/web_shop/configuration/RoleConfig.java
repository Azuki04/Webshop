package ch.web.web_shop.configuration;


import ch.web.web_shop.model.ERole;
import ch.web.web_shop.model.Role;
import ch.web.web_shop.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * v1.0
 * @Author Sy Viet
 * RoleConfig is used to:
 * - create the roles
 * - save the roles in the database
 */
@Configuration
public class RoleConfig {

    @Bean
    public CommandLineRunner roleCommandLineRunner(RoleRepository repository) {
        if(repository.count() > 0) {
            return null;
        }
        return args -> {
            // Erstelle die gewünschten Rollen
            Role adminRole = new Role(ERole.ROLE_ADMIN);
            Role userRole = new Role(ERole.ROLE_USER);
            Role moderatorRole = new Role(ERole.ROLE_MODERATOR);

            // Speichere die Rollen in der Datenbank
            repository.saveAll(List.of(adminRole, userRole, moderatorRole));
        };
    }
}
