package ch.web.web_shop.service;

import ch.web.web_shop.dto.category.CategoryTreeDto;
import ch.web.web_shop.model.ProductModel;
import ch.web.web_shop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.web.web_shop.model.CategoryModel;
import ch.web.web_shop.repository.CategoryRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


/**
 * Category service class.
 * Handles the business logic for category operations.
 * Uses CategoryRepository for data persistence.
 * This class can be used to perform additional business logic if needed.
 * It provides a layer of abstraction between the controller and the repository.
 *
 * @author Sy Viet
 * @version 1.0
 * @see CategoryModel
 * @see CategoryRepository
 */
@Service
@Transactional
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;


    @Autowired
    public CategoryService(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;

    }

    /**
     * Retrieves all categories.
     *
     * @return An iterable collection of categories.
     */
    @Override
    @Transactional(readOnly = true)
    public Iterable<CategoryModel> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public List<CategoryTreeDto> getAllCategoryTree() {
        Iterable<CategoryModel> categories = categoryRepository.findAll();

        return buildCategoryTree((List<CategoryModel>) categories);
    }
/*
    public List<CategoryTreeDto> buildCategoryTree2(List<Category> categories) {
        List<CategoryTreeDto> categoryDtoList = new ArrayList<>();
        Map<Long, CategoryTreeDto> categoryDtoMap = new HashMap<>();

        for (Category category : categories) {
            if (category.getParentCategory() == null) {
                CategoryTreeDto categoryTreeDto = new CategoryTreeDto(category.getId(), category.getName());
                categoryDtoList.add(categoryTreeDto);
                categoryDtoMap.put(category.getId(), categoryTreeDto);
            }
        }

        for (Category category : categories) {
            if (category.getParentCategory() != null) {
                CategoryTreeDto parentDto = categoryDtoMap.get(category.getParentCategory().getId());
                if (parentDto != null) {
                    CategoryTreeDto childDto = new CategoryTreeDto(category.getId(), category.getName());
                    parentDto.addChildCategory(childDto);
                    categoryDtoMap.put(category.getId(), childDto);
                }
            }
        }

        return categoryDtoList;
    }
    */

    private List<CategoryTreeDto> buildCategoryTree(List<CategoryModel> categories) {
        List<CategoryTreeDto> parentCategoryDtoList = new ArrayList<>();
        // get all root categories
        getRootCategory(categories, parentCategoryDtoList);
        for (CategoryTreeDto parentCategoryTreeDto : parentCategoryDtoList) {
            createSubcategoryDto(parentCategoryTreeDto);
        }
        return parentCategoryDtoList;
    }

    private static void getRootCategory(List<CategoryModel> categories, List<CategoryTreeDto> parentCategoryDtoList) {
        for (CategoryModel category : categories) {
            if (category.getParentCategory() == null) {
                CategoryTreeDto categoryTreeDto = new CategoryTreeDto(category.getId(), category.getName());
                parentCategoryDtoList.add(categoryTreeDto);
            }
        }
    }

    private void createSubcategoryDto(CategoryTreeDto parentCategoryTreeDto) {
        List<CategoryModel> subCategories = categoryRepository.getSubcategoriesByParentCategoryId(parentCategoryTreeDto.getId());
        for (CategoryModel subCategory : subCategories) {
            CategoryTreeDto subCategoryTreeDto = new CategoryTreeDto(subCategory.getId(), subCategory.getName());
            // recursive call
            createSubcategoryDto(subCategoryTreeDto);
            parentCategoryTreeDto.addChildCategory(subCategoryTreeDto);
        }
    }


    @Override
    public CategoryModel getCategoryById(long categoryId) {
        return categoryRepository.findById(categoryId).get();
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<ProductModel> getAllPublishProductsByCategory(long categoryId) {
        CategoryModel category = categoryRepository.findById(categoryId).orElse(null);
        if (category == null) {
            return Collections.emptyList();
        }

        List<ProductModel> publishedProducts = new ArrayList<>();
        collectPublishedProductsRecursive(category, publishedProducts);

        return publishedProducts;
    }

    private void collectPublishedProductsRecursive(CategoryModel category, List<ProductModel> products) {

        products.addAll(productRepository.findByCategoryIdAndPublished(category.getId(), true));

        List<CategoryModel> subcategories = categoryRepository.getSubcategoriesByParentCategoryId(category.getId());
        for (CategoryModel subCategory : subcategories) {
            collectPublishedProductsRecursive(subCategory, products);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<CategoryModel> getAllSubCategoriesByParentCategory(long categoryId) {
        CategoryModel parentCategory = categoryRepository.findById(categoryId).get();

        return categoryRepository.getSubcategoriesByParentCategoryId(parentCategory.getId());
    }


}
