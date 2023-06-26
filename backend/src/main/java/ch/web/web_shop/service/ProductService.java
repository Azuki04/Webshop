package ch.web.web_shop.service;

import java.util.List;
import java.util.Optional;

import ch.web.web_shop.dto.ProductDTO;
import ch.web.web_shop.exception.*;
import ch.web.web_shop.model.User;
import ch.web.web_shop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.web.web_shop.model.Product;
import ch.web.web_shop.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;


    public List<Product> getAllProducts(String title) {
        try {
            if (title == null) {
                return (List<Product>) productRepository.findAll();
            } else {
                return productRepository.findByTitleContaining(title);
            }
        } catch (Exception ex) {
            throw new ProductLoadException("Product load failed");
        }
    }

    // Get all products for a user
    public List<Product> getAllProducts(long userId) {
        try {
            // Retrieve the user based on the user ID
            Optional<User> user = userRepository.findById(userId);

            if (user.isPresent()) {
                // Retrieve all products associated with the user
                return productRepository.findByUser(user.get());
            } else {
                throw new ResourceNotFoundException("User not found");
            }
        } catch (Exception ex) {
            throw new ProductLoadException("Failed to load products for user");
        }
    }

    public Product getProductById(long id) {
        Optional<Product> productData = productRepository.findById(id);

        if (productData.isPresent()) {
            return productData.get();
        } else {
            throw new ProductNotFoundException(String.valueOf(id));
        }
    }

    public Product createProduct(ProductDTO productDTO) {
        try {
            Product product = convertToEntity(productDTO);
            return productRepository.save(product);
        } catch (Exception ex) {
            throw new ProductCouldNotBeSavedException(productDTO.getTitle());
        }
    }

    public Product updateProduct(long id, ProductDTO productDTO) {
        Optional<Product> productData = productRepository.findById(id);

        if (productData.isPresent()) {
            Product existingProduct = productData.get();
            updateProductFromDTO(existingProduct, productDTO);

            return productRepository.save(existingProduct);
        } else {
            throw new ProductNotFoundException(String.valueOf(id));
        }
    }

    public void deleteProduct(long id) {
        try {
            productRepository.deleteById(id);
        } catch (Exception e) {
            throw new ProductNotFoundException(String.valueOf(id));
        }
    }

    public void deleteAllProducts() {
        try {
            productRepository.deleteAll();
        } catch (Exception e) {
            throw new ProductDeleteException();
        }
    }

    public List<Product> getPublishedProducts() {
        try {
            return productRepository.findByPublished(true);
        } catch (Exception e) {
            throw new ProductLoadException("Product load failed");

        }
    }

    private Product convertToEntity(ProductDTO productDTO) {
        Product product = new Product();
        updateProductFromDTO(product, productDTO);
        return product;
    }

    private void updateProductFromDTO(Product product, ProductDTO productDTO) {
        product.setTitle(productDTO.getTitle());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setStock(productDTO.getStock());
        product.setContent(productDTO.getContent());
        product.setPublished(productDTO.isPublished());
        product.setCategory(productDTO.getCategory());
    }
}
