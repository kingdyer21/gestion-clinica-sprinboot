package com.gestionturnos.turnos.controller;

import com.gestionturnos.turnos.model.Medico;
import com.gestionturnos.turnos.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicos")
@CrossOrigin(origins = "*")
public class MedicoController {

    @Autowired
    private MedicoRepository medicoRepository;

    @GetMapping
    public List<Medico> obtenerMedicos() {
        return medicoRepository.findAll();
    }

    @GetMapping("/{id}")
    public Medico obtenerPorId(@PathVariable Long id) {
        return medicoRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Medico crearMedico(@RequestBody Medico medico) {
        return medicoRepository.save(medico);
    }

    @PutMapping("/{id}")
    public Medico actualizarMedico(@PathVariable Long id, @RequestBody Medico datosActualizados) {
        Medico medico = medicoRepository.findById(id).orElse(null);
        if (medico != null) {
            medico.setNombre(datosActualizados.getNombre());
            medico.setEspecialidad(datosActualizados.getEspecialidad());
            medico.setTelefono(datosActualizados.getTelefono());
            return medicoRepository.save(medico);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void eliminarMedico(@PathVariable Long id) {
        medicoRepository.deleteById(id);
    }
}
