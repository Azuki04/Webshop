package ch.web.web_shop.service;

import ch.web.web_shop.dto.newsletter.NewsletterDto;
import ch.web.web_shop.model.NewsletterModel;

public interface INewsletterService {
    NewsletterModel createNewsletter(NewsletterDto newsletterDto);
}
