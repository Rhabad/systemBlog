package com.sistemblog.models.dao;

import com.sistemblog.models.entities.Rol;
import com.sistemblog.models.entities.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolDao extends CrudRepository<Rol, Long> {
    @Query(value = "select * from roles where nombre = :nombre", nativeQuery = true)
    Optional<Rol> findByNombre(String nombre);
}
