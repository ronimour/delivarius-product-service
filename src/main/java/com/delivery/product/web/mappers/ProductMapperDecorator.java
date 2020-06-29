package com.delivery.product.web.mappers;


import com.delivery.product.domain.Product;
import com.delivery.product.services.inventory.ProductInventoryService;
import com.delivery.product.web.model.ImageDto;
import com.delivery.product.web.model.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;


public abstract class ProductMapperDecorator implements ProductMapper {
    private ProductInventoryService productInventoryService;
    private ProductMapper mapper;

    @Autowired
    public void setBeerInventoryService(ProductInventoryService beerInventoryService) {
        this.productInventoryService = beerInventoryService;
    }

    @Autowired
    public void setMapper(ProductMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public ProductDto productToProductDto(Product product) {
        ProductDto productDto = mapper.productToProductDto(product);
        productDto.setImages(ImageDto.builder()
                        .imageMedium(product.getImageMedium())
                        .imageLarge(product.getImageLarge())
                        .imageSmall(product.getImageSmall())
                .build());
        return productDto;
    }

    @Override
    public ProductDto productToProductDtoWithInventory(Product beer) {
        ProductDto dto = this.productToProductDto(beer);
        dto.setQuantityOnHand(productInventoryService.getOnhandInventory(beer.getId()));
        return dto;
    }

    @Override
    public Product productDtoToProduct(ProductDto beerDto) {
        return mapper.productDtoToProduct(beerDto);
    }
}
