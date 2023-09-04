
package ch.web.web_shop.repository;

import org.springframework.data.repository.CrudRepository;

import ch.web.web_shop.model.CategoryModel;

import java.util.List;

public interface CategoryRepository extends CrudRepository<CategoryModel, Long> {

    // finds only parent categories

    //find by name
    CategoryModel findByName(String name);


    List<CategoryModel> getSubcategoriesByParentCategoryId(Long parentCategoryId);
}
