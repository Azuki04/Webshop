package ch.web.web_shop.controller;

import ch.web.web_shop.model.ShoppingCartPersistence;
import ch.web.web_shop.service.ShoppingCartPersistenceService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/cart")
public class ShoppingCartPersistenceController {

@Autowired
private ShoppingCartPersistenceService shoppingCartPersistenceService;


    //addProductToShoppingCar
    @PostMapping("/{productId}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<ShoppingCartPersistence> addProductToShoppingCar(@PathVariable("productId") long productId, HttpServletRequest request) {
        ShoppingCartPersistence createdCart = shoppingCartPersistenceService.addProductToShoppingCart(productId, request);
        return ResponseEntity.ok(createdCart);
    }

    @GetMapping("")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public String getHelloWorld() {
        return "Hello World";
    }



}
