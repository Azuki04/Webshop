package ch.web.web_shop.service;

import ch.web.web_shop.exception.NotEnoughProductsInStockException;
import ch.web.web_shop.model.Product;

import java.math.BigDecimal;
import java.util.Map;

public interface ShoppingCartsService {

    void addProduct(Product product);

    void removeProduct(Product product);

    Map<Product, Integer> getProductsInCart();

    void checkout() throws NotEnoughProductsInStockException;

    BigDecimal getTotal();
}
