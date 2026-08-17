package com.saborgourmet.repository;

import com.saborgourmet.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repositorio para operaciones CRUD de Reserva
 */
@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    
    List<Reserva> findByClienteId(Long clienteId);
    
    List<Reserva> findByMesaId(Long mesaId);
    
    List<Reserva> findByEstado(Reserva.EstadoReserva estado);
    
    @Query("SELECT r FROM Reserva r WHERE r.mesa.id = :mesaId AND r.estado IN ('CONFIRMADA', 'ACTIVA') " +
           "AND r.fechaReserva BETWEEN :fechaInicio AND :fechaFin")
    List<Reserva> findReservasConflictivas(@Param("mesaId") Long mesaId, 
                                           @Param("fechaInicio") LocalDateTime fechaInicio,
                                           @Param("fechaFin") LocalDateTime fechaFin);
    
    @Query("SELECT r FROM Reserva r WHERE r.fechaReserva BETWEEN :fechaInicio AND :fechaFin")
    List<Reserva> findReservasPorFecha(@Param("fechaInicio") LocalDateTime fechaInicio,
                                       @Param("fechaFin") LocalDateTime fechaFin);
    
    @Query("SELECT r FROM Reserva r WHERE r.estado = 'PENDIENTE' ORDER BY r.fechaCreacion ASC")
    List<Reserva> findReservasPendientes();
}
