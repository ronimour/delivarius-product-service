package com.delivery.product.services;

import com.delivery.product.domain.Product;
import com.delivery.product.repositories.ProductRepository;
import com.delivery.product.web.mappers.ProductMapper;
import com.delivery.product.web.model.ProductDto;
import com.delivery.product.web.model.ProductPagedList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    //@Cacheable(cacheNames = "productListFromStoreCache", condition = "#showInventoryOnHand == false ")
    @Override
    public ProductPagedList listProductsFromStore(@NonNull UUID storeId, String name, String[] tags, PageRequest pageRequest, Boolean showInventoryOnHand) {
        ProductPagedList productPagedList;
        Page<Product> productPage;

        if (!StringUtils.isEmpty(name) && !StringUtils.isEmpty(tags)) {
            //search both
            productPage = productRepository.findAllByNameAndStoreIdAndTagsIn(name, storeId.toString(),  tags, pageRequest);
        } else if (!StringUtils.isEmpty(name) && StringUtils.isEmpty(tags)) {
            //search beer_service name
            productPage = productRepository.findAllByNameAndStoreId(name, storeId.toString(), pageRequest);
        } else if (StringUtils.isEmpty(name) && !StringUtils.isEmpty(tags)) {
            //search beer_service style
            productPage = productRepository.findAllByTagsInAndStoreId(tags, storeId.toString(), pageRequest);
        } else {
            productPage = productRepository.findAllByStoreId(storeId.toString(), pageRequest);
        }

        if (showInventoryOnHand){
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

    //@Cacheable(cacheNames = "productListCache", condition = "#showInventoryOnHand == false ")
    @Override
    public ProductPagedList listProducts(String name, String[] tags, PageRequest pageRequest, Boolean showInventoryOnHand) {
        ProductPagedList productPagedList;
        Page<Product> productPage;

        if (!StringUtils.isEmpty(name) && !StringUtils.isEmpty(tags)) {
            //search both
            productPage = productRepository.findAllByNameAndTagsIn(name, tags, pageRequest);
        } else if (!StringUtils.isEmpty(name) && StringUtils.isEmpty(tags)) {
            //search beer_service name
            productPage = productRepository.findAllByName(name, pageRequest);
        } else if (StringUtils.isEmpty(name) && !StringUtils.isEmpty(tags)) {
            //search beer_service style
            productPage = productRepository.findAllByTagsIn(tags, pageRequest);
        } else {
            productPage = productRepository.findAll(pageRequest);
        }

        if (showInventoryOnHand){
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
}
