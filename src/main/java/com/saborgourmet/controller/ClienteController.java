package com.saborgourmet.controller;

import com.saborgourmet.model.Cliente;
import com.saborgourmet.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

/**
 * Controlador para la gestión de clientes
 */
@Controller
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    /**
     * Muestra el listado de clientes
     */
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("clientes", clienteService.obtenerTodos());
        return "clientes/listar";
    }

    /**
     * Muestra el formulario para crear un nuevo cliente
     */
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "clientes/formulario";
    }

    /**
     * Guarda un nuevo cliente
     */
    @PostMapping
    public String guardar(@Valid @ModelAttribute Cliente cliente, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "clientes/formulario";
        }
        
        // Validar que el email no exista
        if (clienteService.emailExiste(cliente.getEmail())) {
            result.rejectValue("email", "error.email", "El email ya está registrado");
            return "clientes/formulario";
        }
        
        clienteService.crear(cliente);
        return "redirect:/clientes";
    }

    /**
     * Muestra el formulario para editar un cliente
     */
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Cliente cliente = clienteService.obtenerPorId(id)
            .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        model.addAttribute("cliente", cliente);
        return "clientes/formulario";
    }

    /**
     * Actualiza un cliente
     */
    @PostMapping("/{id}")
    public String actualizar(@PathVariable Long id, 
                            @Valid @ModelAttribute Cliente cliente, 
                            BindingResult result) {
        if (result.hasErrors()) {
            return "clientes/formulario";
        }
        
        // Validar que el email no exista (excepto para el cliente actual)
        if (clienteService.emailExisteParaOtroCliente(cliente.getEmail(), id)) {
            result.rejectValue("email", "error.email", "El email ya está registrado");
            return "clientes/formulario";
        }
        
        clienteService.actualizar(id, cliente);
        return "redirect:/clientes";
    }

    /**
     * Elimina un cliente
     */
    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id) {
        clienteService.eliminar(id);
        return "redirect:/clientes";
    }

    /**
     * Busca clientes por nombre
     */
    @GetMapping("/buscar")
    public String buscar(@RequestParam String nombre, Model model) {
        model.addAttribute("clientes", clienteService.buscarPorNombre(nombre));
        model.addAttribute("busqueda", nombre);
        return "clientes/listar";
    }

    /**
     * Muestra los detalles de un cliente
     */
    @GetMapping("/{id}")
    public String ver(@PathVariable Long id, Model model) {
        Cliente cliente = clienteService.obtenerPorId(id)
            .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        model.addAttribute("cliente", cliente);
        return "clientes/detalle";
    }
}
