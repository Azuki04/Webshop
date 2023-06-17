package ch.web.web_shop.dto;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class NewsletterDTO {

    @NotEmpty(message = "Email is mandatory")
    @NotNull(message = "Email cannot be null")
    @Email(message = "Invalid email format")
    private String email;

    //Empty Constructor
    public NewsletterDTO() {
        //Empty Constructor
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
