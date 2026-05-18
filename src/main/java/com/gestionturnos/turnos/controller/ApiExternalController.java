package com.gestionturnos.turnos.controller;

import com.gestionturnos.turnos.service.ApiConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/externa")
public class ApiExternalController {

    @Autowired
    private ApiConsumerService apiConsumerService;

    // Endpoint existente de prueba
    @GetMapping("/usuarios")
    public String obtenerUsuariosExternos() {
        return apiConsumerService.buscarEnfermedades("diabetes"); // ejemplo fijo
    }

    // Nuevo endpoint dinámico
    @GetMapping("/enfermedades")
    public String buscarEnfermedades(@RequestParam String termino) {
        return apiConsumerService.buscarEnfermedades(termino);
    }
}
