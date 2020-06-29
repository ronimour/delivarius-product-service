package com.delivery.product.web.mappers;

import com.delivery.product.domain.Product;
import com.delivery.product.web.model.ProductDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {DateMapper.class, TagMapper.class})
@DecoratedWith(ProductMapperDecorator.class)
public interface ProductMapper {

    ProductDto productToProductDto(Product beer);

    @Mapping(target = "images", ignore = true)
    ProductDto productToProductDtoWithInventory(Product beer);

    Product productDtoToProduct(ProductDto dto);
}
