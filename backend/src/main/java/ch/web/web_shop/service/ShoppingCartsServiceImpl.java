package ch.web.web_shop.service;

import ch.web.web_shop.exception.NotEnoughProductsInStockException;
import ch.web.web_shop.model.Product;
import ch.web.web_shop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


@Service
@Transactional
public class ShoppingCartsServiceImpl implements ShoppingCartsService {

    private ProductRepository productRepository;

    private Map<Product, Integer> products = new HashMap<>();

    @Autowired
    public ShoppingCartsServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void addProduct(Product product) {
        if (products.containsKey(product)) {
            products.replace(product, products.get(product) + 1);
        } else {
            products.put(product, 1);
        }
    }


    @Override
    public void removeProduct(Product product) {
        if (products.containsKey(product)) {
            if (products.get(product) > 1)
                products.replace(product, products.get(product) - 1);
            else if (products.get(product) == 1) {
                products.remove(product);
            }
        }
    }

    @Override
    public Map<Product, Integer> getProductsInCart() {
        return Collections.unmodifiableMap(products);
    }


    @Override
    public void checkout() throws NotEnoughProductsInStockException {
        Product product;
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            // Refresh quantity for every product before checking
            product = productRepository.findOne(entry.getKey().getId());
            if (product.getStock() < entry.getValue())
                throw new NotEnoughProductsInStockException(product);
            entry.getKey().setStock(product.getStock() - entry.getValue());
        }

    }

    @Override
    public BigDecimal getTotal() {
        BigDecimal total = BigDecimal.ZERO;

        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            BigDecimal price = BigDecimal.valueOf(product.getPrice());
            BigDecimal subtotal = price.multiply(BigDecimal.valueOf(quantity));
            total = total.add(subtotal);
        }

        return total;
    }

}