package ch.web.web_shop.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


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
public class CategoryModel {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String name;

    @JsonBackReference
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "parentCategory")
    private CategoryModel parentCategory;

    @OneToMany(mappedBy = "parentCategory")
    private List<CategoryModel> subCategories = new ArrayList<>();

    public CategoryModel(String name) {
        this.name = name;
    }

    public CategoryModel(String name, CategoryModel parentCategory) {
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
