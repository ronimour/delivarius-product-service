package com.delivery.product.web.controller;


import com.delivery.product.services.ProductService;
import com.delivery.product.web.model.ProductDto;
import com.delivery.product.web.model.ProductPagedList;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/api/v1/")
@RestController
public class ProductController {

    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final Integer DEFAULT_PAGE_SIZE = 25;
    public static final String APPLICATION_JSON = MediaType.APPLICATION_JSON_UTF8_VALUE;

    private final ProductService productService;

    @GetMapping(path = "{storeId}/product", produces = {APPLICATION_JSON})
    public ResponseEntity<ProductPagedList> listProductsFromStore(@PathVariable(value = "storeId", required = true) UUID storeId,
                                                                  @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                                  @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                                  @RequestParam(value = "name", required = false) String name,
                                                                  @RequestParam(value = "tags[]", required = false) String[] tags,
                                                                  @RequestParam(value = "showInventoryOnHand", required = false) Boolean showInventoryOnHand){

        if (showInventoryOnHand == null) {
            showInventoryOnHand = false;
        }

        if (pageNumber == null || pageNumber < 0){
            pageNumber = DEFAULT_PAGE_NUMBER;
        }

        if (pageSize == null || pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

        ProductPagedList productList = productService.listProducts(name, tags, PageRequest.of(pageNumber, pageSize), showInventoryOnHand);

        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @GetMapping(path = "product", produces = {APPLICATION_JSON})
    public ResponseEntity<ProductPagedList> listProducts(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                         @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                         @RequestParam(value = "name", required = false) String name,
                                                         @RequestParam(value = "tags[]", required = false) String[] tags,
                                                         @RequestParam(value = "showInventoryOnHand", required = false) Boolean showInventoryOnHand){

        if (showInventoryOnHand == null) {
            showInventoryOnHand = false;
        }

        if (pageNumber == null || pageNumber < 0){
            pageNumber = DEFAULT_PAGE_NUMBER;
        }

        if (pageSize == null || pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

        ProductPagedList productList = productService.listProducts(name, tags, PageRequest.of(pageNumber, pageSize), showInventoryOnHand);

        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @GetMapping( path = "product/{productId}", produces = {APPLICATION_JSON})
    public ResponseEntity<ProductDto> getProductById(@PathVariable("productId") UUID productId,
                                                     @RequestParam(value = "showInventoryOnHand", required = false) Boolean showInventoryOnHand){
        if (showInventoryOnHand == null) {
            showInventoryOnHand = false;
        }

        return new ResponseEntity<>(productService.getById(productId, showInventoryOnHand), HttpStatus.OK);
    }

    @GetMapping( path = "product/upc/{upc}", produces = {APPLICATION_JSON})
    public ResponseEntity<ProductDto> getProductByUpc(@PathVariable("upc") String upc){
        return new ResponseEntity<>(productService.getByUpc(upc), HttpStatus.OK);
    }

    @PostMapping(path = "product", produces = {APPLICATION_JSON})
    public ResponseEntity saveNewProduct(@RequestBody @Validated ProductDto ProductDto){
        return new ResponseEntity<>(productService.saveNewProduct(ProductDto), HttpStatus.CREATED);
    }

    @PutMapping(path = "product/{productId}", produces = {APPLICATION_JSON})
    public ResponseEntity updateProductById(@PathVariable("productId") UUID productId, @RequestBody @Validated ProductDto ProductDto){
        return new ResponseEntity<>(productService.updateProduct(productId, ProductDto), HttpStatus.NO_CONTENT);
    }

}
