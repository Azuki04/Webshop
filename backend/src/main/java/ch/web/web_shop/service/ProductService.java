package ch.web.web_shop.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import ch.web.web_shop.dto.product.ProductDto;
import ch.web.web_shop.dto.product.ProductResponseDto;
import ch.web.web_shop.exception.*;
import ch.web.web_shop.model.FileModel;
import ch.web.web_shop.model.UserModel;
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

    @Override
    @Transactional(readOnly = true)
    public List<ProductModel> getAllProducts(String title) {
        try {
            if (title == null) {
                return (List<ProductModel>) productRepository.findAll();
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
            // Retrieve the user based on the user ID
            Optional<UserModel> user = userRepository.findById(userId);

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

    @Override
    @Transactional(readOnly = true)
    public ProductModel getProductById(long id) {
        Optional<ProductModel> productData = productRepository.findById(id);

        if (productData.isPresent()) {
            return productData.get();
        } else {
            throw new ProductNotFoundException(String.valueOf(id));
        }
    }

    //get product by user and by product id
    @Override
    @Transactional(readOnly = true)
    public ProductModel getProductById(long userId, long productId) {
        try {
            // Retrieve the user based on the user ID
            Optional<UserModel> user = userRepository.findById(userId);

            if (user.isPresent()) {
                // Retrieve the product based on the product ID
                Optional<ProductModel> product = productRepository.findById(productId);

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

    @Override
    @Transactional
    public ProductModel createProduct(ProductDto productDTO) {
        try {
            ProductModel product = convertToEntity(productDTO);
            return productRepository.save(product);
        } catch (Exception ex) {
            throw new ProductCouldNotBeSavedException(productDTO.getTitle());
        }
    }

    @Override
    @Transactional
    public ProductModel updateProduct(long id, ProductDto productDTO) {
        Optional<ProductModel> productData = productRepository.findById(id);

        if (productData.isPresent()) {
            ProductModel existingProduct = productData.get();
            updateProductFromDTO(existingProduct, productDTO);

            return productRepository.save(existingProduct);
        } else {
            throw new ProductNotFoundException(String.valueOf(id));
        }
    }

    //update product by user and by product id

    @Override
    @Transactional
    public ProductModel updateProduct(long userId, long productId, ProductDto productDTO) {
        try {
            // Retrieve the user based on the user ID
            Optional<UserModel> user = userRepository.findById(userId);

            if (user.isPresent()) {
                // Retrieve the product based on the product ID
                Optional<ProductModel> productData = productRepository.findById(productId);

                if (productData.isPresent()) {
                    // Check if the product belongs to the user
                    if (productData.get().getUser().getId() == userId) {
                        ProductModel existingProduct = productData.get();
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

    @Override
    @Transactional
    public void deleteProduct(long id) {
        try {
            productRepository.deleteById(id);
        } catch (Exception e) {
            throw new ProductNotFoundException(String.valueOf(id));
        }
    }

    //delete product by user and by product id
    //override function

    @Override
    @Transactional
    public void deleteProduct(long userId, long productId) {
        try {
            // Retrieve the user based on the user ID
            Optional<UserModel> user = userRepository.findById(userId);

            if (user.isPresent()) {
                // Retrieve the product based on the product ID
                Optional<ProductModel> product = productRepository.findById(productId);

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

    @Override
    @Transactional
    public void deleteAllProducts() {
        try {
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


    //get published products by product id

    @Override
    @Transactional(readOnly = true)
    public ProductModel getPublishedProductById(long productId) {
        try {
            return productRepository.findByPublishedAndId(true, productId);
        } catch (Exception e) {
            throw new ProductLoadException("Product load failed");

        }
    }

    private ProductModel convertToEntity(ProductDto productDTO) {
        ProductModel product = new ProductModel();
        updateProductFromDTO(product, productDTO);
        return product;
    }


    private void updateProductFromDTO(ProductModel product, ProductDto productDTO) {
        product.setTitle(productDTO.getTitle());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setStock(productDTO.getStock());
        product.setContent(productDTO.getContent());
        product.setPublished(productDTO.isPublished());
        product.setCategory(productDTO.getCategory());
        product.setUser(productDTO.getUser());
    }

    @Override
    public List<ProductResponseDto> convertToDto(List<ProductModel> products) {
        List<ProductResponseDto> productResponseDtoList = new ArrayList<>();

        for (ProductModel product : products) {

            productResponseDtoList.add(new ProductResponseDto(
                    product.getId(),
                    product.getTitle(),
                    product.getDescription(),
                    product.getContent(),
                    product.getPrice(),
                    product.getStock(),
                    product.isPublished(),
                    product.getCategory(),
                    product.getUser(),
                    getFileFromProduct(product)));
        }
        return productResponseDtoList;
    }

    @Override
    public ProductResponseDto convertToDto(ProductModel product) {


        return new ProductResponseDto(
                product.getId(),
                product.getTitle(),
                product.getDescription(),
                product.getContent(),
                product.getPrice(),
                product.getStock(),
                product.isPublished(),
                product.getCategory(),
                product.getUser(),
                getFileFromProduct(product));
    }

    private List<String> getFileFromProduct(ProductModel product) {
        List<FileModel> files = fileRepository.findByProduct(product);
        List<String> imagePaths = new ArrayList<>();

        for (FileModel file : files) {
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("api/file/downloadFile/")
                    .path(file.getName())
                    .toUriString();

            imagePaths.add(fileDownloadUri);
        }
        return imagePaths;
    }

}
