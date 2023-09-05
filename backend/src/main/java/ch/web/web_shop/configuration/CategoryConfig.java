package ch.web.web_shop.configuration;

import ch.web.web_shop.model.CategoryModel;
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
            CategoryModel clothing = new CategoryModel("Clothing");
            CategoryModel shoes = new CategoryModel("Shoes", clothing);
            CategoryModel tShirt = new CategoryModel("T-Shirt", clothing);
            CategoryModel pants = new CategoryModel("Pants", clothing);
            CategoryModel shorts = new CategoryModel("shorts", pants);
            CategoryModel cargo = new CategoryModel("Cargo", pants);

            CategoryModel tvAudio = new CategoryModel("Tv & Audio");
            CategoryModel soundBox = new CategoryModel("Sound-Box", tvAudio);

            CategoryModel toy = new CategoryModel("Toy");
            CategoryModel lego = new CategoryModel("Lego", toy);
            CategoryModel ball = new CategoryModel("Ball", toy);
            CategoryModel gameBoy = new CategoryModel("Game-boy", toy);

            CategoryModel tools = new CategoryModel("Tools");

            CategoryModel computerGaming = new CategoryModel("Computer & Gaming");
            CategoryModel display = new CategoryModel("Display", computerGaming);
            CategoryModel computer = new CategoryModel("Computer", computerGaming);
            CategoryModel computerHp = new CategoryModel("HP-Gaming", computer);
            CategoryModel computeRazer = new CategoryModel("Razer-Gaming", computer);

            CategoryModel householdKitchen = new CategoryModel("Household & Kitchen");
            CategoryModel knife = new CategoryModel("Knife", householdKitchen);
            CategoryModel glass = new CategoryModel("Glass");
            CategoryModel beautyHealth = new CategoryModel("Beauty & Health");

            CategoryModel sport = new CategoryModel("Sport");
            CategoryModel tennis = new CategoryModel("Tennis", sport);
            CategoryModel football = new CategoryModel("Football", sport);


            CategoryModel office = new CategoryModel("Office");
            CategoryModel paper = new CategoryModel("Paper", office);
            CategoryModel pen = new CategoryModel("pen", office);

            // Save the categories
            repository.saveAll(List.of(
                    clothing, shoes, tShirt, pants, cargo, shorts,
                    tvAudio, soundBox, toy, lego, ball, gameBoy,
                    tools, computerGaming, display, computer, computerHp, computeRazer,
                    householdKitchen, glass, knife, beautyHealth, sport, tennis, football,
                    office, paper, pen
            ));
        };
    }
}
