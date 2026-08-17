package com.saborgourmet.service;

import com.saborgourmet.model.Cliente;
import com.saborgourmet.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Servicio para la gestión de clientes
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ClienteService {

    private final ClienteRepository clienteRepository;

    /**
     * Obtiene todos los clientes
     */
    public List<Cliente> obtenerTodos() {
        return clienteRepository.findAll();
    }

    /**
     * Obtiene un cliente por ID
     */
    public Optional<Cliente> obtenerPorId(Long id) {
        return clienteRepository.findById(id);
    }

    /**
     * Obtiene un cliente por email
     */
    public Optional<Cliente> obtenerPorEmail(String email) {
        return clienteRepository.findByEmail(email);
    }

    /**
     * Busca clientes por nombre (búsqueda parcial)
     */
    public List<Cliente> buscarPorNombre(String nombre) {
        return clienteRepository.findByNombreContainingIgnoreCase(nombre);
    }

    /**
     * Crea un nuevo cliente
     */
    public Cliente crear(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    /**
     * Actualiza un cliente existente
     */
    public Cliente actualizar(Long id, Cliente clienteActualizado) {
        return clienteRepository.findById(id).map(cliente -> {
            cliente.setNombre(clienteActualizado.getNombre());
            cliente.setEmail(clienteActualizado.getEmail());
            cliente.setTelefono(clienteActualizado.getTelefono());
            cliente.setNotas(clienteActualizado.getNotas());
            return clienteRepository.save(cliente);
        }).orElseThrow(() -> new RuntimeException("Cliente no encontrado: " + id));
    }

    /**
     * Elimina un cliente
     */
    public void eliminar(Long id) {
        clienteRepository.deleteById(id);
    }

    /**
     * Verifica si un email ya existe (para validar duplicados)
     */
    public boolean emailExiste(String email) {
        return clienteRepository.findByEmail(email).isPresent();
    }

    /**
     * Verifica si un email ya existe (excluyendo un ID específico)
     */
    public boolean emailExisteParaOtroCliente(String email, Long idExcluir) {
        Optional<Cliente> cliente = clienteRepository.findByEmail(email);
        return cliente.isPresent() && !cliente.get().getId().equals(idExcluir);
    }
}
