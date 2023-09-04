package ch.web.web_shop.service;

import ch.web.web_shop.dto.newsletter.NewsletterDTO;
import ch.web.web_shop.model.Newsletter;

public interface INewsletterService {


    Newsletter createNewsletter(NewsletterDTO newsletterDTO);
}
