package ch.web.web_shop.service;

import ch.web.web_shop.dto.cart.AddToCartDto;
import ch.web.web_shop.dto.cart.CartDto;
import ch.web.web_shop.model.User;
import jakarta.servlet.http.HttpServletRequest;

public interface CartServiceInterface {

    void addToCart(AddToCartDto addToCartDto, long productId, HttpServletRequest token);

    CartDto listCartItems(HttpServletRequest token);

    void updateCartItem(AddToCartDto cartDto);

    void deleteCartItem(Integer ItemId, HttpServletRequest token);

    void deleteAllCartItemsFromUser(HttpServletRequest token);
}
