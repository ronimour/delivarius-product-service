package com.delivery.product.repositories;

import com.delivery.product.domain.Product;
import com.delivery.product.domain.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    Page<Product> findAllByName(String name, Pageable pageable);

    Page<Product> findAllByNameAndPriceBetween(String name, BigDecimal min, BigDecimal max, Pageable pageable);

    Page<Product> findAllByNameAndPriceBetweenAndTagsIn(String name, BigDecimal min, BigDecimal max, Collection<Tag> tags, Pageable pageable);

    Page<Product> findAllByPriceBetween(BigDecimal min, BigDecimal max, Pageable pageable);

    Page<Product> findAllByStoreId(String storeId, Pageable pageable);

    Page<Product> findAllByNameAndTagsIn(String name, Collection<Tag> tags, Pageable pageable);

    Page<Product> findAllByPriceBetweenAndTagsIn(BigDecimal min, BigDecimal max, Collection<Tag> tags, Pageable pageable);

    Page<Product> findAllByNameAndStoreIdAndTagsIn(String name, String storedId, Collection<Tag> tags, Pageable pageable);

    Page<Product> findAllByNameAndStoreId(String name, String storedId, Pageable pageable);

    Page<Product> findAllByTagsIn(Collection<Tag> tags, Pageable pageable);

    Page<Product> findAllByTagsInAndStoreId(Collection<Tag> tags, String storeId, Pageable pageable);

    Product findByUpc(String upc);
}
