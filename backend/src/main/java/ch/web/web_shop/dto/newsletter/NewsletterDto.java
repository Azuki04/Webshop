package ch.web.web_shop.dto.newsletter;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/**
 * v1.0
 * @Author Sy Viet
 * NewsletterDTO is used to:
 * - store the email addresses of the users who want to receive the newsletter
 */
public class NewsletterDto {

    @NotEmpty(message = "Email is mandatory")
    @NotNull(message = "Email cannot be null")
    @Email(message = "Invalid email format")
    private String email;

    //Empty Constructor
    public NewsletterDto() {
        //Empty Constructor
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
