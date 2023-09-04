package ch.web.web_shop.service;

import ch.web.web_shop.dto.product.ProductDto;
import ch.web.web_shop.exception.*;
import ch.web.web_shop.model.*;
import ch.web.web_shop.repository.ProductRepository;
import org.junit.jupiter.api.*;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllProducts_NoTitle() {
        List<ProductModel> productList = new ArrayList<>();
        productList.add(new ProductModel());
        productList.add(new ProductModel());

        when(productRepository.findAll()).thenReturn(productList);

        List<ProductModel> result = productService.getAllProducts(null);
        assertEquals(2, result.size());
    }

    @Test
    void testGetAllProducts_WithTitle() {
        List<ProductModel> productList = new ArrayList<>();
        productList.add(new ProductModel());
        productList.add(new ProductModel());

        when(productRepository.findByTitleContaining("test")).thenReturn(productList);

        List<ProductModel> result = productService.getAllProducts("test");
        assertEquals(2, result.size());
    }

    @Test
    void testGetAllProducts_Exception() {
        when(productRepository.findAll()).thenThrow(RuntimeException.class);

        assertThrows(ProductLoadException.class, () -> {
            productService.getAllProducts(null);
        });
    }

    @Test
    void testGetProductById_Success() {
        long productId = 1;
        ProductModel product = new ProductModel();
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        ProductModel result = productService.getProductById(productId);
        assertEquals(product, result);
    }

    @Test
    void testGetProductById_NotFound() {
        long productId = 1;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> {
            productService.getProductById(productId);
        });
    }

    @Test
    void testCreateProduct_Success() {
        ProductDto productDTO = new ProductDto();
        productDTO.setTitle("Test Product");
        productDTO.setDescription("Test Description");
        productDTO.setPrice(10);
        productDTO.setStock(5);
        productDTO.setPublished(false);
        productDTO.setCategory(new CategoryModel());

        ProductModel product = new ProductModel();
        product.setTitle("Test Product");
        product.setDescription("Test Description");
        product.setPrice(10);
        product.setStock(5);
        product.setPublished(false);
        product.setCategory(new CategoryModel());

        when(productRepository.save(Mockito.any(ProductModel.class))).thenReturn(product);

        ProductModel result = productService.createProduct(productDTO);
        assertEquals(product, result);
    }


    @Test
    void testCreateProduct_Exception() {
        ProductDto productDTO = new ProductDto();
        when(productRepository.save(Mockito.any(ProductModel.class))).thenThrow(RuntimeException.class);

        assertThrows(ProductCouldNotBeSavedException.class, () -> {
            productService.createProduct(productDTO);
        });
    }

    @Test
    void testUpdateProduct_Success() {
        long productId = 1;
        ProductDto productDTO = new ProductDto();
        ProductModel existingProduct = new ProductModel();
        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(existingProduct)).thenReturn(existingProduct);

        ProductModel result = productService.updateProduct(productId, productDTO);

        assertEquals(existingProduct, result);
        verify(productRepository, times(1)).save(existingProduct);
    }

    @Test
    void testUpdateProduct_NotFound() {
        long productId = 1;
        ProductDto productDTO = new ProductDto();
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> {
            productService.updateProduct(productId, productDTO);
        });
    }

    @Test
    void testDeleteProduct_Success() {
        long productId = 1;
        doNothing().when(productRepository).deleteById(productId);

        assertDoesNotThrow(() -> {
            productService.deleteProduct(productId);
        });

        verify(productRepository, times(1)).deleteById(productId);
    }

    @Test
    void testDeleteProduct_NotFound() {
        long productId = 1;
        doThrow(RuntimeException.class).when(productRepository).deleteById(productId);

        assertThrows(ProductNotFoundException.class, () -> {
            productService.deleteProduct(productId);
        });
    }

    @Test
    void testDeleteAllProducts_Success() {
        doNothing().when(productRepository).deleteAll();

        assertDoesNotThrow(() -> {
            productService.deleteAllProducts();
        });

        verify(productRepository, times(1)).deleteAll();
    }

    @Test
    void testDeleteAllProducts_Exception() {
        doThrow(RuntimeException.class).when(productRepository).deleteAll();

        assertThrows(ProductDeleteException.class, () -> {
            productService.deleteAllProducts();
        });
    }



}
