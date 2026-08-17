package com.saborgourmet.repository;

import com.saborgourmet.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

/**
 * Repositorio para operaciones CRUD de Cliente
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByEmail(String email);
    List<Cliente> findByNombreContainingIgnoreCase(String nombre);
}
