package ch.web.web_shop.service;

import ch.web.web_shop.dto.cart.AddToCartDto;
import ch.web.web_shop.dto.cart.CartDto;
import ch.web.web_shop.dto.cart.CartItemDto;
import ch.web.web_shop.dto.product.IProductDtoMapper;
import ch.web.web_shop.exception.CartItemNotExistException;
import ch.web.web_shop.model.CartModel;
import ch.web.web_shop.model.ProductModel;
import ch.web.web_shop.model.UserModel;
import ch.web.web_shop.repository.CartRepository;
import ch.web.web_shop.repository.ProductRepository;
import ch.web.web_shop.repository.UserRepository;
import ch.web.web_shop.security.jwt.AuthTokenFilter;
import ch.web.web_shop.security.jwt.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CartService implements ICartService {


    private final JwtUtils jwtUtils;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final AuthTokenFilter authTokenFilter;
    private final IProductDtoMapper productDtoMapper;

    @Autowired
    public CartService(
            JwtUtils jwtUtils,
            CartRepository cartRepository,
            ProductRepository productRepository,
            UserRepository userRepository,
            AuthTokenFilter authTokenFilter,
            IProductDtoMapper productDtoMapper) {
        this.jwtUtils = jwtUtils;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.authTokenFilter = authTokenFilter;
        this.productDtoMapper = productDtoMapper;
    }
    private String getUsernameFromToken(HttpServletRequest token) {
        String jwt = authTokenFilter.parseJwt(token);
        return jwtUtils.getUserNameFromJwtToken(jwt);
    }

    @Override
    public CartModel addToCart(AddToCartDto addToCartDto, HttpServletRequest token) {
        Optional<UserModel> user = userRepository.findByUsername(getUsernameFromToken(token));
        Optional<ProductModel> product = productRepository.findById(addToCartDto.getProductId());
        CartModel cartProduct = cartRepository.findCartByUserAndProduct(user, product);

        if (cartProduct == null) {
            return createNewCartEntity(addToCartDto, product, user);
        } else {
            updateQuantityCart(addToCartDto, cartProduct);
            return cartProduct;
        }
    }

    private CartModel createNewCartEntity(AddToCartDto addToCartDto, Optional<ProductModel> product, Optional<UserModel> user) {
        CartModel cart = new CartModel(product, addToCartDto.getQuantity(), user);
        cartRepository.save(cart);
        return cart;
    }

    private void updateQuantityCart(AddToCartDto addToCartDto, CartModel cartProduct) {
        int updatedQuantity = addToCartDto.getQuantity() + cartProduct.getQuantity();
        if(cartProduct.getProduct().getStock() < updatedQuantity){
            updatedQuantity = cartProduct.getProduct().getStock();
        }
        cartProduct.setQuantity(updatedQuantity);
        cartProduct.setCreatedDate(new Date());
        cartRepository.save(cartProduct);
    }

    @Override
    public CartDto listCartItems(HttpServletRequest token) {

        Optional<UserModel> user = userRepository.findByUsername(getUsernameFromToken(token));

        List<CartModel> cartList = cartRepository.findCartByUser(user);

        List<CartItemDto> cartItems = getCartItems(cartList);

        double totalCost = getTotalCost(cartItems);

        return new CartDto(cartItems, totalCost);
    }

    public static double getTotalCost(List<CartItemDto> cartItems) {
        double totalCost = 0;
        for (CartItemDto cartItemDto : cartItems) {
            totalCost += (cartItemDto.getProduct().getPrice() * cartItemDto.getQuantity());
        }
        return totalCost;
    }

    private List<CartItemDto> getCartItems(List<CartModel> cartList) {
        List<CartItemDto> cartItems = new ArrayList<>();

        for (CartModel cart : cartList) {
            CartItemDto cartItemDto = getDtoFromCart(cart);
            cartItems.add(cartItemDto);
        }
        return cartItems;
    }

    private CartItemDto getDtoFromCart(CartModel cart) {
        return new CartItemDto(cart, productDtoMapper);
    }

    @Override
    public void updateCartItem(AddToCartDto cartDto) {
        //TODO: check if quantity is not higher than stock
        CartModel cart = cartRepository.getOne(cartDto.getId());
        cart.setQuantity(cartDto.getQuantity());
        cart.setCreatedDate(new Date());
        if(cart.getQuantity() == 0){
            cartRepository.deleteById(cart.getId());
        }else {
            cartRepository.save(cart);
        }
    }

    @Override
    public void deleteCartItem(long ItemId, HttpServletRequest token) throws CartItemNotExistException {
        CartModel cart = cartRepository.findById(ItemId).orElseThrow(() -> new CartItemNotExistException("Cart item not found"));
        long userIdFromCart = cart.getUser().getId();
        long userIdFromToken = userRepository.findByUsername(getUsernameFromToken(token)).get().getId();

        if (userIdFromCart == userIdFromToken) {
            cartRepository.deleteById(ItemId);
        } else {
            throw new CartItemNotExistException("Cart coudn't be deleted");
        }
    }

    @Override
    public void deleteAllCartItemsFromUser(HttpServletRequest token) {
        Optional<UserModel> user = userRepository.findByUsername(getUsernameFromToken(token));
        cartRepository.deleteByUser(user);
    }
}