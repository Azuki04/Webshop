package ch.web.web_shop.repository;

import ch.web.web_shop.model.NewsletterModel;
import org.springframework.data.jpa.repository.JpaRepository;



public interface NewsletterRepository extends JpaRepository<NewsletterModel, Long> {

}