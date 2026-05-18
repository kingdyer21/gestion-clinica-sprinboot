package com.gestionturnos.turnos.repository;

import com.gestionturnos.turnos.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
}