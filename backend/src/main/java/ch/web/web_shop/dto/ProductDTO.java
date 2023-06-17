package ch.web.web_shop.dto;

import ch.web.web_shop.model.Category;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ProductDTO {

    @NotEmpty(message = "Title is mandatory")
    @NotNull(message = "Title cannot be null")
    private String title;

    @Size(min = 1, max = 50, message = "Description must be between 1 and 50 characters")
    @NotNull(message = "Description cannot be null")
    private String description;

    private String content;

    @Min(value = 0, message = "Price should not be less than 0")
    @NotNull(message = "Price cannot be null")
    private int price;

    @Min(value = 1, message = "Stock should not be less than 1")
    @NotNull(message = "Stock cannot be null")
    private int stock;

    private boolean published;

    @NotNull(message = "Category cannot be null")
    private Category category;


    public ProductDTO() {
        // Default constructor
    }

    public ProductDTO(String title, String description, String content, int price, int stock,
                       Category category) {
        this.title = title;
        this.description = description;
        this.content = content;
        this.price = price;
        this.stock = stock;
        this.published = false;
        this.category = category;
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

}
