package com.delivery.product.repositories;

import com.delivery.product.domain.Product;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface ProductRepository extends PagingAndSortingRepository<Product, UUID>, QuerydslPredicateExecutor<Product> {
}
