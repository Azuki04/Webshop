package ch.web.web_shop.configuration;

import ch.web.web_shop.model.Category;
import ch.web.web_shop.repository.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CategoryConfig {
    @Bean
    CommandLineRunner categoryCommandLineRunner(CategoryRepository repository) {
        if (repository.count() > 0) {
            return null;
        }

        return args -> {
            // Create and save categories in the database
            Category clothing = new Category("Clothing");
            Category shoes = new Category("Shoes", clothing);
            Category tShirt = new Category("T-Shirt", clothing);
            Category pants = new Category("Pants", clothing);
            Category shorts = new Category("shorts", pants);

            Category tvAudio = new Category("Tv & Audio");

            Category toy = new Category("Toy");
            Category lego = new Category("Lego", toy);
            Category ball = new Category("Ball", toy);
            Category gameBoy = new Category("Game-boy", toy);

            Category tools = new Category("Tools");
            Category computerGaming = new Category("Computer & Gaming");
            Category householdKitchen = new Category("Household & Kitchen");
            Category beautyHealth = new Category("Beauty & Health");

            Category sport = new Category("Sport");
            Category tennis = new Category("Tennis", sport);
            Category football = new Category("Football", sport);

            Category office = new Category("Office");
            Category paper = new Category("Paper", office);

            // Save the categories
            repository.saveAll(List.of(
                    clothing, shoes, tShirt, pants,
                    tvAudio,
                    toy, lego, ball, gameBoy,
                    tools, computerGaming, householdKitchen, beautyHealth,
                    sport, tennis, football,
                    office, paper, shorts
            ));
        };
    }
}
