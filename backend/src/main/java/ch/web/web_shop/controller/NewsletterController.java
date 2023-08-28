package ch.web.web_shop.controller;

import ch.web.web_shop.dto.newsletter.NewsletterDTO;
import ch.web.web_shop.model.Newsletter;
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

    private final NewsletterService newsletterService;

    @Autowired
    public NewsletterController(NewsletterService newsletterService) {
        this.newsletterService = newsletterService;
    }

    @PostMapping("")
    public Newsletter createNewsletter(@Valid @RequestBody NewsletterDTO newsletterDTO) {
        return newsletterService.createNewsletter(newsletterDTO);
    }
}
