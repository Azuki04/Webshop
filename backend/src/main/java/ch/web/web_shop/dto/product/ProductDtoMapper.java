package ch.web.web_shop.dto.product;

import ch.web.web_shop.model.File;
import ch.web.web_shop.model.Product;
import ch.web.web_shop.repository.FileRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductDtoMapper implements IProductDtoMapper {

    private final FileRepository fileRepository;

    public ProductDtoMapper(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }


    @Override
    public List<ProductResponseDto> convertToDto(List<Product> products) {
        List<ProductResponseDto> productResponseDtoList = new ArrayList<>();

        for (Product product : products) {

            productResponseDtoList.add(new ProductResponseDto(
                    product.getId(),
                    product.getTitle(),
                    product.getDescription(),
                    product.getContent(),
                    product.getPrice(),
                    product.getStock(),
                    product.isPublished(),
                    product.getCategory(),
                    product.getUser(),
                    getFileFromProduct(product)));
        }
        return productResponseDtoList;
    }

    @Override
    public ProductResponseDto convertToDto(Product product ) {


        return new ProductResponseDto(
                product.getId(),
                product.getTitle(),
                product.getDescription(),
                product.getContent(),
                product.getPrice(),
                product.getStock(),
                product.isPublished(),
                product.getCategory(),
                product.getUser(),
                getFileFromProduct(product));
    }

    private List<String> getFileFromProduct(Product product) {
        List<File> files = fileRepository.findByProduct(product);
        List<String> imagePaths = new ArrayList<>();

        for (File file : files) {
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("api/file/downloadFile/")
                    .path(file.getName())
                    .toUriString();

            imagePaths.add(fileDownloadUri);
        }
        return imagePaths;
    }

}
