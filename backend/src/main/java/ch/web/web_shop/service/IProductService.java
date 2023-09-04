package ch.web.web_shop.service;

import ch.web.web_shop.dto.product.ProductDto;
import ch.web.web_shop.dto.product.ProductResponseDto;
import ch.web.web_shop.model.ProductModel;

import java.util.List;

public interface IProductService {

    List<ProductModel> getAllProducts(String title);

    List<ProductModel> getAllProducts(long userId, String title);

    ProductModel getProductById(long id);

    ProductModel getProductById(long userId, long productId);

    ProductModel createProduct(ProductDto productDTO);

    ProductModel updateProduct(long id, ProductDto productDTO);

    ProductModel updateProduct(long userId, long productId, ProductDto productDTO);

    void deleteProduct(long id);

    void deleteProduct(long userId, long productId);

    void deleteAllProducts();

    List<ProductModel> getPublishedProducts(String title);

    ProductModel getPublishedProductById(long productId);


}
