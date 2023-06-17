package ch.web.web_shop.controller;

import ch.web.web_shop.dto.NewsletterDTO;
import ch.web.web_shop.model.Newsletter;
import ch.web.web_shop.service.NewsletterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/newsletter")
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
