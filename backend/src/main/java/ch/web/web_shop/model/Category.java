package ch.web.web_shop.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * v1.0
 * @Author Sy Viet
 * Category is used to:
 * - store the categories of the products
 */
@Entity
@Table(name = "category")
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "name")
	@NotBlank(message = "Category name is mandatory")
	@Size(max = 50, message = "Category name must not exceed {max} characters")
	private String name;

	public Category() {
		// Default constructor required by JPA
	}

	public Category(long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Category(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + "]";
	}
}
