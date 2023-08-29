package ch.web.web_shop.model;


import jakarta.persistence.*;

@Entity
@Table(name = "product_image")
public class File {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;
    private long size;
    private String alt;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;


    public File(String name, String type, long size, String alt, Product product) {
        this.name = name;
        this.type = type;
        this.size = size;
        this.alt = alt;
        this.product = product;
    }

    public File(String name, String type, long size, String alt) {
        this.name = name;
        this.type = type;
        this.size = size;
        this.alt = alt;
    }

    public File() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
