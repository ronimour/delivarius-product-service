package com.delivery.product.services;

import com.delivery.product.domain.Product;
import com.delivery.product.domain.QProduct;
import com.delivery.product.domain.QTag;
import com.delivery.product.domain.Tag;
import com.delivery.product.repositories.ProductRepository;
import com.delivery.product.repositories.TagRepository;
import com.delivery.product.web.mappers.ProductMapper;
import com.delivery.product.web.model.ProductDto;
import com.delivery.product.web.model.ProductPagedList;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    private final QProduct qProduct = QProduct.product;

    //@Cacheable(cacheNames = "productListFromStoreCache", condition = "#showInventoryOnHand == false ")
    @Override
    public ProductPagedList listProductsFromStore(@NonNull UUID storeId, String name, Collection<String> tags, PageRequest pageRequest, Boolean showInventoryOnHand, BigDecimal minPrice, BigDecimal maxPrice) {
        BooleanExpression where = qProduct.storeId.eq(storeId);
        return getProductDtos(name, tags, pageRequest, showInventoryOnHand, minPrice, maxPrice, where);
    }

    //@Cacheable(cacheNames = "productListCache", condition = "#showInventoryOnHand == false ")
    @Override
    public ProductPagedList listProducts(String name, Collection<String> tags, PageRequest pageRequest, Boolean showInventoryOnHand, BigDecimal minPrice, BigDecimal maxPrice) {
        BooleanExpression where = qProduct.id.isNotNull();//neutral expression to handle it null safe
        return getProductDtos(name, tags, pageRequest, showInventoryOnHand, minPrice, maxPrice, where);
    }

    @Override
    public ProductDto getById(UUID productId, Boolean showInventoryOnHand) {
        return null;
    }

    @Override
    public ProductDto saveNewProduct(ProductDto productDto) {
        return null;
    }

    @Override
    public ProductDto updateProduct(UUID productId, ProductDto productDto) {
        return null;
    }

    @Override
    public ProductDto getByUpc(String upc) {
        return null;
    }

    private ProductPagedList getProductDtos(String name, Collection<String> tags, PageRequest pageRequest, Boolean showInventoryOnHand, BigDecimal minPrice, BigDecimal maxPrice, BooleanExpression where) {
        if(!StringUtils.isEmpty(name))
            where = where.and(qProduct.name.eq(name));
        if(minPrice != null)
            where = where.and(qProduct.price.gt(minPrice));
        if(maxPrice != null)
            where = where.and(qProduct.price.lt(maxPrice));
        if(!CollectionUtils.isEmpty(tags))
            where = where.and(qProduct.tags.any().value.in(tags));

        Page<Product> productPage = productRepository.findAll(where, pageRequest);
        ProductPagedList productPagedList = getProductDtos(showInventoryOnHand, productPage);

        return productPagedList;
    }

    private ProductPagedList getProductDtos(Boolean showInventoryOnHand, Page<Product> productPage) {
        ProductPagedList productPagedList;
        if (showInventoryOnHand) {
            productPagedList = new ProductPagedList(productPage
                    .getContent()
                    .stream()
                    .map(productMapper::productToProductDtoWithInventory)
                    .collect(Collectors.toList()),
                    PageRequest
                            .of(productPage.getPageable().getPageNumber(),
                                    productPage.getPageable().getPageSize()),
                    productPage.getTotalElements());
        } else {
            productPagedList = new ProductPagedList(productPage
                    .getContent()
                    .stream()
                    .map(productMapper::productToProductDto)
                    .collect(Collectors.toList()),
                    PageRequest
                            .of(productPage.getPageable().getPageNumber(),
                                    productPage.getPageable().getPageSize()),
                    productPage.getTotalElements());
        }
        return productPagedList;
    }
}
