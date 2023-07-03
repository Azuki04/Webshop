package ch.web.web_shop.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * v1.0
 * @Author Sy Viet
 * Newsletter is used to:
 * - store the email addresses of the users who want to receive the newsletter
 */
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
