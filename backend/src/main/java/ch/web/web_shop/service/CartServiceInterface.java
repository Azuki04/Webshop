package ch.web.web_shop.service;

import ch.web.web_shop.dto.cart.AddToCartDto;
import ch.web.web_shop.dto.cart.CartDto;
import ch.web.web_shop.model.User;
import jakarta.servlet.http.HttpServletRequest;

public interface CartServiceInterface {

    public void addToCart(AddToCartDto addToCartDto, long productId, HttpServletRequest token);

    public CartDto listCartItems(HttpServletRequest token);

    public void updateCartItem(AddToCartDto cartDto);

    public void deleteCartItem(int id);

    public void deleteUserCartItems(HttpServletRequest token);
}
