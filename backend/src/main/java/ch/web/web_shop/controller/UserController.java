package ch.web.web_shop.controller;


import ch.web.web_shop.model.Product;
import ch.web.web_shop.service.ProductService;
import ch.web.web_shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/")
public class UserController {
    @Autowired
    private UserService userService;


    //get all users cart
    @GetMapping("/user/cart/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<Product>> getAllUsersCart(@PathVariable("id") long id) {
        List<Product> products = (List<Product>) userService.getAllUsersCart(id);

        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(products);
    }


}
