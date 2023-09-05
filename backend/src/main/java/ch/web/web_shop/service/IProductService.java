package ch.web.web_shop.service;

import ch.web.web_shop.dto.product.ProductDto;
import ch.web.web_shop.dto.product.ProductResponseDto;
import ch.web.web_shop.exception.ProductNotFoundException;
import ch.web.web_shop.model.ProductModel;

import java.util.List;

public interface IProductService {

    List<ProductModel> getAllProducts(String title);

    List<ProductModel> getAllProducts(long userId, String title);

    ProductModel getProductById(long productId);

    ProductModel getProductById(long userId, long productId) throws ProductNotFoundException;

    ProductModel createProduct(ProductDto productDto);

    ProductModel updateProduct(long id, ProductDto productDTO);

    ProductModel updateProduct(long userId, long productId, ProductDto productDTO);

    void deleteProduct(long productId);

    void deleteProduct(long userId, long productId);

    void deleteAllProducts();

    List<ProductModel> getPublishedProducts(String title);

    ProductModel getPublishedProductById(long productId);


}
