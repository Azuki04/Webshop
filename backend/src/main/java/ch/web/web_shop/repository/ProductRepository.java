package ch.web.web_shop.repository;

import java.util.List;

import ch.web.web_shop.model.Category;
import ch.web.web_shop.model.User;
import org.springframework.data.repository.CrudRepository;

import ch.web.web_shop.model.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {
    Iterable<Product> getAllProductsByCategory(Category category);

    List<Product> findByPublished(boolean published);
	List<Product> findByTitleContaining(String title);

	List<Product> findByUser(User user);

    Product findByPublishedAndId(boolean b, long productId);

	List<Product> findByUserAndTitleContaining(User user, String title);

    List<Product> findByTitleContainingAndPublished(String title, boolean b);

    List<Product> findByCategoryId(long categoryId);

    List<Product> findByCategoryName(String categoryName);

}
