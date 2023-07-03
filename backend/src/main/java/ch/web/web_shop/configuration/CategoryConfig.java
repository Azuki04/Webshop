package ch.web.web_shop.configuration;

import ch.web.web_shop.model.Category;
import ch.web.web_shop.repository.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
/**
 * v1.0
 * @Author Faustina
 * CategoryConfig is used to:
 * - create the categories
 * - save the categories in the database
 */
@Configuration
public class CategoryConfig {
    @Bean
   CommandLineRunner categoryCommandLineRunner(CategoryRepository repository) {
        return args -> {
            //create categories in database
            repository.saveAll(List.of(
                    new Category(1, "Clothing"),
                    new Category(2, "Tv & Audio"),
                    new Category(3, "Toy"),
                    new Category(4, "Tools"),
                    new Category(5, "Shoes"),
                    new Category(6, "Computer & Gaming"),
                    new Category(7, "Household & Kitchen"),
                    new Category(8, "Beauty & Health"),
                    new Category(9, "Sport"),
                    new Category(10, "Office")
            ));
        };
    }




}
