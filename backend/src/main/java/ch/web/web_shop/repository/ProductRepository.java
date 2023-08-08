package ch.web.web_shop.repository;

import java.util.List;
import java.util.Optional;

import ch.web.web_shop.model.User;
import org.springframework.data.repository.CrudRepository;

import ch.web.web_shop.model.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {
	Optional<Product> findById(Long id);
	List<Product> findByPublished(boolean published);
	List<Product> findByTitleContaining(String title);

	List<Product> findByUser(User user);

    Product findByPublishedAndId(boolean b, long productId);

	List<Product> findByUserAndTitleContaining(User user, String title);

    List<Product> findByTitleContainingAndPublished(String title, boolean b);

    Product findOne(long id);
}
