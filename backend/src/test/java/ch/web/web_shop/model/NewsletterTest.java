package ch.web.web_shop.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class NewsletterTest {

    @Test
    void testCreateNewsletter() {
        String email = "test@example.com";
        NewsletterModel newsletter = new NewsletterModel(email);

        Assertions.assertEquals(email, newsletter.getEmail());
    }
}