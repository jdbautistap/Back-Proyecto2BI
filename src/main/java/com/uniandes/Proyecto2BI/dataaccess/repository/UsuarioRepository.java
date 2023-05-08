package com.uniandes.Proyecto2BI.dataaccess.repository;

import com.uniandes.Proyecto2BI.dataaccess.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
    Usuario findByEmail(String email);

    Optional<Usuario> findById(Long id);
}