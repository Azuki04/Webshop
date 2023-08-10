package ch.web.web_shop.controller;

import ch.web.web_shop.dto.cart.AddToCartDto;
import ch.web.web_shop.dto.cart.CartDto;
import ch.web.web_shop.service.CartService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add/{productId}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void addToCart(@PathVariable("productId") long productId, @RequestBody AddToCartDto addToCartDto, HttpServletRequest request) {
        cartService.addToCart(addToCartDto, productId, request);
    }
    @GetMapping("")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<CartDto> getCartItems(HttpServletRequest request) {
        CartDto cartDto = cartService.listCartItems(request);
        return new ResponseEntity<CartDto>(cartDto, HttpStatus.OK);
    }
    @PutMapping("/update/{cartItemId}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void updateCartItem(@RequestBody @Valid AddToCartDto cartDto){
        cartService.updateCartItem(cartDto);
    }

    @DeleteMapping("/delete/{cartItemId}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void deleteCartItem(@PathVariable("cartItemId") int itemID) {
        cartService.deleteCartItem(itemID);
    }

    @DeleteMapping("/deleteAll")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void deleteCartItems(HttpServletRequest request) {
        cartService.deleteUserCartItems(request);
    }


}