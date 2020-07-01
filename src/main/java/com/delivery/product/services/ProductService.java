package com.delivery.product.services;

import com.delivery.product.web.model.ProductDto;
import com.delivery.product.web.model.ProductPagedList;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.UUID;

public interface ProductService {

    ProductPagedList listProductsFromStore(UUID storeId, String name, Collection<String> tags, PageRequest pageRequest, Boolean showInventoryOnHand, BigDecimal minPrice, BigDecimal maxPrice);

    ProductPagedList listProducts(String name, Collection<String> tags, PageRequest pageRequest, Boolean showInventoryOnHand, BigDecimal minPrice, BigDecimal maxPrice);

    ProductDto getById(UUID productId, Boolean showInventoryOnHand);

    ProductDto saveNewProduct(ProductDto productDto);

    ProductDto updateProduct(UUID productId, ProductDto productDto);

    ProductDto getByUpc(String upc);
}
