package ch.web.web_shop.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ch.web.web_shop.model.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {
	List<Product> findByPublished(boolean published);
	List<Product> findByTitleContaining(String title);

}
