package ch.web.web_shop.service;

import ch.web.web_shop.dto.newsletter.NewsletterDto;
import ch.web.web_shop.model.NewsletterModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.web.web_shop.repository.NewsletterRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class NewsletterService implements INewsletterService {

    private final NewsletterRepository newsletterRepository;

    @Autowired
    public NewsletterService(NewsletterRepository newsletterRepository) {
        this.newsletterRepository = newsletterRepository;
    }

    @Transactional
    @Override
    public NewsletterModel createNewsletter(NewsletterDto newsletterDTO) {
        NewsletterModel newsletter = new NewsletterModel(newsletterDTO.getEmail());
        return newsletterRepository.save(newsletter);
    }
}
