package ch.web.web_shop.controller;

import ch.web.web_shop.dto.product.ProductDto;
import ch.web.web_shop.model.CategoryModel;
import ch.web.web_shop.model.ProductModel;
import ch.web.web_shop.model.UserModel;
import ch.web.web_shop.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
    void testGetProductById() {
        // Create test data
        long productId = 1;
        ProductModel product = new ProductModel("Test Product", "Test Description", null, 10, 5,
                new CategoryModel(),new UserModel());

        // Mock the productService
        when(productService.getProductById(productId)).thenReturn(product);

        // Call the controller method
        ResponseEntity<ProductModel> response = productController.getProductById(productId);

        // Verify the response
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(product, response.getBody());
    }

    @Test
    void testUpdateProduct() {
        // Create test data
        long productId = 1;
        ProductDto productDTO = new ProductDto("Test Product", "Test Description", null, 10, 5,
                new CategoryModel(),new UserModel());

        // Mock the productService
        ProductModel updatedProduct = new ProductModel("Test Product", "Test Description", null, 10, 5,
                new CategoryModel(),new UserModel());
        when(productService.updateProduct(eq(productId), any(ProductDto.class))).thenReturn(updatedProduct);

        // Call the controller method
        ResponseEntity<ProductModel> response = productController.updateProduct(productId, productDTO);

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

}
