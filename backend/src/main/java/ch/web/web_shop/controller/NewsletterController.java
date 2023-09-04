package ch.web.web_shop.controller;

import ch.web.web_shop.dto.newsletter.NewsletterDto;
import ch.web.web_shop.model.NewsletterModel;
import ch.web.web_shop.service.INewsletterService;
import ch.web.web_shop.service.NewsletterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * v1.0
 *
 * @Author Sy Viet
 * NewsletterController is used to:
 * - create newsletter
 */

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/newsletter")
public class NewsletterController {

    private final INewsletterService newsletterService;

    @Autowired
    public NewsletterController(NewsletterService newsletterService) {
        this.newsletterService = newsletterService;
    }

    @PostMapping("")
    public NewsletterModel createNewsletter(@Valid @RequestBody NewsletterDto newsletterDTO) {
        return newsletterService.createNewsletter(newsletterDTO);
    }
}
