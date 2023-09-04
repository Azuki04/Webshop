package ch.web.web_shop.service;

import ch.web.web_shop.dto.cart.AddToCartDto;
import ch.web.web_shop.dto.cart.CartDto;
import ch.web.web_shop.model.Cart;
import jakarta.servlet.http.HttpServletRequest;

public interface ICartService {

    Cart addToCart(AddToCartDto addToCartDto, HttpServletRequest token);

    CartDto listCartItems(HttpServletRequest token);

    void updateCartItem(AddToCartDto cartDto);

    void deleteCartItem(long ItemId, HttpServletRequest token);

    void deleteAllCartItemsFromUser(HttpServletRequest token);
}
