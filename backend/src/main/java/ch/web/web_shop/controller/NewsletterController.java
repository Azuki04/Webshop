package ch.web.web_shop.controller;

import ch.web.web_shop.dto.NewsletterDTO;
import ch.web.web_shop.model.Newsletter;
import ch.web.web_shop.service.NewsletterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

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
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public Newsletter createNewsletter(@Valid @RequestBody NewsletterDTO newsletterDTO) {
		return newsletterService.createNewsletter(newsletterDTO);
	}
}
