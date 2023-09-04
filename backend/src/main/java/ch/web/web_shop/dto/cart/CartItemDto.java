package ch.web.web_shop.dto.cart;

import ch.web.web_shop.model.CartModel;
import ch.web.web_shop.model.ProductModel;
import jakarta.validation.constraints.NotNull;

public class CartItemDto {
    private long id;
    @NotNull
    private Integer quantity;
    @NotNull
    private ProductModel product;

    public CartItemDto() {
    }

    public CartItemDto(CartModel cart) {
        this.setId(cart.getId());
        this.setQuantity(cart.getQuantity());
        this.setProduct(cart.getProduct());
    }

    @Override
    public String toString() {
        return "CartDto{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", productTitle=" + product.getTitle() +
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
    public ProductModel getProduct() {
        return product;
    }

    public void setProduct(ProductModel product) {
        this.product = product;
    }

}