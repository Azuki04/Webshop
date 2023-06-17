package ch.web.web_shop.service;

import ch.web.web_shop.dto.NewsletterDTO;
import ch.web.web_shop.model.Newsletter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.web.web_shop.repository.NewsletterRepository;

@Service
public class NewsletterService {

    private final NewsletterRepository newsletterRepository;

    @Autowired
    public NewsletterService(NewsletterRepository newsletterRepository) {
        this.newsletterRepository = newsletterRepository;
    }

    public Newsletter createNewsletter(NewsletterDTO newsletterDTO) {
        Newsletter newsletter = new Newsletter(newsletterDTO.getEmail());
        return newsletterRepository.save(newsletter);
    }
}
