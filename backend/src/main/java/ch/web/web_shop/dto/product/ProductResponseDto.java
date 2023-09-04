package ch.web.web_shop.dto.product;


import ch.web.web_shop.model.CategoryModel;

import ch.web.web_shop.model.UserModel;

import java.util.List;

/**
 * v1.0
 * @Author Sy Viet
 * ProductDTO is used to:
 * - store the data of the products
 */
public class ProductResponseDto {

    private long id;


    private String title;


    private String description;

    private String content;


    private int price;


    private int stock;

    private boolean published;


    private CategoryModel category;

    private UserModel user;

    private final List<String> imagePaths;


    public ProductResponseDto(long id, String title, String description, String content, int price, int stock, boolean published, CategoryModel category, UserModel user, List<String> imagePaths) {
        // Default constructor
        this.id = id;
        this.title = title;
        this.description = description;
        this.content = content;
        this.price = price;
        this.stock = stock;
        this.published = published;
        this.category = category;
        this.user = user;
        this.imagePaths = imagePaths;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public void setContent(String content) {
        this.content = content;
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

    public CategoryModel getCategory() {
        return category;
    }

    public void setCategory(CategoryModel category) {
        this.category = category;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public List<String> getImagePaths() {
        return imagePaths;
    }
}
