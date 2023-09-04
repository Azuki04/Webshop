package ch.web.web_shop.service;

import ch.web.web_shop.model.Product;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IFileStorageService {

    String storeFileOnly(MultipartFile file);

    void storeFile(MultipartFile[] files, Product product);

    Resource loadFileAsResource(String fileName);
}
