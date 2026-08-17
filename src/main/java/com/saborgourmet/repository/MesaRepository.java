package com.saborgourmet.repository;

import com.saborgourmet.model.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para operaciones CRUD de Mesa
 */
@Repository
public interface MesaRepository extends JpaRepository<Mesa, Long> {
    List<Mesa> findByEstado(Mesa.EstadoMesa estado);
    
    List<Mesa> findByCapacidadGreaterThanEqual(Integer capacidad);
    
    @Query("SELECT m FROM Mesa m WHERE m.estado = 'DISPONIBLE' AND m.capacidad >= :capacidad")
    List<Mesa> findAvailableMesasForCapacity(Integer capacidad);
}
