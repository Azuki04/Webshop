package ch.web.web_shop.service;

import ch.web.web_shop.model.Product;
import ch.web.web_shop.model.User;
import ch.web.web_shop.repository.ProductRepository;
import ch.web.web_shop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;

    //Put User Product in Cart
public void putUserProductInCart(long userId, long productId) {
        // Retrieve the user based on the user ID
        Optional<User> user = userRepository.findById(userId);
        // Retrieve the product based on the product ID
        Optional<Product> product = productRepository.findById(productId);
        // Check if the user and product exist
        if (user.isPresent() && product.isPresent()) {
            // Add the product to the user's cart
            user.get().getPurchasehistory().add(product.get());
            // Save the user
            userRepository.save(user.get());
        }
    }

//Remove User Product from Cart
public void removeUserProductFromCart(long userId, long productId) {
        // Retrieve the user based on the user ID
        Optional<User> user = userRepository.findById(userId);
        // Retrieve the product based on the product ID
        Optional<Product> product = productRepository.findById(productId);
        // Check if the user and product exist
        if (user.isPresent() && product.isPresent()) {
            // Remove the product from the user's cart
            user.get().getPurchasehistory().remove(product.get());
            // Save the user
            userRepository.save(user.get());
        }
    }

//Get Product from user Cart
public Iterable<Product> getAllUsersCart(long userId) {
        // Retrieve the user based on the user ID
        Optional<User> user = userRepository.findById(userId);
        // Check if the user exists
        if (user.isPresent()) {
            // Return the user's cart
            return user.get().getPurchasehistory();
        }
        // Return an empty list if the user does not exist
        return null;
    }




}
