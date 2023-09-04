package ch.web.web_shop.repository;

import ch.web.web_shop.model.FileModel;
import ch.web.web_shop.model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<FileModel, Long> {
   List<FileModel> findByProduct(ProductModel product);

    void deleteByProduct(ProductModel product);
}
