package com.saborgourmet.controller;

import com.saborgourmet.service.ClienteService;
import com.saborgourmet.service.MesaService;
import com.saborgourmet.service.ReservaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controlador para la página de inicio y dashboard
 */
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ReservaService reservaService;
    private final ClienteService clienteService;
    private final MesaService mesaService;

    /**
     * Página de inicio / Dashboard
     */
    @GetMapping({"/", "/index", "/home", "/dashboard"})
    public String dashboard(Model model) {
        // Estadísticas generales
        model.addAttribute("totalReservas", reservaService.obtenerTotalReservas());
        model.addAttribute("reservasActivas", reservaService.obtenerTotalReservasActivas());
        model.addAttribute("totalClientes", clienteService.obtenerTodos().size());
        model.addAttribute("totalMesas", mesaService.obtenerTodas().size());
        model.addAttribute("mesasDisponibles", mesaService.obtenerMesasDisponibles().size());
        
        // Información en tiempo real
        model.addAttribute("reservasPendientes", reservaService.obtenerReservasPendientes());
        
        return "index";
    }

    /**
     * Página de error
     */
    @GetMapping("/error")
    public String error() {
        return "error";
    }
}
