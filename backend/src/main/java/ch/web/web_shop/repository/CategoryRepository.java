
package ch.web.web_shop.repository;

import org.springframework.data.repository.CrudRepository;

import ch.web.web_shop.model.Category;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    // finds only parent categories

    //find by name
    Category findByName(String name);


    List<Category> getSubcategoriesByParentCategoryId(Long parentCategoryId);
}
