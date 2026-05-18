package com.gestionturnos.turnos.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

class ApiAuthControllerTest {

    private final ApiAuthController controller = new ApiAuthController();

    @Test
    void loginDebeRetornarAutenticacionExitosa() {
        ApiAuthController.LoginRequest request = new ApiAuthController.LoginRequest();
        request.setUsername("usuario");
        request.setPassword("clave");

        ResponseEntity<?> response = controller.apiLogin(request);

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo("Auteticación exitosa");
    }
}

