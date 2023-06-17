package ch.web.web_shop.model;

import ch.web.web_shop.model.Newsletter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class NewsletterTest {

    @Test
    void testCreateNewsletter() {
        String email = "test@example.com";
        Newsletter newsletter = new Newsletter(email);

        Assertions.assertEquals(email, newsletter.getEmail());
    }
}