package ch.web.web_shop.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "newsletter")
@Data
@NoArgsConstructor
@ToString
public class Newsletter {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "email")
	@NotEmpty(message = "Email is mandatory")
	@NotNull(message = "Email cannot be null")
	@Email(message = "Invalid email format")
	private String email;

	public Newsletter(String email) {
		this.email = email;
	}
}
