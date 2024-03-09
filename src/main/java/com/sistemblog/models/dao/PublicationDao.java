package com.sistemblog.models.dao;

import com.sistemblog.models.entities.Publication;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublicationDao extends CrudRepository<Publication, Long> {
    Object findAll(Pageable pageable);
}
