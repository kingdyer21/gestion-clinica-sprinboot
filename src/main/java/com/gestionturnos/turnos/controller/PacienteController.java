package com.gestionturnos.turnos.controller;

import com.gestionturnos.turnos.model.Paciente;
import com.gestionturnos.turnos.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
@CrossOrigin(origins = "*") // Para evitar problemas con CORS si usas frontend
public class PacienteController {

    @Autowired
    private PacienteRepository pacienteRepository;

    @GetMapping
    public List<Paciente> getAllPacientes() {
        return pacienteRepository.findAll();
    }

    @PostMapping
    public Paciente createPaciente(@RequestBody Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    @GetMapping("/{id}")
    public Paciente getPacienteById(@PathVariable Long id) {
        return pacienteRepository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public Paciente updatePaciente(@PathVariable Long id, @RequestBody Paciente paciente) {
        Paciente existing = pacienteRepository.findById(id).orElse(null);
        if (existing != null) {
            existing.setNombre(paciente.getNombre());
            existing.setIdentificacion(paciente.getIdentificacion());
            existing.setTelefono(paciente.getTelefono());
            return pacienteRepository.save(existing);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deletePaciente(@PathVariable Long id) {
        pacienteRepository.deleteById(id);
    }
}
