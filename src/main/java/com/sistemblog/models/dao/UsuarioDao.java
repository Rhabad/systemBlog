package com.sistemblog.models.dao;

import com.sistemblog.models.entities.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioDao extends CrudRepository<Usuario, Long> {

    @Query(value = "select * from usuario where email = :email", nativeQuery = true)
    Optional<Usuario> findByEmail(String email);

    @Query(value = "select * from usuario where email = :email or username = :username", nativeQuery = true)
    Optional<Usuario> findByUsernameOrEmail(String username, String email);

    @Query(value = "select * from usuario where username = :username", nativeQuery = true)
    Optional<Usuario> findByUsername(String username);

    @Query(value = "select count(*) > 0 as existe from usuario where username = :username", nativeQuery = true)
    Long existByUsername(String username);

    @Query(value = "select count(*) > 0 as existe from usuario where email = :email", nativeQuery = true)
    Long existByEmail(String email);
}
