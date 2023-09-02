package ch.web.web_shop.repository;


import ch.web.web_shop.model.Cart;
import ch.web.web_shop.model.Product;
import ch.web.web_shop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findCartByUser(Optional<User> user);

    void deleteByUser(Optional<User> user);

   Cart findCartByUserAndProduct(Optional<User> user, Optional<Product> product);
}
