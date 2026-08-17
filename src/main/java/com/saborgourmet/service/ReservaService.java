package com.saborgourmet.service;

import com.saborgourmet.model.Cliente;
import com.saborgourmet.model.Mesa;
import com.saborgourmet.model.Reserva;
import com.saborgourmet.repository.ClienteRepository;
import com.saborgourmet.repository.MesaRepository;
import com.saborgourmet.repository.ReservaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Servicio para la gestión de reservas
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final ClienteRepository clienteRepository;
    private final MesaRepository mesaRepository;

    /**
     * Obtiene todas las reservas
     */
    public List<Reserva> obtenerTodas() {
        return reservaRepository.findAll();
    }

    /**
     * Obtiene una reserva por ID
     */
    public Optional<Reserva> obtenerPorId(Long id) {
        return reservaRepository.findById(id);
    }

    /**
     * Obtiene reservas de un cliente
     */
    public List<Reserva> obtenerPorCliente(Long clienteId) {
        return reservaRepository.findByClienteId(clienteId);
    }

    /**
     * Obtiene reservas de una mesa
     */
    public List<Reserva> obtenerPorMesa(Long mesaId) {
        return reservaRepository.findByMesaId(mesaId);
    }

    /**
     * Obtiene reservas por estado
     */
    public List<Reserva> obtenerPorEstado(Reserva.EstadoReserva estado) {
        return reservaRepository.findByEstado(estado);
    }

    /**
     * Obtiene reservas en un rango de fechas
     */
    public List<Reserva> obtenerPorFecha(LocalDateTime inicio, LocalDateTime fin) {
        return reservaRepository.findReservasPorFecha(inicio, fin);
    }

    /**
     * Obtiene reservas pendientes de confirmación
     */
    public List<Reserva> obtenerReservasPendientes() {
        return reservaRepository.findReservasPendientes();
    }

    /**
     * Crea una nueva reserva después de validar disponibilidad
     */
    public Reserva crear(Long clienteId, Long mesaId, LocalDateTime fechaReserva, 
                        Integer numeroComensales, String observaciones) {
        
        // Validar cliente
        Cliente cliente = clienteRepository.findById(clienteId)
            .orElseThrow(() -> new RuntimeException("Cliente no encontrado: " + clienteId));
        
        // Validar mesa
        Mesa mesa = mesaRepository.findById(mesaId)
            .orElseThrow(() -> new RuntimeException("Mesa no encontrada: " + mesaId));
        
        // Validar capacidad
        if (numeroComensales > mesa.getCapacidad()) {
            throw new RuntimeException("El número de comensales excede la capacidad de la mesa");
        }
        
        // Validar disponibilidad
        validarDisponibilidad(mesaId, fechaReserva);
        
        // Crear la reserva
        Reserva reserva = Reserva.builder()
            .cliente(cliente)
            .mesa(mesa)
            .fechaReserva(fechaReserva)
            .numeroComensales(numeroComensales)
            .observaciones(observaciones)
            .estado(Reserva.EstadoReserva.PENDIENTE)
            .build();
        
        return reservaRepository.save(reserva);
    }

    /**
     * Actualiza una reserva existente
     */
    public Reserva actualizar(Long id, LocalDateTime fechaReserva, 
                             Integer numeroComensales, String observaciones) {
        return reservaRepository.findById(id).map(reserva -> {
            // Si la fecha o mesa cambió, validar disponibilidad
            if (!reserva.getFechaReserva().equals(fechaReserva)) {
                validarDisponibilidad(reserva.getMesa().getId(), fechaReserva);
            }
            
            // Validar capacidad
            if (numeroComensales > reserva.getMesa().getCapacidad()) {
                throw new RuntimeException("El número de comensales excede la capacidad de la mesa");
            }
            
            reserva.setFechaReserva(fechaReserva);
            reserva.setNumeroComensales(numeroComensales);
            reserva.setObservaciones(observaciones);
            return reservaRepository.save(reserva);
        }).orElseThrow(() -> new RuntimeException("Reserva no encontrada: " + id));
    }

    /**
     * Confirma una reserva pendiente
     */
    public Reserva confirmar(Long id) {
        return reservaRepository.findById(id).map(reserva -> {
            if (!reserva.getEstado().equals(Reserva.EstadoReserva.PENDIENTE)) {
                throw new RuntimeException("Solo se pueden confirmar reservas pendientes");
            }
            reserva.setEstado(Reserva.EstadoReserva.CONFIRMADA);
            // Actualizar estado de la mesa
            reserva.getMesa().setEstado(Mesa.EstadoMesa.RESERVADA);
            return reservaRepository.save(reserva);
        }).orElseThrow(() -> new RuntimeException("Reserva no encontrada: " + id));
    }

    /**
     * Marca una reserva como activa (el cliente llegó)
     */
    public Reserva marcarActiva(Long id) {
        return reservaRepository.findById(id).map(reserva -> {
            if (!reserva.getEstado().equals(Reserva.EstadoReserva.CONFIRMADA)) {
                throw new RuntimeException("Solo se pueden activar reservas confirmadas");
            }
            reserva.setEstado(Reserva.EstadoReserva.ACTIVA);
            reserva.getMesa().setEstado(Mesa.EstadoMesa.OCUPADA);
            return reservaRepository.save(reserva);
        }).orElseThrow(() -> new RuntimeException("Reserva no encontrada: " + id));
    }

    /**
     * Completa una reserva
     */
    public Reserva completar(Long id) {
        return reservaRepository.findById(id).map(reserva -> {
            if (!reserva.getEstado().equals(Reserva.EstadoReserva.ACTIVA)) {
                throw new RuntimeException("Solo se pueden completar reservas activas");
            }
            reserva.setEstado(Reserva.EstadoReserva.COMPLETADA);
            reserva.getMesa().setEstado(Mesa.EstadoMesa.DISPONIBLE);
            return reservaRepository.save(reserva);
        }).orElseThrow(() -> new RuntimeException("Reserva no encontrada: " + id));
    }

    /**
     * Cancela una reserva
     */
    public Reserva cancelar(Long id, String motivo) {
        return reservaRepository.findById(id).map(reserva -> {
            if (reserva.getEstado().equals(Reserva.EstadoReserva.CANCELADA) || 
                reserva.getEstado().equals(Reserva.EstadoReserva.COMPLETADA)) {
                throw new RuntimeException("No se puede cancelar una reserva en ese estado");
            }
            
            reserva.setEstado(Reserva.EstadoReserva.CANCELADA);
            reserva.setFechaCancelacion(LocalDateTime.now());
            reserva.setMotivoCancelacion(motivo);
            reserva.getMesa().setEstado(Mesa.EstadoMesa.DISPONIBLE);
            return reservaRepository.save(reserva);
        }).orElseThrow(() -> new RuntimeException("Reserva no encontrada: " + id));
    }

    /**
     * Elimina una reserva
     */
    public void eliminar(Long id) {
        reservaRepository.deleteById(id);
    }

    /**
     * Valida que una mesa esté disponible en la fecha y hora especificada
     */
    private void validarDisponibilidad(Long mesaId, LocalDateTime fechaReserva) {
        LocalDateTime inicio = fechaReserva.minusHours(2);
        LocalDateTime fin = fechaReserva.plusHours(2);
        
        List<Reserva> conflictivas = reservaRepository.findReservasConflictivas(mesaId, inicio, fin);
        
        if (!conflictivas.isEmpty()) {
            throw new RuntimeException("La mesa no está disponible en esa fecha y hora");
        }
    }

    /**
     * Obtiene estadísticas de reservas
     */
    public long obtenerTotalReservas() {
        return reservaRepository.count();
    }

    /**
     * Obtiene el total de reservas activas
     */
    public long obtenerTotalReservasActivas() {
        return obtenerPorEstado(Reserva.EstadoReserva.CONFIRMADA).size() + 
               obtenerPorEstado(Reserva.EstadoReserva.ACTIVA).size();
    }
}
