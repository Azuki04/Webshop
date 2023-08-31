package ch.web.web_shop.controller;

import java.util.List;

import ch.web.web_shop.dto.product.ProductResponseDto;
import ch.web.web_shop.service.FileStorageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import ch.web.web_shop.dto.product.ProductDTO;
import ch.web.web_shop.model.Product;
import ch.web.web_shop.service.ProductService;
import org.springframework.web.multipart.MultipartFile;

/**
 * v1.0
 *
 * @Author Sy Viet
 * ProductController is used to:
 * - get all products
 * - delete all products
 * - get product by id
 * - update product by id
 * - delete product by id
 * - create product
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private FileStorageService fileStorageService;

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ProductResponseDto>> getAllProducts(@RequestParam(required = false) String title) {
        List<Product> products = productService.getAllProducts(title);

        List<ProductResponseDto> productResponseDtosList = productService.convertToDto(products);
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(productResponseDtosList);
    }

    @DeleteMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> deleteAllProducts() {
        productService.deleteAllProducts();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/admin/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Product> getProductById(@PathVariable("id") long id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @PutMapping("admin/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") long id, @Valid @RequestBody ProductDTO productDTO) {
        Product updatedProduct = productService.updateProduct(id, productDTO);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("admin/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable("id") long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    // get all products by user id
    @GetMapping("/seller/{userId}")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<List<ProductResponseDto>> getAllProductsByUserId(@PathVariable("userId") long userId, @RequestParam(required = false) String title) {
        List<Product> products = productService.getAllProducts(userId, title);
        List<ProductResponseDto> productResponseDtosList = productService.convertToDto(products);
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productResponseDtosList);
    }

    // get product by user id and product id
    @GetMapping("/seller/{userId}/{id}")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<ProductResponseDto> getProductByUserIdAndProductId(@PathVariable("userId") long userId, @PathVariable("id") long id) {
        Product product = productService.getProductById(userId, id);
        ProductResponseDto productResponseDto = productService.convertToDto(product);
        return ResponseEntity.ok(productResponseDto);
    }

    // delete product by user id and product id
    @DeleteMapping("/seller/{userId}/{id}")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<HttpStatus> deleteProductByUserIdAndProductId(@PathVariable("userId") long userId, @PathVariable("id") long id) {
        productService.deleteProduct(userId, id);
        return ResponseEntity.noContent().build();
    }

    // put product by user id and product id
    @PutMapping("/seller/{userId}/{id}")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<Product> updateProductByUserIdAndProductId(@PathVariable("userId") long userId, @PathVariable("id") long id, @Valid @RequestBody ProductDTO productDTO) {
        Product updatedProduct = productService.updateProduct(userId, id, productDTO);
        return ResponseEntity.ok(updatedProduct);
    }


    @PostMapping("/seller")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<Product> createProduct(@RequestParam("data") String data, @RequestParam("file") MultipartFile[]  files) {
        ObjectMapper objectMapper = new ObjectMapper();
        ProductDTO productDTO = null;
        try {
            productDTO = objectMapper.readValue(data, ProductDTO.class);
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }

        Product createdProduct = productService.createProduct(productDTO);

        fileStorageService.storeFile(files, createdProduct);

        return ResponseEntity.ok(createdProduct);
    }

    //Get all published product Rest API
    @GetMapping("/published")
    public ResponseEntity<List<ProductResponseDto>> findByPublished(@RequestParam(required = false) String title) {
        List<Product> publishedProducts = productService.getPublishedProducts(title);
        List<ProductResponseDto> productResponseDtosList = productService.convertToDto(publishedProducts);
        if (publishedProducts.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(productResponseDtosList);
    }

    //Get published product by id Rest API
    @GetMapping("/published/{id}")
    public ResponseEntity<ProductResponseDto> getPublishedProductById(@PathVariable("id") long id) {
        Product publishedProduct = productService.getPublishedProductById(id);
        ProductResponseDto productResponseDto = productService.convertToDto(publishedProduct);
        return ResponseEntity.ok(productResponseDto);
    }


}
