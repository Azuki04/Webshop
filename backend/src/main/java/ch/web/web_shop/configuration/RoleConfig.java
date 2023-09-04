package ch.web.web_shop.configuration;


import ch.web.web_shop.model.ERole;
import ch.web.web_shop.model.RoleModel;
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
            RoleModel adminRole = new RoleModel(ERole.ROLE_ADMIN);
            RoleModel customerRole = new RoleModel(ERole.ROLE_CUSTOMER);
            RoleModel sellerRole = new RoleModel(ERole.ROLE_SELLER);

            // Speichere die Rollen in der Datenbank
            repository.saveAll(List.of(adminRole, customerRole, sellerRole));
        };
    }
}
