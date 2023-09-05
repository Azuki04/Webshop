package ch.web.web_shop.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * v1.0
 *
 * @Author Sy Viet
 * Product is the model for the products table in the database.
 * It is used to map the JSON response body to a Java object.
 */
@Entity
@Table(name = "products")
public class ProductModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title")
    @NotEmpty(message = "Title is mandatory")
    @NotNull(message = "Title cannot be null")
    private String title;

    @Column(name = "description")
    @Size(min = 1, max = 50, message = "Description must be between 1 and 50 characters")
    @NotNull(message = "Description cannot be null")
    private String description;

    @Column(name = "content")
    private String content;

    @Column(name = "price")
    @Min(value = 0, message = "Price should not be less than 0")
    @NotNull(message = "Price cannot be null")
    private int price;

    @Column(name = "stock")
    @Min(value = 1, message = "Stock should not be less than 1")
    @NotNull(message = "Stock cannot be null")
    private int stock;

    @Column(name = "published")
    private boolean published;

    // category 1:n
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryModel category;

    // user 1:n
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user;


    public ProductModel() {
        // Default constructor required by JPA
    }

    public ProductModel(int price) {
        this.price = price;
    }

    public ProductModel(String title, String description, String content, int price, int stock,
                        CategoryModel category, UserModel user) {
        this.title = title;
        this.description = description;
        this.content = content;
        this.price = price;
        this.stock = stock;
        this.published = false;
        this.category = category;
        this.user = user;
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

    @Override
    public String toString() {
        return "Product [title=" + title + ", description=" + description + ", published=" + published + "]";
    }
}
