package ch.web.web_shop.service;

import ch.web.web_shop.dto.category.CategoryTreeDto;
import ch.web.web_shop.model.Product;
import ch.web.web_shop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ch.web.web_shop.model.Category;
import ch.web.web_shop.repository.CategoryRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
public class CategoryService implements CategoryServiceInterface {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productResponsitory;



    @Autowired
    public CategoryService(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productResponsitory = productRepository;

    }

    /**
     * Retrieves all categories.
     *
     * @return An iterable collection of categories.
     */

    @Override
    @Transactional(readOnly = true)
    public Iterable<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public List<CategoryTreeDto> getAllCategoryTree() {
        Iterable<Category> categories = categoryRepository.findAll();

        return buildCategoryTree((List<Category>) categories);
    }

    private List<CategoryTreeDto> buildCategoryTree(List<Category> categories) {
        List<CategoryTreeDto> parentCategoryDtoList = new ArrayList<>();
        // get all root categories
        for (Category category : categories) {
            if (category.getParentCategory() == null ) {
                CategoryTreeDto categoryTreeDto = new CategoryTreeDto(category.getId(), category.getName());
                parentCategoryDtoList.add(categoryTreeDto);
            }
        }
        for (CategoryTreeDto parentCategoryTreeDto : parentCategoryDtoList) {
            createSubcategoryDto(parentCategoryTreeDto);
        }
        return parentCategoryDtoList;
    }

    private void createSubcategoryDto(CategoryTreeDto parentCategoryTreeDto) {
            List<Category> subCategories = categoryRepository.getSubcategoriesByParentCategoryId(parentCategoryTreeDto.getId());
            for (Category subCategory : subCategories) {
                CategoryTreeDto subCategoryTreeDto = new CategoryTreeDto(subCategory.getId(), subCategory.getName());
                // recursive call
                createSubcategoryDto(subCategoryTreeDto);
                parentCategoryTreeDto.addChildCategory(subCategoryTreeDto);
            }
    }


    @Override
    public Category getCategoryById(long categoryId) {
        return categoryRepository.findById(categoryId).get();
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Product> getAllProductsByCategory(long categoryId) {
        Iterable<Product> products = productResponsitory.findByCategoryId(categoryId);
        return products;
    }

  @Override
 @Transactional(readOnly = true)
 public Iterable<Category> getAllSubCategoriesByParentCategory(long categoryId) {
      Category parentCategory = categoryRepository.findById(categoryId).get();

        Iterable<Category> subCategories = categoryRepository.getSubcategoriesByParentCategoryId(parentCategory.getId());

    return subCategories;
 }


}
