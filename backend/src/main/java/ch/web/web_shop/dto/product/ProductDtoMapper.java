package ch.web.web_shop.dto.product;

import ch.web.web_shop.model.FileModel;
import ch.web.web_shop.model.ProductModel;
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
    public List<ProductResponseDto> convertToDto(List<ProductModel> products) {
        List<ProductResponseDto> productResponseDtoList = new ArrayList<>();

        for (ProductModel product : products) {

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
    public ProductResponseDto convertToDto(ProductModel product ) {


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

    private List<String> getFileFromProduct(ProductModel product) {
        List<FileModel> files = fileRepository.findByProduct(product);
        List<String> imagePaths = new ArrayList<>();

        for (FileModel file : files) {
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("api/file/downloadFile/")
                    .path(file.getName())
                    .toUriString();

            imagePaths.add(fileDownloadUri);
        }
        return imagePaths;
    }

}
