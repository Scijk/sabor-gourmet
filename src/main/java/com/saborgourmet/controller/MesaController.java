package com.saborgourmet.controller;

import com.saborgourmet.model.Mesa;
import com.saborgourmet.service.MesaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

/**
 * Controlador para la gestión de mesas
 */
@Controller
@RequestMapping("/mesas")
@RequiredArgsConstructor
public class MesaController {

    private final MesaService mesaService;

    /**
     * Muestra el listado de mesas
     */
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("mesas", mesaService.obtenerTodas());
        model.addAttribute("estados", Mesa.EstadoMesa.values());
        return "mesas/listar";
    }

    /**
     * Muestra el formulario para crear una nueva mesa
     */
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("mesa", new Mesa());
        model.addAttribute("estados", Mesa.EstadoMesa.values());
        return "mesas/formulario";
    }

    /**
     * Guarda una nueva mesa
     */
    @PostMapping
    public String guardar(@Valid @ModelAttribute Mesa mesa, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("estados", Mesa.EstadoMesa.values());
            return "mesas/formulario";
        }
        
        mesaService.crear(mesa);
        return "redirect:/mesas";
    }

    /**
     * Muestra el formulario para editar una mesa
     */
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Mesa mesa = mesaService.obtenerPorId(id)
            .orElseThrow(() -> new RuntimeException("Mesa no encontrada"));
        model.addAttribute("mesa", mesa);
        model.addAttribute("estados", Mesa.EstadoMesa.values());
        return "mesas/formulario";
    }

    /**
     * Actualiza una mesa
     */
    @PostMapping("/{id}")
    public String actualizar(@PathVariable Long id, 
                            @Valid @ModelAttribute Mesa mesa, 
                            BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("estados", Mesa.EstadoMesa.values());
            return "mesas/formulario";
        }
        
        mesaService.actualizar(id, mesa);
        return "redirect:/mesas";
    }

    /**
     * Elimina una mesa
     */
    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id) {
        mesaService.eliminar(id);
        return "redirect:/mesas";
    }

    /**
     * Muestra las mesas disponibles
     */
    @GetMapping("/disponibles")
    public String disponibles(Model model) {
        model.addAttribute("mesas", mesaService.obtenerMesasDisponibles());
        return "mesas/disponibles";
    }

    /**
     * Busca mesas por capacidad
     */
    @GetMapping("/buscar")
    public String buscarPorCapacidad(@RequestParam Integer capacidad, Model model) {
        model.addAttribute("mesas", mesaService.obtenerMesasDisponibles(capacidad));
        model.addAttribute("capacidad", capacidad);
        return "mesas/disponibles";
    }

    /**
     * Muestra los detalles de una mesa
     */
    @GetMapping("/{id}")
    public String ver(@PathVariable Long id, Model model) {
        Mesa mesa = mesaService.obtenerPorId(id)
            .orElseThrow(() -> new RuntimeException("Mesa no encontrada"));
        model.addAttribute("mesa", mesa);
        return "mesas/detalle";
    }

    /**
     * Cambia el estado de una mesa
     */
    @PostMapping("/{id}/estado")
    public String cambiarEstado(@PathVariable Long id, @RequestParam Mesa.EstadoMesa estado) {
        mesaService.actualizarEstado(id, estado);
        return "redirect:/mesas/" + id;
    }
}
