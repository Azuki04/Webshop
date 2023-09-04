package ch.web.web_shop.service;

import ch.web.web_shop.dto.product.ProductDTO;
import ch.web.web_shop.dto.product.ProductResponseDto;
import ch.web.web_shop.model.Product;

import java.util.List;

public interface IProductService {

    List<Product> getAllProducts(String title);

    List<Product> getAllProducts(long userId, String title);

    Product getProductById(long id);

    Product getProductById(long userId, long productId);

    Product createProduct(ProductDTO productDTO);

    Product updateProduct(long id, ProductDTO productDTO);

    Product updateProduct(long userId, long productId, ProductDTO productDTO);

    void deleteProduct(long id);

    void deleteProduct(long userId, long productId);

    void deleteAllProducts();

    List<Product> getPublishedProducts(String title);

    Product getPublishedProductById(long productId);

// no need to implement this method in the service
    List<ProductResponseDto> convertToDto(List<Product> products);

    ProductResponseDto convertToDto(Product product);

}
