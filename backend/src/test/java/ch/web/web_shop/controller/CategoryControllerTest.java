package ch.web.web_shop.controller;

import ch.web.web_shop.model.CategoryModel;
import ch.web.web_shop.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllCategories() {
        // Prepare test data
        CategoryModel category1 = new CategoryModel("Category 1");
        CategoryModel category2 = new CategoryModel("Category 2");

        List<CategoryModel> categories = new ArrayList<>();
        categories.add(category1);
        categories.add(category2);

        // Mock the service method
        when(categoryService.getAllCategories()).thenReturn(categories);

        // Call the controller method
        ResponseEntity<List<CategoryModel>> response = categoryController.getAllCategories();

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(categories, response.getBody());

        // Verify that the service method was called
        verify(categoryService, times(1)).getAllCategories();
    }

    @Test
    void testGetAllCategories_Exception() {
        // Mock the service method to throw an exception
        when(categoryService.getAllCategories()).thenThrow(RuntimeException.class);

        // Call the controller method
        ResponseEntity<List<CategoryModel>> response = categoryController.getAllCategories();

        // Verify the response
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(null, response.getBody());

        // Verify that the service method was called
        verify(categoryService, times(1)).getAllCategories();
    }
}
