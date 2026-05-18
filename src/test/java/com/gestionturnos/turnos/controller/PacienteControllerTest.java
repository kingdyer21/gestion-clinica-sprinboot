package com.gestionturnos.turnos.controller;

import com.gestionturnos.turnos.model.Paciente;
import com.gestionturnos.turnos.repository.PacienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PacienteControllerTest {

    @Mock
    private PacienteRepository pacienteRepository;

    @InjectMocks
    private PacienteController pacienteController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllPacientes() {
        Paciente p = new Paciente("Juan Perez", "12345", "3001234567");
        when(pacienteRepository.findAll()).thenReturn(Arrays.asList(p));

        var lista = pacienteController.getAllPacientes();

        assertNotNull(lista);
        assertEquals(1, lista.size());
        assertEquals("Juan Perez", lista.get(0).getNombre());
        verify(pacienteRepository, times(1)).findAll();
    }

    @Test
    void testCreatePaciente() {
        Paciente p = new Paciente("Ana Gomez", "67890", "3109876543");
        when(pacienteRepository.save(any(Paciente.class))).thenReturn(p);

        Paciente creado = pacienteController.createPaciente(p);

        assertNotNull(creado);
        assertEquals("Ana Gomez", creado.getNombre());
        assertEquals("67890", creado.getIdentificacion());
        assertEquals("3109876543", creado.getTelefono());
        verify(pacienteRepository, times(1)).save(any(Paciente.class));
    }

    @Test
    void testGetPacienteById() {
        Paciente p = new Paciente("Luis", "55555", "3005555555");
        p.setId(1L);
        when(pacienteRepository.findById(1L)).thenReturn(Optional.of(p));

        Paciente resultado = pacienteController.getPacienteById(1L);

        assertNotNull(resultado);
        assertEquals("Luis", resultado.getNombre());
        assertEquals("55555", resultado.getIdentificacion());
        verify(pacienteRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdatePaciente() {
        Paciente existente = new Paciente("Maria", "111", "3034445566");
        existente.setId(1L);

        // cuando se guarda, devolvemos el objeto que se pasa
        when(pacienteRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(pacienteRepository.save(any(Paciente.class))).thenAnswer(inv -> inv.getArgument(0));

        Paciente cambios = new Paciente("Maria Actualizada", "111", "3039990000");

        Paciente actualizado = pacienteController.updatePaciente(1L, cambios);

        assertNotNull(actualizado);
        assertEquals("Maria Actualizada", actualizado.getNombre());
        assertEquals("3039990000", actualizado.getTelefono());
        verify(pacienteRepository, times(1)).findById(1L);
        verify(pacienteRepository, times(1)).save(any(Paciente.class));
    }

    @Test
    void testDeletePaciente() {
        doNothing().when(pacienteRepository).deleteById(1L);

        pacienteController.deletePaciente(1L);

        verify(pacienteRepository, times(1)).deleteById(1L);
    }
}
