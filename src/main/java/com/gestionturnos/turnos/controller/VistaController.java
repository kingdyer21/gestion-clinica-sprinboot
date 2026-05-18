package com.gestionturnos.turnos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class VistaController {

    @GetMapping({"/", "/index"})
    public String mostrarIndex(Model model) {
        model.addAttribute("titulo", "Sistema de Gestión de Turnos");
        return "index";
    }

    @GetMapping("/login")
    public String mostrarLogin(
            @RequestParam(required = false) String error,
            @RequestParam(required = false) String logout,
            Model model
    ) {
        if (error != null) {
            model.addAttribute("error", "Credenciales incorrectas. Intente nuevamente.");
        }
        if (logout != null) {
            model.addAttribute("logout", "Ha cerrado sesión exitosamente.");
        }
        return "login";
    }
}