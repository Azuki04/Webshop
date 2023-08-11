package ch.web.web_shop.dto.cart;

import jakarta.validation.constraints.NotNull;

public class AddToCartDto {
    private long id;
    @NotNull
    private long productId;
    @NotNull
    private Integer quantity;

    public AddToCartDto() {
    }



    @Override
    public String toString() {
        return "CartDto{" +
                "id=" + id +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ",";
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}