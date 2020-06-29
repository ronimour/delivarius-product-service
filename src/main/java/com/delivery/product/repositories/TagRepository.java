package com.delivery.product.repositories;

import com.delivery.product.domain.Tag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TagRepository extends CrudRepository<Tag, UUID> {

}
