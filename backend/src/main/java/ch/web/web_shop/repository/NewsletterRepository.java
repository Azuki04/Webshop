package ch.web.web_shop.repository;

import ch.web.web_shop.model.NewsletterModel;
import org.springframework.data.repository.CrudRepository;



public interface NewsletterRepository extends CrudRepository<NewsletterModel, Long> {

}