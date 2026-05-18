package com.gestionturnos.turnos.controller;

import com.gestionturnos.turnos.model.Turno;
import com.gestionturnos.turnos.model.Paciente; // Importar la entidad Paciente
import com.gestionturnos.turnos.model.Medico;   // Importar la entidad Medico
import com.gestionturnos.turnos.repository.TurnoRepository;
import com.gestionturnos.turnos.repository.PacienteRepository;
import com.gestionturnos.turnos.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus; // Para manejar respuestas HTTP
import org.springframework.http.ResponseEntity; // Para manejar respuestas HTTP, útil para POST/PUT
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException; // Para lanzar excepciones con estado HTTP

import java.time.LocalDate; // Para el tipo de dato LocalDate
import java.time.LocalTime; // Para el tipo de dato LocalTime
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/turnos")
@CrossOrigin(origins = "*") // Es importante que esta anotación esté presente
public class TurnoController {

    @Autowired
    private TurnoRepository turnoRepository;

    @Autowired
    private PacienteRepository pacienteRepository; // Necesario para buscar Pacientes por ID

    @Autowired
    private MedicoRepository medicoRepository;     // Necesario para buscar Médicos por ID

    // La clase DTO (Data Transfer Object) interna para recibir los datos del frontend
    // El frontend envía 'pacienteId' y 'medicoId', no los objetos completos.
    static class TurnoRequest {
        public LocalDate fecha;
        public LocalTime hora;
        public Long pacienteId; // Este campo recibirá el ID del paciente del frontend
        public Long medicoId;   // Este campo recibirá el ID del médico del frontend

        // Puedes añadir getters y setters si lo prefieres, pero para @RequestBody Jackson no los necesita si los campos son públicos
        // Para mayor robustez, se recomienda añadir getters y setters y hacer los campos private.
        // public LocalDate getFecha() { return fecha; }
        // public void setFecha(LocalDate fecha) { this.fecha = fecha; }
        // ... y así para todos los campos.
    }

    @GetMapping
    public List<Turno> getAllTurnos() {
        return turnoRepository.findAll();
    }

    @PostMapping // Maneja la creación de un nuevo turno
    public ResponseEntity<Turno> createTurno(@RequestBody TurnoRequest turnoRequest) {
        // 1. Buscar Paciente por ID
        Paciente paciente = pacienteRepository.findById(turnoRequest.pacienteId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente no encontrado con ID: " + turnoRequest.pacienteId));

        // 2. Buscar Médico por ID
        Medico medico = medicoRepository.findById(turnoRequest.medicoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Médico no encontrado con ID: " + turnoRequest.medicoId));

        // 3. Crear la entidad Turno y asignar los objetos Paciente y Medico
        Turno nuevoTurno = new Turno();
        nuevoTurno.setFecha(turnoRequest.fecha);
        nuevoTurno.setHora(turnoRequest.hora);
        nuevoTurno.setPaciente(paciente); // Asignar el objeto Paciente encontrado
        nuevoTurno.setMedico(medico);     // Asignar el objeto Medico encontrado

        // 4. Guardar el Turno en la base de datos
        Turno savedTurno = turnoRepository.save(nuevoTurno);

        // Devolver una respuesta HTTP 201 (Created) con el turno guardado
        return new ResponseEntity<>(savedTurno, HttpStatus.CREATED);
    }

    @PutMapping("/{id}") // Maneja la actualización de un turno existente
    public ResponseEntity<Turno> updateTurno(@PathVariable Long id, @RequestBody TurnoRequest turnoRequest) {
        // 1. Buscar el turno existente por ID
        Optional<Turno> optionalTurno = turnoRepository.findById(id);

        if (optionalTurno.isPresent()) {
            Turno turnoExistente = optionalTurno.get();

            // 2. Buscar Paciente por ID (puede que haya cambiado o sea el mismo)
            Paciente paciente = pacienteRepository.findById(turnoRequest.pacienteId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente no encontrado con ID: " + turnoRequest.pacienteId));

            // 3. Buscar Médico por ID (puede que haya cambiado o sea el mismo)
            Medico medico = medicoRepository.findById(turnoRequest.medicoId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Médico no encontrado con ID: " + turnoRequest.medicoId));

            // 4. Actualizar los campos del turno existente
            turnoExistente.setFecha(turnoRequest.fecha);
            turnoExistente.setHora(turnoRequest.hora);
            turnoExistente.setPaciente(paciente); // Asignar el objeto Paciente encontrado
            turnoExistente.setMedico(medico);     // Asignar el objeto Medico encontrado

            // 5. Guardar el turno actualizado en la base de datos
            Turno updatedTurno = turnoRepository.save(turnoExistente);

            // Devolver una respuesta HTTP 200 (OK) con el turno actualizado
            return new ResponseEntity<>(updatedTurno, HttpStatus.OK);
        } else {
            // Si el turno no se encuentra, lanzar una excepción 404
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Turno no encontrado con ID: " + id);
        }
    }

    @DeleteMapping("/{id}") // Maneja la eliminación de un turno
    @ResponseStatus(HttpStatus.NO_CONTENT) // Devuelve un 204 No Content si la eliminación es exitosa
    public void deleteTurno(@PathVariable Long id) {
        turnoRepository.deleteById(id);
    }
}