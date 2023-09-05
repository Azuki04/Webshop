package ch.web.web_shop.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import ch.web.web_shop.dto.product.ProductDto;
import ch.web.web_shop.dto.product.ProductResponseDto;
import ch.web.web_shop.exception.*;
import ch.web.web_shop.model.FileModel;
import ch.web.web_shop.model.UserModel;
import ch.web.web_shop.repository.CartRepository;
import ch.web.web_shop.repository.FileRepository;
import ch.web.web_shop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.web.web_shop.model.ProductModel;
import ch.web.web_shop.repository.ProductRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * Product service class.
 * Handles the business logic for product operations.
 * Uses ProductRepository for data persistence.
 * This class can be used to perform additional business logic if needed.
 * It provides a layer of abstraction between the controller and the repository.
 *
 * @version 1.0
 * @Author: Sy Viet
 * @see ProductModel
 * @see ProductRepository
 * @see ProductDto
 * @see UserRepository
 * @see UserModel
 * @see ProductLoadException
 * @see ProductNotFoundException
 */
@Service
@Transactional
public class ProductService implements IProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private CartRepository cartRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ProductModel> getAllProducts(String title) {
        try {
            if (title == null) {
                return productRepository.findAll();
            } else {
                return productRepository.findByTitleContaining(title);
            }
        } catch (Exception ex) {
            throw new ProductLoadException("Product load failed");
        }
    }

    // Get all products for a user
    @Override
    @Transactional(readOnly = true)
    public List<ProductModel> getAllProducts(long userId, String title) {
        try {
            Optional<UserModel> user = userRepository.findById(userId);

            if (user.isPresent() && title == null) {
                return productRepository.findByUser(user.get());
            } else {
                return productRepository.findByUserAndTitleContaining(user.get(), title);
            }
        } catch (Exception ex) {
            throw new ProductLoadException("Failed to load products for user");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ProductModel getProductById(long productId) {
        Optional<ProductModel> productData = productRepository.findById(productId);

        if (productData.isPresent()) {
            return productData.get();
        } else {
            throw new ProductNotFoundException(String.valueOf(productId));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ProductModel getProductById(long userId, long productId) throws ProductNotFoundException {

        ProductModel product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(String.valueOf(productId)));

        if (product.getUser().getId() != userId) {
            throw new ProductNotFoundException(String.valueOf(productId));
        }

        return product;
    }


    @Override
    @Transactional
    public ProductModel createProduct(ProductDto productDto) {
        try {
            ProductModel product = convertToEntity(productDto);
            return productRepository.save(product);
        } catch (Exception ex) {
            throw new ProductCouldNotBeSavedException(productDto.getTitle());
        }
    }

    @Override
    @Transactional
    public ProductModel updateProduct(long productId, ProductDto productDTO) {
        Optional<ProductModel> productData = productRepository.findById(productId);

        if (productData.isPresent()) {
            ProductModel existingProduct = productData.get();
            updateProductFromDTO(existingProduct, productDTO);

            return productRepository.save(existingProduct);
        } else {
            throw new ProductNotFoundException(String.valueOf(productId));
        }
    }


    @Override
    @Transactional
    public ProductModel updateProduct(long userId, long productId, ProductDto productDTO) {
        ProductModel product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(String.valueOf(productId)));

        ensureProductBelongsToUser(product, userId);

        updateProductFromDTO(product, productDTO);

        return productRepository.save(product);
    }

    private void ensureProductBelongsToUser(ProductModel product, long userId) {
        if (product.getUser().getId() != userId) {
            throw new ProductNotFoundException(String.valueOf(product.getId()));
        }
    }


    @Override
    @Transactional
    public void deleteProduct(long productId) {
        try {
            ProductModel product = productRepository.findById(productId)
                    .orElseThrow(() -> new ProductNotFoundException(String.valueOf(productId)));

            cartRepository.deleteByProduct(product);
            fileRepository.deleteByProduct(product);

            productRepository.deleteById(productId);
        } catch (Exception e) {
            throw new ProductNotFoundException(String.valueOf(productId));
        }
    }


    @Override
    @Transactional
    public void deleteProduct(long userId, long productId) {
        ProductModel product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(String.valueOf(productId)));

        ensureProductBelongsToUser(product, userId);

        cartRepository.deleteByProduct(product);
        fileRepository.deleteByProduct(product);

        productRepository.deleteById(productId);
    }

    @Override
    @Transactional
    public void deleteAllProducts() {
        try {
            cartRepository.deleteAll();
            fileRepository.deleteAll();
            productRepository.deleteAll();
        } catch (Exception e) {
            throw new ProductDeleteException();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductModel> getPublishedProducts(String title) {
        try {
            if (title == null) {
                return productRepository.findByPublished(true);
            } else {
                return productRepository.findByTitleContainingAndPublished(title, true);
            }
        } catch (Exception e) {
            throw new ProductLoadException("Product load failed");

        }
    }

    @Override
    @Transactional(readOnly = true)
    public ProductModel getPublishedProductById(long productId) {
        try {
            return productRepository.findByPublishedAndId(true, productId);
        } catch (Exception e) {
            throw new ProductLoadException("Product load failed");

        }
    }

    private ProductModel convertToEntity(ProductDto productDto) {
        ProductModel product = new ProductModel();
        updateProductFromDTO(product, productDto);
        return product;
    }


    private void updateProductFromDTO(ProductModel product, ProductDto productDto) {
        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setStock(productDto.getStock());
        product.setContent(productDto.getContent());
        product.setPublished(productDto.isPublished());
        product.setCategory(productDto.getCategory());
        product.setUser(productDto.getUser());
    }


}
