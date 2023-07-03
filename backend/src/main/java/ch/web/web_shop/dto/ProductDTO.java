package ch.web.web_shop.dto;

import ch.web.web_shop.model.Category;

import ch.web.web_shop.model.User;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * v1.0
 * @Author Sy Viet
 * ProductDTO is used to:
 * - store the data of the products
 */
public class ProductDTO {


    private String title;


    private String description;

    private String content;


    private int price;


    private int stock;

    private boolean published;


    private Category category;

    private User user;


    public ProductDTO() {
        // Default constructor
    }

    public ProductDTO(String title, String description, String content, int price, int stock,
                       Category category, User user) {
        this.title = title;
        this.description = description;
        this.content = content;
        this.price = price;
        this.stock = stock;
        this.published = false;
        this.category = category;
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
}
