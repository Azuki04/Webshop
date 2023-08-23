package ch.web.web_shop.service;

import ch.web.web_shop.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.web.web_shop.model.Category;
import ch.web.web_shop.repository.CategoryRepository;
import org.springframework.transaction.annotation.Transactional;


/**
 * Category service class.
 * Handles the business logic for category operations.
 * Uses CategoryRepository for data persistence.
 * This class can be used to perform additional business logic if needed.
 * It provides a layer of abstraction between the controller and the repository.
 *
 * @author Sy Viet
 * @version 1.0
 * @see Category
 * @see CategoryRepository
 */
@Service
@Transactional
public class CategoryService  {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Retrieves all categories.
     *
     * @return An iterable collection of categories.
     */

    @Transactional(readOnly = true)
    public Iterable<Category> getAllCategories() {
        return categoryRepository.findAll();
    }


}
