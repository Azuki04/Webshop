package ch.web.web_shop.service;

import ch.web.web_shop.dto.NewsletterDTO;
import ch.web.web_shop.model.Newsletter;
import ch.web.web_shop.repository.NewsletterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class NewsletterServiceTest {

    @Mock
    private NewsletterRepository newsletterRepository;

    @InjectMocks
    private NewsletterService newsletterService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateNewsletter_Success() {
        NewsletterDTO newsletterDTO = new NewsletterDTO();
        Newsletter newsletter = new Newsletter();

        when(newsletterRepository.save(any(Newsletter.class))).thenReturn(newsletter);

        Newsletter result = newsletterService.createNewsletter(newsletterDTO);

        assertEquals(newsletter, result);
    }
}
