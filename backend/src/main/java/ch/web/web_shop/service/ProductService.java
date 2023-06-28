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
    public List<Product> getAllProducts(long userId, String title) {
        try {
            // Retrieve the user based on the user ID
            Optional<User> user = userRepository.findById(userId);

            if (user.isPresent() && title == null) {
                // Retrieve all products associated with the user
                return productRepository.findByUser(user.get());
            } else {
                // Retrieve all products associated with the user and containing the title
                return productRepository.findByUserAndTitleContaining(user.get(), title);
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

    //get product by user and by product id
    public Product getProductById(long userId, long productId) {
        try {
            // Retrieve the user based on the user ID
            Optional<User> user = userRepository.findById(userId);

            if (user.isPresent()) {
                // Retrieve the product based on the product ID
                Optional<Product> product = productRepository.findById(productId);

                if (product.isPresent()) {
                    // Check if the product belongs to the user
                    if (product.get().getUser().getId() == userId) {
                        return product.get();
                    } else {
                        throw new ProductNotFoundException(String.valueOf(productId));
                    }
                } else {
                    throw new ProductNotFoundException(String.valueOf(productId));
                }
            } else {
                throw new ResourceNotFoundException("User not found");
            }
        } catch (Exception ex) {
            throw new ProductLoadException("Failed to load product");
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
    //update product by user and by product id
    public Product updateProduct(long userId, long productId, ProductDTO productDTO) {
        try {
            // Retrieve the user based on the user ID
            Optional<User> user = userRepository.findById(userId);

            if (user.isPresent()) {
                // Retrieve the product based on the product ID
                Optional<Product> productData = productRepository.findById(productId);

                if (productData.isPresent()) {
                    // Check if the product belongs to the user
                    if (productData.get().getUser().getId() == userId) {
                        Product existingProduct = productData.get();
                        updateProductFromDTO(existingProduct, productDTO);

                        return productRepository.save(existingProduct);
                    } else {
                        throw new ProductNotFoundException(String.valueOf(productId));
                    }
                } else {
                    throw new ProductNotFoundException(String.valueOf(productId));
                }
            } else {
                throw new ResourceNotFoundException("User not found");
            }
        } catch (Exception ex) {
            throw new ProductCouldNotBeSavedException(productDTO.getTitle());
        }
    }

    public void deleteProduct(long id) {
        try {
            productRepository.deleteById(id);
        } catch (Exception e) {
            throw new ProductNotFoundException(String.valueOf(id));
        }
    }

    //delete product by user and by product id
    //override function
    public void deleteProduct(long userId, long productId) {
        try {
            // Retrieve the user based on the user ID
            Optional<User> user = userRepository.findById(userId);

            if (user.isPresent()) {
                // Retrieve the product based on the product ID
                Optional<Product> product = productRepository.findById(productId);

                if (product.isPresent()) {
                    // Check if the product belongs to the user
                    if (product.get().getUser().getId() == userId) {
                        productRepository.deleteById(productId);
                    } else {
                        throw new ProductNotFoundException(String.valueOf(productId));
                    }
                } else {
                    throw new ProductNotFoundException(String.valueOf(productId));
                }
            } else {
                throw new ResourceNotFoundException("User not found");
            }
        } catch (Exception e) {
            throw new ProductNotFoundException(String.valueOf(productId));
        }
    }


    public void deleteAllProducts() {
        try {
            productRepository.deleteAll();
        } catch (Exception e) {
            throw new ProductDeleteException();
        }
    }

    public List<Product> getPublishedProducts(String title) {
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

    //get published products by product id
    public Product getPublishedProductById(long productId) {
        try {
            return productRepository.findByPublishedAndId(true, productId);
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
        product.setUser(productDTO.getUser());
    }

}
