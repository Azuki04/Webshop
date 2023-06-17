package ch.web.web_shop.repository;

import ch.web.web_shop.model.Newsletter;
import org.springframework.data.repository.CrudRepository;



public interface NewsletterRepository extends CrudRepository<Newsletter, Long> {

}