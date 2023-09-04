package ch.web.web_shop.dto.product;

import ch.web.web_shop.model.Product;

import java.util.List;

public interface IProductDtoMapper {

    List<ProductResponseDto> convertToDto(List<Product> products);

    ProductResponseDto convertToDto(Product product);
}
