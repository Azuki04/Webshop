package ch.web.web_shop.repository;

import java.util.Collection;
import java.util.List;

import ch.web.web_shop.model.CategoryModel;
import ch.web.web_shop.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import ch.web.web_shop.model.ProductModel;

public interface ProductRepository extends JpaRepository<ProductModel, Long> {
    List<ProductModel> getAllProductsByCategory(CategoryModel category);

    List<ProductModel> findByPublished(boolean published);
	List<ProductModel> findByTitleContaining(String title);

	List<ProductModel> findByUser(UserModel user);

    ProductModel findByPublishedAndId(boolean b, long productId);

	List<ProductModel> findByUserAndTitleContaining(UserModel user, String title);

    List<ProductModel> findByTitleContainingAndPublished(String title, boolean published);

    List<ProductModel> findByCategoryId(long categoryId);

    List<ProductModel> findByCategoryName(String categoryName);

    Collection<? extends ProductModel> findByCategoryIdAndPublished(long categoryId, boolean published);
}
