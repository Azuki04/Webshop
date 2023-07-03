package ch.web.web_shop.model;

import jakarta.persistence.*;

/**
 * v1.0
 * @Author Sy Viet
 * Role is used to:
 * - define the roles of the users
 */
@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;

    public Role() {

    }

    public Role(ERole name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name.toString();
    }

    public void setName(ERole name) {
        this.name = name;
    }
}
