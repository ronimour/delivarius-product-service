package com.delivery.product.repositories;

import com.delivery.product.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    Page<Product> findAllByName(String name, Pageable pageable);

    Page<Product> findAllByStoreId(String storeId, Pageable pageable);

    Page<Product> findAllByNameAndTagsIn(String name, String[] tags, Pageable pageable);

    Page<Product> findAllByNameAndStoreIdAndTagsIn(String name, String storedId, String[] tags, Pageable pageable);

    Page<Product> findAllByNameAndStoreId(String name, String storedId, Pageable pageable);

    Page<Product> findAllByTagsIn(String[] tags, Pageable pageable);

    Page<Product> findAllByTagsInAndStoreId(String[] tags, String storeId, Pageable pageable);

    Product findByUpc(String upc);
}
