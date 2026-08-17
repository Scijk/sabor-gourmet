package com.saborgourmet.controller;

import com.saborgourmet.model.Reserva;
import com.saborgourmet.service.ClienteService;
import com.saborgourmet.service.MesaService;
import com.saborgourmet.service.ReservaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Controlador para la gestión de reservas
 */
@Controller
@RequestMapping("/reservas")
@RequiredArgsConstructor
public class ReservaController {

    private final ReservaService reservaService;
    private final ClienteService clienteService;
    private final MesaService mesaService;

    /**
     * Muestra el listado de reservas
     */
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("reservas", reservaService.obtenerTodas());
        model.addAttribute("estados", Reserva.EstadoReserva.values());
        model.addAttribute("totalReservas", reservaService.obtenerTotalReservas());
        model.addAttribute("reservasActivas", reservaService.obtenerTotalReservasActivas());
        return "reservas/listar";
    }

    /**
     * Muestra el formulario para crear una nueva reserva
     */
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("clientes", clienteService.obtenerTodos());
        model.addAttribute("mesas", mesaService.obtenerMesasDisponibles());
        return "reservas/formulario";
    }

    /**
     * Guarda una nueva reserva
     */
    @PostMapping
    public String guardar(@RequestParam Long clienteId,
                         @RequestParam Long mesaId,
                         @RequestParam String fechaReserva,
                         @RequestParam Integer numeroComensales,
                         @RequestParam(required = false) String observaciones,
                         Model model) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            LocalDateTime fecha = LocalDateTime.parse(fechaReserva, formatter);
            
            reservaService.crear(clienteId, mesaId, fecha, numeroComensales, observaciones);
            return "redirect:/reservas";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("clientes", clienteService.obtenerTodos());
            model.addAttribute("mesas", mesaService.obtenerMesasDisponibles());
            return "reservas/formulario";
        }
    }

    /**
     * Muestra el formulario para editar una reserva
     */
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Reserva reserva = reservaService.obtenerPorId(id)
            .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
        model.addAttribute("reserva", reserva);
        model.addAttribute("clientes", clienteService.obtenerTodos());
        model.addAttribute("mesas", mesaService.obtenerTodas());
        return "reservas/editar";
    }

    /**
     * Actualiza una reserva
     */
    @PostMapping("/{id}")
    public String actualizar(@PathVariable Long id,
                            @RequestParam String fechaReserva,
                            @RequestParam Integer numeroComensales,
                            @RequestParam(required = false) String observaciones,
                            Model model) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            LocalDateTime fecha = LocalDateTime.parse(fechaReserva, formatter);
            
            reservaService.actualizar(id, fecha, numeroComensales, observaciones);
            return "redirect:/reservas";
        } catch (Exception e) {
            Reserva reserva = reservaService.obtenerPorId(id).orElseThrow();
            model.addAttribute("reserva", reserva);
            model.addAttribute("error", e.getMessage());
            model.addAttribute("clientes", clienteService.obtenerTodos());
            model.addAttribute("mesas", mesaService.obtenerTodas());
            return "reservas/editar";
        }
    }

    /**
     * Confirma una reserva
     */
    @PostMapping("/{id}/confirmar")
    public String confirmar(@PathVariable Long id) {
        reservaService.confirmar(id);
        return "redirect:/reservas";
    }

    /**
     * Marca una reserva como activa (cliente llegó)
     */
    @PostMapping("/{id}/activar")
    public String activar(@PathVariable Long id) {
        reservaService.marcarActiva(id);
        return "redirect:/reservas";
    }

    /**
     * Completa una reserva
     */
    @PostMapping("/{id}/completar")
    public String completar(@PathVariable Long id) {
        reservaService.completar(id);
        return "redirect:/reservas";
    }

    /**
     * Cancela una reserva
     */
    @PostMapping("/{id}/cancelar")
    public String cancelar(@PathVariable Long id, @RequestParam(required = false) String motivo) {
        reservaService.cancelar(id, motivo != null ? motivo : "Cancelada por el usuario");
        return "redirect:/reservas";
    }

    /**
     * Elimina una reserva
     */
    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id) {
        reservaService.eliminar(id);
        return "redirect:/reservas";
    }

    /**
     * Muestra los detalles de una reserva
     */
    @GetMapping("/{id}")
    public String ver(@PathVariable Long id, Model model) {
        Reserva reserva = reservaService.obtenerPorId(id)
            .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
        model.addAttribute("reserva", reserva);
        return "reservas/detalle";
    }

    /**
     * Filtra reservas por estado
     */
    @GetMapping("/filtro")
    public String filtrarPorEstado(@RequestParam Reserva.EstadoReserva estado, Model model) {
        model.addAttribute("reservas", reservaService.obtenerPorEstado(estado));
        model.addAttribute("estadoSeleccionado", estado);
        model.addAttribute("estados", Reserva.EstadoReserva.values());
        return "reservas/listar";
    }

    /**
     * Panel de administración de reservas
     */
    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("reservasPendientes", reservaService.obtenerReservasPendientes());
        model.addAttribute("reservasActivas", reservaService.obtenerPorEstado(Reserva.EstadoReserva.ACTIVA));
        model.addAttribute("reservasConfirmadas", reservaService.obtenerPorEstado(Reserva.EstadoReserva.CONFIRMADA));
        return "reservas/admin";
    }
}
