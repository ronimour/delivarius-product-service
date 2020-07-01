package com.delivery.product.repositories;

import com.delivery.product.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.UUID;

public interface TagRepository extends JpaRepository<Tag, UUID> {

    Collection<Tag> findAllByValueIn(Collection<String> values);
}
