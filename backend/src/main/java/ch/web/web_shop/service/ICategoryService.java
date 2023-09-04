package ch.web.web_shop.service;

import ch.web.web_shop.dto.category.CategoryTreeDto;
import ch.web.web_shop.model.Category;
import ch.web.web_shop.model.Product;

import java.util.List;

public interface ICategoryService {

    Iterable<Category> getAllCategories();

    Iterable<Product> getAllProductsByCategory(long categoryId);

    Category getCategoryById(long categoryId);

    Iterable<Category> getAllSubCategoriesByParentCategory(long categoryId);

    List<CategoryTreeDto> getAllCategoryTree();

}
