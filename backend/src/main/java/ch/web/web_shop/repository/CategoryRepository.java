
package ch.web.web_shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.web.web_shop.model.CategoryModel;

import java.util.List;

public interface CategoryRepository extends JpaRepository<CategoryModel, Long> {

    //find by name
    CategoryModel findByName(String name);


    List<CategoryModel> getSubcategoriesByParentCategoryId(Long parentCategoryId);
}
