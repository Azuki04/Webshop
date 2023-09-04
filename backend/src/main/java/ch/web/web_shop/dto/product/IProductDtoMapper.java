package ch.web.web_shop.dto.product;

import ch.web.web_shop.model.ProductModel;

import java.util.List;

public interface IProductDtoMapper {

    List<ProductResponseDto> convertToDto(List<ProductModel> products);

    ProductResponseDto convertToDto(ProductModel product);
}
