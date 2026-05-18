package com.gestionturnos.turnos.repository;

import com.gestionturnos.turnos.model.Turno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TurnoRepository extends JpaRepository<Turno, Long> {
}
