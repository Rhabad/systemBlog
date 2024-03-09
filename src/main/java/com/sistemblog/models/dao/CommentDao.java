package com.sistemblog.models.dao;

import com.sistemblog.models.dto.CommentDto;
import com.sistemblog.models.entities.Comment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentDao extends CrudRepository<Comment, Long> {

    @Query(value = "select * from comment where publication_id = :idPublicacion", nativeQuery = true)
    List<Comment> findByIdPublication(Long idPublicacion);
}
