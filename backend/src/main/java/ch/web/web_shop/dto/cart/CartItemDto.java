package ch.web.web_shop.dto.cart;

import ch.web.web_shop.model.CartModel;
import ch.web.web_shop.dto.product.ProductResponseDto;
import ch.web.web_shop.dto.product.IProductDtoMapper;
import jakarta.validation.constraints.NotNull;


public class CartItemDto {
    private long id;
    @NotNull
    private Integer quantity;
    @NotNull
    private ProductResponseDto product;

    public CartItemDto() {
    }

    public CartItemDto(CartModel cart, IProductDtoMapper productDtoMapper) {
        this.setId(cart.getId());
        this.setQuantity(cart.getQuantity());

        if (productDtoMapper != null) {
            this.setProduct(productDtoMapper.convertToDto(cart.getProduct()));
        }
    }

    @Override
    public String toString() {
        return "CartItemDto{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", productTitle=" + (product != null ? product.getTitle() : "N/A") +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public ProductResponseDto getProduct() {
        return product;
    }

    public void setProduct(ProductResponseDto product) {
        this.product = product;
    }
}
