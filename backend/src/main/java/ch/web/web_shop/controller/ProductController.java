package ch.web.web_shop.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import ch.web.web_shop.dto.product.ProductDTO;
import ch.web.web_shop.model.Product;
import ch.web.web_shop.service.ProductService;

/**
 * v1.0
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

    @GetMapping("/admin")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<Product>> getAllProducts(@RequestParam(required = false) String title) {
        List<Product> products = productService.getAllProducts(title);

        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(products);
    }

    @DeleteMapping("/admin")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> deleteAllProducts() {
        productService.deleteAllProducts();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/admin/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Product> getProductById(@PathVariable("id") long id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @PutMapping("admin/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") long id, @Valid @RequestBody ProductDTO productDTO) {
        Product updatedProduct = productService.updateProduct(id, productDTO);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("admin/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable("id") long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    // get all products by user id
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Product>> getAllProductsByUserId(@PathVariable("userId") long userId, @RequestParam(required = false) String title) {
        List<Product> products = productService.getAllProducts(userId, title);

        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(products);
    }

    // get product by user id and product id
    @GetMapping("/user/{userId}/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Product> getProductByUserIdAndProductId(@PathVariable("userId") long userId, @PathVariable("id") long id) {
        Product product = productService.getProductById(userId, id);
        return ResponseEntity.ok(product);
    }

    // delete product by user id and product id
    @DeleteMapping("/user/{userId}/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<HttpStatus> deleteProductByUserIdAndProductId(@PathVariable("userId") long userId, @PathVariable("id") long id) {
        productService.deleteProduct(userId, id);
        return ResponseEntity.noContent().build();
    }

    // put product by user id and product id
    @PutMapping("/user/{userId}/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Product> updateProductByUserIdAndProductId(@PathVariable("userId") long userId, @PathVariable("id") long id, @Valid @RequestBody ProductDTO productDTO) {
        Product updatedProduct = productService.updateProduct(userId, id, productDTO);
        return ResponseEntity.ok(updatedProduct);
    }


    @PostMapping("")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Product> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        Product createdProduct = productService.createProduct(productDTO);
        return ResponseEntity.ok(createdProduct);
    }

    /*
    *open Rest API
    *
    */

    //Get all published product Rest API
    @GetMapping("/published")
    public ResponseEntity<List<Product>> findByPublished(@RequestParam(required = false) String title) {
        List<Product> publishedProducts = productService.getPublishedProducts(title);

        if (publishedProducts.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(publishedProducts);
    }

    //Get published product by id Rest API
    @GetMapping("/published/{id}")
    public ResponseEntity<Product> getPublishedProductById(@PathVariable("id") long id) {
        Product publishedProduct = productService.getPublishedProductById(id);
        return ResponseEntity.ok(publishedProduct);
    }


}
