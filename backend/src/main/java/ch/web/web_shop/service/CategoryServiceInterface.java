package ch.web.web_shop.service;

import ch.web.web_shop.model.Category;
import ch.web.web_shop.model.Product;

import java.util.List;

public interface CategoryServiceInterface {

    Iterable<Category> getAllCategories();

    List<Product> getAllProductsByCategory(long categoryId);

    Category getCategoryById(long categoryId);

}
