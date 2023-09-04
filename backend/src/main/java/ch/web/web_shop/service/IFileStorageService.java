package ch.web.web_shop.service;

import ch.web.web_shop.model.ProductModel;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IFileStorageService {

    String storeFileOnly(MultipartFile file);

    void storeFile(MultipartFile[] files, ProductModel product);

    Resource loadFileAsResource(String fileName);
}
