package ch.web.web_shop.service;


import ch.web.web_shop.exception.ProductNotFoundException;
import ch.web.web_shop.model.Product;
import ch.web.web_shop.model.ShoppingCartPersistence;
import ch.web.web_shop.model.User;
import ch.web.web_shop.repository.ProductRepository;
import ch.web.web_shop.repository.ShoppingCartPersistenceRepository;
import ch.web.web_shop.repository.UserRepository;
import ch.web.web_shop.security.jwt.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class ShoppingCartPersistenceService {

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private ShoppingCartPersistenceRepository shoppingCartPersistenceRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;



    //add product to shopping cart
    public ShoppingCartPersistence addProductToShoppingCart(long productId, HttpServletRequest request) {
        String jwt = parseJwt(request);
        if(StringUtils.hasText(jwt) && jwtUtils.validateJwtToken(jwt) ) {
            String username = jwtUtils.getUserNameFromJwtToken(jwt);

        // Retrieve the user based on the user ID
        Optional<User> user = userRepository.findByUsername(username);
        // Retrieve the product based on the product ID
        Optional<Product> product = productRepository.findById(productId);
        // Check if the user and product exist
            // Create a new shopping cart item
            int quantity = 0;
            quantity++;

            ShoppingCartPersistence shoppingCartPersistence = new ShoppingCartPersistence(user.get(), product.get(), quantity, product.get().getPrice() * quantity);
            // Update the product stock
            product.get().setStock(product.get().getStock() - 1);
            // Save the shopping cart item
             return shoppingCartPersistenceRepository.save(shoppingCartPersistence);
        } else {
            throw new ProductNotFoundException("Product not found");
        }

    }

    //get shopping cart by user
    public ShoppingCartPersistence getShoppingCartByUser(String jwt) {
        // Retrieve the user ID from the Jwt token
        String username = jwtUtils.getUserNameFromJwtToken(jwt);
        // Retrieve the user based on the user ID
        Optional<User> user = userRepository.findByUsername(username);
        // Check if the user exists
        if (user.isPresent()) {
            // Retrieve the shopping cart item based on the user ID
            Optional<ShoppingCartPersistence> shoppingCartPersistence = shoppingCartPersistenceRepository.findByUser(user.get());
            // Check if the shopping cart item exists
            if (shoppingCartPersistence.isPresent()) {
                return shoppingCartPersistence.get();
            } else {
                throw new ProductNotFoundException("Shopping cart not found");
            }
        } else {
            throw new ProductNotFoundException("User not found");
        }
    }


    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7, headerAuth.length());
        }

        return null;
    }



}
