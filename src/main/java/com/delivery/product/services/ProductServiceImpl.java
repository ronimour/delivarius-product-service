package com.delivery.product.services;

import com.delivery.product.domain.Product;
import com.delivery.product.domain.Tag;
import com.delivery.product.repositories.ProductRepository;
import com.delivery.product.repositories.TagRepository;
import com.delivery.product.web.mappers.ProductMapper;
import com.delivery.product.web.model.ProductDto;
import com.delivery.product.web.model.ProductPagedList;
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
    private TagRepository tagRepository;

    @Autowired
    private ProductMapper productMapper;

    //@Cacheable(cacheNames = "productListFromStoreCache", condition = "#showInventoryOnHand == false ")
    @Override
    public ProductPagedList listProductsFromStore(@NonNull UUID storeId, String name, Collection<String> tags, PageRequest pageRequest, Boolean showInventoryOnHand) {
        ProductPagedList productPagedList;
        Page<Product> productPage;

        Collection<Tag> tagsEntityCollection = null;
        if(!CollectionUtils.isEmpty(tags)){
            tagsEntityCollection = tagRepository.findAllByValueIn(tags);
        }

        if (!StringUtils.isEmpty(name) && !CollectionUtils.isEmpty(tags)) {
            //search both
            productPage = productRepository.findAllByNameAndStoreIdAndTagsIn(name, storeId.toString(),  tagsEntityCollection, pageRequest);
        } else if (!StringUtils.isEmpty(name) && CollectionUtils.isEmpty(tags)) {
            //search beer_service name
            productPage = productRepository.findAllByNameAndStoreId(name, storeId.toString(), pageRequest);
        } else if (StringUtils.isEmpty(name) && !CollectionUtils.isEmpty(tags)) {
            //search beer_service style
            productPage = productRepository.findAllByTagsInAndStoreId(tagsEntityCollection, storeId.toString(), pageRequest);
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
    public ProductPagedList listProducts(String name, Collection<String> tags, PageRequest pageRequest, Boolean showInventoryOnHand, BigDecimal minPrice, BigDecimal maxPrice) {
        ProductPagedList productPagedList;
        Page<Product> productPage;

        boolean byPrice = minPrice != null || maxPrice != null;
        boolean byName = !StringUtils.isEmpty(name);
        boolean byTags = !CollectionUtils.isEmpty(tags);

        boolean byAllFilters = byName && byTags && byPrice;
        boolean byPriceAndName = byPrice && byName && !byTags;
        boolean byPriceAndTags = byPrice && byTags && !byName;
        boolean byTagsAndName = byTags && byName && !byPrice;
        boolean byOnlyName = !byTags && byName && !byPrice;
        boolean byOnlyPrice = !byTags && !byName && byPrice;
        boolean byOnlyTags = byTags && !byName && !byPrice;

        Collection<Tag> tagsEntityCollection = null;
        if(byTags){
            tagsEntityCollection = tagRepository.findAllByValueIn(tags);
        }

        if (byAllFilters) {
            productPage = productRepository.findAllByNameAndPriceBetweenAndTagsIn(name, minPrice, maxPrice, tagsEntityCollection, pageRequest);
        } else if (byPriceAndName) {
            productPage = productRepository.findAllByNameAndPriceBetween(name, minPrice, maxPrice, pageRequest);
        } else if (byPriceAndTags) {
            productPage = productRepository.findAllByPriceBetweenAndTagsIn(minPrice, maxPrice, tagsEntityCollection, pageRequest);
        } else if(byTagsAndName){
            productPage = productRepository.findAllByNameAndTagsIn(name, tagsEntityCollection, pageRequest);
        } else if(byOnlyName){
            productPage = productRepository.findAllByName(name, pageRequest);
        }  else if(byOnlyPrice){
            productPage = productRepository.findAllByPriceBetween(minPrice, maxPrice, pageRequest);
        }  else if(byOnlyTags){
            productPage = productRepository.findAllByTagsIn(tagsEntityCollection, pageRequest);
        }  else {
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
