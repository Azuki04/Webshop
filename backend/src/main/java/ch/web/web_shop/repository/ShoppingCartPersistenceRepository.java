package ch.web.web_shop.repository;

import ch.web.web_shop.model.ShoppingCartPersistence;
import ch.web.web_shop.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ShoppingCartPersistenceRepository extends CrudRepository<ShoppingCartPersistence, Long>{
    ShoppingCartPersistence findByUserAndProduct(long userId, long productId);

    Optional<ShoppingCartPersistence> findByUser(User user);
}
