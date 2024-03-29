package ch.web.web_shop.model;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ProductTest {

    @Test
    void testProductCreation() {
        // Create test data
        String title = "Test Product";
        String description = "This is a test product";
        String content = "Test content";
        int price = 100;
        int stock = 10;
        CategoryModel category = new CategoryModel("Test Category");
        UserModel user= new UserModel(1,"test", "test@test.com", "password");

        // Create a new product
        ProductModel product = new ProductModel(title, description, content, price, stock, category, user);

        // Test the product attributes
        Assertions.assertEquals(title, product.getTitle());
        Assertions.assertEquals(description, product.getDescription());
        Assertions.assertEquals(content, product.getContent());
        Assertions.assertEquals(price, product.getPrice());
        Assertions.assertEquals(stock, product.getStock());
        Assertions.assertEquals(category, product.getCategory());
        Assertions.assertEquals(user, product.getUser());
    }
}
