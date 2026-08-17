package com.saborgourmet.service;

import com.saborgourmet.model.Mesa;
import com.saborgourmet.repository.MesaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Servicio para la gestión de mesas
 */
@Service
@RequiredArgsConstructor
@Transactional
public class MesaService {

    private final MesaRepository mesaRepository;

    /**
     * Obtiene todas las mesas
     */
    public List<Mesa> obtenerTodas() {
        return mesaRepository.findAll();
    }

    /**
     * Obtiene una mesa por ID
     */
    public Optional<Mesa> obtenerPorId(Long id) {
        return mesaRepository.findById(id);
    }

    /**
     * Obtiene mesas por estado
     */
    public List<Mesa> obtenerPorEstado(Mesa.EstadoMesa estado) {
        return mesaRepository.findByEstado(estado);
    }

    /**
     * Obtiene mesas disponibles con capacidad mínima
     */
    public List<Mesa> obtenerMesasDisponibles(Integer capacidad) {
        return mesaRepository.findAvailableMesasForCapacity(capacidad);
    }

    /**
     * Obtiene todas las mesas disponibles
     */
    public List<Mesa> obtenerMesasDisponibles() {
        return mesaRepository.findByEstado(Mesa.EstadoMesa.DISPONIBLE);
    }

    /**
     * Obtiene mesas con capacidad mayor o igual a la especificada
     */
    public List<Mesa> obtenerPorCapacidad(Integer capacidad) {
        return mesaRepository.findByCapacidadGreaterThanEqual(capacidad);
    }

    /**
     * Crea una nueva mesa
     */
    public Mesa crear(Mesa mesa) {
        mesa.setEstado(Mesa.EstadoMesa.DISPONIBLE);
        return mesaRepository.save(mesa);
    }

    /**
     * Actualiza una mesa existente
     */
    public Mesa actualizar(Long id, Mesa mesaActualizada) {
        return mesaRepository.findById(id).map(mesa -> {
            mesa.setNumeroMesa(mesaActualizada.getNumeroMesa());
            mesa.setCapacidad(mesaActualizada.getCapacidad());
            mesa.setUbicacion(mesaActualizada.getUbicacion());
            mesa.setEstado(mesaActualizada.getEstado());
            return mesaRepository.save(mesa);
        }).orElseThrow(() -> new RuntimeException("Mesa no encontrada: " + id));
    }

    /**
     * Actualiza el estado de una mesa
     */
    public Mesa actualizarEstado(Long id, Mesa.EstadoMesa nuevoEstado) {
        return mesaRepository.findById(id).map(mesa -> {
            mesa.setEstado(nuevoEstado);
            return mesaRepository.save(mesa);
        }).orElseThrow(() -> new RuntimeException("Mesa no encontrada: " + id));
    }

    /**
     * Elimina una mesa
     */
    public void eliminar(Long id) {
        mesaRepository.deleteById(id);
    }

    /**
     * Verifica si una mesa existe
     */
    public boolean existe(Long id) {
        return mesaRepository.existsById(id);
    }
}
