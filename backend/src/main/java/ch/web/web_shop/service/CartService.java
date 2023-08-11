package ch.web.web_shop.service;

import ch.web.web_shop.dto.cart.AddToCartDto;
import ch.web.web_shop.dto.cart.CartDto;
import ch.web.web_shop.dto.cart.CartItemDto;
import ch.web.web_shop.exception.CartItemNotExistException;
import ch.web.web_shop.model.Cart;
import ch.web.web_shop.model.Product;
import ch.web.web_shop.model.User;
import ch.web.web_shop.repository.CartRepository;
import ch.web.web_shop.repository.ProductRepository;
import ch.web.web_shop.repository.UserRepository;
import ch.web.web_shop.security.jwt.AuthTokenFilter;
import ch.web.web_shop.security.jwt.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CartService implements CartServiceInterface {
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthTokenFilter authTokenFilter;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    private String getUsernameFromToken(HttpServletRequest token) {
        String jwt = authTokenFilter.parseJwt(token);
        return jwtUtils.getUserNameFromJwtToken(jwt);
    }

    @Override
    public void addToCart(AddToCartDto addToCartDto, long productId, HttpServletRequest token) {

        Optional<User> user = userRepository.findByUsername(getUsernameFromToken(token));
        // Retrieve the product based on the product ID
        Optional<Product> product = productRepository.findById(productId);

        Cart cart = new Cart(product, addToCartDto.getQuantity(), user);
        cartRepository.save(cart);
    }

    @Override
    public CartDto listCartItems(HttpServletRequest token) {

        Optional<User> user = userRepository.findByUsername(getUsernameFromToken(token));

        List<Cart> cartList = cartRepository.findCartByUser(user);
        List<CartItemDto> cartItems = new ArrayList<>();

        for (Cart cart:cartList){
            CartItemDto cartItemDto = getDtoFromCart(cart);
            cartItems.add(cartItemDto);
        }
        double totalCost = 0;
        for (CartItemDto cartItemDto :cartItems){
            totalCost += (cartItemDto.getProduct().getPrice()* cartItemDto.getQuantity());
        }
        return new CartDto(cartItems,totalCost);
    }

    private static CartItemDto getDtoFromCart(Cart cart) {
        return new CartItemDto(cart);
    }

    @Override
    public void updateCartItem(AddToCartDto cartDto){
        Cart cart = cartRepository.getOne(cartDto.getId());
        cart.setQuantity(cartDto.getQuantity());
        cart.setCreatedDate(new Date());
        cartRepository.save(cart);
    }

    @Override
    public void deleteCartItem(long ItemId, HttpServletRequest token) throws CartItemNotExistException {
        Cart cart = cartRepository.findById(ItemId).orElseThrow(() -> new CartItemNotExistException("Cart item not found"));
        long userIdFromCart = cart.getUser().getId();
        long userIdFromToken = userRepository.findByUsername(getUsernameFromToken(token)).get().getId();

        if (userIdFromCart == userIdFromToken) {
            cartRepository.deleteById(ItemId);
        }else{
            throw new CartItemNotExistException("Cart coudn't be deleted");
        }
    }

    @Override
    public void deleteAllCartItemsFromUser(HttpServletRequest token) {
        Optional<User> user = userRepository.findByUsername(getUsernameFromToken(token));
        cartRepository.deleteByUser(user);
    }
}