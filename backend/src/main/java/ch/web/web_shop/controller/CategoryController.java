package ch.web.web_shop.controller;

import ch.web.web_shop.dto.category.CategoryTreeDto;
import ch.web.web_shop.model.Product;
import ch.web.web_shop.service.CategoryServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ch.web.web_shop.model.Category;
import ch.web.web_shop.service.CategoryService;

import java.util.List;

/**
 * Category controller class.
 * This class is used to manage categories.
 * The @RestController annotation combines the @Controller and @ResponseBody annotations,
 * simplifying the code and eliminating the need for individual @ResponseBody annotations.
 * The @RequestMapping("/api/category") annotation informs that this controller
 * will process requests whose URI begins with "/api/category".
 * Handles HTTP methods such as GET, POST, etc. using appropriate mapping annotations.
 * Manages and provides access to Category objects.
 * Uses CategoryService to retrieve and manipulate data.
 * <p>
 * Note: Make sure to inject the CategoryService dependency in this controller.
 * <p>
 * Example:
 *
 * @version 1.0
 * @Autowired private CategoryService categoryService;
 * <p>
 * And use categoryService.getAllCategories() to retrieve categories.
 * <p>
 * Alternatively, you can use constructor injection instead of field injection.
 * <p>
 * Example:
 * <p>
 * private final CategoryService categoryService;
 * @Autowired public CategoryController(CategoryService categoryService) {
 * this.categoryService = categoryService;
 * }
 * <p>
 * This approach is recommended for better testability and maintainability.
 * <p>
 * Update the code accordingly based on your preference.
 * @Author Sy Viet
 * @see Category
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryServiceInterface categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * Retrieves all categories.
     *
     * @return ResponseEntity with a list of categories if successful,
     * or an error response if an exception occurs.
     */
    @GetMapping("")
    public ResponseEntity<Iterable<Category>> getAllCategories() {
        try {
            Iterable<Category> categories = categoryService.getAllCategories();
            return ResponseEntity.ok(categories);
        } catch (Exception ex) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @GetMapping("/categoryTree")
    public ResponseEntity<List<CategoryTreeDto>> getCategoryTree() {

        try {
            return ResponseEntity.ok(categoryService.getAllCategoryTree());

        } catch (Exception ex) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/subcategories/{id}")
    public ResponseEntity<Iterable<Category>> getAllSubCategoriesByParentCategory(@PathVariable("id") long categoryId) {
        try {
            Iterable<Category> categories = categoryService.getAllSubCategoriesByParentCategory(categoryId);
            return ResponseEntity.ok(categories);
        } catch (Exception ex) {
            System.out.println("no parent category");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

        }
    }


    @GetMapping("/products/{id}")
    public ResponseEntity<Iterable<Product>> getAllProductsByCategory(@PathVariable("id") long categoryId) {
        try {
            Iterable<Product> products = categoryService.getAllProductsByCategory(categoryId);
            return ResponseEntity.ok(products);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
