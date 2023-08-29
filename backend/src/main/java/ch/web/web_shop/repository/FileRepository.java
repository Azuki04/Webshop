package ch.web.web_shop.repository;

import ch.web.web_shop.model.File;
import ch.web.web_shop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<File, Long> {
   List<File> findByProduct(Product product);

}
