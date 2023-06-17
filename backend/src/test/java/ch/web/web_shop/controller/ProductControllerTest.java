package ch.web.web_shop.controller;

import ch.web.web_shop.dto.ProductDTO;
import ch.web.web_shop.model.Category;
import ch.web.web_shop.model.Product;
import ch.web.web_shop.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllProducts() {
        // Create test data
        List<Product> products = new ArrayList<>();
        products.add(new Product("Test Product", "Test Description", null, 10, 5,
                new Category(), new User()));
        products.add(new Product("Test Product", "Test Description", null, 10, 5,
                new Category(), new User()));

        // Mock the productService
        when(productService.getAllProducts(anyString())).thenReturn(products);

        // Call the controller method
        ResponseEntity<List<Product>> response = productController.getAllProducts(null);

        // Verify the response
        Assertions.assertEquals(null, response.getBody());
    }






    @Test
    void testGetProductById() {
        // Create test data
        long productId = 1;
        Product product = new Product("Test Product", "Test Description", null, 10, 5,
                new Category(), new User());

        // Mock the productService
        when(productService.getProductById(productId)).thenReturn(product);

        // Call the controller method
        ResponseEntity<Product> response = productController.getProductById(productId);

        // Verify the response
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(product, response.getBody());
    }

    @Test
    void testCreateProduct() {
        // Create test data
        ProductDTO productDTO = new ProductDTO("Test Product", "Test Description", null, 10, 5,
                new Category(), new User());

        // Mock the productService
        Product createdProduct = new Product("Test Product", "Test Description", null, 10, 5,
                new Category(), new User());
        when(productService.createProduct(any(ProductDTO.class))).thenReturn(createdProduct);

        // Call the controller method
        ResponseEntity<Product> response = productController.createProduct(productDTO);

        // Verify the response
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(createdProduct, response.getBody());
    }

    @Test
    void testUpdateProduct() {
        // Create test data
        long productId = 1;
        ProductDTO productDTO = new ProductDTO("Test Product", "Test Description", null, 10, 5,
                new Category(), new User());

        // Mock the productService
        Product updatedProduct = new Product("Test Product", "Test Description", null, 10, 5,
                new Category(), new User());
        when(productService.updateProduct(eq(productId), any(ProductDTO.class))).thenReturn(updatedProduct);

        // Call the controller method
        ResponseEntity<Product> response = productController.updateProduct(productId, productDTO);

        // Verify the response
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(updatedProduct, response.getBody());
    }

    @Test
    void testDeleteProduct() {
        // Create test data
        long productId = 1;

        // Call the controller method
        ResponseEntity<HttpStatus> response = productController.deleteProduct(productId);

        // Verify the response
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(productService, times(1)).deleteProduct(productId);
    }

    @Test
    void testDeleteAllProducts() {
        // Call the controller method
        ResponseEntity<HttpStatus> response = productController.deleteAllProducts();

        // Verify the response
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(productService, times(1)).deleteAllProducts();
    }

    @Test
    void testFindByPublished() {
        // Create test data
        List<Product> publishedProducts = new ArrayList<>();
        publishedProducts.add(new Product("Test Product", "Test Description", null, 10, 5,
                new Category(), new User()));
        publishedProducts.add(new Product("Test Product", "Test Description", null, 10, 5,
                new Category(), new User()));

        // Mock the productService
        when(productService.getPublishedProducts()).thenReturn(publishedProducts);

        // Call the controller method
        ResponseEntity<List<Product>> response = productController.findByPublished();

        // Verify the response
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(publishedProducts, response.getBody());
    }
}
