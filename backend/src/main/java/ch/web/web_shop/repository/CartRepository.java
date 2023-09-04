package ch.web.web_shop.repository;


import ch.web.web_shop.model.CartModel;
import ch.web.web_shop.model.ProductModel;
import ch.web.web_shop.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<CartModel, Long> {

    List<CartModel> findCartByUser(Optional<UserModel> user);

    void deleteByUser(Optional<UserModel> user);

   CartModel findCartByUserAndProduct(Optional<UserModel> user, Optional<ProductModel> product);
}
