package ch.web.web_shop.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;



/**
 * v1.0
 *
 * @Author Sy Viet
 * Category is used to:
 * - store the categories of the products
 */

@Entity
@NoArgsConstructor
@Data
@Table(name = "category")
public class Category {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String name;

    //@JsonBackReference
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "parentCategory")
    private Category parentCategory;

    //@OneToMany(mappedBy = "parentCategory")
    //private List<Category> subCategories = new ArrayList<>();

    public Category(String name) {
        this.name = name;
    }

    public Category(String name, Category parentCategory) {
        this.name = name;
        this.parentCategory = parentCategory;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Category [id=" + id + ", name=" + name + "]";
    }

}
