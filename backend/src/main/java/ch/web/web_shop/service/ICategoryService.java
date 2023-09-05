package ch.web.web_shop.service;

import ch.web.web_shop.dto.category.CategoryTreeDto;
import ch.web.web_shop.model.CategoryModel;
import ch.web.web_shop.model.ProductModel;

import java.util.List;

public interface ICategoryService {

    List<CategoryModel> getAllCategories();

    List<ProductModel> getPublishedProductsRecursive(long categoryId);

    List<CategoryModel> getAllSubCategoriesByParentCategory(long categoryId);

    List<CategoryTreeDto> getAllCategoryTree();

}
