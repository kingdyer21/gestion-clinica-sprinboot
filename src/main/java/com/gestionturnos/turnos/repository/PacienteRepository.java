package com.gestionturnos.turnos.repository;

import com.gestionturnos.turnos.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
}