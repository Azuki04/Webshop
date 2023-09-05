package ch.web.web_shop.controller;

import java.util.List;

import ch.web.web_shop.dto.product.IProductDtoMapper;
import ch.web.web_shop.dto.product.ProductResponseDto;
import ch.web.web_shop.service.IFileStorageService;
import ch.web.web_shop.service.IProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import ch.web.web_shop.dto.product.ProductDto;
import ch.web.web_shop.model.ProductModel;
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
    private IProductService productService;

    @Autowired
    private IFileStorageService fileStorageService;

    @Autowired
    private IProductDtoMapper productDtoMapper;

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ProductResponseDto>> getAllProducts(@RequestParam(required = false) String title) {
        List<ProductModel> products = productService.getAllProducts(title);

        List<ProductResponseDto> productResponseDtosList = productDtoMapper.convertToDto(products);
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
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable("id") long id) {
        ProductModel product = productService.getProductById(id);
        ProductResponseDto productResponseDto = productDtoMapper.convertToDto(product);
        return ResponseEntity.ok(productResponseDto);
    }

    @PutMapping("admin/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductModel> updateProduct(@PathVariable("id") long id, @Valid @RequestBody ProductDto productDTO) {
        ProductModel updatedProduct = productService.updateProduct(id, productDTO);
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
        List<ProductModel> products = productService.getAllProducts(userId, title);
        List<ProductResponseDto> productResponseDtosList = productDtoMapper.convertToDto(products);
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productResponseDtosList);
    }

    // get product by user id and product id
    @GetMapping("/seller/{userId}/{id}")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<ProductResponseDto> getProductByUserIdAndProductId(@PathVariable("userId") long userId, @PathVariable("id") long id) {
        ProductModel product = productService.getProductById(userId, id);
        ProductResponseDto productResponseDto = productDtoMapper.convertToDto(product);
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
    public ResponseEntity<ProductModel> updateProductByUserIdAndProductId(@PathVariable("userId") long userId, @PathVariable("id") long id, @Valid @RequestBody ProductDto productDTO) {
        ProductModel updatedProduct = productService.updateProduct(userId, id, productDTO);
        return ResponseEntity.ok(updatedProduct);
    }


    @PostMapping("/seller")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<ProductModel> createProduct(@RequestParam("data") String data, @RequestParam("file") MultipartFile[] files) {
        ObjectMapper objectMapper = new ObjectMapper();
        ProductDto productDTO;
        try {
            productDTO = objectMapper.readValue(data, ProductDto.class);

            ProductModel createdProduct = productService.createProduct(productDTO);

            fileStorageService.storeFile(files, createdProduct);

            return ResponseEntity.ok(createdProduct);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    //Get all published product Rest API
    @GetMapping("/published")
    public ResponseEntity<List<ProductResponseDto>> findByPublished(@RequestParam(required = false) String title) {
        List<ProductModel> publishedProducts = productService.getPublishedProducts(title);
        List<ProductResponseDto> productResponseDtosList = productDtoMapper.convertToDto(publishedProducts);
        if (publishedProducts.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(productResponseDtosList);
    }

    @GetMapping("/published/{id}")
    public ResponseEntity<ProductResponseDto> getPublishedProductById(@PathVariable("id") long id) {
        ProductModel publishedProduct = productService.getPublishedProductById(id);
        ProductResponseDto productResponseDto = productDtoMapper.convertToDto(publishedProduct);
        return ResponseEntity.ok(productResponseDto);
    }


}
