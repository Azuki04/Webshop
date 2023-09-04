package ch.web.web_shop.service;

import ch.web.web_shop.dto.newsletter.NewsletterDto;
import ch.web.web_shop.model.NewsletterModel;
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
        NewsletterDto newsletterDTO = new NewsletterDto();
        NewsletterModel newsletter = new NewsletterModel();

        when(newsletterRepository.save(any(NewsletterModel.class))).thenReturn(newsletter);

        NewsletterModel result = newsletterService.createNewsletter(newsletterDTO);

        assertEquals(newsletter, result);
    }
}
